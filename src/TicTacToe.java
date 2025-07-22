import java.util.Scanner;

public class TicTacToe {

    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static int moveCount = 0;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean playAgain;

        do {
            clearBoard();  // Reset board to spaces
            moveCount = 0;
            String currentPlayer = "X";

            boolean gameOver = false;

            while (!gameOver) {
                display();  // Show the board

                int row, col;

                do {
                    row = SafeInput.getRangedInt(console, currentPlayer + " - Enter row [1-3]: ", 1, 3) - 1;
                    col = SafeInput.getRangedInt(console, currentPlayer + " - Enter column [1-3]: ", 1, 3) - 1;

                    if (!isValidMove(row, col)) {
                        System.out.println("That cell is already taken. Try again.");
                    }
                } while (!isValidMove(row, col));

                board[row][col] = currentPlayer;
                moveCount++;
                if (moveCount >= 5 && isWin(currentPlayer)) {
                    display();
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOver = true;
                } else if (moveCount >= 7 && isTie()) {
                    display();
                    System.out.println("It's a tie!");
                    gameOver = true;
                } else {
                    // Toggle player
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }

            playAgain = SafeInput.getYNConfirm(console, "Play again?");
        } while (playAgain);

        System.out.println("Thanks for playing!");
    }

    // ====== Helper Methods Stubbed (will fill in next) ======

    private static void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " ";
            }
        }
    }

    private static void display() {
        System.out.println("\nCurrent Board:");
        for (int r = 0; r < ROWS; r++) {
            System.out.print("| ");
            for (int c = 0; c < COLS; c++) {
                System.out.print(board[r][c] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private static boolean isTie() {
        if (moveCount < 9) return false;

        // Check all 8 winning paths — if all are blocked by both X and O, it's a tie
        String[][] winPaths = {
                {board[0][0], board[0][1], board[0][2]},
                {board[1][0], board[1][1], board[1][2]},
                {board[2][0], board[2][1], board[2][2]},
                {board[0][0], board[1][0], board[2][0]},
                {board[0][1], board[1][1], board[2][1]},
                {board[0][2], board[1][2], board[2][2]},
                {board[0][0], board[1][1], board[2][2]},
                {board[0][2], board[1][1], board[2][0]}
        };

        for (String[] path : winPaths) {
            boolean hasX = false, hasO = false;
            for (String s : path) {
                if (s.equals("X")) hasX = true;
                if (s.equals("O")) hasO = true;
            }
            if (!(hasX && hasO)) {
                return false; // A win path is still possible
            }
        }
        return true; // All win paths blocked
    }
}
