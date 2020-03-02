

# Music App
>  This App will provide a music player interface to stream music and more. 

  
 
and 
## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

+ You will need an IDE, [Android Studio](https://developer.android.com/studio) is preferred.

## Screenshots
![Example screenshot](./img/screenshot.png)

## Technologies
* [Java](https://www.java.com/) - Language used

## Deployment

Music App is very easy to install and deploy in a Docker container.

Docker will expose port 3000, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd MusicApp
docker build -t MusicApp .
```
This will create the MusicApp image and pull in the necessary dependencies. Be sure to include '.' at the end of the Docker build command.

Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 3000 of the host to port 3000 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -p 3000:3000 MusicApp
```

Verify the deployment by navigating to your server address in your preferred browser.

```
http://localhost:3000
```


## Installing


## Features

To-do list:
* Add unit testing
 

## Status
Project is: _In Progress_ - We are finalizing the design & beckend architecture.

* ![status](https://img.shields.io/badge/Paxton's-Status-blue) : Working on UI of app
* ![nextsteps](https://img.shields.io/badge/Paxton's-Next%20Steps-brightgreen) :  Integrate Functionality
* ![status](https://img.shields.io/badge/Hollie's-Status-blue) : Searching for backend implementation
* ![nextsteps](https://img.shields.io/badge/Hollie's-Next%20Steps-brightgreen) : Working on UI
* ![status](https://img.shields.io/badge/Jason's-Status-blue) : Gathering information for backend framework
* ![nextsteps](https://img.shields.io/badge/Jason's-Next%20Steps-brightgreen) : Implementing backend 
* ![status](https://img.shields.io/badge/David's-Status-blue) : Working on front end for user friendly UI.
* ![nextsteps](https://img.shields.io/badge/David's-Next%20Steps-brightgreen) : Working on adding a feature to show music interface
                


## Documentations



## Inspiration
Project inspired by Jason Saldana

## Contact
Jason Saldana -- contact@txstate.edu
Hollie Wilson -- contact@txstate.edu
David Stevens -- contact@txstate.edu
Paxton Scott -- pas151@txstate.edu
