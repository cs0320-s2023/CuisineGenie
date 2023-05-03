import { useState } from "react";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./pages.css";

export default function Results() {
  const [resultBoxes, setResultBoxes] = useState([52771, 52929, 52923]);
  const [ingredientsList, setIngredientsList] = useState([
    "tomatoes",
    "potatoes",
    "salt",
    "sugar",
  ]);

  return (
    <div style={{ margin: "0", padding: "0" }}>
      {/* <div className="nav-color"></div> */}
      <Navbar />
      <div className="grid ">
        <ResultList list={["tomatoes", "2", "3", "4"]} />
        <div>
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
