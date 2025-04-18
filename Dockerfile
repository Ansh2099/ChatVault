# Dockerfile for Keycloak (v23.0.7) on a 512 MB VPS
FROM quay.io/keycloak/keycloak:23.0.7

# Allow overriding these at deploy time (via Koyeb/Railway env vars)
ENV KEYCLOAK_ADMIN=${KEYCLOAK_ADMIN:-admin}
ENV KEYCLOAK_ADMIN_PASSWORD=${KEYCLOAK_ADMIN_PASSWORD:-admin}

# Database configuration
ENV KC_DB=postgres
ENV KC_DB_URL=${KC_DB_URL}
ENV KC_DB_USERNAME=${KC_DB_USERNAME:-postgres}
ENV KC_DB_PASSWORD=${KC_DB_PASSWORD}

# Hostname flags
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

# Limit Java heap to ~384 MB so it doesn’t OOM on tiny instances
ENV JAVA_OPTS_APPEND="-Xmx384m"

# Expose the default Keycloak HTTP port
EXPOSE 8080

# Use shell form so $PORT (or default 8080) is picked up by the host
ENTRYPOINT ["/bin/sh", "-c", "/opt/keycloak/bin/kc.sh start-dev \
  --spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true \
  --proxy=edge \
  --http-port=${PORT:-8080}"]