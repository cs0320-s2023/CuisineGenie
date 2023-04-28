import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./quiz.css";

export default function Quiz() {
  return (
    <div>
      <Navbar />
      <div className="quiz-body container-fluid" style={{ margin: "0", padding: "0" }}>
  
        <div className = "qheader-img-container1">
          <img className="qheader-img-1" src="src/images/quiz-purple.png"></img>
        </div>

        <div className = "qheader-img-container2">
          <img className="qheader-img-2" src="src/images/header-food-img.png"></img>
        </div>
        
      </div>
    </div>
  );
}
