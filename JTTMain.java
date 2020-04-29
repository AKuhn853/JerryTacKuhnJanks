import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class JTTMain extends JFrame implements ActionListener {
	class Closer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.out.println("Hope you had fun!");
			System.exit(0);
		}
	}

	class Node {
		Color color;
		int size;
		int x, y;

		public Node(int x, int y, Color c, int s) {
			this.x = x;
			this.y = y;
			color = c;
			size = s;
		}

		public void changeColor() {
			color = new Color((int) (150), (int) (50), (int) (50));
		}

		// Function to change the nodes selected by the CPU
		public void changeColorCPU() {
			color = new Color((int) (50), (int) (255), (int) (0));
		}

		public void draw(Graphics g) {
			g.setColor(color);
			g.fillOval(x, y, size, size / 2);
		}
	}

	class Backdrop extends JPanel {
		Node[] nodes;

		public Backdrop() {
			nodes = new Node[9];
			setSize(1100, 600);
			// f=new Node(100,100, Color.yellow, 200);
			for (int i = 0; i < nodes.length; i++) {
				if (i <= 2) {
					nodes[i] = new Node((int) (125 + 350 * i), (int) (100), Color.yellow, (int) (100));
				} else if (i <= 5) {
					nodes[i] = new Node((int) (300 + 175 * (i - 3)), (int) (250), Color.yellow, (int) (100));
				} else if (i <= 8) {
					nodes[i] = new Node((int) (125 + 350 * (i - 6)), (int) (400), Color.yellow, (int) (100));
				}
			}
		}

		public void setcolor(int i) {
			nodes[i].changeColor();
		}

		public void setcolorCPU(int i) {
			nodes[i].changeColorCPU();
		}

		public void paintComponent(Graphics g) {
			// gameboard
			g.setColor(Color.gray);
			g.fillRect(0, 0, 1100, 600);
			// lines
			g.setColor(Color.black);
			g.drawLine(175, 125, 875, 125);
			g.drawLine(175, 125, 875, 425);
			g.drawLine(175, 125, 525, 425);
			g.drawLine(175, 425, 875, 425);
			g.drawLine(525, 125, 175, 425);
			g.drawLine(350, 275, 700, 275);
			g.drawLine(525, 125, 875, 425);
			g.drawLine(875, 125, 175, 125);
			g.drawLine(875, 125, 175, 425);
			g.drawLine(875, 125, 525, 425);
			// f.draw(g);
			for (int i = 0; i < nodes.length; i++)
				nodes[i].draw(g);
			// numbers
			g.setColor(Color.black);
			g.drawString("1", 172, 125);
			g.drawString("2", 522, 125);
			g.drawString("3", 872, 125);
			g.drawString("4", 347, 275);
			g.drawString("5", 522, 275);
			g.drawString("6", 697, 275);
			g.drawString("7", 172, 425);
			g.drawString("8", 522, 425);
			g.drawString("9", 872, 425);
		}
	}

	JLabel header;
	JLabel instruction;
	JButton setcolor;
	JTextField input;
	Backdrop gameboard;
	String youwon;
	String youlost;
	String tie;
	JButton playagain;
	JButton exit;
	JButton cpufirst;
	JPanel bottom;
	JPanel top;

	// Establishing Player versus Computer
	char player = 'P';
	char computer = 'C';

	// Board Work
	char[] b = new char[10];

	public void clearBoard() {
		for (int i = 1; i < 10; i++) {
			b[i] = '_';
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == setcolor && gameWon() == false && gameLost() == false) {
			top.remove(cpufirst);
			header.setText("Checking possible dub...");
			String x = input.getText();
			int i = Integer.parseInt(x);
			b[i] = 'P';
			gameboard.setcolor(i - 1);
			// forces a redraw of the gameboard art object
			gameboard.repaint();
			if(gameTie() == false)
				CPUturn();
		} else if (e.getSource() == playagain) {
			// if(gameWon() == true | gameLost() == true | gameTie == true) {
			JTTMain submarine = new JTTMain();
			// }
		} else if (e.getSource() == exit) {
			// if(gameWon() == true) {
			System.out.println("Hope you had fun!");
			System.exit(0);
			// }
		} else if (e.getSource() == cpufirst) {
			CPUturn();
			top.remove(cpufirst);
			header.setText("Checking possible dub...");
		}

		if (gameWon()) {
			header.setText(youwon);
			top.add(playagain, "West");
			top.add(exit, "East");
		}

		if (gameLost()) {
			header.setText(youlost);
			top.add(playagain, "West");
			top.add(exit, "East");
		}

		if (gameTie()) {
			header.setText(tie);
			top.add(playagain, "West");
			top.add(exit, "East");
		}
	}

	public boolean movesLeft(char[] b) {
		for (int i = 1; i < 10; i++) {
			if (b[i] == '_') {
				return true;
			}
		}
		return false;
	}

	public int evaluate(char[] b) {
		// There are nine different ways to win
		// 1-2-3
		if (b[1] == b[2] && b[2] == b[3]) {
			if (b[1] == computer)
				return +10;
			else if (b[1] == player)
				return -10;
		}
		// 1-5-9
		if (b[1] == b[5] && b[5] == b[9]) {
			if (b[1] == computer)
				return +10;
			else if (b[1] == player)
				return -10;
		}
		// 1-4-8
		if (b[1] == b[4] && b[4] == b[8]) {
			if (b[1] == computer)
				return +10;
			else if (b[1] == player)
				return -10;
		}
		// 2-4-7
		if (b[2] == b[4] && b[4] == b[7]) {
			if (b[2] == computer)
				return +10;
			else if (b[2] == player)
				return -10;
		}
		// 2-6-9
		if (b[2] == b[6] && b[6] == b[9]) {
			if (b[2] == computer)
				return +10;
			else if (b[2] == player)
				return -10;
		}
		// 3-5-7
		if (b[3] == b[5] && b[5] == b[7]) {
			if (b[3] == computer)
				return +10;
			else if (b[3] == player)
				return -10;
		}
		// 3-6-8
		if (b[3] == b[6] && b[6] == b[8]) {
			if (b[3] == computer)
				return +10;
			else if (b[3] == player)
				return -10;
		}
		// 4-5-6
		if (b[4] == b[5] && b[5] == b[6]) {
			if (b[4] == computer)
				return +10;
			else if (b[4] == player)
				return -10;
		}
		// 7-8-9
		if (b[7] == b[8] && b[8] == b[9]) {
			if (b[8] == computer)
				return +10;
			else if (b[8] == player)
				return -10;
		}
		return 0;
	}

	// Minimax function that considers all possible ways game can go
	// Returns the value of the board
	public int minimax(char[] b, int depth, boolean isMax) {
		int score = evaluate(b);

		// If Max has won, return evaluated score
		if (score == 10) {
			return score;
		}

		// If Min has won the game return score
		if (score == -10) {
			return score;
		}

		// // If no more moves exist at all we show that the game is a tie
		if (movesLeft(b) == false) {
			return 0;
		}

		// Maximizer's move
		if (isMax) {
			// Finding the best move
			int best = -1000;

			// Traverse all cells
			for (int i = 1; i < 10; i++) {
				// if there is a move to be made
				if (b[i] == '_') {
					// Make it
					b[i] = computer;

					// Call minimax recursively and chose the minimum value
					best = Math.max(best, minimax(b, depth + 1, !isMax));

					// Undo our move
					b[i] = '_';
				}
			}
			return (best);
		}
		// If this is minimizer's move
		else {
			int best = 1000;

			// Traverse all cells
			for (int i = 1; i < 10; i++) {
				// if there is a move to be made
				if (b[i] == '_') {
					// Make it
					b[i] = player;

					// Evaluate the move
					best = Math.min(best, minimax(b, depth + 1, !isMax));

					// Undo our move
					b[i] = '_';
				}
			}
			return (best);
		}
	}

	// This function will consider all possible moves and return the
	// best possible move
	public int findBestMove(char[] b) {
		int bestVal = -1000;
		int bestMove = -1;
		// Traverse all cells, evaluate minimax function for all empty
		// cells. And return the cell with optimal value.
		for (int i = 1; i < 10; i++) {
			// Confirm empty cell
			if (b[i] == '_') {
				// Make our move
				b[i] = computer;

				// Compute evaluation of the move
				int moveEval = minimax(b, 0, false);

				// Undo our move
				b[i] = '_';

				// If the evaluation of our current move is more than
				// the best value so far, then update best
				// System.out.println("Current Move Value: " + moveEval);
				// System.out.println("Best Move Value: " + bestVal);
				if (moveEval >= bestVal) {
					bestMove = i;
					bestVal = moveEval;
				}
			}
		}
		return bestMove;
	}

	// Advanced Minimax CPUTurn code
	public void CPUturn() {
		if (gameWon() == true) {
			return;
		}

		char[] board = new char[10];

		for (int i = 1; i < 10; i++) {
			board[i] = b[i];
		}

		int bestMove = findBestMove(board);

		b[bestMove] = 'C';
		gameboard.setcolorCPU(bestMove - 1);
		gameboard.repaint();
	}

	// Simple AI
	// public void CPUturn() {
		// if (gameWon() == true) {
			// return;
		// }
		// for (int i = 1; i < 10; i++) {
			// if (b[i] == 0) {
				// b[i] = 2;
				// gameboard.setcolorCPU(i - 1);
				// gameboard.repaint();
				// gameLost();
				// return;
			// }
		// }
	// }

	public boolean gameWon() {
		if ((b[1] == 'P' && b[2] == 'P' && b[3] == 'P') || (b[1] == 'P' && b[4] == 'P' && b[8] == 'P')
				|| (b[1] == 'P' && b[5] == 'P' && b[9] == 'P') || (b[2] == 'P' && b[4] == 'P' && b[7] == 'P')
				|| (b[2] == 'P' && b[6] == 'P' && b[9] == 'P') || (b[3] == 'P' && b[5] == 'P' && b[7] == 'P')
				|| (b[3] == 'P' && b[6] == 'P' && b[8] == 'P') || (b[4] == 'P' && b[5] == 'P' && b[6] == 'P')
				|| (b[7] == 'P' && b[8] == 'P' && b[9] == 'P')) {
			return true;
		} else
			return false;
	}

	public boolean gameLost() {
		if ((b[1] == 'C' && b[2] == 'C' && b[3] == 'C') || (b[1] == 'C' && b[4] == 'C' && b[8] == 'C')
				|| (b[1] == 'C' && b[5] == 'C' && b[9] == 'C') || (b[2] == 'C' && b[4] == 'C' && b[7] == 'C')
				|| (b[2] == 'C' && b[6] == 'C' && b[9] == 'C') || (b[3] == 'C' && b[5] == 'C' && b[7] == 'C')
				|| (b[3] == 'C' && b[6] == 'C' && b[8] == 'C') || (b[4] == 'C' && b[5] == 'C' && b[6] == 'C')
				|| (b[7] == 'C' && b[8] == 'C' && b[9] == 'C')) {
			return true;
		} else
			return false;
	}

	public boolean gameTie() {
		if (b[1] != '_' && b[2] != '_' && b[3] != '_' && b[4] != '_' && b[5] != '_' && b[6] != '_' && b[7] != '_' &&
			b[8] != '_' && b[9] != '_')
			return true;
		else
			return false;
	}

	public JTTMain() {
		// Ensured board is clear before beginning game.
		clearBoard();
		setTitle("Jerry Tac Toe: Tik Tac Toe but better!");
		setSize(1100, 700);
		addWindowListener(new Closer());

		cpufirst = new JButton("CPU Goes First");
		header = new JLabel(" Welcome to the game! Best of luck, but be warned: you are no match for Jerry!"
				+ " Press the button to your left if you would like Jerry to go first... otherwise make your move!");
		youwon = "You won! Jerry bows down to you. Look left to play again or right to go bye bye.";
		youlost = "You lost! Jerry laughs in your face. Look left to play again or right to go bye bye.";
		tie = "You tied! Jerry showed you mercy just this one time. Look left to play again or right to go bye bye.";
		instruction = new JLabel("Enter the node you'd like to mark then press \"Submit Move\".");
		setcolor = new JButton("   Submit Move   ");
		input = new JTextField("");
		playagain = new JButton("Play Again/Reset");
		exit = new JButton(" Go Bye Bye ");
		gameboard = new Backdrop();

		setcolor.addActionListener(this);
		playagain.addActionListener(this);
		exit.addActionListener(this);
		cpufirst.addActionListener(this);

		// put stuff in the window
		Container glass = getContentPane();
		glass.setLayout(new BorderLayout()); // layout manager

		bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add(instruction, "West");
		bottom.add(input, "Center");
		bottom.add(setcolor, "East");

		top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(header, "Center");
		top.add(cpufirst, "West");

		glass.add(top, "North");
		glass.add(gameboard, "Center");
		glass.add(bottom, "South");
		glass.add(new JPanel(), "East");
		glass.add(new JPanel(), "West");

		setVisible(true);
	}

	public static void main(String[] args) {
		JTTMain submarine = new JTTMain();
	}
}