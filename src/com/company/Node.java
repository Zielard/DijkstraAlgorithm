package com.company;

import java.util.HashMap;
import java.util.Map;

public class Node {
    Graph graph;
    String name;
    Boolean visit = false;
    Map<String, Pair<Node,Integer >> mapNeighbors = new HashMap<String, Pair<Node,Integer > >();

    public Node(String nameIn,Graph graphIn)
    {
        graph = graphIn;
        name = nameIn;
    }

    public void print()
    {
        for (Map.Entry<String,  Pair<Node,Integer >> entry : mapNeighbors.entrySet()) {
            System.out.print(entry.getKey() + entry.getValue().getValue()+ ", ");
        }
    }

    public void addNeighbor(String str, Node node, Integer waga)
    {
        Pair<Node,Integer > temp = mapNeighbors.get(str);
        if( temp == null)
        {
            mapNeighbors.put(str, new Pair(node,waga));
        }
    }

    public void addNeighbor(String str, Integer waga)
    {
        Node temp = graph.allNodes.get(str);
        if( temp == null)
        {
            Node newNode = new Node(str,graph);
            graph.allNodes.put(str,newNode);
            mapNeighbors.put(str,new Pair(graph.allNodes.get(str),waga));

            //dodanie do sasiada instacji "this" poniewaz moze nie widziec ze ma tez sasiada
            Node thisNode = graph.allNodes.get(this.name);
            if( thisNode != null)
            {
                graph.allNodes.get(str).addNeighbor(name,thisNode, waga);
            }
        }
        else
        {
            //graph.allNodes.put(str,temp);
            mapNeighbors.put(str,new Pair(graph.allNodes.get(str),waga));

            //dodanie do sasiada instacji "this" poniewaz moze nie widziec ze ma tez sasiada
            Node thisNode = graph.allNodes.get(this.name);
            graph.allNodes.get(str).addNeighbor(name,thisNode, waga);
        }

    }
}
