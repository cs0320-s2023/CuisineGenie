
import ReactDOM from "react-dom";
import { HashRouter, Routes, Route } from "react-router-dom";
import Layout from "./Layout"
import ScrollToTop from "./ScrollToTop"
import "../styles/App.css";
import Results from "./pages/results";
import MealQuiz from "./pages/quiz";

export default function App() {
  return (
    <HashRouter>
      <ScrollToTop />
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<MealQuiz/>} />
          <Route path="/quiz" element={<MealQuiz />} />
          <Route path="/results" element={<Results />} />
        </Route>
      </Routes>
    </HashRouter>
  );
}

