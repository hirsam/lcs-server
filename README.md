# LCS Server Deployment and Running Guide

## Deployment with Docker

### Prerequisites
- Docker installed on your system
- Access to a Docker repository (e.g., Docker Hub)

### Steps
1. **Build the JAR File**: Execute `mvn clean package` to build the JAR file.
2. **Build Docker Image**: Build the Docker image using `docker build -t lcs-server .`.
3. **Tag the Image**: Tag the Docker image with `docker tag lcs-server [username]/lcs-server:latest`.
4. **Push to Repository**: Push the tagged image with `docker push [username]/lcs-server:latest`.
5. **Pull Image on Server**: On your server, pull the Docker image with `docker pull hirsam/lcs-server:latest`.
6. **Run Container**: Start a Docker container with `docker run -d -p 8081:8081 --name lcs-server-container [username]/lcs-server:latest`.

## Running with JAR File

### Prerequisites
- Java Runtime Environment (JRE) installed on your system

### Steps
1. **Build the JAR File**: Execute `mvn clean package` to build the JAR file.
2. **Run the JAR File**: Navigate to the target directory and run the JAR file with `java -jar lcs-server.jar`.
3. **Access the Application**: Once the application is running, access it at http://localhost:8081.

## Notes
- Replace `[username]/lcs-server` with your Docker repository and image name.
- Make sure port 8081 is available before running the LCS Server.

