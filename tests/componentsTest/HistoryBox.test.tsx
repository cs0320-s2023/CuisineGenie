import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import { CommandOutput } from "../../src/apiCaller/CommandOutput";
import HistoryBox from "../../src/components/HistoryBox";




test("loads and displays empty repl history component", async () => {
  
  let history: JSX.Element[] = [];
  render(<HistoryBox history={history} />);
  const replHistory = screen.getByRole("history-box");
  expect(replHistory).toBeInTheDocument();
});

test("loads and displays repl history component", async () => {
  
  let history: JSX.Element[] = [];
  let input : JSX.Element;
  input = (<div> command </div>);

  act(() => {
    history.push(input);
    render(<HistoryBox history={history} />);
  });
  const replHistory = screen.getByRole("history-box");
  expect(replHistory).toBeInTheDocument();
  expect(await screen.findByText(/command/i)).toBeInTheDocument();

});