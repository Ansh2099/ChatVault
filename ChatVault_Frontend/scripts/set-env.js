const { writeFileSync } = require('fs');
require('dotenv').config();

const targetPath = './src/environments/environment.prod.ts';

const envConfigFile = `export const environment = {
  production: true,
  keycloakUrl: '${process.env.KEYCLOAK_URL}',
  keycloakRealm: 'ChatVault',
  keycloakClientId: 'chatvault-app',
  appUrl: '${process.env.APP_URL}',
  apiUrl: '${process.env.API_URL}'
};
`;

writeFileSync(targetPath, envConfigFile);
console.log(`Environment variables written to ${targetPath}`);