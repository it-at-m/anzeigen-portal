# For documentation see https://github.com/sclorg/nginx-container
FROM registry.access.redhat.com/ubi9/nginx-124:1-1730563934@sha256:04cf31e9a3874781ab2c0f3b0fa5963ca5beed1aa1a3d36d5896337bedee523f

# Copy built web application
COPY dist .

# Copy custom nginx configurations
COPY docker/nginx/*.conf "${NGINX_DEFAULT_CONF_PATH}"

# Start the web server
CMD nginx -g "daemon off;"