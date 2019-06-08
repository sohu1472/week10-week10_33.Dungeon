package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Dungeon {
    private int length;
    private int height;
    private int vampires;
    private int moves;
    private boolean vampiresMove;
    private List<Unit> units;
    private Scanner reader;
    private Board board;
    private List<Object> toBeRemoved;

    public Dungeon(int length, int height, int vampires, int moves, boolean vampiresMove) {
        this.length = length;
        this.height = height;
        this.vampires = vampires;
        this.moves = moves;
        this.vampiresMove = vampiresMove;
        units = new ArrayList<Unit>();
        this.reader = new Scanner(System.in);
        board = new Board(height, length);
        toBeRemoved = new ArrayList<Object>();
    }

    public void move(String command, Unit unit) {
        int x = unit.getPosX();
        int y = unit.getPosY();

        List<Character> moveSet = Utils.commandToArray(command);

        for(char c : moveSet) {
            if (c == 'w' && y > 0) {
                y -= 1;
            }
            if (c == 's' && y < this.height-1) {
                y += 1;
            }
            if (c == 'a' && x > 0) {
                x -= 1;
            }
            if (c == 'd' && x < this.length-1) {
                x += 1;
            }
        }

        unit.move(x,y);
        if(this.vampiresMove) {
            int moveCount = moveSet.size();
            vampireMove(moveCount);
        }
        else {
            for (Unit vampire : units) {
                if (vampire.drawModel() == 'v') {
                    checkIfHit(vampire);
                }
            }
            removePwnedVampires();
        }
    }

    public void vampireMove(int moveCount) {
        Random rand = new Random();


        for(Unit vampire : units) {
            if (vampire.drawModel() == 'v') {
                checkIfHit(vampire);

                for(int i = 0; i < moveCount; i++) {
                    if (rand.nextInt(2) == 0) {
                        if (rand.nextInt(2) == 0 && vampire.getPosY() < this.height-1) {
                            vampire.move(0,1);
                        }
                        else if (vampire.getPosY() > 0) {
                            vampire.move(0, -1);
                        }
                    }
                    else {
                        if (rand.nextInt(2) == 0 && vampire.getPosX() < this.length-1) {
                            vampire.move(1, 0);
                        }
                        else if (vampire.getPosX() > 0){
                            vampire.move(-1,0);
                        }
                    }
                    checkIfHit(vampire);
                }
            }
        }
        removePwnedVampires();
    }

    public void checkIfHit(Unit vampire) {

        if(vampire.getPosX() == units.get(0).getPosX() && vampire.getPosY() == units.get(0).getPosY()) {
            toBeRemoved.add(vampire);
        }
    }

    public void removePwnedVampires() {
        units.removeAll(toBeRemoved);
    }

    public void drawPosList() {
        for (Unit unit : units) {
            System.out.println(unit.drawModel() + " " + unit.getPosX() + " " + unit.getPosY());
        }
        System.out.println("");
    }

    public List initializeVampires() {
        Random rand = new Random();
        List<Vampire> vampires = new ArrayList<Vampire>();

        for (int i = 0; i < this.vampires; i++) {
            boolean repeated = false;
            int x = rand.nextInt(length-1) + 1;
            int y = rand.nextInt(height);

            for(Vampire vampire : vampires) {
                if (vampire.getPosY() == y && vampire.getPosX() == x) {
                    repeated = true;
                }
            }
            if (repeated) {
                i -= 1;
                continue;
            }
            else {
                vampires.add(new Vampire(x, y));
            }
        }
        return vampires;
    }



    public void run() {
        int movesLeft = this.moves;

        Unit player = new Player(0,0);
        units.add(player);
        units.addAll(initializeVampires());

        while(true) {
            if (movesLeft <= 0) {
                System.out.println("YOU LOSE");
                break;
            }
            System.out.println(movesLeft + "\n");

            drawPosList();

            board.drawBoard(this.units);

            board.printBoard();

            System.out.println("");

            move(reader.nextLine(), player);

            movesLeft--;

            if(units.size() == 1) {
                System.out.println("YOU WIN");
                break;
            }
        }
    }
}
