package com.company;

public class Pair<T, T1> {

    T node;
    T1 waga;

    public Pair(T nodeIn, T1 wagaIn)
    {
        this.node = nodeIn;
        this.waga = wagaIn;
    }

    public T getKey()
    {
        return node;
    }

    public T1 getValue()
    {
        return waga;
    }
}
