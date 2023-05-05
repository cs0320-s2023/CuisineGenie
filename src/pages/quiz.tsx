import {QuizBox} from "../components/QuizBox";
import QuizBoxOne from "../components/QuizBox";
import Navbar from "./navbar";
import "./quiz.css";
import { Link } from 'react-scroll';
import { eachMeal } from "../components/QuizBox";
import { useState } from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Results from "./results";

export  function Quiz() {

// //   /**
// //    * To Do-List
// //    * - put everything in a container 
// //    * - create two seperate functions where one is displayind data and gettting data
// //    * - connect kayla section with mine 
// //    * - data: name, id, ingredients, 
// //    */
  
  return (
    <div>
      <Navbar />
      <div className="quiz-body container-fluid no-gutters" style={{ margin: "0", padding: "0" }}>
  
        <div className = " qheader-img-container1 ">
            <img className="qheader-img-1" src="src/images/quiz-purple.png" alt = "image with the start quiz button"></img>
            <div className = "overlay">
              <div className = "qheader-text-container">
                <h2 className = "qheader-text">Find out what foods you will enjoy!</h2>
              </div>
                 
                <button className = "qheader-button">
                  <Link 
                  activeClass="active"
                  to= "qheader-buttonid"
                  spy= {true}
                  smooth = {true}
                  offset = {-10}
                  duration = {500}>
                  Take the quiz!
                  </Link>
                  </button>


            </div>

          </div>

          <div className = " qheader-img-container2">
            <img className="qheader-img-2" src="src/images/header-food-img.png" alt = "image that shows the different kinds of foods that the application can recommend you"></img>
          </div>
        
      </div>

      <div className = "quiz-subtitle-container">
        <h5 className = "quiz-subtitle">answer for handcrafted recommendations</h5>
        <div className = "quiz-subtitle-rectangle mb-4"></div>
      </div>


      <div className = "question-main-container">
        <h1 className = "question-main mb-5">Choose five top meals! </h1> 
      
       
      </div>

  

    <section id = "qheader-buttonid">
    <div className = "responses-image-grid">
      <div className = "responses-image-container container-fluid">

  
      <QuizBox
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/utxqpt1511639216.jpg"
        name = "Kedgeree"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/1525874812.jpg"
        name = "Mapo Tofu"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/sxwquu1511793428.jpg"
        name = "Three-Cheese Souffles"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/sstssx1487349585.jpg"
        name = "Thai Green Curry"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/1520084413.jpg"
        name = "Escovitch Fish"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/rvxxuy1468312893.jpg"
        name = "Vegan Lasagna"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/qtqvys1468573168.jpg"
        name = "Rigatoni with Fennel Sausage"/>

        <QuizBox
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/rvtvuw1511190488.jpg"
        name = "Clam Chowder"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/qqwypw1504642429.jpg"
        name = "Vietnamese Grilled Pork"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wuxrtu1483564410.jpg"
        name = "Dal Fry"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/1543774956.jpg"
        name = "Fruit & Cream Cheese Pastries"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/tyywsw1505930373.jpg"
        name = "Chicken Karaage"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/thazgm1555350962.jpg"
        name = "Homemade Mandazi"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/qxutws1486978099.jpg"
        name = "Vegan Chocolate Cake"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/1520081754.jpg"
        name = "Fennel & Aubergine Paella"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/uttupv1511815050.jpg"
        name = "Montreal Smoked Meat"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/y2irzl1585563479.jpg"
        name = "Honey Yogurt Cheesecake"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/rvypwy1503069308.jpg"
        name = "Laksa King Prawn Noodles"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/cybyue1614349443.jpg"
        name = "Portuguese Barbecued Pork"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/qtuwxu1468233098.jpg"
        name = "Chicken Enchilada Cassrole"/>


        </div>
        </div>

    </section>
   

        <div className = "quiz-button-end-container"  id = "qheader-buttonid">
          <button className = "quiz-button-end mt-5 mb-5">Generate List</button>
        </div>



      
    </div>
  );
}

export function Quiz1() {
  const [selectedMeals, setSelectedMeals] = useState<Meal[]>([]);

  const handleMealClick = (meal: Meal): void => {
    // check if the meal is already selected
    if (selectedMeals.find((m: Meal) => m.idMeal === meal.idMeal)) {
      // if so, remove it from the selectedMeals array
      setSelectedMeals(selectedMeals.filter((m:Meal) => m.idMeal !== meal.idMeal));
    } else if (selectedMeals.length < 5) { // check if less than 5 meals are already selected
      // if not, add it to the selectedMeals array
      setSelectedMeals([...selectedMeals, meal]);
    }else {
          // if the maximum number of selected meals has been reached, show an error message to the user
        console.error('You can only select up to 5 meals.');
        }
  };


  const handleGenerateListClick = (): void => {
    if (selectedMeals.length !== 5) {
      // if the number of selected meals is not 5, show an error message to the user
      alert('Please select exactly 5 meals to continue!');
      return;
    }
    registerSelectedMeals(selectedMeals.map((meal:Meal) => meal.idMeal));
    console.log("selectedMealIds:", selectedMeals.map((meal: Meal) => meal.idMeal));
  };



  const registerSelectedMeals = (selectedMealIds: string[]): void => {
    // make API call to register the selected meal ids to the backend
    // using fetch or axios or any other HTTP library
    fetch('http://your-backend.com/register-selected-meals', {
      method: 'POST',
      body: JSON.stringify({ selectedMealIds }),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then((response) => {
      // handle response from the backend if necessary
    })
    .catch((error) => {
      // handle error if necessary
    });
  };


    return (
      <div>
        <Navbar />
        <div className="quiz-body container-fluid no-gutters" style={{ margin: "0", padding: "0" }}>
    
          <div className = " qheader-img-container1 ">
              <img className="qheader-img-1" src="src/images/quiz-purple.png" alt = "image with the start quiz button"></img>
              <div className = "overlay">
                <div className = "qheader-text-container">
                  <h2 className = "qheader-text">Find out what foods you will enjoy!</h2>
                </div>
                   
                  <button className = "qheader-button">
                    <Link 
                    activeClass="active"
                    to= "qheader-buttonid"
                    spy= {true}
                    smooth = {true}
                    offset = {-10}
                    duration = {500}>
                    Take the quiz!
                    </Link>
                    </button>
  
  
              </div>
  
            </div>
  
            <div className = " qheader-img-container2">
              <img className="qheader-img-2" src="src/images/header-food-img.png" alt = "image that shows the different kinds of foods that the application can recommend you"></img>
            </div>
          
        </div>
  
        <div className = "quiz-subtitle-container">
          <h5 className = "quiz-subtitle">answer for handcrafted recommendations</h5>
          <div className = "quiz-subtitle-rectangle mb-4"></div>
        </div>
  
  
        <div className = "question-main-container">
          <h1 className = "question-main mb-5">Choose five top meals! </h1> 
        
         
        </div>
  
    
  
      <section id = "qheader-buttonid">
      <div className = "responses-image-grid">
        <div className = "responses-image-container container-fluid">

        {meals.map((meal) => (
        <QuizBoxOne
          key={meal.idMeal}
          idMeal={meal.idMeal}
          strMeal={meal.strMeal}
          strMealThumb={meal.strMealThumb}
          onClick={() => handleMealClick(meal)}
          selected={selectedMeals.find((m: Meal) => m.idMeal === meal.idMeal)}
         
        />
      ))}
        
  
  
          </div>
          </div>
  
      </section>
     
  
          <div className = "quiz-button-end-container"  id = "qheader-buttonid">
            <button onClick = {handleGenerateListClick} className = "quiz-button-end mt-5 mb-5">Generate List</button>
          </div>
  
  
  
        
      </div>
    );
  }
  




interface Meal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
}



 // assume meals is an array of meal objects fetched from the API
 const meals: Meal[] = [
  {idMeal: '52887', strMeal: 'Kedgeree', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/utxqpt1511639216.jpg' },
  {idMeal: '52947', strMeal: 'Ma Po Tofu', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/1525874812.jpg' },
  {idMeal:'52912', strMeal: 'Three-cheese souffles', strMealThumb: "https:\/\/www.themealdb.com\/images\/media\/meals\/sxwquu1511793428.jpg" },
  {idMeal: '52814', strMeal: 'Thai Green Curry', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/sstssx1487349585.jpg'},
  {idMeal: '52944', strMeal: 'Escovitch Fish', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/1520084413.jpg'  },
  {idMeal: '52775', strMeal: 'Vegan Lasagna', strMealThumb: "https:\/\/www.themealdb.com\/images\/media\/meals\/rvxxuy1468312893.jpg"},
  {idMeal: '52783', strMeal: 'Rigatoni with fennel sausage sauce', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/qtqvys1468573168.jpg'},
  {idMeal:'52840', strMeal:'Clam chowder', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/rvtvuw1511190488.jpg'},
  {idMeal: '52828', strMeal: 'Vietnamese Grilled Pork', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/qqwypw1504642429.jpg'},
  {idMeal: '52785', strMeal:'Dal fry', strMealThumb: "https:\/\/www.themealdb.com\/images\/media\/meals\/wuxrtu1483564410.jpg"},
  {idMeal:'52957', strMeal: 'Fruit and Cream Cheese Breakfast Pastries', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/1543774956.jpg'},
  {idMeal: '52831', strMeal:'Chicken Karaage', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/tyywsw1505930373.jpg'},
  {idMeal: '52967', strMeal: 'Home-made Mandazi', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/thazgm1555350962.jpg'},
  {idMeal:'52794', strMeal: 'Vegan Chocolate Cake', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/qxutws1486978099.jpg'},
  {idMeal: '52942', strMeal: 'Roast fennel and aubergine paella', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/1520081754.jpg'},
  {idMeal: '52927', strMeal: 'Montreal Smoked Meat', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/uttupv1511815050.jpg'},
  {idMeal: '53007', strMeal: 'Honey Yogurt Cheesecake', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/y2irzl1585563479.jpg'},
  {idMeal: '52821', strMeal: 'Laksa King Prawn Noodles', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/rvypwy1503069308.jpg'},
  {idMeal: '53044', strMeal: 'Portuguese barbecued pork', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/cybyue1614349443.jpg'},
  {idMeal: '52765', strMeal: 'Chicken Enchilada Casserole', strMealThumb: 'https:\/\/www.themealdb.com\/images\/media\/meals\/qtuwxu1468233098.jpg'}



  
  // ...and so on, up to 20 meals
];
// This code defines a functional component called MealQuiz that renders a quiz interface for selecting five favorite meals out of a list of meals. It uses the React useState hook to manage state for the selected meals.



 export default function MealQuiz(){
  const [selectedMeals, setSelectedMeals] = useState<Meal[]>([]);

 
  const handleMealClick = (meal: Meal): void => {
    // check if the meal is already selected
    if (selectedMeals.find((m: Meal) => m.idMeal === meal.idMeal)) {
      // if so, remove it from the selectedMeals array
      setSelectedMeals(selectedMeals.filter((m:Meal) => m.idMeal !== meal.idMeal));
    } else if (selectedMeals.length < 5) { // check if less than 5 meals are already selected
      // if not, add it to the selectedMeals array
      setSelectedMeals([...selectedMeals, meal]);
    }else {
          // if the maximum number of selected meals has been reached, show an error message to the user
        console.error('You can only select up to 5 meals.');
        }
  };
  console.log("selectedMeals:", selectedMeals);

  const handleGenerateListClick = (): React.ReactNode => {
    const mealIDS = selectedMeals.map((meal:Meal) => meal.idMeal);
    return( 
    <Results propValue={mealIDS} />
    );
  };
  console.log("selectedMealIds:", selectedMeals.map((meal: Meal) => meal.idMeal));


  // const registerSelectedMeals = (selectedMealIds: string[]): void => {
  //   // make API call to register the selected meal ids to the backend
  //   // using fetch or any other HTTP library
  //   fetch('http://your-backend.com/register-selected-meals', {
  //     method: 'POST',
  //     body: JSON.stringify({ selectedMealIds }),
  //     headers: {
  //       'Content-Type': 'application/json'
  //     }
  //   })
  //   .then((response) =>  {
  //     // handle response from the backend if necessary
  //      // if the response is successful, show a success message to the user
  //     if (response.ok) {
  //       console.log('Selected meals have been registered successfully!')
  //       return response.json();
  //       // if the response is not successful, show an error message to the user
  //     } else {
  //       return new Error('Failed to register selected meals. There was an error registering the selected meals');
  //     }
      
  //   })
  //   .catch((error) => {
  //     // handle error by showing an error message to the user
  //     // handle error if necessary
  //     console.error(error);
  //   });
  // }; // this function assumes that the backend API is already set up to receive and handle the registration of selected meals.

  return (
    
    <div>
    <Navbar />
    <div className="quiz-body container-fluid no-gutters" style={{ margin: "0", padding: "0" }}>

      <div className = " qheader-img-container1 ">
          <img className="qheader-img-1" src="src/images/quiz-purple.png" alt = "image with the start quiz button"></img>
          <div className = "overlay">
            <div className = "qheader-text-container">
              <h2 className = "qheader-text">Find out what foods you will enjoy!</h2>
            </div>
               
              <button className = "qheader-button">
                <Link 
                activeClass="active"
                to= "qheader-buttonid"
                spy= {true}
                smooth = {true}
                offset = {-10}
                duration = {500}>
                Take the quiz!
                </Link>
                </button>


          </div>

        </div>

        <div className = " qheader-img-container2">
          <img className="qheader-img-2" src="src/images/header-food-img.png" alt = "image that shows the different kinds of foods that the application can recommend you"></img>
        </div>
      
    </div>

    <div className = "quiz-subtitle-container">
      <h5 className = "quiz-subtitle">answer for handcrafted recommendations</h5>
      <div className = "quiz-subtitle-rectangle mb-4"></div>
    </div>


    <div className = "question-main-container">
      <h1 className = "question-main mb-5">Choose five top meals! </h1> 
    
     
    </div>



    <section id = "qheader-buttonid">
    <div className = "responses-image-grid">
      <div className = "responses-image-container container-fluid">

        {meals.map((meal) => (
        <QuizBoxOne
          key={meal.idMeal}
          idMeal={meal.idMeal}
          strMeal={meal.strMeal}
          strMealThumb={meal.strMealThumb}
          onClick={() => handleMealClick(meal)}
          selected={selectedMeals.find((m: Meal) => m.idMeal === meal.idMeal)}
        />
      ))}


    
      </div>
    </div>

    </section>
   

      <div className = "quiz-button-end-container"  id = "qheader-buttonid">
          <button onClick={handleGenerateListClick} className = "quiz-button-end mt-5 mb-5">Generate List</button>
      </div>
      </div>

  
  );
}




 