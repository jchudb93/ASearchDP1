package Models;

public class Nodo {
    private int x;
    private int y;
    private int numId;

    public Nodo(int x, int y, int numId) {
        this.x = x;
        this.y = y;
        this.numId = numId;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }
}
