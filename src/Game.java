import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */ 

@SuppressWarnings("serial")
public class Game extends JPanel {
   // Named-constants for the game board
   public static final int ROWS = 3;  // ROWS by COLS cells
   public static final int COLS = 3;
   public static final String TITLE = "Tic Tac Toe";
 
   // Name-constants for the various dimensions used for graphics drawing
   public static final int CELL_SIZE = 100; // cell width and height (square)
   public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  // the drawing canvas
   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 8;  // Grid-line's width
   public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public static final int CELL_PADDING = CELL_SIZE / 6;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
   public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width

   // Player choice
   public static boolean playerTurn;
   public static boolean botTurn;
   public static int choice;
 
   private Logic logic;
   private Board board;            // the game board
   private GameState currentState; // the current state of the game
   private Seed currentPlayer;     // the current player
   private JLabel statusBar;       // for displaying status message
 
   /** Constructor to setup the UI and game components */
   public Game() {
 
      // This JPanel fires MouseEvent
      this.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int rowSelected = mouseY / CELL_SIZE;
            int colSelected = mouseX / CELL_SIZE;
 
            if (currentState == GameState.PLAYING) {
               if (playerTurn && !botTurn) {
                  if (rowSelected >= 0 && rowSelected < ROWS
                        && colSelected >= 0 && colSelected < COLS
                        && board.cells[rowSelected][colSelected].content == Seed.EMPTY) {
                     board.cells[rowSelected][colSelected].content = currentPlayer; // move
                     if (choice == 2) {
                        playerTurn = false;
                        botTurn = true;
                     }
                     updateGame(currentPlayer, rowSelected, colSelected); // update currentState
                     // Switch player
                     currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                  }
               }
               if (choice == 2 && botTurn && !playerTurn) {
                  Move move = logic.getMove(board.cells);
                  if (move.row >= 0 && move.row < ROWS
                        && move.col >= 0 && move.col < COLS
                        && board.cells[move.row][move.col].content == Seed.EMPTY) {
                     board.cells[move.row][move.col].content = currentPlayer; // move
                     playerTurn = true;
                     botTurn = false;
                     // System.out.println("\n---- " + move.row + "  " + move.col + "\n");
                     updateGame(currentPlayer, move.row, move.col); // update currentState
                     // Switch player
                     currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                  }
               }
            } else {        // game over
               initGame();  // restart the game
            }

            // Refresh the drawing canvas
            repaint();  // Call-back paintComponent().
         }
      });
 
      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("         ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
      statusBar.setOpaque(true);
      statusBar.setBackground(Color.LIGHT_GRAY);
 
      setLayout(new BorderLayout());
      add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
      setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
            // account for statusBar in height
 
      board = new Board();   // allocate the game-board
      initGame();  // Initialize the game variables
   }
 
   /** Initialize the game-board contents and the current-state */
   public void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board.cells[row][col].content = Seed.EMPTY; // all cells empty
         }
      }
      currentState = GameState.PLAYING;  // ready to play

      System.out.print("\n1. Player vs Player \n2. Player vs CPU\n3. Exit\nYour Choice: ");
      choice = new Scanner(System.in).nextInt();
      if (choice == 3)  System.exit(0);
      System.out.print("\nCross(X) or Nought(O): ");
      char ch = new Scanner(System.in).next().charAt(0);
      currentPlayer = (ch == 'X' || ch == 'x')? Seed.CROSS: Seed.NOUGHT ; 

      playerTurn = true;
      botTurn = false;
      logic = new Logic(currentPlayer);
   }
 
   /** Update the currentState after the player with "theSeed" has placed on (row, col) */
   public void updateGame(Seed theSeed, int row, int col) {
      if (board.hasWon(theSeed, row, col)) {  // check for win
         currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (board.isDraw()) {  // check for draw
         currentState = GameState.DRAW;
      }
      
      // Otherwise, no change to current state (PLAYING).
   }
 
   /** Custom painting codes on this JPanel */
   @Override
   public void paintComponent(Graphics g) {  // invoke via repaint()
      super.paintComponent(g);    // fill background
      setBackground(Color.WHITE); // set its background color
 
      board.paint(g);  // ask the game board to paint itself
 
      // Print status-bar message
      if (currentState == GameState.PLAYING) {
         statusBar.setForeground(Color.BLACK);
         if (currentPlayer == Seed.CROSS) {
            statusBar.setText("X's Turn");
         } else {
            statusBar.setText("O's Turn");
         }
      } else if (currentState == GameState.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == GameState.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'X' Won! Click to play again.");
      } else if (currentState == GameState.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'O' Won! Click to play again.");
      }
   }

   public static void main(String[] args) {
      // Run GUI construction codes in Event-Dispatching thread for thread safety
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            System.out.println("\t################################################");
            System.out.println("\t#   _____ _     _____         _____            #");
            System.out.println("\t#  |_   _(_) __|_   _|_ _  __|_   _|__   ___   #");
            System.out.println("\t#    | | | |/ __|| |/ _` |/ __|| |/ _ \\ / _ \\  #");
            System.out.println("\t#    | | | | (__ | | (_| | (__ | | (_) |  __/  #");
            System.out.println("\t#    |_| |_|\\___||_|\\__,_|\\___||_|\\___/ \\___|  #");
            System.out.println("\t#                                              #");
            System.out.println("\t################################################");
            System.out.println("\t\t\t    ver 1.0\n\t\tDeveloped By Kumar Aditya");

            JFrame frame = new JFrame(TITLE);

            // Set the content-pane of the JFrame to an instance of main JPanel
            frame.setContentPane(new Game());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true); 
         }
      });
   }
} 
