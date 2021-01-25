package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Graph graph = new Graph();


//        graph.addNode("A" ,"HOME" , 3);
//        graph.addNode("A" ,"D" , 3);
//        graph.addNode("HOME" ,"B" ,2);
//        graph.addNode("HOME" ,"C" , 5);
//        graph.addNode("B" ,"D" , 1);
//        graph.addNode("B" ,"E" , 6 );
//        graph.addNode("C" ,"E" , 2 );
//        graph.addNode("HOME" ,"C" , 5);
//        graph.addNode("E" ,"F" , 1);
//        graph.addNode("D" ,"F" , 4);
//        graph.addNode("School" ,"F" , 2);
//        graph.addNode("School" ,"E" , 4);
//
//        graph.dijkstra("HOME","School");
        //graph.printNeighbors();

        graph.addNode("0" ,"1" , 4);
        graph.addNode("0" ,"7" , 8);
        graph.addNode("1" ,"7" , 11);

        graph.addNode("7" ,"8" , 7);

        graph.addNode("1" ,"2" , 8);
        graph.addNode("2" ,"8" , 2);
        graph.addNode("8" ,"6" , 6);
        graph.addNode("7" ,"6" , 1);

        graph.addNode("2" ,"3" , 7);
        graph.addNode("2" ,"5" , 4);
        graph.addNode("6" ,"5" , 2);

        graph.addNode("3" ,"5" , 14);

        graph.addNode("3" ,"4" , 9);
        graph.addNode("5" ,"4" , 10);

        graph.dijkstra("0","4");
        graph.printDijkstra();
    }
}
