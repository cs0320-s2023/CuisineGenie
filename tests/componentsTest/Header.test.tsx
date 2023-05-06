import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Header from "../../src/components/Header";
import { Header_AriaLabel, Header_Describedby, Header_Role } from "../../src/components/Header";




describe("stencil code example", () => {
  
  test("loads and displays header", async () => {
    const header = Header_Role;
    render(<Header />);
    expect(screen.getByText(/Cuisine Genie/)).toBeInTheDocument();
  });
});

  test("ensures that all objects are displayed, and tests for about button on home page", async () => {
    render(<Header />);
  const header1 = screen.getByRole("Application header", {
      name: Header_Role,
    });
    expect(header1).toBeInTheDocument(); });
