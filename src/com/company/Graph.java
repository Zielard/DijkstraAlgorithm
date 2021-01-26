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
            for (int i = TempListNode.size()-1; i > 0; i--) {
                System.out.print(TempListNode.get(i).name + ", ");
            }
            System.out.println(" W: " + entry.getValue().getValue());
        }
        System.out.println("");
    }

    private String searchWay(String startNode , Map<String, String> p)
    {
        return p.get(startNode);
    }

    public void dijkstra(String startNode, String endNode)
    {
        Integer inf = (int)Double.POSITIVE_INFINITY;

        //lista wierzcholkow sprawdzonych i nie sprawdzonych
        Map<String, Node> S = new HashMap<String, Node>();
        Map<String, Node> Q = new HashMap<String, Node>();

        //tablice odleglosci oraz wag
        Map<String, String> d = new HashMap<String, String>();
        Map<String, Integer> p = new HashMap<String, Integer>();

        //Najmniejsza wartosc krawedzi sasiada wraz z jego referencja
        Pair<Integer, Node> minNodeLast= new Pair<Integer, Node>(inf,null);

        //wypelnianie kosztow dojscia dla konkretnego wierzcholka
        for (Map.Entry<String, Node> entry : allNodes.entrySet())
        {
            p.put(entry.getKey(), inf);
        }

        //wypelnianie wierzchoklow poprzednikow
        for (Map.Entry<String, Node> entry : allNodes.entrySet())
        {
            d.put(entry.getKey(), null);
        }

        //wypelnianie mapy drog pustymi wartosciami
        for (Map.Entry<String, Node> entry : allNodes.entrySet())
        {
            Q.put(entry.getKey(),entry.getValue());
        }

        Node currentNode = Q.get(startNode);
        d.put(startNode,startNode);
        p.put(startNode,0);
        while(Q.size() != 0)
        {

            for (Map.Entry<String,  Pair<Node,Integer >> entry : currentNode.mapNeighbors.entrySet())
            {
                if(currentNode.name == "7")
                {
                    int trappp = 0;
                }
                    if(entry.getValue().getKey().visit == false)
                    {
                        entry.getValue().getKey().visit = true;
                        p.put(entry.getKey(),entry.getValue().getValue());
                        d.put(entry.getKey(),currentNode.name);
                    }
                    else
                    {
                        //obliczam droge dla aktywnego node
                        String currentNodeWay =currentNode.name;
                        int kosztDrogiDoCurrent =0;
                        while(currentNodeWay != startNode)
                        {
                            kosztDrogiDoCurrent+=p.get(currentNodeWay);
                            currentNodeWay = this.searchWay(currentNodeWay, d);
                        }

                        //obliczam droge dla aktywnego sasiada node
                        currentNodeWay =entry.getKey();
                        int kosztDrogiDoAktywnego =0;
                        while(currentNodeWay != startNode)
                        {
                            kosztDrogiDoAktywnego+=p.get(currentNodeWay);
                            currentNodeWay = this.searchWay(currentNodeWay, d);
                        }

                        //sprawdzam czy droga przez aktywny node nie jest bardziej opytmalna od tej ktorej posiada
                         if(kosztDrogiDoAktywnego > (kosztDrogiDoCurrent + entry.getValue().getValue()))
                         {
                             p.put(entry.getKey(),entry.getValue().getValue());
                             d.put(entry.getKey(),currentNode.name);

                             //dodaje jeszcze raz do listy noda ktory zmienil sciezke zeby sprawdzic tez jego sasiadow
                             Q.put(entry.getKey(),entry.getValue().getKey());
                         }

                    }
                }

            Q.remove(currentNode.name);
            currentNode.visit = true;
            S.put(currentNode.name,currentNode);

            currentNode = minNodeLast.getValue();
            int minValue = inf;
            for (Map.Entry<String, Integer> entry : p.entrySet())
            {
                if(Q.get(entry.getKey()) != null)
                {
                    if(minValue > entry.getValue() && ( entry.getValue()!=0))
                    {
                        minValue = entry.getValue();
                        currentNode = allNodes.get(entry.getKey());
                    }
                }
            }
        }

        //Print
        System.out.println("");
        ///////////////////////BOARD
        for(int i=0;i<(S.size()*3)+4;i++)
        {
            System.out.print("#");
        }

            System.out.println("");
            System.out.print("    ");
            for (Map.Entry<String, String> entry : d.entrySet())
            {
                System.out.print(entry.getKey() + " |");
            }
            System.out.println("");
            System.out.print("d[]:");
            for (Map.Entry<String, String> entry : d.entrySet())
            {
                System.out.print(entry.getValue() + " |");
            }
            System.out.println("");
            System.out.print("p[]:");
            for (Map.Entry<String, Integer> entry : p.entrySet())
            {
                System.out.print(entry.getValue() + " |");
            }
            ///////////////////////BOARD
            System.out.println("");
            for(int i=0;i<(S.size()*3)+4;i++)
            {
                System.out.print("#");
            }

        System.out.println("");
        //Wypelniam drogami moja mape pod indexy nazw wezlow
        for (Map.Entry<String, String> entry : d.entrySet())
        {
            String currentNodeWay =entry.getKey();
            List<Node> tempList = new ArrayList<Node>();
            int wagaPolicz =0;
            while(currentNodeWay != startNode)
            {
                tempList.add(allNodes.get(currentNodeWay));
                wagaPolicz+=p.get(currentNodeWay);
                currentNodeWay = this.searchWay(currentNodeWay, d);
            }
            tempList.add(allNodes.get(startNode));
            pathsForNodes.put(entry.getKey(), new Pair<>(tempList, wagaPolicz));
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
