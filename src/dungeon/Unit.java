package dungeon;

public class Unit {
    private int x;
    private int y;

    public Unit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public char drawModel() {
        return 'e';
    }

    public void setModel(char model) {
        System.out.print("err");
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}


