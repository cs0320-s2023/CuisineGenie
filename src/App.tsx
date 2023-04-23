import { REPLFunction } from "./apiCaller/REPLFunction";
import ReactDOM from "react-dom";
import { HashRouter, Routes, Route } from "react-router-dom";
import quiz from "./pages/quiz";
import results from "./pages/results";
import Layout from "./Layout"
import ScrollToTop from "./ScrollToTop"

import { useEffect, useState } from "react";
import "../styles/App.css";
import Header from "./components/Header";
import HistoryBox from "./components/HistoryBox";
import InputBox from "./components/InputBox";
import { load, view, search, mode } from "./apiCaller/commands";
import {
  mockMap,
  fruits,
  artists,
  mockSearchMap,
  csvFile,
} from "../tests/componentsTest/mockedFunctionalityTest/mockedJson";
import Results from "./pages/results";
import Profile from "./pages/profile";

export default function App() {
  return (
    <HashRouter>
      <ScrollToTop />
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/quiz" element={<Quiz />} />
        </Route>
      </Routes>
    </HashRouter>
  );
}

//from lecture code
let registered = new Map<String, REPLFunction>();

/**
 * Registers commands like load, search, etc, so that they can be called on by command in input box
 * @param name name of string to get corresponding REPLFunction command in input box
 * @param func REPLFunction command that has functionality defined in comands.tsx
 */
function registerCommand(name: string, func: REPLFunction) {
  registered.set(name, func);
}

/**
 * Unregisters commands like load, search, etc, so that they can be deleted for testing in input box
 * @param name name of string to get corresponding REPLFunction command in input box
 * @param func REPLFunction command that has functionality defined in comands.tsx
 */
function unregisterCommand(name: string) {
  registered.delete(name);
}

function checkCommand(name: string) {
  return registered.get(name) !== undefined;
}

//register commands upon window refresh
window.onload = () => {
  registerCommand("load", load);
  registerCommand("search", search);
  registerCommand("view", view);
  registerCommand("mode", mode);
};

/**
 * App component that holds the webpage
 * @returns JSX.Element to update the overall webpage using components
 */
export function App2() {
  // The data state is an array of strings, which is passed to our components
  // You may want to make this a more complex object, but for now it's just a string
  const [history, setHistory] = useState<JSX.Element[]>([]);

  //registering commands, update using useEffect
  useEffect(() => {
    registerCommand("load", load);
    registerCommand("search", search);
    registerCommand("view", view);
    registerCommand("mode", mode);
  }, []);

  return (
    <div className="header-class">
      <div>
        <Header />
      </div>
      <div className="repl">
        {/* TODO: Add HistoryBox */}
        <div className="page-content">
          <div className="history-box">
            <HistoryBox history={history}></HistoryBox>
          </div>
          <div className="input-box">
            <InputBox
              history={history}
              setHistory={setHistory}
              commands={registered}
            ></InputBox>
          </div>
        </div>
      </div>
    </div>
  );
}

//export functions to be used in other files
export { App, registerCommand, unregisterCommand, checkCommand };
