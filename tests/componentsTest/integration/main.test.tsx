
import "@testing-library/jest-dom";
import { render, screen, waitFor, within } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { REPLFunction } from "../../../src/apiCaller/REPLFunction";
import {App, unregisterCommand, registerCommand, checkCommand} from "../../../src/App"
import HistoryBox from "../../../src/components/HistoryBox";
import { mockLoad, mockSearch, mockView, resetFile, fileContent, filepath } from "./mocking/mockAPICalls";



let history: HTMLElement;
let input: HTMLInputElement;
let button: HTMLElement;



// replace REPL functions with mock functions
function initFuncMap() {
    unregisterCommand("load_file");
    unregisterCommand("view");
    unregisterCommand("search");
    registerCommand("mock_load", mockLoad);
    registerCommand("mock_view", mockView);
    registerCommand("mock_search", mockSearch);
    
}

// set up test environment
beforeEach(() => {
    const utils = render(<App />)
    
    initFuncMap();
    resetFile();
    history = screen.getByRole("history-box")
    input = screen.getByRole("input")
    button = screen.getByRole("button");
})

test("loads and displays header", async () => {
    expect(screen.getByText(/REPL/i)).toBeInTheDocument();
});

//tests to see if your commands are registered
test("register command", async () => {
    expect(checkCommand("mock_load")).toBe(true);
    expect(checkCommand("mock_view")).toBe(true);
    expect(checkCommand("mock_search")).toBe(true);
    expect(checkCommand("mock_invalid")).toBe(false);
});


//tests unregistering commands
test("register unique command after unreg", async () => {
    unregisterCommand("mock_load")
    registerCommand("mock_load", mockLoad);
    expect(checkCommand("mock_load")).toBe(true);
});


test("mode brief", async () => {
    //check the mode is initially set to brief
    let user = userEvent.setup();
    await user.type(input, "mode brief");
    await user.click(button);
    expect(await screen.findByText("Mode is now brief.")).toBeInTheDocument();
    const command = screen.getByRole("output-content");
    expect(command).toBeVisible();

});

test("mode verbose", async () => {
    //check the mode is set to verbose
    let user = userEvent.setup();
    await user.type(input, "mode verbose");
    await user.click(button);
    expect(await screen.findByText("Mode is now verbose.")).toBeInTheDocument();
    const verbose = screen.getByRole("verbose-temp");
    expect(verbose).toBeVisible();
    const command = screen.getByRole("output-content");
    expect(command).toBeVisible();

});


//tests if load with no params gives error
test("load no params", async () => {
    let user = userEvent.setup();
    await user.type(input, "load");
    await user.click(button);
    expect(await screen.findByText("Invalid number of parameters. Please type in the form: load [filepath].")).toBeInTheDocument();
    const output = screen.getByRole("output-content");
    expect(output).toBeVisible();

});


//tests correct load
test("load ten-stars", async () => {
    let user = userEvent.setup();
    await user.type(input, "load ten-stars.csv");
    await user.click(button);
    expect(await screen.findByText("ten-stars.csv was loaded.")).toBeInTheDocument();
    const output = screen.getByRole("output-content");
    expect(output).toBeVisible();
});

//tests extra params for load
test("load extra params", async () => {
    let user = userEvent.setup();
    await user.type(input, "load ten-stars.csv woah too many params");
    await user.click(button);
    expect(await screen.findByText("Invalid number of parameters. Please type in the form: load [filepath].")).toBeInTheDocument();
    const output = screen.getByRole("output-content");
    expect(output).toBeVisible();
});

//tests verbose and load
test("mode then load ten-stars", async () => {
    let user = userEvent.setup();

    await user.type(input, "mode verbose");
    await user.click(button);
    expect(await screen.findByText("Mode is now verbose.")).toBeInTheDocument();
    const verbose = screen.getByRole("verbose-temp");
    expect(verbose).toBeVisible();
    await user.type(input, "load ten-stars.csv");
    await user.click(button);
    expect(await screen.findByText("ten-stars.csv was loaded.")).toBeInTheDocument();
});

//tests view on no load
test("view no file load", async () => {
    let user = userEvent.setup();
    await user.type(input, "mock_view");
    await expect(input.value).toBe("mock_view");
    await user.click(button);
    expect(
      await screen.findByText("Please load a valid/non-empty csv to view, using /loadcsv")
    ).toBeInTheDocument();

});
//tests load then view
test("load view", async () => {
    let user = userEvent.setup();
    await user.type(input, "mock_load mockCSVSimple3");
    await expect(input.value).toBe("mock_load mockCSVSimple3");
    await user.click(button);
    expect(
      await screen.findByText("mockCSVSimple3 was loaded.")
    ).toBeInTheDocument();
    await user.type(input, "mock_view");
    await expect(input.value).toBe("mock_view");
    await user.click(button);

    const table = document.getElementsByTagName("table");
    expect(table).not.toBeNull();

});


//tests error search
test("false target search", async () => {
    let user = userEvent.setup();
    await user.type(input, "mock_load mockCSVSimple3");
    await expect(input.value).toBe("mock_load mockCSVSimple3");
    await user.click(button);
    expect(
      await screen.findByText("mockCSVSimple3 was loaded.")
    ).toBeInTheDocument();
    await user.type(input, "search fake fake fake");
    
    await user.click(button);

    const table = document.getElementsByTagName("table");
    expect(table).not.toBeNull();
    expect(
        await screen.findByText("Error")
      ).toBeInTheDocument();

});

//test incorrect params search
test(" 3 param search", async () => {
    let user = userEvent.setup();
    await user.type(input, "mock_load mockStarCSV");
    await expect(input.value).toBe("mock_load mockStarCSV");
    await user.click(button);
    expect(
      await screen.findByText("mockStarCSV was loaded.")
    ).toBeInTheDocument();
    await user.type(input, "mock_search 10 Sol");
    
    await user.click(button);
    expect(
        await screen.findByText("Invalid number of parameters")
      ).toBeInTheDocument();
});

//test user search
test(" Sol search", async () => {
    let user = userEvent.setup();
    await user.type(input, "mock_load mockStarCSV");
    await expect(input.value).toBe("mock_load mockStarCSV");
    await user.click(button);
    expect(
      await screen.findByText("mockStarCSV was loaded.")
    ).toBeInTheDocument();
    await user.type(input, "mock_search 10 Sol false");
    
});






