package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<String, Node> allNodes = new HashMap<String, Node>();
    public Map<String, Pair<List<Node>,Integer > > pathsForNodes = new HashMap<String, Pair<List<Node>,Integer > >();

    public Graph() { }

    public void printNeighbors()
    {
        System.out.println("All printNeighbors with nodes :");
        for (Map.Entry<String, Node> entry : allNodes.entrySet()) {

            System.out.print(entry.getKey() + ":");
            entry.getValue().print();
            System.out.println();
        }
        System.out.println("");
    }

    public void printDijkstra()
    {
        System.out.println("Min way :");
        for (Map.Entry<String, Pair<List<Node>, Integer>> entry : pathsForNodes.entrySet())
        {
            System.out.print(entry.getKey() + ": ");
            List<Node> TempListNode = entry.getValue().getKey();
            for (int i = 0; i < TempListNode.size(); i++) {
                System.out.print(TempListNode.get(i).name + ", ");
            }
            System.out.println(" W: " + entry.getValue().getValue());
        }
        System.out.println("");
    }

    public void dijkstra(String startNode, String endNode)
    {
        Integer inf = (int)Double.POSITIVE_INFINITY;
        Node currentNode = allNodes.get(startNode);
        if(currentNode != null)
        {
            // suma wag w aktywnej sciezce
            int waga = 0;

            //jesli sa jeszcze jakies nie odwiedzone wezly to jest na true
            boolean flagCheck = false;

            //flaga do glownej petli
            boolean flagEnd = true;

            //Najmniejsza wartosc krawedzi sasiada wraz z jego referencja
            Pair<Integer, Node> minNodeLast= new Pair<Integer, Node>(inf,currentNode);

            //lista aktywnej drogi
            List<Node> tempList = new ArrayList<Node>();

            //wypelnianie mapy drog pustymi wartosciami
            for (Map.Entry<String, Node> entry : allNodes.entrySet())
            {
                pathsForNodes.put(entry.getKey(), new Pair<>(new ArrayList<Node>(), inf));
            }
            pathsForNodes.put(startNode, new Pair<>(new ArrayList<Node>(), 0));


            while(flagEnd)
            {
                currentNode = minNodeLast.getValue();

                //odwiedzilem ten wierzcholek
                tempList.add(currentNode);
                currentNode.visit = true;
                if( minNodeLast.getKey() != inf)
                {
                    waga += minNodeLast.getKey();
                }

                //reset current waga
                minNodeLast = new Pair<Integer, Node>(999,minNodeLast.getValue());

                for (Map.Entry<String,  Pair<Node,Integer >> entry : currentNode.mapNeighbors.entrySet())
                {
                    if(entry.getValue().getKey() != null)
                    {
                        if(entry.getValue().getKey().visit == false)
                        {
                            if(inf == pathsForNodes.get(entry.getKey()).getValue())
                            {
                                //Tworze copie tablicy aktywnej drogi.
                                List<Node> copy = new ArrayList<>(tempList);
                                //dodaje do niej aktywny wezel
                                copy.add(entry.getValue().getKey());
                                int lastWaga = entry.getValue().getValue();

                                //dodaje nowa droge wraz z waga do wieszcholka w mapie
                                pathsForNodes.put(entry.getKey(), new Pair<>(copy, waga + entry.getValue().getValue()));

                                //sprawdzam ktory z sasiadow ma najmniejsza wage i go pobieram jesli taki sie pojawi
                                    if(minNodeLast.getKey() >  lastWaga)
                                    {
                                        //zaznaczam go sobie jako nastepnego bo mam najmniejsza wage
                                        minNodeLast= new Pair<Integer, Node>(entry.getValue().getValue(),entry.getValue().getKey());
                                    }

                                    //jesli znalazl jeszcze jakis nie odwiedzony to nie konicz glownej petli
                                flagCheck = true;
                            }
                            else
                            {
                                Node checkNode = entry.getValue().getKey();
                                for (Map.Entry<String,  Pair<Node,Integer >> NeiCheck : checkNode.mapNeighbors.entrySet())
                                {
                                    if((pathsForNodes.get(NeiCheck.getKey()).getValue() != inf && checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue() != inf)
                                    && pathsForNodes.get(checkNode.name).getValue() != inf && checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue() != inf)
                                    {
                                        //jezeli moj sasiad ma mniejsza wage od swoich sasiadow to podyfikuje sciezke do niego
                                        //waga do checkNode
                                        waga = (pathsForNodes.get(entry.getKey()).getValue());
                                        int waga_1 = (pathsForNodes.get(NeiCheck.getKey()).getValue() + checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue());
                                        if(waga_1 < waga)
                                        {
                                            //dodaje do listy nowy wieszcholek
                                            List<Node> copy = new ArrayList<>(pathsForNodes.get(NeiCheck.getKey()).getKey());
                                            copy.add(checkNode);
                                            int newWaga = pathsForNodes.get(NeiCheck.getKey()).getValue() + checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue();
                                            String nodeTest = entry.getKey();

                                            pathsForNodes.put(checkNode.name ,new Pair<>(copy, waga_1));
                                            //zaznaczam go sobie jako ostatnio odwiedzoneo
                                            int trappp = 0;
                                        }

                                        int waga_2 = (pathsForNodes.get(checkNode.name).getValue() + checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue());
                                        if(waga_2 < pathsForNodes.get(NeiCheck.getKey()).getValue())
                                        {
                                            //dodaje do listy nowy wieszcholek
                                            List<Node> copy = new ArrayList<>(pathsForNodes.get(checkNode.name).getKey());
                                            copy.add(NeiCheck.getValue().getKey());
                                            int newWaga = pathsForNodes.get(checkNode.name).getValue() + checkNode.mapNeighbors.get(NeiCheck.getKey()).getValue();
                                            pathsForNodes.put(NeiCheck.getKey() ,new Pair<>(copy, waga_2));
                                        }
                                    }
                                }
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


        }
        else
        {
            System.out.print("Wierzchołek nie istnieje");
        }

        System.out.println("Min way from " + startNode + " to "+ endNode);
        List<Node> TempListNode = pathsForNodes.get(endNode).getKey();
        for (int i = 0; i < TempListNode.size(); i++) {
            System.out.print(TempListNode.get(i).name + ", ");
        }
        System.out.println(" W: " +  pathsForNodes.get(endNode).getValue());
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
                //jesli nie ma tworze i dodaje
                Node newEndNode = new Node(endNode,this);
                allNodes.put(endNode,newEndNode);
                tempStart.addNeighbor(endNode, waga);
            }
            else
            {
                //jesli jest poprostu dodaje
                allNodes.put(endNode,tempEnd);
                tempStart.addNeighbor(endNode, waga);
            }
        }
    }
}
