import { BrowserRouter, Route, Link } from "react-router-dom";
import "./pages.css";

export default function Navbar() {
  return (
    <nav 
    aria-describedby="This is the navigation bar of our application. In here, we have the name of the application and icon which is CuisineGenie. Then, we have tabs of our program which is the quiz and results."
    aria-roledescription="navigation-bar"
    className=" navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid"
      aria-describedby="Welcome to Cuisine Genie. Cuisine Genie is an application where users are able to generate recipes they will like based on their selection of meals in the quiz page. They will be able to see their generated recipes in the results page">
        <a 
        className="navbar-brand" 
        href="#"
        aria-describedby="Welcome to Cuisine Genie. Cuisine Genie is an application where users are able to generate recipes they will like based on their selection of meals in the quiz page. They will be able to see their generated recipes in the results page"
        aria-role = 'navbar-brand'>
          <span 
          className = "brand-icon">🧞</span> CuisineGenie
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
         
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div
          className="collapse navbar-collapse ms-5"
          id="navbarSupportedContent"
        >
          <ul className="navbar-nav ms-auto">
            <li className="navbar-item navbar-quiz"
           >
              <Link 
              aria-describedby="Quiz tab of our application"
              className="navbar-item link ms-5" to="/quiz">
                quiz
              </Link>
            </li>
            <li className="navbar-item navbar-results">
              <Link className="navbar-item link ms-5" to="/results"
              aria-describedby="Results tab of our application">
                results
              </Link>
            </li>
            <li className="navbar-item navbar-profile">
              <Link className="navbar-item link ms-5" to="/profile">
                profile
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
