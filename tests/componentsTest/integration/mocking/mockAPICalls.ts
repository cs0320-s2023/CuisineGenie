import { REPLFunction } from "../../../../src/apiCaller/REPLFunction"
import { extraSearchMap, mockViewMap } from "./mockJSON";



let fileContent : string[][];
let filepath : string;



const mockLoad : REPLFunction = function (args: string[]) : Promise<string> {
    return new Promise((resolve, reject) => {
        if(args.length == 2){
            var given : string;
            given = args[1];
            if(mockViewMap.has(given)){
                filepath = given
                const contents = mockViewMap.get(filepath);
                if(contents != null){
                    fileContent == mockViewMap.get(filepath);
                    resolve(filepath + " was loaded.");
                }
            }
        }
        else{
            resolve("Please supply a valid filepath. Current supported filepaths: [empty.csv, stars.csv, ten-stars.csv]")
        }
    })
};

const mockView : REPLFunction = function (args: string[]) : Promise<string> {
    return new Promise((resolve, reject) => {
        if(filepath == null || fileContent[0].length ==0){
            resolve("Please load a valid/non-empty csv to view, using /loadcsv")
        }else{
            var finalTable = "<table>";
                for (var row = 0; row < fileContent.length; row++) {
                    finalTable += "<tr>";
                    for (var col = 0; col <  fileContent[row].length; col++) {
                    finalTable += "<td>" + fileContent[row][col] + "</td>";
                    }
                    finalTable += "</tr>";
                }
                finalTable += "</table>";
                resolve(finalTable);
        }
    
       })
};

function createTable(activeFile: (number[] | string[])[] | undefined) {
    let result = "<table border=1>";
    if (activeFile === undefined) {
      return "There is no CSV Data to display. Please load another CSV!";
    } else {
      for (let i = 0; i < activeFile.length; i++) {
        result += "<tr>";
        for (let j = 0; j < activeFile[i].length; j++) {
          result += "<td >" + activeFile[i][j] + "<td>";
        }
        result += "</tr>";
      }
      result += "<table>";
      return result;
    }
  }

const mockSearch : REPLFunction = function (args: string[]) : Promise<string> {
    return new Promise((resolve, reject) => {
        if(args.length != 4){
            resolve("Invalid number of parameters")
        }else{
            const colID = args[1];
            const target = args[2];
            const header = args[3];
            if(extraSearchMap.has(colID)){
                let innerMap = extraSearchMap.get(colID);
                if(innerMap !== undefined){
                    if(innerMap.has(target)){
                        let row = innerMap.get(target);
                        if (row !== undefined) {
                            if (row === null || row === undefined) {
                              return "No row was found in the CSV.";
                            } else{
                                return createTable(row);
                            }

                    }else{
                        return "The column does not exist. Provide the valid arguments search[columnNumber][word].";
                    }
                } } else {
                    return "Invalid word. Spell the word correctly (case-sensitive), ensure you loaded the file-right, and have the right column number!"
            }
        }
        }
    
       })
};

 function resetFile() {
    fileContent = [[]]
    filepath = ""; 
  }

export{mockLoad, mockView, mockSearch, resetFile, fileContent, filepath}