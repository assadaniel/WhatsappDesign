# Web chat

**This project was made by Daniel Assa, Daniel Lifshitz, and Nitai Delgoshen.**

**Web app + android app**

## How to run the server:

First run:

### `cd ./server`

In the server directory, you can run the following:

### `npm install`    

and then if you use Windows run:

### `npm run windows`

or otherwise, if you use Linux or macOS run:

### `npm run linux`

### Make sure to have MongoDB installed on your computer and have a DB in the address of `mongodb://127.0.0.1:27017`.
This is because the server connects to the database at this address.

## How to open the web app:

Open in browser

### `http://localhost:5000`

This will run the chat app in the register window. 

## How to open the Android app:

Open the android studio app in the project directory.

Then press the play button.

### To get notifications you need to go to the settings app in the phone and turn notifications permission on for the app.


## Server insight
The server is built using `MVC`, and other folders such as `middleware` and `services`.  
The folder `middleware` is used to get the username out of the token sent to the server.  
The folder `services` is used to communicate against mongoDB and the database.
The folder `config` with the .env.local file contains the port numbers and IP addresses needed
### The public folder:

This folder holds the entire CSS and js of the client side.


## Notes
The `/chat` path is blocked, and is only reachable via login.



