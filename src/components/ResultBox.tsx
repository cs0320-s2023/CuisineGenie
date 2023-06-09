import { useEffect, useState } from "react";
import "./components.css";

/**
 * PROPS: input box takes in three props, history, setHistory, and commands
 * history is a public state variable of an array of jsx elements
 * setHistory is a public state variable that is used to update the state variable of history
 * commands is a map that maps from the string to a REPLFunction (ex. "load" to load REPLFunction)
 */
interface ResultBoxProps {
  id: String;
  likeList: String[];
  setLikeList: (data: String[]) => void;
  vidList: String[];
  setVidList: (data: String[]) => void;
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
    var mealName: String;
    meal.map((meals) => (mealName = meals.strMeal));

    var vidLink: String;
    meal.map((meals) => (vidLink = meals.strYoutube));

    if (like === false) {
      var updatedFavorites = [...props.likeList, mealName];
      var updatedVids = [...props.vidList, vidLink];
      // if item is favorited prior to button click, then check if the item is in the favProducts/updatedFavorites list and filter it out if so
    } else if (like === true) {
      var updatedFavorites = [...props.likeList];
      var updatedVids = [...props.vidList];
      var filtered = updatedFavorites.filter((compare) => {
        return compare !== mealName;
      });
      var vidFiltered = updatedVids.filter((compare1) => {
        return compare1 !== vidLink;
      });
      updatedFavorites = filtered;
      updatedVids = vidFiltered;
    }

    // set the state of favProducts to updatedFavorites to be used in App component
    props.setLikeList(updatedFavorites);
    props.setVidList(updatedVids);
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

  return (
    <div
      tabIndex={0}
      className="result-box"
      aria-label="recipe box"
      data-testid="result"
      role="result-box"
    >
      {/* TODO: Add a div for each command in the history */}
      {/* Hint: You can use the map function to iterate over an array */}
      <div className="inner-box">
        <div>
          {meal.map((meals) => (
            <img
              className="image"
              src={meals.strMealThumb + "/preview"}
              alt={meals.strMeal + "image"}
              aria-label="button to generate recipe list"
            />
          ))}

          <button
            className="button-style"
            role="button"
            onClick={handleLike}
            aria-label="favorite button"
          >
            Favorite: {like ? "♥" : "♡"}
          </button>
          {/* <button
            className="dislike-button-style"
            role="button"
            onClick={handleDislike}
          >
            {dislike ? "✖" : "x"}
          </button> */}
        </div>
        <div className="margin-10px">
          {meal.map((meals, index) => (
            <div key={index}>
              <h4>
                <a
                  href={meals.strYoutube}
                  target="_blank"
                  aria-label="click on meal name for youtube video"
                >
                  {meals.strMeal}
                </a>
              </h4>
              <h5 aria-label="cuisine">{meals.strArea}</h5>
              <h5 id="category" aria-label="category">
                {meals.strCategory}
              </h5>
              <p id="ingredients" aria-label="ingredients">
                Ingredients:
              </p>
              <p>1. {meals.strIngredient1}</p>
              <p>2. {meals.strIngredient2}</p>
              <p>3. {meals.strIngredient3}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
