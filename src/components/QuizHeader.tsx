import { ButtonHTMLAttributes, useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";
import { Buttons } from "@testing-library/user-event/dist/types/system/pointer/buttons";

interface QuizHeaderProps {
    title: String;
    button: String
}

/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function QuizHeaderBox(props: QuizHeaderProps) {
    return (
      <div
        tabIndex={0}
        className="quiz-header-box"
        aria-label="contains quiz-header"
        data-testid="result"
        role="result-box"
      >
        {/* TODO: Add a div for each command in the history */}
        {/* Hint: You can use the map function to iterate over an array */}
        <h4>{props.title}</h4>
        <button>{props.button}</button>
      </div>
    );
  }
  