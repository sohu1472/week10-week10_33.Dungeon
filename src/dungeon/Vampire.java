package dungeon;

public class Vampire extends Unit {

    private char model;

    public Vampire(int x, int y) {
        super(x, y);
        this.model = 'v';
    }

    @Override
    public char drawModel() {
        return this.model;
    }

    @Override
    public void setModel(char model) {
        this.model = model;
    }

    @Override
    public void move(int x, int y) {
        int newX = getPosX() + x;
        int newY = getPosY() + y;
        setX(newX);
        setY(newY);
    }
}
