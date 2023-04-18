import { useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface InputBoxProps {
  history: JSX.Element[];
  setHistory: (data: JSX.Element[]) => void;
  commands: Map<String, REPLFunction>;
}

/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function InputBox(props: InputBoxProps) {
  // TODO: Add a state variable for the textbox contents

  //state variables for isVerbose and textbox - limit scope to just input box
  const [isVerbose, setIsVerbose] = useState<Boolean>(false);
  const [textbox, setTextbox] = useState("");

  function printMessage(
    //private variables: message, command, verbose
    message: string,
    command: string,
    verbose: Boolean
  ): void {
    if (verbose) {
      var verbMessage = new String("This is string one");

      let toPrint: JSX.Element;
      let commandOutput: JSX.Element;
      toPrint = (
        <div id="verbose" role="verbose-temp">
          Command: {command} <br></br> Output:
          <p
            className="Container"
            dangerouslySetInnerHTML={{ __html: message }}
          ></p>
        </div>
      );
      commandOutput = (
        <div
          role="alert"
          aria-live="assertive"
          aria-description={verbMessage.concat(
            "Command:",
            command,
            "Output:",
            message
          )}
          aria-relevant="additions"
        >
          {toPrint}
        </div>
      );
      props.setHistory([...props.history, commandOutput]);

      // );
      //   props.setHistory([...props.history,  <div role="alert" aria-live="assertive" aria-details="verbose"  aria-relevant="additions"
      // ></div>,])
    } else {
      props.setHistory([
        ...props.history,
        <div
          role="alert"
          aria-live="assertive"
          aria-description={message}
          aria-relevant="additions"
          className="Container"
          dangerouslySetInnerHTML={{ __html: message }}
        ></div>,
      ]);
    }
  }

  /**
   * Handles the submit button being clicked or the enter key being pressed!
   * In this function, we handle the commands to print, and then
   */
  function handleSubmit() {
    //private variables: inputText and func
    let inputText: string[] = textbox.split(" ");
    let func: REPLFunction | undefined = props.commands.get(inputText[0]);
    if (func === undefined) {
      printMessage("Error", "Invalid command.", false);
    } else {
      func(inputText).then((result) => {
        if (result === "Mode is now verbose.") {
          setIsVerbose(true);
          printMessage(result, inputText[0], true);
        } else if (result === "Mode is now brief.") {
          setIsVerbose(false);
          printMessage(result, inputText[0], false);
        } else {
          printMessage(result, inputText[0], isVerbose);
        }
      });
    }
    setTextbox("");
  }

  return (
    <div className="repl-input">
      {/* TODO: Make this input box sync with the state variable */}
      <input
        type="text"
        role="input"
        className="repl-command-box"
        placeholder="Enter Command Here!"
        aria-label="input box"
        aria-description="Please input the relevant command here, followed by either the enter key or pressing the submit button"
        value={textbox}
        onChange={(e) => setTextbox(e.target.value)}
        onKeyUp={(e) => {
          if (e.key == "Enter") {
            handleSubmit();
          }
        }}
      />
      {/* TODO: Make this button call handleSubmit when clicked */}
      <button
        onClick={handleSubmit}
        className="repl-button"
        aria-label="submit button"
        aria-description="Click this button to submit command entered into input box"
        role="button"
      >
        Submit
      </button>
    </div>
  );
}
