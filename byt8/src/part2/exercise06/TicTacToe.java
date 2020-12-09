package part2.exercise06;

public class TicTacToe {
    private final StringBuffer board;

    public TicTacToe(String boardString) {
        board = new StringBuffer(boardString);
    }

    public TicTacToe(String boardString, int position, char player) {
        board = new StringBuffer(boardString);
        board.setCharAt(position, player);
    }

    // move made by 'AI'
    public int suggestMove(char player) {
        int position = winningMove(player);
        if (position != -1) return position;
        return randomMove();
    }

    private int randomMove() {
        int position = -1;
        for (int i = 0; i < 9; i++) {
            if (board.charAt(i) == '-') {
                position = i;
                break;
            }
        }
        return position;
    }

    private int winningMove(char player) {
        int position = -1;
        for (int i = 0; i < 9; i++) {
            if (board.charAt(i) == '-') {
                TicTacToe game = makeMove(i, player);
                if (game.winner() == player) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    public TicTacToe makeMove(int i, char player) {
        return new TicTacToe(board.toString(), i, player);
    }

    public char winner() {
        char winner = horizontalWinner();
        if (winner != '-') return winner;

        winner = verticalWinner();
        if (winner != '-') return winner;

        winner = diagonalWinner();
        return winner;
    }

    private char diagonalWinner() {
        char winner = '-';
        if (board.charAt(0) != '-' && board.charAt(4) == board.charAt(0)
                && board.charAt(8) == board.charAt(0)) {
            winner = board.charAt(0);
        } else if (board.charAt(2) != '-' && board.charAt(4) == board.charAt(2)
                && board.charAt(6) == board.charAt(2)) {
            winner = board.charAt(2);
        }
        return winner;
    }

    private char verticalWinner() {
        char winner = '-';
        for (int i = 0; i < 3; ++i) {
            if (board.charAt(i) != '-'
                    && board.charAt(i + 3) == board.charAt(i)
                    && board.charAt(i + 6) == board.charAt(i)) {
                winner = board.charAt(i);
                break;
            }
        }
        return winner;
    }

    private char horizontalWinner() {
        char winner = '-';
        for (int i = 0; i < 9; i += 3) {
            if (board.charAt(i) != '-'
                    && board.charAt(i + 1) == board.charAt(i)
                    && board.charAt(i + 2) == board.charAt(i)) {
                winner = board.charAt(i);
                break;
            }
        }
        return winner;
    }
}
