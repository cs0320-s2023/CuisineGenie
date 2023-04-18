# sprint-3-hearnest-jtran43
hearnest-jtran43 team's sprint-3 repo
## Project Details
Project Name: Sprint 3 Echo

Project Team: Henry Earnest (hearnest) & Jennifer Tran (jtran43)

Estimated time: 16 hours

Link: https://github.com/cs0320-s2023/sprint-3-hearnest-jtran43

## Design Choices

In this project, we used hashmaps to store error messages and outputs. All responses are serializations of a Map<String, Object> object. 
The CSVParser makes use of two interfaces: the CreatorFromRow and Reader interfaces. Their general characteristics provide all a parser 
needs to turn CSV rows into a list of the desired datatype. In this case, we are turning CSV rows into Strings.

We have 4 handler classes: LoadHandler, ViewHandler, SearchHandler, and WeatherHandler. In our Server class' main method, we set up these four handlers as endpoints, and then begin to listen for user requests to any endpoint. 

## Errors and Bugs 
N/A

## Tests

We tested the Load Handlers, Search Handler, View Handler, Weather Handler, CSV Searcher, and CSV Parser (comments on tests are in the testing suites)


## How to

First, the user or developer must Load a file. This can be done by running the main method in the Server class and opening http://localhost:3000 in 
a browser. To Load, users can http://localhost:3000/loadcsv?filepath=[ENTER CSV FILEPATH HERE]. To view, the endpoint /viewcsv with 
no parameters must be used. To Search, the user must input a target, and whether or not the file has a header in this format /search?target=[enter target
here]&hasHeader=[true or false]. There are also optional parameters like the Column index or label that the user can input (colIndex=[number]&colLabel=[String]. To use the Weather endpoint, the user must input the longitude and latitude in this format: /weather?lon=[enter number]&lat[enter number].





