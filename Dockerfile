FROM quay.io/keycloak/keycloak:23.0.7

# Set Keycloak admin credentials
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Set hostname settings
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

# Create a script to start Keycloak with the correct port
RUN echo '#!/bin/bash' > /start-keycloak.sh && \
    echo '/opt/keycloak/bin/kc.sh start-dev --spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true --proxy=edge --http-port=$PORT' >> /start-keycloak.sh && \
    chmod +x /start-keycloak.sh

# Expose the port Render will use
EXPOSE 8080

# Use the script as entrypoint
ENTRYPOINT ["/start-keycloak.sh"]