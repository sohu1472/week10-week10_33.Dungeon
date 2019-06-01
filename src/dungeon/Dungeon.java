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
    private char[][] board;

    public Dungeon(int length, int height, int vampires, int moves, boolean vampiresMove) {
        this.length = length;
        this.height = height;
        this.vampires = vampires;
        this.moves = moves;
        this.vampiresMove = vampiresMove;
        units = new ArrayList<Unit>();
        this.reader = new Scanner(System.in);
        this.board = new char[length][height];
    }

    public void drawBoard() {
        drawDots();
        drawUnits();
    }

    public void drawUnits() {
        for (Unit unit : this.units) {
            int x = unit.getPosX();
            int y = unit.getPosY();

            board[x][y] = unit.drawModel();
        }
    }

    public void drawDots() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < length; i++) {
                board[i][j] = '.';
            }
        }
    }

    public void printBoard() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < length; i++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void move(String command, Unit unit) {
        int x = unit.getPosX();
        int y = unit.getPosY();

        List<Character> moveSet = commandToArray(command);

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
    }

    public void vampireMove(int moveCount) {
        Random rand = new Random();


        for(Unit vampire : units) {
            if (vampire.drawModel() == 'v') {
                for(int i = 0; i < moveCount; i++) {
                    if (rand.nextInt(1) == 0) {
                        if (rand.nextInt(1) == 0) {
                            vampire.move(0,1);
                        }
                        else {
                            vampire.move(0, -1);
                        }
                    }
                    else {
                        if (rand.nextInt(1) == 0) {
                            vampire.move(1, 0);
                        }
                        else {
                            vampire.move(-1,0);
                        }
                    }
                }
                if (this.board[vampire.getPosX()][vampire.getPosY()] == '@') {
                    units.remove(vampire);
                }
            }
        }
    }

    public List commandToArray(String command) {
        List<Character> moveSet = new ArrayList<Character>();
        for (char c : command.toCharArray()) {
            moveSet.add(c);
        }
        return moveSet;
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
            int x = rand.nextInt(length);
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

            drawBoard();

            printBoard();

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
