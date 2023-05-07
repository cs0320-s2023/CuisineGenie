
import QuizBoxOne from "../components/QuizBox";
import Navbar from "./navbar";
import "./quiz.css";
import { Link } from "react-scroll";
import { EventHandler, useEffect, useState } from "react";
import { BrowserRouter, Route } from "react-router-dom";

/**
* Interface used to specify the property and types that will be used when QuizBox is instantianted 
*/
interface Meal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
}

// assume meals is an array of meal objects fetched from the API
const meals: Meal[] = [
  {
    idMeal: "52887",
    strMeal: "Kedgeree",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/utxqpt1511639216.jpg",
  },
  {
    idMeal: "52947",
    strMeal: "Ma Po Tofu",
    strMealThumb: "https://www.themealdb.com/images/media/meals/1525874812.jpg",
  },
  {
    idMeal: "52912",
    strMeal: "Three-cheese souffles",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/sxwquu1511793428.jpg",
  },
  {
    idMeal: "52814",
    strMeal: "Thai Green Curry",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/sstssx1487349585.jpg",
  },
  {
    idMeal: "52944",
    strMeal: "Escovitch Fish",
    strMealThumb: "https://www.themealdb.com/images/media/meals/1520084413.jpg",
  },
  {
    idMeal: "52775",
    strMeal: "Vegan Lasagna",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/rvxxuy1468312893.jpg",
  },
  {
    idMeal: "52783",
    strMeal: "Rigatoni with fennel sausage sauce",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/qtqvys1468573168.jpg",
  },
  {
    idMeal: "52840",
    strMeal: "Clam chowder",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/rvtvuw1511190488.jpg",
  },
  {
    idMeal: "52828",
    strMeal: "Vietnamese Grilled Pork",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/qqwypw1504642429.jpg",
  },
  {
    idMeal: "52785",
    strMeal: "Dal fry",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/wuxrtu1483564410.jpg",
  },
  {
    idMeal: "52957",
    strMeal: "Fruit and Cream Cheese Breakfast Pastries",
    strMealThumb: "https://www.themealdb.com/images/media/meals/1543774956.jpg",
  },
  {
    idMeal: "52831",
    strMeal: "Chicken Karaage",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/tyywsw1505930373.jpg",
  },
  {
    idMeal: "52967",
    strMeal: "Home-made Mandazi",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/thazgm1555350962.jpg",
  },
  {
    idMeal: "52794",
    strMeal: "Vegan Chocolate Cake",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg",
  },
  {
    idMeal: "52942",
    strMeal: "Roast fennel and aubergine paella",
    strMealThumb: "https://www.themealdb.com/images/media/meals/1520081754.jpg",
  },
  {
    idMeal: "52927",
    strMeal: "Montreal Smoked Meat",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/uttupv1511815050.jpg",
  },
  {
    idMeal: "53007",
    strMeal: "Honey Yogurt Cheesecake",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/y2irzl1585563479.jpg",
  },
  {
    idMeal: "52821",
    strMeal: "Laksa King Prawn Noodles",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/rvypwy1503069308.jpg",
  },
  {
    idMeal: "53044",
    strMeal: "Portuguese barbecued pork",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/cybyue1614349443.jpg",
  },
  {
    idMeal: "52765",
    strMeal: "Chicken Enchilada Casserole",
    strMealThumb:
      "https://www.themealdb.com/images/media/meals/qtuwxu1468233098.jpg",
  },

  // ...and so on, up to 20 meals
];


export const TEXT_topbutton_accessible_label = "Press the button to be redirected to the meal selection quiz page. The quiz box generator that generates 20 container with the image of a meal and the name of the meal so then what you should do is select exactly 5 meals and the recipe generator will generate recipes for you!"
export const TEXT_topbutton_role = "quiz header button"
export const TEXT_bottombutton_accessible_label = "After you select exactly 5 images, click the generate list button and then go to the results page, you should see the 5 meals you selected. Then, click the generate list button on top of the results page to generate recipes that will be to your liking"
export const TEXT_bottom_button_accessible_role = "quiz header bottom button"

/**
 * This code defines a functional component called MealQuiz that renders a quiz interface for selecting five favorite meals out of a list of meals. It uses the React useState hook to manage state for the selected meals.
 * @returns HTML and CSS needed to render the quiz page 
 */


export default function MealQuiz() {
  const [selectedMeals, setSelectedMeals] = useState<Meal[]>([]);

  const handleMealClick = (meal: Meal): void => {
    // check if the meal is already selected
    if (selectedMeals.find((m: Meal) => m.idMeal === meal.idMeal)) {
      // if so, remove it from the selectedMeals array
      setSelectedMeals(
        selectedMeals.filter((m: Meal) => m.idMeal !== meal.idMeal)
      );
    } else if (selectedMeals.length < 5) {
      // check if less than 5 meals are already selected
      // if not, add it to the selectedMeals array
      setSelectedMeals([...selectedMeals, meal]);
    } else {
      // if the maximum number of selected meals has been reached, show an error message to the user
      console.error("You can only select up to 5 meals. Select exactly 5 for the food generator to generate recipes tailored for you!");
    }


  };
  console.log("selectedMeals:", selectedMeals);

  const handleGenerateListClick = () => {
    const mealIDS = selectedMeals.map((meal: Meal) => meal.idMeal);
    localStorage.setItem("strings", JSON.stringify(mealIDS)); // inspired by chat gpt lul 
  };
  console.log(
    "selectedMealIds:",
    selectedMeals.map((meal: Meal) => meal.idMeal)
  );

  const handleKeyPress = (e: KeyboardEvent): void => {
    if ( e.ctrlKey && e.key == "Enter") {
      console.log("Enter key with Ctrl pressed");
      handleGenerateListClick
    }
  };

  useEffect(() => {
    const listener = (e: KeyboardEvent) => handleKeyPress(e);
    window.addEventListener("keydown", listener);
    console.log("Event listener added");
  
    return () => {
      window.removeEventListener("keydown", listener);
      console.log("Event listener removed");
    };
  }, []);


  return (
    <div
    data-testid="quiz-container"
    >
        <Navbar/>
      <div
        className="quiz-body container-fluid no-gutters"
        style={{ margin: "0", padding: "0" }}
      >
        <div className=" qheader-img-container1 ">
          <img
            className="qheader-img-1"
            src="src/images/quiz-purple.png"
            alt="image with the start quiz button"
          ></img>
          <div className="overlay">
            <div className="qheader-text-container">
              <h2 className="qheader-text"
             >
                Find out what foods you will enjoy!
              </h2>
            </div>

            <button className="qheader-button"
            aria-label = {TEXT_topbutton_accessible_label}
            aria-describedby= {TEXT_topbutton_accessible_label}
            aria-role = {TEXT_topbutton_role}>
              <Link
                activeClass="active"
                to="qheader-buttonid"
                spy={true}
                smooth={true}
                offset={-10}
                duration={500}
              >
                Take the quiz!
              </Link>
            </button>
          </div>
        </div>

        <div className=" qheader-img-container2">
          <img
            className="qheader-img-2"
            src="src/images/header-food-img.png"
            alt="image that shows the different kinds of foods that the application can recommend you"
          ></img>
        </div>
      </div>

      <div className="quiz-subtitle-container">
        <h5 className="quiz-subtitle">
          answer for handcrafted recommendations
        </h5>
        <div className="quiz-subtitle-rectangle mb-4"></div>
      </div>

      <div className="question-main-container">
        <h1 className="question-main mb-5">Choose five top meals! </h1>
      </div>

      <section id="qheader-buttonid">
        <div className="responses-image-grid">
          <div className="responses-image-container container-fluid">
            {meals.map((meal) => (
              <QuizBoxOne
                key={meal.idMeal}
                idMeal={meal.idMeal}
                strMeal={meal.strMeal}
                strMealThumb={meal.strMealThumb}
                onClick={() => handleMealClick(meal)}
                selected={selectedMeals.find(
                  (m: Meal) => m.idMeal === meal.idMeal
                )}
                
                  // } else if (e.key == "ArrowUp") {
                  //   scroll("up");
                  // } else if (e.key == "ArrowDown") {
                  //   scroll("down");
                  // }
                  
                
              />
            ))}
          </div>
        </div>
      </section>

      <div className="quiz-button-end-container" id="qheader-buttonid">
        <button
          onClick={handleGenerateListClick}
          className="quiz-button-end mt-5 mb-5"
          aria-label = {TEXT_bottombutton_accessible_label}
          aria-describedby= {TEXT_bottombutton_accessible_label}
          aria-role = {TEXT_bottom_button_accessible_role}
        >
          Generate List
        </button>
      </div>
    </div>
  );
}

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
