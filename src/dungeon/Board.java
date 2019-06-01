package dungeon;

import java.util.List;

public class Board {

    private int height;
    private int length;
    private char[][] board;
    public Board(int height, int length) {
        this.height = height;
        this.length = length;
        board = new char[height][length];
    }


    public void drawBoard(List<Unit> units) {
        drawDots();
        drawUnits(units);
    }

    public void drawUnits(List<Unit> units) {
        for (Unit unit : units) {
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
}
