import { useEffect, useState } from "react";
import "./components.css";
import "/src/pages/quiz.css"

export const TEXT_imagebox_accessible_label = "You selected name of meal:"
 const TEXT_quizbox_accessible_aria= "a quiz box generator that generates 20 container with the image of a meal and the name of the meal"
 const TEXT_quiz_accessible_label = "images for 20 meals"
export const TEXT_quizbox_accessible_role = "quiz-box"


/**
* Interface used to specify the property and types that the QuizBoxOne should have
*/

export interface eachMeal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
  onClick: () => void;
  selected: boolean;

}

/**
* This function is used to render the container of the 20 meals in the quiz selection page. This function ensures that only the quizname and quizimage gets rendered.
* @param props of type interface eachMeal
* @returns HTML and CSS that will be rendered in Quiz
*/
export default function QuizBoxOne(props: eachMeal) {
  return (
      <div
      tabIndex={0}
      className={`quiz-box ${props.selected ? 'selected' : ''}`}
      //data-testid="meal-container"
     
      role= {TEXT_quizbox_accessible_role}
      style={{ border: props.selected ? '3px solid lime' : '3px solid #8E8CFF' }}
      onClick={props.onClick} 
    >

      <div className = "responses-image-grid">
          <div className = "quiz-box-image-container"> 
          <img 
          className = "quiz-box-image mb-2" 
          aria-label= {TEXT_imagebox_accessible_label}
          src = {props.strMealThumb} alt = {props.strMeal}></img>
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

