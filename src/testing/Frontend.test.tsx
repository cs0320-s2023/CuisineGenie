import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import ResultList from "../components/ResultList";
import Header from "../components/Header";
import App from "../App";
import Navbar from "../pages/navbar";


test("loads and displays empty result list component", async () => {
  let list: String[] = [];
  let vidList: String[] = [];
  render(<ResultList list={list} vidList={vidList} />);
  const replHistory = screen.getByRole("result-list");
  expect(replHistory).toBeInTheDocument();
});

test('l')

describe("stencil code example", () => {

   // setup
   let header: HTMLElement;
  
  
 
  
  test("loads and displays header", async () => {
    render(<Navbar />)
    // const loginButton = screen.getByText("CuisineGenie");
    const loginButtonhehe = screen.findByText(hello)
    expect(loginButtonhehe).toBeInTheDocument();
    // const header = Header_Role;
    // render(<header />);
    // expect(screen.getByText(/Cuisine Genie/)).toBeInTheDocument();
  });
});



// describe("stencil code example", () => {
  
//   test("loads and displays header", async () => {
//     const header = Header_Role;
//     render(<Header />);
//     expect(screen.getByText(/Cuisine Genie/)).toBeInTheDocument();
//   });
// });

//   test("ensures that all objects are displayed, and tests for about button on home page", async () => {
//     render(<Header />);
//   const header1 = screen.getByRole("Application header", {
//       name: Header_Role,
//     });
//     expect(header1).toBeInTheDocument(); });

