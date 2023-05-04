import { useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";
import "./components.css";
import "/src/pages/quiz.css"

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface QuizBoxProps {
 
  image: string
  name: string
//   name: string;
}







// export function getMealIds(props: eachMeal){
//    const [selectedMeals, setSelectedMeals] = useState([]);
//    const [meals, setMeals] = useState<eachMeal[]>([]);

//    const handleMealClick = (id: string) => {
//     // Make a copy of the selectedMeals array and add the new meal id to it
//     const newSelectedMeals = [...selectedMeals, id];
//     setSelectedMeals(newSelectedMeals);
//   };


// }







// function getMealIDS(){
//   const [mealIds, setMealIds] = useState([]);
//   useEffect(() => {
//     async function fetchMealIds() {
//       const ids = [];
//       for (let i = 1; i <= 20; i++) {
//         const response = await fetch(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${i}`);
//         const data = await response.json();
//         ids.push(data.meals[0].idMeal);
//       }
//       setMealIds(ids);
//     }
//     fetchMealIds();
//   }, []);
//   return (
//     <div>
//       <h2>Meal IDs:</h2>
//       <ul>
//         {mealIds.map((id) => (
//           <li key={id}>{id}</li>
//         ))}
//       </ul>
//     </div>
//   )
// }

// get meal info that gets the data
//display data that displays all of the info 
/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */

export interface eachMeal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
  onClick: () => void;
  selected: boolean;
}
export function QuizBox(props: QuizBoxProps) {
    return (
        <div
        tabIndex={0}
        className="quiz-box"
        aria-label="contains quiz"
        data-testid="quiz"
        role="quiz-box"
      >

        <div className = "responses-image-grid">
            <div className = "quiz-box-image-container"> 
            <img className = "quiz-box-image mb-2" src = {props.image}></img>
            </div>  
        </div> 

        <div className = "responses-images-name-container">
          <div className = "responses-images-name">
            <h6> {props.name}</h6>
          </div>

        </div>

      </div>

    )
}



export default function QuizBoxOne(props: eachMeal) {
  return (
    
      <div
      tabIndex={0}
      className={`quiz-box ${props.selected ? 'selected' : ''}`}
      aria-label="contains quiz"
      data-testid="quiz"
      role="quiz-box"
      style={{ border: props.selected ? '3px solid blue' : '3px solid black' }}
      onClick={props.onClick} 
    
      
    >

      <div className = "responses-image-grid">
          <div className = "quiz-box-image-container"> 
          <img className = "quiz-box-image mb-2" src = {props.strMealThumb} alt = {props.strMeal}></img>
          </div>  
      </div> 

      <div className = "responses-images-name-container">
        <div className = "responses-images-name">
          <h6> {props.strMeal}</h6>
        </div>

      </div>

    </div>


  )
}


    

    

    // return( margin was added to include props.name 
    //     <div
    //     tabIndex={0}
    //     className="quiz-box"
    //     aria-label="contains quiz"
    //     data-testid="quiz"
    //     role="quiz-box"
    //   >
    //     {/* TODO: Add a div for each command in the history */}
    //     {/* Hint: You can use the map function to iterate over an array */}
    //     <div className="quiz-box-components">
    
    //     {/* <h4>{props.name}</h4> */}
    //     <div className = "quiz-grid">
    //         <div className = "quiz-box-image-container"> 
    //             <img className = "quiz-box-image" src = {props.image}></img>
    //         </div>  
    //     </div>
    //     </div>
    //   </div>
    // )

