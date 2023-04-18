import { Fragment } from "react";

/**
 * PROPS: history box takes in only one prop (history array to print on screen)
 * history is a public state variable that is sent through App.tsx
 */
interface HistoryBoxProps {
  history: JSX.Element[];
}

/**
 * HistoryBox Component for React that stores all the history of commands that have been entered
 * @param props defined above: for this component, it just takes in history
 * @returns a JSX.Element to be printed on screen
 */
function HistoryBox(props: HistoryBoxProps) {
  return (
    <div
      tabIndex={0}
      className="repl-history"
      aria-label="contains history"
      data-testid="repl--history"
      role="history-box"
    >
      {/* TODO: Add a div for each command in the history */}
      {/* Hint: You can use the map function to iterate over an array */}
      {props.history.map((text, index) => (
        <div
          role="output-content"
          className="history-output"
          key={index}
          aria-label="history"
          aria-description={text}
        >
          <Fragment aria-live="assertive" aria-description={text}>
            {text}
          </Fragment>
        </div>
      ))}
    </div>
  );
}

export default HistoryBox;
