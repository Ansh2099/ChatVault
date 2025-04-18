# Multi-stage build for optimized Keycloak (v23.0.7) on a 512 MB VPS
FROM quay.io/keycloak/keycloak:23.0.7 as builder

# Set database configuration for optimization
ENV KC_DB=postgres

# Build optimized Keycloak for faster startup
RUN /opt/keycloak/bin/kc.sh build

# Final image
FROM quay.io/keycloak/keycloak:23.0.7
COPY --from=builder /opt/keycloak/ /opt/keycloak/

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

# Limit Java heap to ~384 MB so it doesn't OOM on tiny instances
ENV JAVA_OPTS_APPEND="-Xmx384m"

# Expose the default Keycloak HTTP port
EXPOSE 8080

# Use production mode instead of dev mode for better stability
ENTRYPOINT ["/bin/sh", "-c", "/opt/keycloak/bin/kc.sh start \
  --spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true \
  --proxy=edge \
  --http-port=${PORT:-8080}"]