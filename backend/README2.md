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

We have 4 handler classes: LoadHandler, ViewHandler, SearchHandler, and WeatherHandler. In our Server class' main method, we create 
a new Handler when a user uses its endpoint. 

## Errors and Bugs 
N/A

## Tests


## How to

First, the user or developer must Load a file. This can be done by running the main method in the Server class and opening http://localhost:3000 in 
a browser. To Load, users can http://localhost:3000/load?filepath=[ENTER CSV FILEPATH HERE]. 





