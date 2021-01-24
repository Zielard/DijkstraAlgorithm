package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<String, Node> allNodes = new HashMap<String, Node>();
    public Map<String, Pair<List<Node>,Integer > > pathsForNodes = new HashMap<String, Pair<List<Node>,Integer > >();

    public Graph() { }

    public void print()
    {
        for (Map.Entry<String, Node> entry : allNodes.entrySet()) {

            System.out.print(entry.getKey() + ":");
            entry.getValue().print();
            System.out.println();
        }

    }

    public void searchNode()
    {

    }

    public void dijkstra(String startNode, String endNode)
    {
        Integer inf = (int)Double.POSITIVE_INFINITY;
        Node currentNode = allNodes.get(startNode);
        if(currentNode != null)
        {
            int waga = 0;
            boolean flagCheck = false;
            boolean flagEnd = true;
            Node lastNode = currentNode;
            int lastWaga = 0;
            List<Node> tempList = new ArrayList<Node>();

            for (Map.Entry<String, Node> entry : allNodes.entrySet())
            {
                pathsForNodes.put(entry.getKey(), new Pair<>(new ArrayList<Node>(), inf));
            }
            pathsForNodes.put(startNode, new Pair<>(new ArrayList<Node>(), 0));


            while(flagEnd)
            {
                currentNode = lastNode;

                //odwiedzilem ten wierzcholek
                tempList.add(currentNode);
                currentNode.visit = true;
                waga += lastWaga;
                for (Map.Entry<String,  Pair<Node,Integer >> entry : currentNode.mapNeighbors.entrySet())
                {
                    if(entry.getValue().getKey() != null)
                    {
                        if(entry.getValue().getKey().visit == false)
                        {
                            if(inf == pathsForNodes.get(entry.getKey()).getValue())
                            {
                                //dodaje do listy nowy wieszcholek
                                List<Node> copy = new ArrayList<>(tempList);
                                lastWaga = entry.getValue().getValue();
                                copy.add(entry.getValue().getKey());
                                pathsForNodes.put(entry.getKey(), new Pair<>(copy, waga + entry.getValue().getValue()));

                                //zaznaczam go sobie jako ostatnio odwiedzonego
                                lastNode = entry.getValue().getKey();

                                //contiune main loop
                                flagCheck = true;
                            }
                        }
                    }
                }


                if(flagCheck == true)
                {
                    flagCheck = false;
                }
                else
                {
                    flagEnd = false;
                }
            }

            //wracam ta sama sciezka co szedlem
            for(int i=tempList.size()-1;i>0; i--)
            {
                currentNode = tempList.get(i);
                waga = pathsForNodes.get(currentNode.name).waga;
                for (Map.Entry<String,  Pair<Node,Integer >> entry : currentNode.mapNeighbors.entrySet())
                {
                    if((pathsForNodes.get(entry.getKey()).getValue() + currentNode.mapNeighbors.get(entry.getKey()).getValue()) < waga)
                    {
                        //dodaje do listy nowy wieszcholek
                        List<Node> copy = new ArrayList<>(pathsForNodes.get(entry.getKey()).getKey());
                        copy.add(currentNode);
                        int newWaga = pathsForNodes.get(entry.getKey()).getValue() + currentNode.mapNeighbors.get(entry.getKey()).getValue();
                        String nodeTest = entry.getKey();

                        pathsForNodes.put(currentNode.name ,new Pair<>(copy, newWaga));
                        //zaznaczam go sobie jako ostatnio odwiedzoneo
                        lastNode = entry.getValue().getKey();
                        int trappp = 0;
                    }
                    if((pathsForNodes.get(currentNode.name).getValue() + currentNode.mapNeighbors.get(entry.getKey()).getValue()) < pathsForNodes.get(entry.getKey()).getValue())
                    {
                        //dodaje do listy nowy wieszcholek
                        List<Node> copy = new ArrayList<>(pathsForNodes.get(currentNode.name).getKey());
                        copy.add(entry.getValue().getKey());
                        int newWaga = pathsForNodes.get(currentNode.name).getValue() + currentNode.mapNeighbors.get(entry.getKey()).getValue();
                        String nodeTest = entry.getKey();

                        pathsForNodes.put(entry.getKey() ,new Pair<>(copy, newWaga));
                        //zaznaczam go sobie jako ostatnio odwiedzoneo
                        lastNode = entry.getValue().getKey();
                        int trappp = 0;
                    }
                }
            }
        }
        else
        {
            System.out.print("Wierzchołek nie istnieje");
        }

        for (Map.Entry<String, Pair<List<Node>, Integer>> entry : pathsForNodes.entrySet())
        {
            System.out.print(entry.getKey() + ": ");
            List<Node> TempListNode = entry.getValue().getKey();
            for (int i = 0; i < TempListNode.size(); i++) {
                System.out.print(TempListNode.get(i).name + ", ");
            }
            System.out.println(" W: " + entry.getValue().getValue());
        }


    }
    public void addNode(String startNode, String endNode , Integer waga)
    {
        //pobieram pierwszy wierzcholek
        Node tempStart = allNodes.get(startNode);

        //jesli wierzcholka nie ma w liscie
        if(tempStart == null)
        {
            //tworze brakujacy wierzcholek
            Node newNode = new Node(startNode,this);

            //sprawdzam czy drugi wierzcholek istnieje
            Node tempEnd = allNodes.get(endNode);

            //jesli nie istnieje to go tworze
            if(tempEnd == null)
            {
                Node newEndNode = new Node(endNode,this);

                //dodaje startowy i konicowy wierzcholek do listy
                allNodes.put(startNode,newNode);
                allNodes.put(endNode,newEndNode);

                //łącze te wierzchołki sasiedztwem
                allNodes.get(startNode).addNeighbor(endNode, waga);
                allNodes.get(endNode).addNeighbor(startNode, waga);
            }
            else
            {
                allNodes.put(startNode,newNode);
                newNode.addNeighbor(endNode, waga);
            }

        }
        //jesli wierzcholek jest w liscie
        else
        {
            //sprawdzamy czy konicowy tez jest
            Node tempEnd = allNodes.get(endNode);
            if(tempEnd == null)
            {
                Node newEndNode = new Node(endNode,this);
                allNodes.put(endNode,newEndNode);
                tempStart.addNeighbor(endNode, waga);
            }
            else
            {
                allNodes.put(endNode,tempEnd);
                tempStart.addNeighbor(endNode, waga);
            }
        }
    }
}
