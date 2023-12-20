import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SnakesAndLaddersGUI extends JFrame {
    private JButton rollDiceButton;
    private JTextArea gameTextArea;

    private SnakesAndLaddersGame game;

    public SnakesAndLaddersGUI() {
        super("Snakes and Ladders Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playTurn();
            }
        });

        gameTextArea = new JTextArea();
        gameTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameTextArea);

        add(rollDiceButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        game = new SnakesAndLaddersGame();

        updateGameDisplay();

        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void playTurn() {
        game.playTurn();
        updateGameDisplay();
        if (game.hasCurrentPlayerWon()) {
            rollDiceButton.setEnabled(false);
            gameTextArea.append("Player " + (game.getCurrentPlayer() + 1) + " has won!\n");
        }
    }

    private void updateGameDisplay() {
        gameTextArea.setText(game.getBoardState());
        gameTextArea.append("Player " + (game.getCurrentPlayer() + 1) + "'s turn.\n");
        gameTextArea.append("Dice result: " + game.getLastDiceResult() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SnakesAndLaddersGUI();
            }
        });
    }
}
