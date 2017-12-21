package me.gumennyi.lab2.types;

public class Polygon<T extends Point> {
    private T a;
    private T b;
    private T c;
    private int vIndex;
    private int hIndex;
    private int vTotal;
    private int hTotal;
    private boolean top;

    public Polygon(T a, T b, T c, int vIndex, int hIndex, int vTotal, int hTotal, boolean top) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.vIndex = vIndex;
        this.hIndex = hIndex;
        this.vTotal = vTotal;
        this.hTotal = hTotal;
        this.top = top;
    }

    public Polygon(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isTop() {
        return top;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon<?> polygon = (Polygon<?>) o;

        if (vIndex != polygon.vIndex) return false;
        if (hIndex != polygon.hIndex) return false;
        return top == polygon.top;
    }

    @Override
    public int hashCode() {
        int result = vIndex;
        result = 31 * result + hIndex;
        result = 31 * result + (top ? 1 : 0);
        return result;
    }

    public int getvIndex() {
        return vIndex;
    }

    public int gethIndex() {
        return hIndex;
    }

    public int gethTotal() {
        return hTotal;
    }

    public int getvTotal() {
        return vTotal;
    }
}
