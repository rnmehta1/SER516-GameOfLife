import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Canvas extends JFrame {

    //Spacing constrains for graphics
    public int mx = -100;
    public int my = -100;
    int spacing = 1;

    //Game of life cell grid
    boolean cells[][] = new boolean[40][20];

    //Boolean that indicates if the simulation is running
    static volatile boolean running = false;
    //Thread to update game of life cells
    public Thread updater;

    public Canvas() {
        setSize(1440, 900);
        setUpButtons();
        Grid grid = new Grid();
        this.add(grid);
        PaintCell paintCell = new PaintCell();
        this.addMouseListener(paintCell);

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 20; y++) {
                cells[x][y] = false;
            }
        }
        //This thread updates the game of life grid once every second
        updater = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (running) {
                        generateNewCells();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

        updater.start();

        setVisible(true);
    }
    /*
    This function applies the rules of the game of life to the cell grid is responsible for changing a cells "alive"
    or "dead" status.
     */
    public void generateNewCells() {
        boolean[][] nextGrid = new boolean[cells.length][cells[0].length];
        //Cell neighbors
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1},};
        //Loop through all cells and count their neighbors
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                int neighboursTrue = 0;
                //Count cell neighbors
                for (int[] dir : dirs) {
                    if (i + dir[1] >= 0 && i + dir[1] < 40 && j + dir[0] >= 0 && j + dir[0] < 20) {
                        if (cells[i + dir[1]][j + dir[0]]) {
                            neighboursTrue++;
                        }
                    }
                }
                //A cell cannot be its own neighbor
                if (cells[i][j]) {
                    neighboursTrue--;
                }
                //Game of life rules
                if (cells[i][j] && neighboursTrue < 2) {
                    nextGrid[i][j] = false;
                } else if (cells[i][j] && neighboursTrue >= 4) {
                    nextGrid[i][j] = false;
                } else if (!cells[i][j] && neighboursTrue == 3) {
                    nextGrid[i][j] = true;
                } else {
                    nextGrid[i][j] = cells[i][j];
                }

            }
        }
        //Update grid with next generation of cells
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = nextGrid[i][j];
            }
        }
        //Rerender the grid
        repaint();
    }

    /*
    This method adds the buttons the the main panel and gives them control over starting and stopping the game
     */
    public void setUpButtons() {
        //Create the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1440, 100));
        JButton startPauseButton = new JButton("Start");
        JButton resetButton = new JButton("Reset");

        //When the Start button is clicked
        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startPauseButton.getText().startsWith("S")) {
                    startPauseButton.setText("Pause");
                    running = true;
                } else {
                    startPauseButton.setText("Start");
                    running = false;
                }
            }
        });

        //When the reset button is clicked
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setText("Start");
                running = false;
                for (int x = 0; x < 40; x++) {
                    for (int y = 0; y < 20; y++) {
                        cells[x][y] = false;
                    }
                }
                repaint();
            }
        });

        //Add buttons to the main panel
        buttonPanel.add(startPauseButton);
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.PAGE_END);
    }

    /*
    This class handles the rendering for the grid.
     */
    public class Grid extends JPanel {

        public void paint(Graphics g) {
            //Draw grid background
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 30, 1200, 600);
            //Loop through all cells
            for (int x = 0; x < 40; x++) {
                for (int y = 0; y < 20; y++) {
                    //Only draw yellow cells if they are alive
                    if (cells[x][y]) {
                        g.setColor(Color.yellow);
                        g.fillRect(spacing + x * 30, spacing + y * 30 + 30, 30 - 2 * spacing, 30 - 2 * spacing);
                    }
                    //Draw grid
                    g.setColor(Color.gray);
                    g.drawRect(spacing + x * 30, spacing + y * 30 + 30, 30 - 2 * spacing, 30 - 2 * spacing);
                }
            }
        }
    }

    /*
    This class listens for when the user clicks on a cell in the grid
     */
    public class PaintCell implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            //Transforms mouse click coordinates into game of life grid coordinates
            int gridX = (e.getX() - spacing) / 30;
            int gridY = (e.getY() - spacing - 60) / 30;
            if (gridX < 40 && gridY < 20) {
                //Set selected cell to "alive" status
                cells[gridX][gridY] = !cells[gridX][gridY];
            }
            //Rerender the grid with updated cell information
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
