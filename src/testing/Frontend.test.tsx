import "@testing-library/jest-dom";
import { render, screen, fireEvent } from "@testing-library/react";
import ResultList from "../components/ResultList";
import userEvent from "@testing-library/user-event";
import { HashRouter, Routes, Route } from "react-router-dom";
import ScrollToTop from "../ScrollToTop";
import App from "../App";
import Navbar from "../pages/navbar";
import MealQuiz from "../pages/quiz";
import Results from "../pages/results";
import { TEXT_bottom_button_accessible_role, TEXT_bottombutton_accessible_label, TEXT_topbutton_accessible_label, TEXT_topbutton_role  } from "../pages/quiz";
import { TEXT_navbar_accessible_aria,TEXT_navbar_accessible_label, TEXT_navbar_accessible_role } from "../pages/navbar";
import QuizBoxOne, { TEXT_imagebox_accessible_label, TEXT_quizbox_accessible_role } from "../components/QuizBox";
import React, { LinkHTMLAttributes } from "react";
import { TEXT_results_accessible_label } from "../pages/results";




let generateQuizButton: HTMLElement;
let navbar: HTMLButtonElement
let quizbox: HTMLElement[];
let quiz: HTMLElement;
let redirectQuizButton: HTMLElement;
let results: HTMLElement;


beforeEach(() => {

  
  // need this for ts to recognize scrollIntoView as func
  window.HTMLElement.prototype.scrollIntoView = function() {};
  render(
    <React.StrictMode>
    <App />
  </React.StrictMode>
  )

  quiz = screen.getByTestId("quiz-container")
  generateQuizButton = screen.getByLabelText(TEXT_bottombutton_accessible_label);
  navbar = screen.getByLabelText(TEXT_navbar_accessible_label);
  quizbox = screen.getAllByLabelText(TEXT_imagebox_accessible_label);
  redirectQuizButton = screen.getByLabelText(TEXT_topbutton_accessible_label);
  // results = screen.getByLabelText(TEXT_results_accessible_label);

  

})

test("test that quiz elements render properly ", async () => {

  // quiz section
  userEvent.click(screen.getByText("quiz"));

  expect(screen.getByText(/CuisineGenie/i)).toBeInTheDocument();
  expect(screen.getByText(/🧞/i)).toBeInTheDocument();
  expect(screen.getByText(/answer for handcrafted recommendations/i)).toBeInTheDocument();
  expect(screen.getByText(/Find out what foods you will enjoy!/i)).toBeInTheDocument();
  expect(screen.getByText(/Choose five top meals!/i)).toBeInTheDocument();
  expect(screen.getByText(/Kedgeree/i)).toBeInTheDocument();
  expect(screen.getByText(/Ma Po Tofu/i)).toBeInTheDocument();
  expect(screen.getByText(/Three-cheese souffles/i)).toBeInTheDocument();
  expect(screen.getByText(/Escovitch Fish/i)).toBeInTheDocument();
  expect(screen.getByText(/Vegan Lasagna/i)).toBeInTheDocument();
  expect(screen.getByText(/Rigatoni with sausage sauce/i)).toBeInTheDocument();
  expect(screen.getByText(/Clam chowder/i)).toBeInTheDocument();
  expect(screen.getByText(/Vietnamese Grilled Pork/i)).toBeInTheDocument();
  expect(screen.getByText(/Dal fry/i)).toBeInTheDocument();
  expect(screen.getByText(/Fruit & Cream Cheese Pastries/i)).toBeInTheDocument();
  expect(screen.getByText(/Chicken Karaage/i)).toBeInTheDocument();
  expect(screen.getByText(/Home-made Mandazi/i)).toBeInTheDocument();
  expect(screen.getByText(/Vegan Chocolate Cake/i)).toBeInTheDocument();
  expect(screen.getByText(/Fennel and aubergine paella/i)).toBeInTheDocument();
  expect(screen.getByText(/Montreal Smoked Meat/i)).toBeInTheDocument();
  expect(screen.getByText(/Honey Yogurt Cheesecake/i)).toBeInTheDocument();
  expect(screen.getByText(/Laksa King Prawn Noodles/i)).toBeInTheDocument();
  expect(screen.getByText(/Portuguese barbecued pork/i)).toBeInTheDocument();
  expect(screen.getByText(/Chicken Enchilada Casserole/i)).toBeInTheDocument();
  expect(screen.getByText(/Generate List/i)).toBeInTheDocument();
});

test("test navbar items ", async () => {

userEvent.click(screen.getByText("results"));
userEvent.click(screen.getByText("quiz"));
userEvent.click(screen.getByText("profile"))
});

test("test that quiz page exists with user interaction ", async () => {

  userEvent.click(screen.getByText("quiz"));
  expect(navbar).toBeInTheDocument();
  expect(generateQuizButton).toBeInTheDocument();
  expect(redirectQuizButton).toBeInTheDocument();
  expect(quiz).toBeInTheDocument();

  // Assert that quizboxes array is not empty
  expect(quizbox.length).toBeGreaterThan(0);

  // Assert that quizboxes array is exactly 20 items
  expect(quizbox.length).toBeGreaterThanOrEqual(20);

  expect(quizbox.length).not.toBeGreaterThanOrEqual(21);
});


test("test selecting 5 meals ", async () => {
  userEvent.click(screen.getByText("quiz"));

  for (let i = 0; i < 5; i++) {
    fireEvent.click(quizbox[i]);

    
    expect(quizbox[0].tagName).toBe("IMG");
    expect(quizbox[0].getAttribute("alt")).toBe("Kedgeree");
    expect(quizbox[0].getAttribute("aria-label")).toBe("You selected name of meal:");
    expect(quizbox[0].getAttribute("src")).toBe("https://www.themealdb.com/images/media/meals/utxqpt1511639216.jpg"); 


    expect(quizbox[1].tagName).toBe("IMG");
    expect(quizbox[1].getAttribute("alt")).toBe("Ma Po Tofu");
    expect(quizbox[1].getAttribute("aria-label")).toBe("You selected name of meal:");
    expect(quizbox[1].getAttribute("src")).toBe("https://www.themealdb.com/images/media/meals/1525874812.jpg"); 

    expect(quizbox[2].tagName).toBe("IMG");
    expect(quizbox[2].getAttribute("alt")).toBe("Three-cheese souffles");
    expect(quizbox[2].getAttribute("aria-label")).toBe("You selected name of meal:");
    expect(quizbox[2].getAttribute("src")).toBe("https://www.themealdb.com/images/media/meals/sxwquu1511793428.jpg"); 

    expect(quizbox[3].tagName).toBe("IMG");
    expect(quizbox[3].getAttribute("alt")).toBe("Thai Green Curry");
    expect(quizbox[3].getAttribute("aria-label")).toBe("You selected name of meal:");
    expect(quizbox[3].getAttribute("src")).toBe("https://www.themealdb.com/images/media/meals/sstssx1487349585.jpg"); 

    expect(quizbox[4].tagName).toBe("IMG");
    expect(quizbox[4].getAttribute("alt")).toBe("Escovitch Fish");
    expect(quizbox[4].getAttribute("aria-label")).toBe("You selected name of meal:");
    expect(quizbox[4].getAttribute("src")).toBe("https://www.themealdb.com/images/media/meals/1520084413.jpg"); 


    expect(generateQuizButton.textContent).toBe('Generate List')


    // fireEvent.click(generateQuizButton);
    // const storedMealIds = JSON.parse(localStorage.getItem('strings') || '[]'); 

    // expect(storedMealIds).toContain("['52887', '52947', '52912', '52814', '52944']");
    // expect(storedMealIds).toHaveLength(5)

  }
});

test ("selecting a random meal and getting id ", async () => {
  fireEvent.click(quizbox[15]);
  expect(quizbox[15].getAttribute("alt")).toBe("Montreal Smoked Meat");

  fireEvent.click(generateQuizButton);
  const storedMealIds = JSON.parse(localStorage.getItem('strings') || '[]'); 
  expect(storedMealIds).toContain('52927')

});

test ("selecting 5 random meal and getting ids ", async () => {
  fireEvent.click(quizbox[15]);
  fireEvent.click(quizbox[16]);
  fireEvent.click(quizbox[10]);
  fireEvent.click(quizbox[7]);
  fireEvent.click(quizbox[19]);

  expect(quizbox[15].getAttribute("alt")).toBe("Montreal Smoked Meat");
  expect(quizbox[16].getAttribute("alt")).toBe("Honey Yogurt Cheesecake");
  expect(quizbox[10].getAttribute("alt")).toBe("Fruit & Cream Cheese Pastries");
  expect(quizbox[7].getAttribute("alt")).toBe("Clam chowder");
  expect(quizbox[19].getAttribute("alt")).toBe("Chicken Enchilada Casserole");

  fireEvent.click(generateQuizButton);
  const storedMealIds = JSON.parse(localStorage.getItem('strings') || '[]'); 
  expect(storedMealIds).toContain('52927', '53007', '52957', '52840', '52765');


});






// // Assert that quizboxes array is not empty
// expect(quizboxes.length).toBeGreaterThan(0);

// // Additional assertions to check specific elements in quizboxes
// // For example, you can check if a specific quizbox exists:
// const quizboxExists = quizboxes.some((quizbox) =>
//   quizbox.textContent?.includes("Quiz Box 1")
// );
// expect(quizboxExists).toBe(true);
// });


test("loads and displays empty result list component", async () => {
  let list: String[] = [];
  let vidList: String[] = [];
  render(<ResultList list={list} vidList={vidList} />);
  const replHistory = screen.getByRole("result-list");
  expect(replHistory).toBeInTheDocument();
});


// describe("stencil code example", () => {

//    // setup
//    let header: HTMLElement;
  
  
 
  
//   test("loads and displays header", async () => {
//     render(<Navbar />)
//     // const loginButton = screen.getByText("CuisineGenie");
//     const loginButtonhehe = screen.findByText(hello)
//     expect(loginButtonhehe).toBeInTheDocument();
//     // const header = Header_Role;
//     // render(<header />);
//     // expect(screen.getByText(/Cuisine Genie/)).toBeInTheDocument();
//   });
// });



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

