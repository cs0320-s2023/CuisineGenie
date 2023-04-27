import { useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";
import "./components.css";

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface ResultBoxProps {
  image: String;
  name: String;
  cuisine: String;
  ingredients: String[];
}

/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function ResultBox(props: ResultBoxProps) {
  return (
    <div
      tabIndex={0}
      className="result-box"
      aria-label="contains result"
      data-testid="result"
      role="result-box"
    >
      {/* TODO: Add a div for each command in the history */}
      {/* Hint: You can use the map function to iterate over an array */}
      <div className="inner-box">
        <img src={props.image} />
        <div>
          <h4>{props.name}</h4>
          <h5>{props.cuisine}</h5>
          <p>Ingredients:</p>
          {props.ingredients.map((text, index) => (
            <p>
              {/* do we need measurements? */}
              {index + 1}: {text}
            </p>
          ))}
        </div>
      </div>
    </div>
  );
}
