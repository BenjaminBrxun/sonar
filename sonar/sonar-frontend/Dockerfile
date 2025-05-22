### build environment ###
FROM node:18-alpine AS build

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
COPY --from=build /app/nginx /etc/nginx/conf.d
# Copy nginx configuration files to replace the default configuration
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Define the command to run your app
CMD ["nginx", "-g", "daemon off;"]