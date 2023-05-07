import { useEffect, useState } from "react";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./pages.css";

export const TEXT_results_accessible_label = "contains results"

export default function Results() {
  const getRecipe = (args: String[]) => {
    if (args.length == 5) {
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
            console.log(resultBoxes);
          } else {
            return data["errorMessage"];
          }
        });
    } else {
      setErrorMessage(
        "Incorrect number of recipes selected in quiz! Please retake quiz and come back."
      );
    }
  };

  function regenerateList() {
    setResultBoxes([]);
    getRecipe(resultBoxes);
  }

  useEffect(() => {
    const strings = JSON.parse(localStorage.getItem("strings") || "[]");
    setResultBoxes(strings);
  }, []);

  const [resultBoxes, setResultBoxes] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  const [likeList, setLikeList] = useState([]);
  const [vidList, setVidList] = useState([]);

  function handleKeyPress(event) {
    if (event.key === "Enter") {
      regenerateList();
    }
  }

  useEffect(() => {
    document.addEventListener("keypress", handleKeyPress);
    return () => {
      document.removeEventListener("keypress", handleKeyPress);
    };
  });

  return (
    <div style={{ margin: "0", padding: "0" }} aria-label={TEXT_results_accessible_label}>
      <Navbar />
      <div className="grid">
        <ResultList list={likeList} vidList={vidList} />
        <div className="header-results">
          <h4 id="purple">Completed the quiz?</h4>
          <h5 id="purple">
            Click the button below to generate a list of recommendations!
          </h5>
          <button
            className="list-button-style"
            role="button"
            aria-label="button to generate recipe list"
            onClick={regenerateList}
          >
            Generate List
          </button>
          <p>{errorMessage}</p>
          <div
            className="container"
            aria-label="button to generate recipe list"
          >
            {resultBoxes.map((ID) => (
              <ResultBox
                id={ID}
                likeList={likeList}
                setLikeList={setLikeList}
                vidList={vidList}
                setVidList={setVidList}
              />
            ))}
          </div>
        </div>
        <div className="bottom-padding"></div>
      </div>
    </div>
  );
}