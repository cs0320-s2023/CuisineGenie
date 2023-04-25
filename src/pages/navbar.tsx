import { BrowserRouter, Route, Link } from "react-router-dom";
import "./navbar.css";

export default function Navbar() {
  return (
    <nav className=" navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">
          🧞 CuisineGenie
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
            <li className="navbar-item navbar-quiz">
              <Link className="navbar-item link ms-5" to="/quiz">
                quiz
              </Link>
            </li>
            <li className="navbar-item navbar-results">
              <Link className="navbar-item link ms-5" to="/results">
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
