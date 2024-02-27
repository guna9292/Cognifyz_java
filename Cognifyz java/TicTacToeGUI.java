import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private static final int SIZE = 3;
    private JButton[][] buttons;
    private String[] board;
    private String turn;

    public TicTacToeGUI() {
        buttons = new JButton[SIZE][SIZE];
        board = new String[SIZE * SIZE];
        turn = "X";
        initializeBoard();
        initializeUI();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE * SIZE; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (clickedButton == buttons[row][col]) {
                    int index = row * SIZE + col;
                    if (board[index].equals(String.valueOf(index + 1))) {
                        board[index] = turn;
                        clickedButton.setText(turn);
                        if (checkWinner() != null) {
                            displayWinner(checkWinner());
                        } else {
                            turn = (turn.equals("X")) ? "O" : "X";
                        }
                    }
                }
            }
        }
    }

    private String checkWinner() {
        for (int a = 0; a < SIZE * SIZE; a += SIZE) {
            //row check
            if (board[a].equals(board[a + 1]) && board[a + 1].equals(board[a + 2])) {
                return board[a];
            }
            //column check
            if (board[a / SIZE].equals(board[(a / SIZE) + SIZE]) && board[(a / SIZE) + SIZE].equals(board[(a / SIZE) + 2 * SIZE])) {
                return board[a / SIZE];
            }
        }

        // Check diagonals
        if (board[0].equals(board[4]) && board[4].equals(board[8])) {
            return board[0];
        } else if (board[2].equals(board[4]) && board[4].equals(board[6])) {
            return board[2];
        }

        for (String cell : board) {
            if (!cell.equals("X") && !cell.equals("O")) {
                return null; // game is still in progress
            }
        }

        return "draw"; // here's a draw
    }

    private void displayWinner(String winner) {
        if (winner.equals("draw")) {
            JOptionPane.showMessageDialog(this, "It's a draw! Thanks for playing.");
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! " + winner + "'s have won! Thanks for playing.");
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}
