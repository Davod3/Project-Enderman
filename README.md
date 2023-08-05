# The Enderman Project 
This project consists of a web application that allows the easier deployment and maintenance of modded Minecraft servers on linux operating systems.
At the moment only modpack downloads from curseforge are supported (mostly because i check the domain before starting the download, so more websites could easily be added).

# Requirements

## Backend Server:

Ubuntu 22.04 LTS Server or Desktop (should work on others but i didn't test it) with Java 17 and Screen installed.

## Frontend Client:

The build folder contains a react app. Any webserver in any environment should be able to serve it, but i tested it with Node.js 18.17.0 LTS and Serve (https://create-react-app.dev/docs/deployment/) 

# Execution

This "system" was designed mostly for myself, so the API address is hardcoded into the react app as localhost. That means both the frontend and the backend are supposed to run in the same machine.<br>

## To run the backend:

1 - Move the .jar file to an empty folder<br>
2 - Run with `java -jar enderman-*.*.*-prod.jar`

## To run the web client:

1 - Make sure you have Serve installed through NPM<br>
2 - Run with `serve -s build`

# Features

## Server creation

The user can create a server by inputting the server name, server port, and rcon port. The user can then provide a url to download the server files. Finally, the user can then select the start script for the server.

## Server maintenance

The user can start/stop the server with the click of a button and also create and manage backups. The user can also edit the server properties.

## Remote Console

The web client provides a remote console to execute commands directly on the server.

# Observations

This project was made to be used primarily by me and my friends on the headless VPS that we rent. We use it through an ssh tunnel, which means that we have quite a lot of security provided by a third party. While the api is secured with tokens and the web interface comes with a login system, this authentication systems are not properly tested and probably have a bunch of exploits. That goes to say that both the API and the web client are very insecure. So plz don't use them in insecure environments :)


