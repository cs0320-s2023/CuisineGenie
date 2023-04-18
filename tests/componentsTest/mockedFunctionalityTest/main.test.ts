// All exports from main will now be available as main.X
import * as main from "./main";
import "@testing-library/jest-dom";
import { screen } from "@testing-library/dom";
import "@testing-library/jest-dom/extend-expect";
import userEvent from "@testing-library/user-event";

const startHTML = `<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Title</title>
    <link rel="stylesheet" href="../styles/main.css" type="text/css" />
  </head>
  <body>
    <!-- Tell the browser what to show if JavaScript/TypeScript is disabled. -->
    <noscript>This example requires JavaScript to run.</noscript>
    <!-- Prepare a region of the page to hold the entire REPL interface -->
    <div class="repl">
      <!-- Prepare a region of the page to hold the command history -->
      <div class="repl-history" data-testid="history"></div>
      <hr />
      <!-- Prepare a region of the page to hold the command input box -->
      <div class="repl-input">
        <p>
          Commands: mode, load_file [csv-file-path], view, search [column]
          [value], clear
        </p>
        <input
          type="text"
          class="repl-command-box"
          placeholder="Enter command here!"
        />
        <button type="button" class="button">Submit</button>
      </div>
    </div>
    <!-- Load the script! Note: the .js extension is because browsers don't use TypeScript
    directly. Instead, the author of the site needs to compile the TypeScript to JavaScript. -->
    <script type="module" src="../src/main.js"></script>
  </body>
</html>`;

let tryButton: HTMLElement;
let input1: HTMLElement;
let historyPage: HTMLElement;

// Setup! This runs /before every test function/
beforeEach(() => {
  // (1) Restore the program's history to empty
  main.clearHistory(); 

  // (2) Set up a mock document containing the skeleton that
  // index.html starts with. This is refreshed for every test.
  document.body.innerHTML = startHTML;

  // (3) Find the elements that should be present at the beginning
  // Using "getBy..." will throw an error if this element doesn't exist.
  tryButton = screen.getByText("Submit");
  input1 = screen.getByPlaceholderText("Enter command here!");
  historyPage = screen.getByTestId("history");
});

// Tests for each command
test("commandsTests", () => {
  // Mode
  expect(main.modeType).toBe(false);
  main.handleCommands("mode");
  expect(main.modeType).toBe(true);

  // View (without file loaded)
  main.handleCommands("view");
  expect(main.printTable()).toBe("No file found.");

  // Search (without file loaded)
  main.handleCommands("search 0 lemon")
  expect(main.printSearch("0", "lemon")).toBe("No file found.");

  // View and Search (with fruits.csv file loaded)
  main.handleCommands("load_file fruits.csv");
  expect(main.currFileName).toBe("fruits.csv");
  main.handleCommands("view");
  expect(main.printTable()).toBe(
    "<table><tr><td>lemon</td><td>yellow</td><td>citrus</td></tr><tr><td>strawberry</td><td>red</td><td>berry</td></tr><tr><td>cantaloupe</td><td>orange</td><td>melon</td></tr><tr><td>blueberry</td><td>blue</td><td>berry</td></tr><tr><td>watermelon</td><td>red</td><td>melon</td></tr><tr><td>lime</td><td>green</td><td>citrus</td></tr></table>"
  );
  main.handleCommands("search 0 lemon");
  expect(main.printSearch("0", "lemon")).toBe(
    "<table><tr><td>lemon</td><td>yellow</td><td>citrus</td></tr></table>"
  );

  // View and Search (with artists.csv file laoded)
  main.handleCommands("load_file artists.csv");
  expect(main.currFileName).toBe("artists.csv");
  main.handleCommands("view");
  expect(main.printTable()).toBe(
    "<table><tr><th>name</th><th>genre</th><th>song</th><th>year</th></tr><tr><td>taylor swift</td><td>pop</td><td>shake it off</td><td>2014</td></tr><tr><td>harry styles</td><td>pop</td><td>watermelon sugar</td><td>2019</td></tr><tr><td>beyonce</td><td>r&b</td><td>single ladies</td><td>2008</td></tr><tr><td>dolly parton</td><td>country</td><td>jolene</td><td>1974</td></tr></table>"
  );
  main.handleCommands("search genre pop");
  expect(main.printSearch("genre", "pop")).toBe(
    "<table><tr><td>taylor swift</td><td>pop</td><td>shake it off</td><td>2014</td></tr><tr><td>harry styles</td><td>pop</td><td>watermelon sugar</td><td>2019</td></tr></table>"
  );
});

// Tests for wrong number of arguments for each command
test("incorrectNumberOfArgsTests", () => {
  main.prepareButtonPress();
  main.handleCommands("mode extra words");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To switch mode, input "mode"'
  );

  main.prepareButtonPress();
  main.handleCommands("load_file too many extra words");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To load file, input "load_file [filename]".'
  );

  main.prepareButtonPress();
  main.handleCommands("view more stuff");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To view file, input "view"'
  );

  main.prepareButtonPress();
  main.handleCommands("search nothing");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To search file, input "search [command] [value]"'
  );

  main.prepareButtonPress();
  main.handleCommands("search too many things at once");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To search file, input "search [command] [value]"'
  );

  main.prepareButtonPress();
  main.handleCommands("");
  userEvent.click(tryButton);
  expect(historyPage).toHaveTextContent(
    'Incorrect number of arguments. To switch mode, input "mode"' // is this the correct print statement?
  );
});

// Tests for searching in the wrong file 
test("searchErrorTests", () => {
  main.handleCommands("load_file fruits.csv");
  main.handleCommands("search genre pop");
  expect(main.printSearch("genre", "pop")).toBe("No file found.");

  main.handleCommands("search 1 grapes");
  expect(main.printSearch("1", "grapes")).toBe("No file found.") // message printed in history should be "Search input is invalid."

  main.handleCommands("load_file artists.csv");
  main.handleCommands("search 0 lemon");
  expect(main.printSearch("0", "lemon")).toBe("No file found.");

  main.handleCommands("search name rihanna");
  expect(main.printSearch("name", "rihanna")).toBe("No file found.") // message printed in history should be "Search input is invalid."
});


test("is 1 + 1 = 2?", () => {
  expect(1 + 1).toBe(2);
});

// Notice: we're testing the keypress handler's effect on state and /nothing else/
//  We're not actually pressing keys!
//  We're not looking at what the console produces!
test("handleKeypress counting", () => {
  main.handleKeypress(new KeyboardEvent("keypress", { key: "x" }));
  expect(main.getPressCount()).toBe(1);
  main.handleKeypress(new KeyboardEvent("keypress", { key: "y" }));
  expect(main.getPressCount()).toBe(2);
});