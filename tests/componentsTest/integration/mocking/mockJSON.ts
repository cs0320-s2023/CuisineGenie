const solSearch = [["0","Sol","0","0","0"]];
const solSearch2 = [["0","0","Sol","0","0"]];
const nameSearch =  [["My", "name", "is", "jsonnn"]];


const mockCSVSimple = [
    ["1", "2", "3", "4", "5"],
    ["The", "song", "remains", "the", "same."]
]

const mockCSVTwo = [
    ["1","2","3","4"],
    ["The", "song", "remains", "the"],
    ["My", "name", "is", "jsonnn"]
]

const mockCSVSimple3 = [
    ["first", "last"],
    ["Tim", "Nelson"]
]

const mockStarCSV = [
    ["0","Sol","0","0","0"],
    ["0", "0", "Sol", "0", "0"],
    ["1","","282.43485","0.00449","5.36884"]
]

const mockCSVEmpty = [[]];




var mockViewMap = new Map([
    ["mockCSVSimple", mockCSVSimple],
    ["mockCSVTwo", mockCSVTwo],
    ["mockStarCSV", mockStarCSV],
    ["mockCSVEmpty", mockCSVEmpty], 
    ["mockCSVSimple3", mockCSVSimple3]
]);

var mockSearchMap = new Map([
    ["Sol", solSearch]

]);

var mockSearchMap2 = new Map([
    ["Sol", solSearch2]

]);


var mockSearchWord = new Map([
    ["jsonnn", nameSearch]
]);

let extraSearchMap = new Map<string | number, Map<string, string[][]>>();


extraSearchMap.set("3", mockSearchMap2);
extraSearchMap.set("b", mockSearchMap);
extraSearchMap.set("c", mockSearchMap2);
extraSearchMap.set("2", mockSearchMap);
extraSearchMap.set("4", mockSearchWord)

// var extraSearchMap = new Map([
//     [2, ["Sol", solSearch]],
//     [3, ["Sol", solSearch]],
//     ["x", ["Sol", solSearch]]

// ])

//let mockSearchMap = new Map<string, string[]>();
//mockSearchMap.set("Sol", solSearch);
//mockSearchMap.set("cactus", cactusSearch);
export { mockViewMap, solSearch, nameSearch, mockSearchMap, extraSearchMap, mockCSVEmpty, mockSearchWord,  mockStarCSV, mockCSVSimple, mockCSVTwo};