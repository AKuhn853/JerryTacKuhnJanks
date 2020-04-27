import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class JTTMain extends JFrame implements ActionListener
{
	class Closer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.out.println("Hope you had fun!");
			System.exit(0);
		}
	}
	
	class Node
	{
		Color color;
		int size;
		int x,y;
		
		public Node(int x, int y, Color c, int s) 
			{ this.x=x; this.y=y; color=c; size=s; }
			
		public void changeColor()
		{
			color=new Color(
				(int)(150),
				(int)(50),
				(int)(50));
		}
		
		public void draw(Graphics g)
		{
			g.setColor(color);
			g.fillOval(x,y,size, size/2);
		}
	}
	class Backdrop extends JPanel
	{		
		Node [] nodes;
		
		public Backdrop()
		{
			nodes=new Node[9];
			setSize(1100,600);
			//f=new Node(100,100, Color.yellow, 200);
			for(int i=0; i<nodes.length; i++)
			{
				if(i<=2)
				{
					nodes[i]=new Node(
						(int)(125+350*i),
						(int)(100),
						Color.yellow,
						(int)(100)
					);
				}
				else if(i<=5)
				{
					nodes[i]=new Node(
						(int)(300+175*(i-3)),
						(int)(250),
						Color.yellow,
						(int)(100)
					);
				}
				else if(i<=8)
				{
					nodes[i]=new Node(
						(int)(125+350*(i-6)),
						(int)(400),
						Color.yellow,
						(int)(100)
					);
				}	
			}
		}
		
		public void setcolor(int i) { nodes[i].changeColor(); }

		public void paintComponent(Graphics g)
		{
			// gameboard
			g.setColor(Color.gray);
			g.fillRect(0,0, 1100,600);
			// lines
			g.setColor(Color.black);
			g.drawLine(175, 125, 875, 125);
			g.drawLine(175, 125, 875, 425);
			g.drawLine(175, 125, 525, 425);
			g.drawLine(525, 125, 175, 425);
			g.drawLine(525, 125, 525, 425);
			g.drawLine(525, 125, 875, 425);
			g.drawLine(875, 125, 175, 125);
			g.drawLine(875, 125, 175, 425);
			g.drawLine(875, 125, 525, 425);
			//f.draw(g);
			for(int i=0; i<nodes.length; i++)
				nodes[i].draw(g);
			// numbers
			g.setColor(Color.black);
			g.drawString("1",172,125);
			g.drawString("2",522,125);
			g.drawString("3",872,125);
			g.drawString("4",347,275);
			g.drawString("5",522,275);
			g.drawString("6",697,275);
			g.drawString("7",172,425);
			g.drawString("8",522,425);
			g.drawString("9",872,425);
		}
	}
	
	JLabel header;
	JLabel instruction;
	JButton setcolor;
	JTextField input;
	Backdrop gameboard;
	JLabel endofgame;
	JButton playagain;
	JButton exit;
	boolean win;
	boolean lose;
	boolean draw;
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==setcolor)
		{
			String x=input.getText();
			int i=Integer.parseInt(x);
			gameboard.setcolor(i-1);
			// forces a redraw of the gameboard art object
			gameboard.repaint();
		}
		else if(e.getSource()==playagain)
		{
			JTTMain submarine=new JTTMain();
		}
		else if(e.getSource()==exit)
		{
			System.out.println("Hope you had fun!");
			System.exit(0);
		}
	}
	
	public JTTMain()
	{
		setTitle("Jerry Tac Toe: Tik Tac Toe but better!");
		setSize(1100,700);
		addWindowListener(new Closer());
		
		header=new JLabel("Welcome to Jerry Tac Toe! Have fun playing. But be" +
							" warned: you are no match for Jerry!");
		instruction=new JLabel("Enter the node you'd like to mark then press \"Submit Move\".");
		setcolor=new JButton("   Submit Move   ");
		input=new JTextField("");
		endofgame=new JLabel("The game has ended! There's a button on your left to try again (fruitlessly) or if you've had enough click exit.");
		playagain=new JButton("Play Again/Reset");
		exit=new JButton("  Exit  ");
		gameboard=new Backdrop();
		
		setcolor.addActionListener(this);
		playagain.addActionListener(this);
		exit.addActionListener(this);
		
		// put stuff in the window
		Container glass=getContentPane();
		glass.setLayout( new BorderLayout() ) ; // layout manager

		JPanel bottom=new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add(instruction,"West");
		bottom.add(input,"Center");
		bottom.add(setcolor,"East");
		
		JPanel top=new JPanel();
		top.setLayout(new BorderLayout());
		top.add(header,"Center");
		win=true;
		if(win==true | lose==true | draw==true)
		{
			top.add(endofgame, "Center");
			top.add(playagain,"West");
			top.add(exit,"East");
		}
		
		glass.add(top, "North");
		glass.add(gameboard, "Center");
		glass.add(bottom, "South");
		glass.add(new JPanel(),"East");
		glass.add(new JPanel(),"West");
		
		setVisible(true);
	}
	public static void main(String [] args)
	{
		JTTMain submarine=new JTTMain();
	}
}