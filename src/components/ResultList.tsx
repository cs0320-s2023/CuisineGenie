import { useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";
import "./components.css";

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface ResultListProps {
  list: String[];
  vidList: String[];
}

/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function ResultList(props: ResultListProps) {
  // const [mealList, setMealList] = useState([]);

  // const fetchMealName = () => {
  //   for (var i = 0; i < props.list.length; i++) {
  //     console.log(props.list[i]);
  //     fetch(
  //       `https://www.themealdb.com/api/json/v1/1/lookup.php?i=${props.list[i]}`
  //     )
  //       .then((response) => {
  //         return response.json();
  //       })
  //       .then((data) => {
  //         setMealList((mealList) => mealList.concat(data.meals.strMeal));
  //       });
  //   }
  // };

  // useEffect(() => {
  //   fetchMealName();
  // }, []);

  const fetchMealYoutube = async (name: String) => {
    const response = await fetch(
      `www.themealdb.com/api/json/v1/1/search.php?s=${name}`
    );
    const data = await response.json();
    return data.meals.strYoutube;
  };

  return (
    <div
      tabIndex={0}
      className="sidebar"
      aria-label="contains result"
      data-testid="result"
      role="result-box"
    >
      {/* TODO: Add a div for each command in the history */}
      {/* Hint: You can use the map function to iterate over an array */}
      <h4 className="margin-30px">Your Favorited Items:</h4>
      {props.list.map((text, index) => (
        <h5>
          <a href={props.vidList[index]} target="_blank">
            {index + 1}. {text}
          </a>
        </h5>
      ))}
    </div>
  );
}
