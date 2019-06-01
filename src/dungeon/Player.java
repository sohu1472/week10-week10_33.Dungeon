package dungeon;

public class Player extends Unit {

    private String model;

    public Player(int x, int y) {
        super(x, y);
        this.model = "@";
    }

    @Override
    public String drawModel() {
        return model;
    }

    @Override
    public void setModel(char model) {
        this.model = "" + model;
    }
}
