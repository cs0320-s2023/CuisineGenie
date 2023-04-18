import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import { CommandOutput } from "../../src/apiCaller/CommandOutput";
import HistoryBox from "../../src/components/HistoryBox";
import { REPLFunction } from "../../src/apiCaller/REPLFunction";

test("loads and displays the repl input box component", async () => {
  let history: JSX.Element[] = [];
  let currentCommand: Map<String, REPLFunction>;
  let setHistory: (data: JSX.Element[]) => void;
  let commandString: "mock_view";
  // currentCommand.render(
  //   <InputBox
  //     history={history}
  //     setHistory={setHistory}
  //     command={currentCommand}
  //   />
  // );

  // expect(
  //   screen.getByRole("button", { name: repl_submit_button })
  // ).toBeInTheDocument();
//   expect(
//     screen.getByPlaceholderText("ENTER COMMANDS HERE")
//   ).toBeInTheDocument();
// });

// test("loads and displays repl history component", async () => {
//   let history: JSX.Element[] = [];
//   let input: JSX.Element;
//   input = <div> command </div>;

//   act(() => {
//     history.push(input);
//     render(<HistoryBox history={history} />);
//   });
//   const replHistory = screen.getByRole("history-box");
//   expect(replHistory).toBeInTheDocument();
//   expect(await screen.findByText(/command/i)).toBeInTheDocument();
});
