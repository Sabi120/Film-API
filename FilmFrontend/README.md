# 🍿 Film Web Application 🍿


## Please Note 

 - To use the application the server in FilmAPI must be running in Eclipse.
 - You will need Node.js installed to run the NPM commands to run the application and install modules. To install Node.js visit: https://nodejs.org/en

## Features

### CRUD Functionality:
This application allows you to view, add, edit, delete and search for films from a database. This is done by sending and receiving responses to and from the FilmAPI. 

Newly added films will be located at the bottom of the table.

### Format Switching:
You can toggle the format in the dropdown located in the top right in JSON, XML and Text formats.
This allows you to perform the CRUD and search operations in the above formats.

### Search:
You can also perform search operations on the database by using the search box (located in the table header) by:
- Title
- Year
- ID
- All Fields

You can click the clear/`X` button to clear your input and reset the table state.

## How to Run ⚡️ 

 - If there is no `node_modules` folder present, navigate to the `FilmFrontend` folder in your terminal
 - Run `npm install` to install the required dependancies to run the application
 - In the IDE console, you can run `npm run start` in the `FilmFrontend` folder

If the application does not run, the port might still be in use. 
If so, exit the react console and run `npx kill-port 3000` and rerun `npm run start`

