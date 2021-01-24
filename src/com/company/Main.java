package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Graph graph = new Graph();


        graph.addNode("A" ,"HOME" , 3);
        graph.addNode("A" ,"D" , 3);
        graph.addNode("HOME" ,"B" ,2);
        graph.addNode("HOME" ,"C" , 5);
        graph.addNode("B" ,"D" , 1);
        graph.addNode("B" ,"E" , 6 );
        graph.addNode("C" ,"E" , 2 );
        graph.addNode("HOME" ,"C" , 5);
        graph.addNode("E" ,"F" , 1);
        graph.addNode("D" ,"F" , 4);
        graph.addNode("School" ,"F" , 2);
        graph.addNode("School" ,"E" , 4);

        graph.dijkstra("HOME","School");
        //dijkstra.print();
    }
}
