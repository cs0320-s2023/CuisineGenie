import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import ResultList from "../components/ResultList";

test("loads and displays empty result list component", async () => {
  let list: String[] = [];
  let vidList: String[] = [];
  render(<ResultList list={list} vidList={vidList} />);
  const replHistory = screen.getByRole("result-list");
  expect(replHistory).toBeInTheDocument();
});
