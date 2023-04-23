var fruits = {
    hasHeaders: false,
    headers: [],
    data: [
        ["lemon", "yellow", "citrus"],
        ["strawberry", "red", "berry"],
        ["cantaloupe", "orange", "melon"],
        ["blueberry", "blue", "berry"],
        ["watermelon", "red", "melon"],
        ["lime", "green", "citrus"],
    ],
};
var artists = {
    hasHeaders: true,
    headers: ["name", "genre", "song", "year"],
    data: [
        ["taylor swift", "pop", "shake it off", "2014"],
        ["harry styles", "pop", "watermelon sugar", "2019"],
        ["beyonce", "r&b", "single ladies", "2008"],
        ["dolly parton", "country", "jolene", "1974"],
    ],
};
var mockMap = new Map([
    ["fruits.csv", fruits],
    ["artists.csv", artists],
]);
var mockSearchMap = new Map([
    ["artists.csv search name beyonce", [["beyonce", "r&b", "single ladies", "2008"]]],
    ["fruits.csv search 0 lemon", [["lemon", "yellow", "citrus"]]],
    ["fruits.csv search 0 LEMON", [["lemon", "yellow", "citrus"]]],
    [
        "artists.csv search genre pop",
        [
            ["taylor swift", "pop", "shake it off", "2014"],
            ["harry styles", "pop", "watermelon sugar", "2019"],
        ],
    ],
]);
export { mockMap, fruits, artists, mockSearchMap };