import { useEffect, useState } from "react";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./pages.css";

export default function Results() {
  const getRecipe = (args: String[]) => {
    fetch(
      `http://localhost:61747/generaterecipes?1=${args[0]}&2=${args[1]}&3=${args[2]}&4=${args[3]}&5=${args[4]}`
    )
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        if (data["result"] === "success") {
          console.log(data["ids"]);
          setResultBoxes(data["ids"]);
        } else {
          return data["errorMessage"];
        }
      });
  };

  function regenerateList() {
    getRecipe(["52820", "52929", "52923", "52850", "53011"]);
  }

  useEffect(() => {
    console.log(resultBoxes);
  }, []);

  const [resultBoxes, setResultBoxes] = useState([
    "52820",
    "52929",
    "52923",
    "52850",
    "53011",
  ]);
  const [ingredientsList, setIngredientsList] = useState([
    "52820",
    "52929",
    "52923",
    "52850",
    "53011",
  ]);

  return (
    <div style={{ margin: "0", padding: "0" }}>
      {/* <div className="nav-color"></div> */}
      <Navbar />
      <div className="grid ">
        <ResultList list={ingredientsList} />
        <div className="header-results">
          <h4 id="purple">Want to explore other options?</h4>
          <h5 id="purple">
            Select what you like or dislike and then click the button below!
          </h5>
          <button
            className="list-button-style"
            role="button"
            onClick={regenerateList}
          >
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
