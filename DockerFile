FROM quay.io/keycloak/keycloak:latest
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false
CMD ["start-dev", "--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true", "--hostname-strict=false"]