#!/bin/bash

# Define paths
SERVER_DIR="$HOME/{change_this_dir_to_project_dir}/bus-reservation-system/server"
CLIENT_DIR="$HOME/{change_this_dir_to_project_dir}/bus-reservation-system/client"
TOMCAT_WEBAPPS="$HOME/tomcat/webapps"
TOMCAT_BIN="$HOME/tomcat/bin"

# Step 1: Build both projects
echo "Building server..."
cd "$SERVER_DIR" || exit 1
mvn clean install || { echo "Server build failed"; exit 1; }

echo "Building client..."
cd "$CLIENT_DIR" || exit 1
mvn clean install || { echo "Client build failed"; exit 1; }

# Step 2: Deploy WAR to Tomcat
echo "Deploying WAR..."
rm -rf "$TOMCAT_WEBAPPS/bus-reservation-server" "$TOMCAT_WEBAPPS/bus-reservation-server.war"
cp "$SERVER_DIR/target/bus-reservation-server.war" "$TOMCAT_WEBAPPS/"

# Step 3: Restart Tomcat
echo "Restarting Tomcat..."
cd "$TOMCAT_BIN" || exit 1
./shutdown.sh
sleep 5
./startup.sh

# Step 4: Wait for server to start
echo "Waiting for server to start..."
sleep 5

# Step 5: Run client run all the tests
echo "Starting client..."
echo "#######################################"
cd "$CLIENT_DIR" || exit 1
java -cp target/classes org.example.bus.ClientRunner