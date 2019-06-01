package dungeon;

public class Player extends Unit {

    private char model;

    public Player(int x, int y) {
        super(x, y);
        this.model = '@';
    }

    @Override
    public char drawModel() {
        return model;
    }

    @Override
    public void setModel(char model) {
        this.model = model;
    }
}
