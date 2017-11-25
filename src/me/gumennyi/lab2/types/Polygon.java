package me.gumennyi.lab2.types;

import java.util.ArrayList;
import java.util.List;

public class Polygon<T extends Point> {
    private T a;
    private T b;
    private T c;

    public Polygon(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public T getC() {
        return c;
    }

}
