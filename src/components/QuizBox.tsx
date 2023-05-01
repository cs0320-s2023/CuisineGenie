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
//   id: Number;
  image: string
//   name: string;
}

// get meal info that gets the data
//display data that displays all of the info 
/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function QuizBox(props: QuizBoxProps) {
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
            <img className = "quiz-box-image" src = {props.image}></img>
            </div>  
        </div>

      </div>

    )
    // return(
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
}
