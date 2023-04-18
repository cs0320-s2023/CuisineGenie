# Echo

## Project Description

For this sprint, we integrated the front-end and back-end components we had previously been developing separately such as through Echo and Server. We created a REPL interface where users can type in their commands such as mode, load, view, and search into the input box, and then the output gets printed on the screen.

### Jennifer Tran (jtran43) & Kayla Mukai (kmukai)

We worked on majority of the project together, and it took us 15 hours.

## [Link to Repository](https://github.com/cs0320-s2023/sprint-4-jtran43-kmukai.git)

### Design Choices

Our project has both front-end and back-end development. The back-end development was taken from Server. The front-end was developed from partly the gearup code with the Echo functionality in mind along with the new .tsx files that use React calls to the API.

### Relationships between classes/interfaces

Our App.tsx class is our parent file. We have Header, HistoryBox, and InputBox components that can change and access states from the App class since we passed the states from the App class into the individual components. Our Header is just printing the REPL part of our screen, the HistoryBox maps the history state to print the history, and then the InputBox handles the inputted commands into the textbox. Then, we also have commands.tsx that holds our REPLFunctions that we can call using our inputs that we type into our input box component through referring to our hashmap of registered commands. Commands.tsx is where we have our new React functions that use the results from our call to the API and then return a string depending on what the message to be printed on our screen is. Then that is updated in history and then printed on the history screen.

### Data Structures & Runtime Optimizations

In our code, instead of having a large if or switch statement, we created a hashmap that we could registers our commands like mode, load, view, and search, so that they can be called on by command in input box and have the backend API server called from our commands.tsx file. Also, we used states throughout our project to set history, to check the mode to see if we should print the output in verbose or brief mode, and also to set the text in the textbox. Initially, we had the isVerbose state inside of the App.tsx and passed it to InputBox component. However, we decided to limit the scope of that state to just the InputBox component because it was never being used in the App, which helped with runtime.

###Errors or Bugs

There is a bug with our file directories and file paths. We ended up needing to use
the absolute file path to search and parse our CSV files. This was because when we used the content root path, our backend tests worked but our frontend program did not. When we used the repository root, our front end program was able to make calls to our server, but this would fail our backend tests.

### Testing Suite

We tested the Load Handlers, Search Handler, View Handler, Weather Handler, CSV Searcher, and CSV Parser (comments on tests are in the testing suites)
We used mocking to test our API calls. We also test our components to see if they update when they are rendered. Comments on these tests are in the testing suites

### How to Build and Run Tests and Program

To build our program, we first have to start the backend server. This can be done by navigating into our backend folder, then to src, then to main/java/e... then clicking on Server.java and pressing the play button to start the server. Then, we move to the frontend side of things. In a separate terminal, navigate to the folder that this sprint is stored in and then type in npm install once before everything and then npm run dev. Then after that you can press the 'o' key and then your browser of choice should open up with the REPL interface. From there you can enter in your commands like load (load filename), view (view), and search (search target hasHeader) with the proper number of arguments and get a resulting message. You can also toggle modes from brief to verbose by typing in mode brief/verbose.

### Collaboration with classmates

We drew inspiration for how to implement tasks 1-4 & 6 from the gear up and from parts of sprint-4-jkdai-kjiang32's repo. Majority of the code however was outlined in the gear up, but just figuring out how to piece it together was inspired by them. For testing, we drew inspiration from sprint-4-adufort1-lnguye48. Thank you to cs32 students and class code for the help!
