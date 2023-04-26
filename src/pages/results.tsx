import React from "react";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./pages.css";

export default function Results() {
  return (
    <div style={{ margin: "0", padding: "0" }}>
      {/* <div className="nav-color"></div> */}
      <Navbar />

      <div className="grid">
        <ResultList list={["tomatoes", "2", "3", "4"]} />

        <div className="container">
          {/* need to find way to loop */}
          <ResultBox
            name="String"
            cuisine="Test"
            ingredients={["hi", "test"]}
          />
          <ResultBox
            name="String"
            cuisine="Test"
            ingredients={["hi", "test"]}
          />
          <ResultBox
            name="String"
            cuisine="Test"
            ingredients={["hi", "test"]}
          />
          <ResultBox
            name="String"
            cuisine="Test"
            ingredients={["hi", "test"]}
          />
          <ResultBox
            name="String"
            cuisine="Test"
            ingredients={["hi", "test"]}
          />
        </div>
        <div className="bottom-padding"></div>
      </div>
    </div>
  );
}
