import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Header from "../../src/components/Header";



describe("stencil code example", () => {
  
    test("loads and displays header", async () => {
      render(<Header />);
      expect(screen.getByText(/REPL/i)).toBeInTheDocument();
    });
  });