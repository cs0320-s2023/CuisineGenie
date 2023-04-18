import { csvFile, mockMap, mockSearchMap } from "./mockedJson.js";
// The window.onload callback is invoked when the window is first loaded by the browser
window.onload = () => {
  prepareKeypress();
  prepareButtonPress();
};

/**
 * This function sees if there is a valid key being presed by the user and handles the action accordingly
 */
function prepareKeypress() {
  // As far as TypeScript knows, there may be *many* elements with this class.
  const maybeInputs: HTMLCollectionOf<Element> =
    document.getElementsByClassName("repl-command-box");
  // Assumption: there's only one thing
  const maybeInput: Element | null = maybeInputs.item(0);
  if (maybeInput == null) {
    console.log("Couldn't find input element");
  } else if (!(maybeInput instanceof HTMLInputElement)) {
    console.log(`Found element ${maybeInput}, but it wasn't an input`);
  } else {
    maybeInput.addEventListener("keypress", handleKeypress);
  }
}

/**
 * This function clears everything that the program has printed in the scrollable history display.
 * It also wipes out all files that have been loaded and sets the mode type back to default (brief).
 */
function clearHistory() {
  currFile = undefined;
  currFileName = "";
  modeType = false;
  var maybeToPrint: HTMLCollectionOf<Element> =
    document.getElementsByClassName("repl-history");
  var toPrint: Element | null = maybeToPrint.item(0);

  if (toPrint == null) {
    console.log("Couldn't find toPrintSearch element");
  } else if (!(toPrint instanceof HTMLDivElement)) {
    console.log(`Found element ${toPrint}, but it wasn't a div`);
  } else {
    toPrint.innerHTML = "<br>";
  }
}

let pressCount = 0;
function getPressCount() {
  return pressCount;
}

/**
 * This function keeps track of how many times and what keys have been pressed.
 * @param event A KeyboardEvent that represents a user's interaction clicking on their keyboard
 */
function handleKeypress(event: KeyboardEvent) {
  // The event has more fields than just the key pressed (e.g., Alt, Ctrl, etc.)
  pressCount = pressCount + 1;
  console.log(
    `key pressed: ${event.key}. ${getPressCount()} presses seen so far.`
  );
}

/**
 * This function sees if there is a valid button being presed by the user and handles the action accordingly
 */
function prepareButtonPress() {
  const maybeButtons = document.getElementsByClassName("button");
  const maybeButton = maybeButtons.item(0);

  if (maybeButton == null) {
    console.log("Couldn't find button");
  } else if (!(maybeButton instanceof HTMLButtonElement)) {
    console.log(`Found element, but it wasn't a button`);
  } else {
    maybeButton.addEventListener("click", handleButtonPress);
  }
}

let buttonCount = 0;
let modeType = false; // false is brief, true is verbose
let currFile: csvFile | undefined;
let currFileName: string;

/**
 * This function displays the history of each command that the user inputs and its output. The way the
 * message is structured depends on what mode the applicatoin is in, brief or verbose.
 * @param message A string that represents the output of the command
 * @param command A string that represents the command that the user inputs
 */
function printMessage(message: String, command: String) {
  var maybeToPrint: HTMLCollectionOf<Element> =
    document.getElementsByClassName("repl-history");
  var toPrint: Element | null = maybeToPrint.item(0);

  if (toPrint == null) {
    console.log("Couldn't find toPrintMessage element");
  } else if (!(toPrint instanceof HTMLDivElement)) {
    console.log(`Found element ${toPrint}, but it wasn't a div`);
  } else {
    var finalPrint;

    if (modeType) {
      finalPrint = "Command: " + command + "<br/>" + "Output: " + message;
    } else {
      finalPrint = message;
    }
    console.log(finalPrint);
    toPrint.innerHTML = toPrint.innerHTML + "<br>" + finalPrint + "<br/>";
  }
}

/**
 * This function returns the data displayed in the JSON file of a file that has been loaded in, converting it from
 * its 2D array format to an HTML table.
 * @returns A string representing the file loaded in, in the form of an HTML table. If no filed was loaded in, it
 * returns a "No file found." message.
 */
function printTable(): string {
  if (currFile !== undefined) {
    var finalTable = "<table>";

    if (currFile.hasHeaders) {
      finalTable += "<tr>";
      for (var col in currFile.headers) {
        finalTable += "<th>" + currFile.headers[col] + "</th>";
      }
      finalTable += "</tr>";
    }
    for (var row = 0; row < currFile.data.length; row++) {
      finalTable += "<tr>";
      for (var col in currFile.data[row]) {
        finalTable += "<td>" + currFile.data[row][col] + "</td>";
      }
      finalTable += "</tr>";
    }

    finalTable += "</table>";

    var maybeToPrint: HTMLCollectionOf<Element> =
      document.getElementsByClassName("repl-history");
    var toPrint: Element | null = maybeToPrint.item(0);

    if (toPrint == null) {
      console.log("Couldn't find toPrintTable element");
    } else if (!(toPrint instanceof HTMLDivElement)) {
      console.log(`Found element ${toPrint}, but it wasn't a div`);
    } else {
      toPrint.innerHTML = toPrint.innerHTML + "<br>" + finalTable;
    }
    console.log("here");
    console.log(finalTable);
    return finalTable;
  } else {
    printMessage(
      'No file loaded. To load file, input "load_file [filename]"',
      "ERROR"
    );
  }
  return "No file found.";
}

/**
 * This function searches through the JSON file of the file loaded in, given a column identifier and a value.
 * If the value is found, this function reformats the 2D array into an HTML table to display the search result.
 * @param column A string that represents the column to narrow down the search. It can be either a column index or
 * a column name if one exists.
 * @param value A string that represents the target value to be searched for.
 * @returns A string that represents the row(s), in the form of a HTML table, containing the targeted value after 
 * it has been found.
 */
function printSearch(column: string, value: string): string {
  console.log(currFile);

  if (currFile !== undefined) {
    let input = currFileName + " search " + column + " " + value;
    console.log(input);
    if (mockSearchMap.has(input)) {
      printMessage("Search result printed below.", "search");
      var finalTable = "<table>";
      var rows = mockSearchMap.get(input);
      if (rows !== undefined) {
        for (var i = 0; i < rows.length; i++) {
          finalTable += "<tr>";
          for (var j = 0; j < rows[i].length; j++) {
            finalTable += "<td>" + rows[i][j] + "</td>";
          }
          finalTable += "</tr>";
        }
      }
      finalTable += "</table>";

      var maybeToPrint: HTMLCollectionOf<Element> =
        document.getElementsByClassName("repl-history");
      var toPrint: Element | null = maybeToPrint.item(0);

      if (toPrint == null) {
        console.log("Couldn't find toPrintSearch element");
      } else if (!(toPrint instanceof HTMLDivElement)) {
        console.log(`Found element ${toPrint}, but it wasn't a div`);
      } else {
        toPrint.innerHTML = toPrint.innerHTML + "<br>" + finalTable;
      }

      return finalTable;
    } else {
      printMessage("Search input is invalid.", "ERROR");
    }
  } else {
    console.log("File was not loaded.");
    printMessage(
      'No file loaded. To load file, input "load_file [filename]"',
      "ERROR"
    );
  }

  return "No file found.";
}

/**
 * This function checks if a button has been pressed (in this applicaation, that would be the "Submit" button)
 * and handles the command entered into the input box after the button has been pressed.
 * @param event A MouseEvent that represents a click of a button
 */
function handleButtonPress(event: MouseEvent) {
  buttonCount = buttonCount + 1;

  const maybeButtons: HTMLCollectionOf<Element> =
    document.getElementsByClassName("repl-command-box");

  const maybeButton: Element | null = maybeButtons.item(0);
  
  if (maybeButton == null) {
    console.log("Couldn't find input element");
  } else if (!(maybeButton instanceof HTMLInputElement)) {
    console.log(`Found element ${maybeButton}, but it wasn't an input`);
  } else {
    handleCommands(maybeButton.value);
  }

  console.log(`Times button was pressed` + buttonCount);
}

/**
 * This function handles the different commands that a user might input, including "mode", "load_file", "view",
 * "search", and "clear". It also checks that the format of the command is correct and notifies the user if it
 * is not formatted correctly.
 * @param inputCommand A string that represents the command that the user types into the input box.
 */
function handleCommands(inputCommand: string) {
  const inputArray: string[] = inputCommand.split(" ", 4);
  switch (inputArray[0]) {
    case "mode": {
      if (inputArray.length !== 1) {
        printMessage(
          'Incorrect number of arguments. To switch mode, input "mode" ',
          "Invalid Command."
        );
      } else {
        modeType = !modeType;
        if (modeType == false) {
          // brief
          printMessage("Mode switched to brief.", "mode");
        } else {
          // verbose
          printMessage("Mode switched to verbose.", "mode");
        }
      }
      break;
    }
    case "load_file": {
      if (inputArray.length !== 2) {
        printMessage(
          'Incorrect number of arguments. To load file, input "load_file [filename]". ',
          "Invalid Command."
        );
      } else {
        currFileName = inputArray[1].toLowerCase();
        if (mockMap.has(currFileName)) {
          currFile = mockMap.get(currFileName);
          console.log(currFile);
          printMessage(currFileName + " successfully loaded.", "load_file");
        } else {
          printMessage("Incorrect file path.", "load_file");
        }
      }
      break;
    }
    case "view": {
      if (inputArray.length !== 1) {
        printMessage(
          'Incorrect number of arguments. To view file, input "view" ',
          "Invalid Command."
        );
      } else {
        printMessage("View printed below.", "view");
        printTable();
      }
      break;
    }
    case "search": {
      if (inputArray.length !== 3) {
        printMessage(
          'Incorrect number of arguments. To search file, input "search [command] [value]"',
          "Invalid Command."
        );
      } else {
        printSearch(inputArray[1].toLowerCase(), inputArray[2].toLowerCase());
      }
      break;
    }
    case "clear": {
      if (inputArray.length == 1) {
        clearHistory();
      }
      break;
    }
    default: {
      printSearch("Invalid command.", "ERROR");
      break;
    }
  }
}

export {
  handleKeypress,
  prepareKeypress,
  getPressCount,
  handleButtonPress,
  prepareButtonPress,
  handleCommands,
  printTable,
  printSearch,
  clearHistory,
  modeType,
  currFileName,
};