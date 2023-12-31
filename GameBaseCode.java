
import java.util.Random;

public class SnakesAndLaddersGame {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_PLAYERS = 2;

    private int[] players;
    private int[] snakes;
    private int[] ladders;
    private Random random;
    private int currentPlayer;
    private int lastDiceResult; // Added field to store the last dice roll result

    public SnakesAndLaddersGame() {
        players = new int[NUM_PLAYERS];
        snakes = new int[]{16, 47, 49, 56, 62, 64, 87, 93, 95, 98};
        ladders = new int[]{1, 4, 9, 21, 28, 36, 51, 71, 80};
        random = new Random();
        currentPlayer = 0;
        lastDiceResult = 0;
        initializeGame();
    }

    private void initializeGame() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i] = 1; // Start position for each player
        }
    }

    public void playTurn() {
        lastDiceResult = rollDice();
        movePlayer(currentPlayer, lastDiceResult);
        checkForWin();
        switchPlayer();
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }

    private void movePlayer(int playerIndex, int steps) {
        players[playerIndex] += steps;
        if (players[playerIndex] > BOARD_SIZE * BOARD_SIZE) {
            players[playerIndex] = BOARD_SIZE * BOARD_SIZE - (players[playerIndex] - BOARD_SIZE * BOARD_SIZE);
        }
        checkForSnakesAndLadders(playerIndex);
    }

    private void checkForSnakesAndLadders(int playerIndex) {
        int currentPlayerPosition = players[playerIndex];
        if (contains(snakes, currentPlayerPosition)) {
            players[playerIndex] = getSnakeTail(currentPlayerPosition);
        } else if (contains(ladders, currentPlayerPosition)) {
            players[playerIndex] = getLadderTop(currentPlayerPosition);
        }
    }

    private void checkForWin() {
        if (players[currentPlayer] == BOARD_SIZE * BOARD_SIZE) {
            System.out.println("Player " + (currentPlayer + 1) + " has won!");
            System.exit(0);
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getLastDiceResult() {
        return lastDiceResult;
    }

    public String getBoardState() {
        StringBuilder board = new StringBuilder();
        for (int row = BOARD_SIZE; row > 0; row--) {
            for (int col = 1; col <= BOARD_SIZE; col++) {
                int position = row * BOARD_SIZE - (col - 1);
                char symbol = ' ';
                if (contains(snakes, position)) {
                    symbol = 'S';
                } else if (contains(ladders, position)) {
                    symbol = 'L';
                }
                board.append("[").append(symbol).append(position < 10 ? " " : "").append(position).append("] ");
            }
            board.append("\n");
        }
        board.append("\n");
        return board.toString();
    }

    public boolean hasCurrentPlayerWon() {
        return players[currentPlayer] == BOARD_SIZE * BOARD_SIZE;
    }

    private boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    private int getSnakeTail(int position) {
        switch (position) {
            case 16: return 6;
            case 47: return 26;
            default: return position;
        }
    }

    private int getLadderTop(int position) {
        switch (position) {
            case 1: return 38;
            case 4: return 14;
            default: return position;
        }
    }

    public static void main(String[] args) {
        SnakesAndLaddersGame game = new SnakesAndLaddersGame();
        game.playTurn();
        System.out.println(game.getBoardState());
        System.out.println("Current Player: " + (game.getCurrentPlayer() + 1));
        System.out.println("Last Dice Result: " + game.getLastDiceResult());
    }
}
