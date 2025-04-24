import { Component, Input } from "@angular/core";
import { MessageResponse } from "../../../../services/models";
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-message',
  template: `
    <div class="message" [ngClass]="{'sent': isSent, 'received': !isSent}">
      <ng-container [ngSwitch]="message.type">
        <div *ngSwitchCase="'IMAGE'" class="message-image-container">
          <img 
            [src]="message.media" 
            [alt]="'Image message'"
            class="chat-image"
            (click)="expandImage()"
          />
        </div>
        <div *ngSwitchDefault class="message-text">
          {{message.content}}
        </div>
      </ng-container>
      <!-- Read/Seen indicator for sent messages -->
      <div *ngIf="isSent && message.state === 'SEEN'" class="seen-indicator">
        ✔️ Seen
      </div>
    </div>
  `,
  styleUrls: ['./message.component.scss'],
  imports: [CommonModule]
})
export class MessageComponent {
  @Input() message!: MessageResponse;
  @Input() currentUserId!: string;

  get isSent(): boolean {
    return this.message.senderId === this.currentUserId;
  }

  expandImage(): void {
    if (this.message.type === 'IMAGE') {
      // Implement image expansion logic here
    }
  }
} 