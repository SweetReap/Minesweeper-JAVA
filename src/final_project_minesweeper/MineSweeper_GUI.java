package final_project_minesweeper;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MineSweeper_GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//just in case
	MainPanel main;
	JLabel numberOfBombs;
	JPanel top_ui;

	//grid variables for pre determined board
	private  int numRows = 5;
	private  int numCols = 5;
	private  double spread = 2.00;
	private  double difficulty = (numCols*numRows) / spread;
	
	
	//set for the player to make
	private int inputRows;
	private int inputCols;
	private int inputBombs;
	
	private int totalSafeTiles;
	
	//gui variables
	public Grid level;
	public ImageIcon bomb = new ImageIcon(".//bomb.png");
	
	public MineSweeper_GUI() { // Main GUI for game
		setSize(800,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setBackground(Color.decode("#566E3D"));
		
		setLayout(new BorderLayout());
		setTitle(" |Minesweeper v0.85| ");
		
		createHeaderPanel(); //creating header
		
		createNewGrid(); //creating bomb panel
		
		setVisible(true);
	}
	
	

	private void createHeaderPanel() {
		HeaderPanel header = new HeaderPanel();
		add(header, BorderLayout.NORTH);
	}


	public void createNewGrid() {	
		level = new Grid(numRows,numCols,(int)difficulty);
		
		if( main != null ) { //repaints a new main panel before adding a new one - may or may not work
			remove(main);
			revalidate();
			repaint();
		}
		
		//setting up main game grid
		main = new MainPanel(level);
		add(main, BorderLayout.CENTER);
		
	}

	public void checkGame() { // checks if player clicked on all of the safe buttons
		if(totalSafeTiles == 0) {
			displayWin();
		}
	}


	private void displayWin() {
		int option = JOptionPane.showConfirmDialog(main,"You have avoided all of the bombs! Would you like to go again?","YOU WIN",JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			main.clearAllCells();
		}else {
			System.exit(0);
		}
	}
	
	public void displayLoss() {
		int option = JOptionPane.showConfirmDialog(main,"GAME OVER! Would you like to start over?","BOOM!", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		if(option == JOptionPane.YES_OPTION) {
			main.clearAllCells();
		}else {
			System.exit(0);
		}
	}

	public void revealMines() { // reveals all bombs if player touches one bomb
		for(Component cell: main.getComponents()){
			if(cell.getName().equals("BOMB")) {
				((JButton) cell).setIcon(bomb);
			}
		}
		
	}//End of Frame Class
	
	class MainPanel extends JPanel { // Grid Panel
		
		private static final long serialVersionUID = 1L;

		public MainPanel(Grid grid) {
			setLayout(new GridLayout(grid.getNumRows(),grid.getNumRows()));
			setBackground(Color.decode("#566E3D"));
			setPreferredSize(new Dimension(600,500));
			
			StartGame(); //initial game start
			setVisible(true);
		}
		
		//functions of Main Panel class
		
		public void StartGame() {
			int x,y;
			totalSafeTiles = (numRows * numCols) - (int)difficulty;
			
			for(x = 0;x < level.getNumRows();x++) { // creates a new cell that can be a bomb or safe cell; adds to main panel cell
				
				for(y = 0;y < level.getNumColumns();y++) {
					
					Grid_Cells cell = new Grid_Cells();
	
					if(level.isBombAtLocation(x, y)) {
						cell.setName("BOMB");
					}else {
						cell.setName(String.valueOf(level.getCountAtLocation(x, y)));
					}
					
					add(cell);
				}
			}
		
			repaint();
			revalidate();
		}
		
		public void clearAllCells() { //essentially restarts the game
			removeAll();
			revalidate();
			repaint();
			createNewGrid();
		}
		
	} //End of Panel Class

	class Grid_Cells extends JButton implements ActionListener { // Cell Button
		
	
		private static final long serialVersionUID = 1L;
		public boolean bombClicked = false;
		
		public Grid_Cells() {
			addActionListener(this);
			setFont(new Font("Helvetica",Font.BOLD,45));
			setPreferredSize(new Dimension(75,75));	
			setEnabled(true);
		}
		
		//functions of Cell class
		
		public void actionPerformed(ActionEvent e) { //ooo button i can click
			JButton clicked = (JButton) e.getSource();
			
			if(clicked.equals(this)) {
				if(getName() != "BOMB") {
					setText(getName());
					int numBombs = Integer.valueOf(getName());
					setTextColor(numBombs);
					totalSafeTiles--;
					checkGame();
				}else {
					setIcon(bomb);
					revealMines();	
					displayLoss();				
				}
			}
		}


		private void setTextColor(int numBombs) { //to make it pretty
			switch(numBombs){
			case 0:
				setForeground(Color.GREEN);
				break;
			case 1:
				setForeground(Color.decode("#0C4767"));
				break;
			case 2:
				setForeground(Color.decode("#566E3D"));
				break;
			case 3:
				setForeground(Color.decode("#EEE82C"));
				break;
			case 4:
				setForeground(Color.decode("#FE9920"));
				break;
			case 5:
				setForeground(Color.decode("#FA7921"));
				break;
			default:
				setForeground(Color.red);
				break;
			}
		}
		
	}// End of Button Class

	class HeaderPanel extends JPanel { // Header Panel
		
		private static final long serialVersionUID = 1L;

		HeaderPanel(){
			setLayout(new BorderLayout());	
			this.setSize(800, 160);
			
			JPanel bombPanelComp = new JPanel();
			JPanel imgComp = new JPanel();
			JPanel bombCounter = new JPanel();
			JLabel bcText = new JLabel(String.valueOf((int)difficulty));
			
			bombPanelComp.setLayout(new FlowLayout(FlowLayout.CENTER));
			bombPanelComp.setSize(340,100);
			bombPanelComp.setBackground(Color.decode("#566E3D"));
			
			imgComp.add(new JLabel(bomb));
			imgComp.setBackground(Color.decode("#566E3D"));
			
			bombCounter.add(bcText); 
			bcText.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 55));
			bombCounter.setBackground(Color.decode("#566E3D"));
			bcText.setForeground(Color.white);
			
			bombPanelComp.add(imgComp);
			bombPanelComp.add(bombCounter);
		
			add(bombPanelComp, BorderLayout.PAGE_START);
			
		}
		
	}// End of Header Class

}
