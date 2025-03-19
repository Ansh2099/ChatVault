export interface MessageResponse {
    id?: number;
    content?: string;
    type?: 'TEXT' | 'IMAGE' | 'VIDEO' | 'AUDIO';
    state?: 'SENT' | 'SEEN';
    senderId?: string;
    receiverId?: string;
    createdAt?: string;
    media?: string; // Changed from Array<string> to string to store Cloudinary URL
    mediaWidth?: number;
    mediaHeight?: number;
} 