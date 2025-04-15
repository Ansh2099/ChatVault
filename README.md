# ChatVault

ChatVault is a real-time one-on-one chat application with secure authentication, real-time messaging via WebSockets, and modern UI.

[![Frontend](https://img.shields.io/badge/Frontend-Netlify-00C7B7)](https://chat-vault-app.netlify.app)
[![Backend](https://img.shields.io/badge/Backend-Render-46E3B7)](https://chatvault-backend.onrender.com)
[![API Docs](https://img.shields.io/badge/API-Swagger-85EA2D)](https://chatvault-backend.onrender.com/swagger-ui.html)

## Project Purpose

ChatVault was developed to create a secure, real-time messaging platform with modern web technologies. It demonstrates full-stack development skills including:
- Real-time communication with WebSockets
- Secure authentication with OAuth2/OpenID Connect
- RESTful API design
- Modern responsive UI development
- Containerization and cloud deployment

## Project Overview

This application consists of three main components:
- **Angular Frontend**: A responsive web UI for the chat application
- **Spring Boot Backend**: RESTful API and WebSocket server for chat functionality
- **Keycloak Auth Service**: Authentication and authorization service

## Features

- **Secure Authentication**: OAuth2/OpenID Connect via Keycloak
- **Real-time Messaging**: WebSocket communication for instant message delivery
- **File Sharing**: Support for image and file attachments using Cloudinary
- **Responsive UI**: Modern interface adapted for all device sizes

## Demo

### Live Demo
- [Frontend Application](https://chat-vault-app.netlify.app)
- [Backend API](https://chatvault-backend.onrender.com)
- [API Documentation](https://chatvault-backend.onrender.com/swagger-ui.html)

## Technology Stack

### Frontend
- Angular 19
- Bootstrap 5
- WebSocket integration with SockJS and StompJS
- Keycloak-js for authentication
- Emoji picker (@ctrl/ngx-emoji-mart)
- Rich text editor (Quill)

### Backend
- Spring Boot 3.4
- Spring WebSocket
- Spring Security with OAuth2 Resource Server
- Spring Data JPA
- PostgreSQL database
- Lombok
- OpenAPI documentation (SpringDoc)
- Cloudinary for file storage

### Authentication
- Keycloak 23.0.7
- OAuth2/OpenID Connect

## Deployment

The application is deployed across multiple platforms:

- **Frontend**: Hosted on [Netlify](https://chat-vault-app.netlify.app)
- **Backend API**: Hosted on [Render](https://chatvault-backend.onrender.com)
- **Keycloak Auth Service**: 
- **Database**: PostgreSQL on Render

## Setup Instructions

### Prerequisites
- Java 21
- Node.js 18+
- Docker and Docker Compose (for local deployment)
- PostgreSQL (for local development)

### Running Locally

#### Using Docker
```bash
# Start all services (Backend, Keycloak, and PostgreSQL)
docker-compose up
```

#### Manual Setup

1. **Backend**:
```bash
cd ChatVault_Backend
./mvnw spring-boot:run
```

2. **Frontend**:
```bash
cd ChatVault_Frontend
npm install
npm start
```

3. **Keycloak** (using Docker):
```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.7 start-dev
```

## Project Structure

### Frontend
The Angular frontend was adapted from an existing template with the following customizations:
- WebSocket integration for real-time messaging
- Keycloak authentication integration
- API integration with the Spring Boot backend
- UI enhancements and responsive design improvements

### Backend
The Spring Boot backend provides:
- REST API endpoints for user management and chat functionality
- WebSocket endpoints for real-time messaging
- Secure file uploads via Cloudinary
- OAuth2 resource server configuration for Keycloak integration

## Development

### Local Development Environment
1. Start Keycloak and set up a realm and client
2. Configure the backend to use the Keycloak instance
3. Configure the frontend environment variables
4. Run the backend and frontend applications

### Environment Variables

#### Backend
- `SPRING_DATASOURCE_URL`: PostgreSQL connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI`: Keycloak issuer URI
- `CLOUDINARY_URL`: Cloudinary credentials for file storage

#### Frontend
- `API_URL`: Backend API URL
- `KEYCLOAK_URL`: Keycloak server URL
- `KEYCLOAK_REALM`: Keycloak realm name
- `KEYCLOAK_CLIENT_ID`: Keycloak client ID

## Future Improvements
- Group chat functionality
- End-to-end encryption
- Push notifications
- Voice and video calling features
- Message search and filtering

## Credits
The Angular frontend was adapted from an existing WhatsApp-style UI template with significant customizations:
- WebSocket implementation for real-time messaging
- Keycloak authentication integration
- Backend API integration
- UI and UX improvements

## Contact
- [GitHub Profile](https://github.com/Ansh2099)
- [LinkedIn](https://linkedin.com/in/anshpreet-singh-03855a31a)

## License
[MIT License](LICENSE)
