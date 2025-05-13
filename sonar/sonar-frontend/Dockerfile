### build environment ###
FROM node:18-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./
# Install dependencies
RUN npm install

# Copy the rest of your application files
COPY . .
RUN npm run build



### production environment ###
FROM nginx:stable-alpine

# Copy built React files to NGINX web directory
COPY --from=builder /app/build /usr/share/nginx/html
# Copy nginx configuration files to replace the default configuration
COPY --from=builder /app/nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Define the command to run your app
CMD ["nginx", "-g", "daemon off;"]