import './pages/bootstrap.min.css'
import './pages/Home.css'
import { BrowserRouter, Route, Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav>
      <div id="navbar">
        <li className="navbar-item navbar-home">
          <Link className="navbar-item link" to="/">CuisineGenie</Link>
        </li>
        <li className="navbar-item navbar-right">
          <Link className="navbar-item link" to="/about">Quiz</Link>
        </li>
        <li className="navbar-item navbar-right">
          <Link className="navbar-item link" to="/about">Results</Link>
        </li>
        <li className="navbar-item navbar-right">
          <Link className="navbar-item link" to="/about">Profile</Link>
        </li>
      </div>
    </nav>
  );
}
