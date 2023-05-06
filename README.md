# CuisineGenie

## Project Description
https://github.com/cs0320-s2023/CuisineGenie.git 
Katherine Mao (kmao5), Kayla Mukai (kmukai), Lucy Nguyen (lnguye48), Jennifer Tran (jtran43)


CuisineGenie is a web application that helps people explore different cuisines! We take users’ preferences by having them take a quiz and select their top 5 most appealing dishes from a comprehensive list. We use our algorithm to find common themes or categories in their choices and recommend dishes from other cuisines/cultures that contain their favorite category. Our motivation behind this project stems from the fact that discovering new types of food may be difficult for people who do not have prior knowledge of various cuisines. We wanted to introduce users to new cuisines by analyzing their preferences and recommending recipes from different backgrounds that share their preferences.


We worked on majority of the project together, and it took us 30 hours.


### Design Choices
Our project has both front-end and back-end development. Our project relied heavily on the MealDB API for the recipes shown to users. In our frontend, we have users choose their top 5 dishes, then that data is sent to our backend. We designed our backend as an intermediate for our application and the API. Once the backend receives the data, it calls the API so we can convert the API results into Meals and MealProperties objects. We chose to use records to help us convert API results into objects that we can extract data from. 


###Errors or Bugs

We were unable to link the quiz results page to our generate list button without compromising the functionality of the results page. 

### Testing Suite

To run our tests, click the green play button in our TestBackend class.
AllSameCategory() – tests that 5 recipes from the same category return new recipes within that category.

AllDifferentCategories() – Tests that when there are 5 different categories, a list of reccomendations is still generated since the category will be randomly chosen.

OneCommonCategory() – tests that the only common category, chicken, will result in more chicken recipes. 

TieCategories() – tests that tied categories result in a list of recipes, since the category will be chosen randomly. 


### How to Build and Run Tests and Program

To run our program, clone our repository: https://github.com/cs0320-s2023/CuisineGenie.git 
Open the backend folder in IntelliJ to run the backend server. Run the server by clicking the green play button in the Server Class. Then, open the repository in VSCode. In VScode, type “npm run dev” in the terminal to start the frontend. On the quiz page, click your top 5 most appealing dishes, then click Generate List. Next, navigate to the results tab and click the Generate list button once more and wait for new recipes to generate! You can favorite recipes and click the titles to be redirected to its corresponding youtube tutorial. Once you are ready to get new recipes, go back to the quiz page to retake the quiz and regenerate the list.

### Collaboration with classmates

We drew inspiration for converting API Results into objects from sprint-3-abenjell-hmasamur. Thank you to CS32 class code, TAs, and students for all their help on this project!
