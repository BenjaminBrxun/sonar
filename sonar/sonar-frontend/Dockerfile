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


# Expose Vite/Vike default port
EXPOSE 3000

# Start the development server
CMD ["npm", "run", "dev"]