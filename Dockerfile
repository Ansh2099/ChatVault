FROM quay.io/keycloak/keycloak:23.0.7

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true", "--hostname-strict=false", "--proxy=edge", "--http-port=8080", "--hostname=0.0.0.0"]