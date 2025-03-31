const { writeFileSync } = require('fs');
require('dotenv').config();

console.log('Running set-env.js');
console.log('Env variables:', {
  keycloak: process.env.YOUR_KEYCLOAK_URL,
  frontend: process.env.YOUR_FRONTEND_URL,
  api: process.env.YOUR_API_URL
});

const targetPath = './src/environments/environment.prod.ts';

const envConfigFile = `export const environment = {
  production: true,
  keycloakUrl: '${process.env.YOUR_KEYCLOAK_URL || "https://chatvault-keycloak.onrender.com"}',
  keycloakRealm: 'ChatVault',
  keycloakClientId: 'chatvault-app',
  appUrl: '${process.env.YOUR_FRONTEND_URL || "https://your-netlify-app.netlify.app"}',
  apiUrl: '${process.env.YOUR_API_URL || "https://chatvault-backend.onrender.com"}' 
}; `;

writeFileSync(targetPath, envConfigFile);
console.log(`Environment variables written to ${targetPath}`);
console.log('File content:', envConfigFile);