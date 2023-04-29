import QuizBox from "../components/QuizBox";
import ResultBox from "../components/ResultBox";
import ResultList from "../components/ResultList";
import Navbar from "./navbar";
import "./quiz.css";

export default function Quiz() {

  
  return (
    <div>
      <Navbar />
      <div className="quiz-body container-fluid no-gutters" style={{ margin: "0", padding: "0" }}>
  
        <div className = " qheader-img-container1 ">
            <img className="qheader-img-1" src="src/images/quiz-purple.png" alt = "image with the start quiz button"></img>
            <div className = "overlay">
              <div className = "qheader-text-container">
                <h2 className = "qheader-text">Find out what foods you will enjoy!</h2>
              </div>
                  <button className="qheader-button">Take the quiz!</button>

            </div>

          </div>

          <div className = " qheader-img-container2">
            <img className="qheader-img-2" src="src/images/header-food-img.png" alt = "image that shows the different kinds of foods that the application can recommend you"></img>
          </div>
        
      </div>

      <div className = "quiz-subtitle-container">
        <h5 className = "quiz-subtitle">answer for handcrafted recommendations</h5>
        <div className = "quiz-subtitle-rectangle mb-4"></div>
      </div>


      <div className = "question-main-container">
        <h1 className = "question-main mb-5">Choose five top meals! </h1> 
      </div>


    <div className = "responses-image-grid">
      <div className = "responses-image-container container-fluid">

  
      <QuizBox
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

        <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>

      <QuizBox 
        image="https:\/\/www.themealdb.com\/images\/media\/meals\/wrpwuu1511786491.jpg"/>


        </div>

        <div className = "quiz-button-end-container">
          <button className = "quiz-button-end mt-5 mb-5">Generate List</button>
        </div>

      </div>

      
    </div>
  );
}
