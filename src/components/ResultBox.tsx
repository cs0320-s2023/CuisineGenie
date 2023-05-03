import { useEffect, useState } from "react";
import { REPLFunction } from "../apiCaller/REPLFunction";
import "./components.css";

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface ResultBoxProps {
  id: Number;
}

//   // if item is unfavorited prior to button click, then add the item to favProducts list and store in variable updatedFavorites
//   if (like === false) {
//     // var updatedFavorites = [...favProducts, item];
//     // if item is favorited prior to button click, then check if the item is in the favProducts/updatedFavorites list and filter it out if so
//   } else if (like === true) {
//     // var updatedFavorites = [...favProducts];
//     // var filtered = updatedFavorites.filter((compare) => {
//     //   return item.price !== compare.price;
//     // });
//     // updatedFavorites = filtered;
//   }

//   // // set the state of favProducts to updatedFavorites to be used in App component
//   // setFavProducts(updatedFavorites);

//   // // set like to the opposite of what is was prior to the button click
//   setLike((prevState) => !prevState);

//   // // recalculate the total price of all the items in updatedFavorites and set total to that value
//   // var sum = 0;
//   // updatedFavorites.forEach((i) => (sum += i.price));
//   // setTotal(sum);
// };

/**
 * React Component for Input Box that handles what commands are typed into the input box
 * @param props defined above: history, setHistory, and commands
 * @returns a JSX.Element that prints the correct output for the command on the screen
 */
export default function ResultBox(props: ResultBoxProps) {
  const [like, setLike] = useState(false);
  const [dislike, setDislike] = useState(false);

  const handleLike = () => {
    setLike((prevState) => !prevState);
  };

  const handleDislike = () => {
    setDislike((prevState) => !prevState);
  };

  const [meal, setMeal] = useState([]);

  const fetchMealData = () => {
    fetch(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${props.id}`)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setMeal(data.meals);
      });
  };

  useEffect(() => {
    fetchMealData();
  }, []);

  const handleClick = () => {
    meal.map((meals) => window.location.replace(meals.strYoutube));
  };

  return (
    <button
      tabIndex={0}
      className="result-box"
      aria-label="contains result"
      data-testid="result"
      role="result-box"
      onClick={handleClick}
    >
      {/* TODO: Add a div for each command in the history */}
      {/* Hint: You can use the map function to iterate over an array */}
      <div className="inner-box">
        <div>
          {meal.map((meals) => (
            <img className="image" src={meals.strMealThumb + "/preview"} />
          ))}

          <button className="button-style" role="button" onClick={handleLike}>
            {like ? "♥" : "♡"}
          </button>
          <button
            className="dislike-button-style"
            role="button"
            onClick={handleDislike}
          >
            {dislike ? "✖" : "x"}
          </button>
        </div>
        <div className="margin-10px">
          {meal.map((meals, index) => (
            <div key={index}>
              <h4>{meals.strMeal}</h4>
              <h5>{meals.strArea}</h5>
              <p id="ingredients">Ingredients:</p>
              <p>1. {meals.strIngredient1}</p>
              <p>2. {meals.strIngredient2}</p>
              <p>3. {meals.strIngredient3}</p>
              <p>4. {meals.strIngredient4}</p>
              <p>5. {meals.strIngredient5}</p>
            </div>
          ))}
        </div>
      </div>
    </button>
  );
}
