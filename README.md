city-graph-path
===============


This project is an exercise and test of **Dijkstra's algorithm** for shortest path.  
[http://en.wikipedia.org/wiki/Dijkstra's_algorithm](http://en.wikipedia.org/wiki/Dijkstra's_algorithm)

It uses javax.swing library to draws the network road, reads the city graph from a file (with an adjacency matrix) and renders it as regular geometric shape, with the nodes represent the crossings and the lines representing the streets.
![Image](http://www.mccalv.com/city-graph-path/city-path.png) 

The simulation involves moving cars (police cars) through the graph using the shortest path. 
## Implementation 
The central implementation uses a **PriorityQueue** for the unset node and
Set for set ones.
The Road Network is represented by a triangular adjacency matrix.

## Code
mvn  eclipse:clean eclipse:eclipse  
Import the project into Eclipse as existing project.

