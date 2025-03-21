FROM quay.io/keycloak/keycloak:23.0.7

# Set Keycloak admin credentials
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Set hostname settings
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

# Use Render's assigned PORT (Default: 8080)
ENV PORT=8080
EXPOSE ${PORT}

# Start Keycloak properly (all on one line)
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true", "--proxy=edge", "--http-port=8080"]