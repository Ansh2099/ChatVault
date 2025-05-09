import {AfterViewChecked, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ChatListComponent} from '../../components/chat-list/chat-list.component';
import {KeycloakService} from '../../utils/keycloak/keycloak.service';
import {ChatResponse} from '../../services/models/chat-response';
import {MessageService} from '../../services/services/message.service';
import {MessageResponse} from '../../services/models/message-response';
import { Client, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {FormsModule} from '@angular/forms';
import {MessageRequest} from '../../services/models/message-request';
import {Notification} from './models/notification';
import {ChatService} from '../../services/services/chat.service';
import {PickerComponent} from '@ctrl/ngx-emoji-mart';
import {EmojiData} from '@ctrl/ngx-emoji-mart/ngx-emoji';
import { MessageComponent } from './components/message/message.component';
import { environment } from '../../../environments/environment.prod';

// Define the response interface
interface UploadMediaResponse {
  url: string;
}

@Component({
  selector: 'app-main',
  imports: [
    ChatListComponent,
    FormsModule,
    PickerComponent,
    MessageComponent
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit, OnDestroy, AfterViewChecked {

  selectedChat: ChatResponse = {};
  chats: Array<ChatResponse> = [];
  chatMessages: Array<MessageResponse> = [];
  socketClient: any = null;
  messageContent: string = '';
  showEmojis = false;
  @ViewChild('scrollableDiv') scrollableDiv!: ElementRef<HTMLDivElement>;
  private notificationSubscription: any;

  currentUserId: string = '';

  constructor(
    private chatService: ChatService,
    private messageService: MessageService,
    private keycloakService: KeycloakService,
  ) {
  }

  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }

  ngOnDestroy(): void {
    if (this.socketClient !== null) {
      this.socketClient.disconnect();
      if (this.notificationSubscription) {
        this.notificationSubscription.unsubscribe();
      }
      this.socketClient = null;
    }
  }

  ngOnInit(): void {
    console.log('Environment API URL:', environment.apiUrl);
    this.initWebSocket();
    this.getAllChats();
    this.currentUserId = this.keycloakService.userId || '';
  }

  chatSelected(chatResponse: ChatResponse) {
    this.selectedChat = chatResponse;
    this.getAllChatMessages(chatResponse.id as string);
    this.setMessagesToSeen();
    this.selectedChat.unreadCount = 0;
  }

  isSelfMessage(message: MessageResponse): boolean {
    return message.senderId === this.keycloakService.userId;
  }

  sendMessage() {
    if (this.messageContent) {
      const messageRequest: MessageRequest = {
        chatId: this.selectedChat.id,
        senderId: this.getSenderId(),
        receiverId: this.getReceiverId(),
        content: this.messageContent,
        type: 'TEXT',
      };
      this.messageService.saveMessage({
        body: messageRequest
      }).subscribe({
        next: () => {
          const message: MessageResponse = {
            senderId: this.getSenderId(),
            receiverId: this.getReceiverId(),
            content: this.messageContent,
            type: 'TEXT',
            state: 'SENT',
            createdAt: new Date().toString()
          };
          this.selectedChat.lastMessage = this.messageContent;
          this.chatMessages.push(message);
          this.messageContent = '';
          this.showEmojis = false;
        }
      });
    }
  }

  keyDown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.sendMessage();
    }
  }

  onSelectEmojis(emojiSelected: any) {
    const emoji: EmojiData = emojiSelected.emoji;
    this.messageContent += emoji.native;
  }

  onClick() {
    this.setMessagesToSeen();
  }

  uploadMedia(target: EventTarget | null) {
    const file = this.extractFileFromTarget(target);
    if (file !== null) {
      this.messageService.uploadMedia({
        'chat-id': this.selectedChat.id as string,
        body: {
          file: file
        }
      }).subscribe({
        next: (response: UploadMediaResponse) => {
          const message: MessageResponse = {
            senderId: this.getSenderId(),
            receiverId: this.getReceiverId(),
            content: 'Attachment',
            type: 'IMAGE',
            state: 'SENT',
            media: response.url,
            createdAt: new Date().toString(),
            mediaWidth: 300,
            mediaHeight: 300
          };
          this.chatMessages.push(message);
        }
      });
    }
  }

  logout() {
    this.keycloakService.logout();
  }

  userProfile() {
    this.keycloakService.accountManagement();
  }

  private setMessagesToSeen() {
    this.messageService.setMessageToSeen({
      'chat-id': this.selectedChat.id as string
    }).subscribe({
      next: () => {
      }
    });
  }

  private getAllChats() {
    this.chatService.getChatsByReceiver()
      .subscribe({
        next: (res) => {
          this.chats = res;
        }
      });
  }

  private getAllChatMessages(chatId: string) {
    this.messageService.getAllMessages({
      'chat-id': chatId
    }).subscribe({
      next: (messages) => {
        this.chatMessages = messages;
      }
    });
  }

  private initWebSocket() {
    if (this.keycloakService.keycloak.tokenParsed?.sub) {
      // Ensure we're using HTTPS if the page is served over HTTPS
      let secureApiUrl = environment.apiUrl;
      if (window.location.protocol === 'https:' && secureApiUrl.startsWith('http:')) {
        secureApiUrl = secureApiUrl.replace('http:', 'https:');
      }
      
      console.log('Connecting to WebSocket at:', `${secureApiUrl}/ws`);
      
      // Create a factory function for SockJS
      const factory = () => new SockJS(`${secureApiUrl}/ws`);
      
      // Use the factory with Stomp.over()
      this.socketClient = Stomp.over(factory);
      const subUrl = `/user/${this.keycloakService.keycloak.tokenParsed?.sub}/chat`;
      
      this.socketClient.connect({'Authorization': 'Bearer ' + this.keycloakService.keycloak.token},
        () => {
          console.log('WebSocket connection established');
          this.notificationSubscription = this.socketClient.subscribe(subUrl,
            (message: any) => {
              const notification: Notification = JSON.parse(message.body);
              this.handleNotification(notification);
            },
            (error: any) => {
              console.error('Error while connecting to WebSocket:', error);
            }
          );
        },
        (error: any) => {
          console.error('WebSocket connection error:', error);
          // Optionally implement reconnection logic here
        }
      );
    }
  }

  private handleNotification(notification: Notification) {
    if (!notification) return;
    if (this.selectedChat && this.selectedChat.id === notification.chatId) {
      switch (notification.type) {
        case 'MESSAGE':
        case 'IMAGE':
          const message: MessageResponse = {
            senderId: notification.senderId,
            receiverId: notification.receiverId,
            content: notification.content,
            type: notification.messageType,
            media: notification.media,
            createdAt: new Date().toString()
          };
          if (notification.type === 'IMAGE') {
            this.selectedChat.lastMessage = 'Attachment';
          } else {
            this.selectedChat.lastMessage = notification.content;
          }
          this.chatMessages.push(message);
          break;
        case 'SEEN':
          this.chatMessages.forEach(m => m.state = 'SEEN');
          break;
      }
    } else {
      const destChat = this.chats.find(c => c.id === notification.chatId);
      if (destChat && notification.type !== 'SEEN') {
        if (notification.type === 'MESSAGE') {
          destChat.lastMessage = notification.content;
        } else if (notification.type === 'IMAGE') {
          destChat.lastMessage = 'Attachment';
        }
        destChat.lastMessageTime = new Date().toString();
        destChat.unreadCount! += 1;
      } else if (notification.type === 'MESSAGE') {
        const newChat: ChatResponse = {
          id: notification.chatId,
          senderId: notification.senderId,
          receiverId: notification.receiverId,
          lastMessage: notification.content,
          name: notification.chatName,
          unreadCount: 1,
          lastMessageTime: new Date().toString()
        };
        this.chats.unshift(newChat);
      }
    }
  }

  private getSenderId(): string {
    if (this.selectedChat.senderId === this.keycloakService.userId) {
      return this.selectedChat.senderId as string;
    }
    return this.selectedChat.receiverId as string;
  }

  private getReceiverId(): string {
    if (this.selectedChat.senderId === this.keycloakService.userId) {
      return this.selectedChat.receiverId as string;
    }
    return this.selectedChat.senderId as string;
  }

  private scrollToBottom() {
    if (this.scrollableDiv) {
      const div = this.scrollableDiv.nativeElement;
      div.scrollTop = div.scrollHeight;
    }
  }

  private extractFileFromTarget(target: EventTarget | null): File | null {
    const htmlInputTarget = target as HTMLInputElement;
    if (target === null || htmlInputTarget.files === null) {
      return null;
    }
    return htmlInputTarget.files[0];
  }
}