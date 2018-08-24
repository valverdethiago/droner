# Dronner
This repository contains all the components needed to run Dronner, an aplication designed to manage drones.

## Organization
This repository has four main folders:
 1. backend - All services needed to provide the backen api for the application
 2. frontend - Angularjs 4 application that provides the drones list
 3. docker - Docker file
 4. simulation
 
 ## Run
 
 ### Docker
 1. clone this repo using
 ```bash
 git clone git@github.com:valverdethiago/droner.git
cd droner/docker
```
2. Build docker image
 ```bash
docker build --no-cache -t 'droner' .
```
 3. Run docker container
 ```bash
docker run -p 8080:8080 droner
```
PS.: You can switch to another port that better fits your needs

### Build
For debug the application locally or even edit or add more features you may want to use your favorite IDE. 
1. Backend
    1. Build
    ```bash
    mvn clean install
    ```
    2. Run
    ```bash
    mvn spring-boot:run
    ```
 2. Frontend
    1. Install    
    ```bash
    npm install
    ```
    2. Run    
    ```bash
    npm start
    ```
    The application will start on localhost:4200

