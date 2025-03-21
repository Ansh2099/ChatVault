FROM quay.io/keycloak/keycloak:23.0.7

# Set Keycloak admin credentials
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Set hostname settings
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

# Use Render's assigned PORT
ENV KC_HTTP_PORT=${PORT}

# Expose Render's expected port
EXPOSE ${PORT}

# Start Keycloak
CMD ["/opt/keycloak/bin/kc.sh", "start-dev",
     "--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true",
     "--proxy=edge",
     "--http-port=${PORT}"]
