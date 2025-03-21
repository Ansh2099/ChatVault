FROM quay.io/keycloak/keycloak:23.0.7

# Set Keycloak admin credentials
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Set hostname settings
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

# Expose the correct port for Render
EXPOSE 8080

# Start Keycloak in development mode with required flags
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev",
  "--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true",
  "--proxy=edge",
  "--http-port=8080"
]