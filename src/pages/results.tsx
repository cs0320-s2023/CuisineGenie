import { useState } from "react";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./pages.css";

export default function Results() {
  const [resultBoxes, setResultBoxes] = useState([
    52820, 52929, 52923, 52850, 53011,
  ]);
  const [ingredientsList, setIngredientsList] = useState([
    "Tomatoes",
    "Potatoes",
    "Salt",
    "Sugar",
  ]);

  return (
    <div style={{ margin: "0", padding: "0" }}>
      {/* <div className="nav-color"></div> */}
      <Navbar />
      <div className="grid ">
        <ResultList list={ingredientsList} />
        <div>
          <h5>Want to explore other options?</h5>
          <p>
            Select what you like or dislike and then click the button below!
          </p>
          <button className="list-button-style" role="button">
            Regenerate List
          </button>
          <div className="container">
            {resultBoxes.map((ID) => (
              <ResultBox id={ID} />
            ))}
          </div>
        </div>
        <div className="bottom-padding"></div>
      </div>
    </div>
  );
}
