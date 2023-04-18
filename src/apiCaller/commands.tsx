import { REPLFunction } from "./REPLFunction";

/**
 * Load function that calls on the backend API Server that runs load file and
 * returns whether the file was successfully loaded or not (error).
 * @param args inputText array that was passed in from input box component
 * @returns a string Promise that the REPLFunction requires to return that returns
 * filepath was loaded or error depending on the
 */
async function load(args: string[]): Promise<string> {
  if (args.length == 2) {
    return fetch(`http://localhost:61747/loadcsv?filepath=${args[1]}`)
      .then((res) => res.json())
      .then((data) => {
        if (data["result"] === "success") {
          return data["filepath"] + " was loaded.";
        } else {
          return data["errorMessage"];
        }
      })
      .catch((e) => {
        return e;
      });
  }
  return "Invalid number of parameters. Please type in the form: load [filepath].";
}
/**
 * View function that calls on the backend API Server that prints the loaded file.
 * @param args inputText array that was passed in from input box component
 * @returns a string Promise that the REPLFunction requires to return that returns
 * filepath was able to be viewed properly
 */
async function view(args: string[]): Promise<string> {
  console.log("view");
  if (args.length == 1) {
    return fetch(`http://localhost:61747/viewcsv`)
      .then((res) => res.json())
      .then((data) => {
        if (data["result"] === "success") {
          var finalTable = "<table>";
          for (var row = 0; row < data["data"].length; row++) {
            finalTable += "<tr>";
            for (var col in data["data"][row]) {
              finalTable += "<td>" + data["data"][row][col] + "</td>";
            }
            finalTable += "</tr>";
          }

          finalTable += "</table>";
          return finalTable;
        } else {
          return data["errorMessage"];
        }
      });
  }
  return "Invalid number of parameters. Please type in the form: view";
}

/**
 * Search function that calls on the backend API Server that prints the rows of
 * items that can be searched for in the file.
 * @param args inputText array that was passed in from input box component
 * @returns a string Promise that the REPLFunction requires to return that returns
 * filepath was able to be viewed properly
 */
async function search(args: string[]): Promise<string> {
  if (args.length == 4) {
    return fetch(
      `http://localhost:61747/searchcsv?colID=${args[1]}&target=${args[2]}&hasHeader=${args[3]}`
    )
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        if (data["result"] === "success") {
          var finalTable = "<table>";
          for (var row = 0; row < data["data"].length; row++) {
            finalTable += "<tr>";
            for (var col in data["data"][row]) {
              finalTable += "<td>" + data["data"][row][col] + "</td>";
            }
            finalTable += "</tr>";
          }

          finalTable += "</table>";

          return finalTable;
        } else {
          return data["errorMessage"];
        }
      })
      .catch((e) => {
        return e;
      });
  }
  return "Invalid number of parameters.";
}

/**
 * Mode function that calls on the backend API Server that switches mode depending on argument.
 * @param args inputText array that was passed in from input box component
 * @returns a string Promise that the REPLFunction requires to return that returns
 * filepath was able to be viewed properly
 */
async function mode(args: string[]): Promise<string> {
  if (args.length == 2) {
    if (args[1] === "verbose") {
      return "Mode is now verbose.";
    } else if (args[1] === "brief") {
      return "Mode is now brief.";
    } else {
      return "Invalid second argument.";
    }
  }
  return "Invalid number of parameters. Please type in the form: search [target] [hasHeaders]";
}

//export command functions to be used in other files publicly
export { load, view, search, mode };
