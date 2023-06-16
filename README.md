# Web chat

**This project was made by Daniel Assa, Daniel Lifshitz and Nitai Delgoshen.**

**In this project we created 3 screens, a login screen, a sign up screen and a chat screen.**

## How to run:

First run:

### `cd ./server`

In the server directory, you can run:
### `npm install`    

and then if you use windows run:

### `npm run windows`

or otherwise if you use linux or macOS run:

### `npm run linux`

and then open in browser

### `http://localhost:5000`

This will run the chat app in the register window.  
### Make sure to have mongoDB installed in your computer and have a DB in the address of `mongodb://127.0.0.1:27017`.
This is because the server to the database in this address.


# Files:
## React
The React files send requests to the server and update the information in the page.
### ```index.js```
This file calls for the App.js code.
### `App.js`
This file activates the other pages, and allows you to access the chat page only if you are logged in.

### The login_signup folder:

### `users.js`
This file holds all the credentials of each user.
### `login_signup.css`
This file holds the css of the signup and login pages.

### The login folder:

### `Login.js`
This file holds the code for the login page, The user needs to enter the username and the password of the user, If the information the user entered is in the `users.js` file it will let the user enter the account and load the chat page.

### The signup folder:

### `Signup.js`
This file holds the code for the signup page, The user needs to enter a username a password the shown name and a picture.
It then validates them and saves it in `users.js`.
### `InputFields.js`
This file is an input component in the `Signup.js` file.

### The css folder:

### `chat_main.css`
Holds the css information for the background of the chat page.
### `chat_user.css`
Holds the css information for the user list of the chat page.
### `chat_window.css`
Holds the css information for the chat in the chat page.

### The chat folder:

### `Chat.js`
This file holds the code for the chat page. In this page you can send messages for each user in your user list, to do this it uses the code in `Chat_window.js` and `message.js`.
It also allows you to add users using the code in `UserList.js`.
It also allows you to see the last message and the time it was sent in each user chat using the code in `User_preview.js`.
### `Chat_window.js`
returns the messages in each chat the user is currently viewing.
### `Message.js`
returns the message with the content given to it.
### `UserList.js`
returns the user list component using the code in `User_preview.js`.
### `User_preview.js`
returns the way each user looks in the user list component.
 

All the files for the web app are in the folder src.


### `App.js`
This file activates the other pages, and allows you to access the chat page only if you are logged in.
It also allows you to communicate in real-time with other users.

## Server
The server is build using `MVC`, and other folders such as `middleware` and `services`.  
The folder `middleware` is used to get the username out of the token sent to the server.  
The folder `services` is used to communicate against mongoDB and the database.
The folder `config` with the .env.local file contains the port numbers and ip adresses needed
### The public folder:

This folder holds the entire css and js of the client side.


## Notes
The `/chat` path is blocked, and is only reachable via login.



