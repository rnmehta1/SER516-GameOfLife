import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
public class Canvas extends JFrame {

    public int mx = -100;
    public int my = -100;
    boolean cells[][] = new boolean[40][20];
    int spacing = 1;
    static int running = 0;
    public Canvas() {
        setSize(1440, 900);
        setUpButtons();
        Grid grid=new Grid();
        this.add(grid);
        PaintCell paintCell = new PaintCell();
        this.addMouseListener(paintCell);

        for (int x = 0; x < 40; x ++) {
            for (int y = 0; y <20; y ++) {
                cells[x][y] = false;
            }
        }
        setVisible(true);
    }

    public void generateNewCells(){
        System.out.println("Next Generation");
        boolean [][] nextGrid = new boolean[cells.length][cells[0].length];
        int [][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,0},{0,1},{1,-1},{1,0},{1,1},};
        for (int i = 1; i < cells.length - 1; i++)
        {
            for (int j = 1; j < cells[0].length - 1; j++)
            {
                int neighboursTrue = 0;

                for(int [] dir : dirs) {
                    if (cells[i + dir[1]][j + dir[0]]) {
                        neighboursTrue++;
                    }
                }
                if(cells[i][j]){
                    neighboursTrue--;
                }
                if (cells[i][j] && neighboursTrue < 2){
                    nextGrid[i][j] = false;
                } else if (cells[i][j] && neighboursTrue >= 2){
                    nextGrid[i][j] = false;
                } else if (!cells[i][j] && neighboursTrue == 3) {
                    nextGrid[i][j] = true;
                } else{
                    nextGrid[i][j] = cells[i][j];
                }

            }
        }

        for (int i = 1; i < cells.length - 1; i++)
        {
            for (int j = 1; j < cells[0].length - 1; j++)
            {
                cells[i][j] = nextGrid[i][j];
            }
        }
    }

    public void setUpButtons(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1440, 100));
        JButton startPauseButton = new JButton("Start");
        JButton resetButton = new JButton("Reset");

        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startPauseButton.getText().startsWith("S")) {
                    startPauseButton.setText("Pause");
                    System.out.println("Start has been clicked!");
                    running=1;

                }else {
                    startPauseButton.setText("Start");
                    running=0;
                    System.out.println("Pause has been clicked!");

                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setText("Start");
                System.out.println("Reset has been clicked!");
                new Canvas();
                running=0;
            }
        });

        buttonPanel.add(startPauseButton);
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.PAGE_END);
    }

    public class Grid extends JPanel {

        public void paint(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,30,1200,600);
            for (int x = 0; x < 40; x++) {
                for (int y = 0; y < 20; y ++) {
                    g.setColor(Color.gray);
                    if (mx >= spacing+x*30 && mx < x*30+30-spacing && my>=spacing+y*30+30+26 && my<spacing+y*30+26+30+30-2*spacing) {
                        g.setColor(Color.red);
                        g.fillRect(spacing+x*30, spacing+y*30+30, 30-2*spacing, 30-2*spacing);
                        cells[x][y]=!cells[x][y];
                    }
                    if(cells[x][y]){
                        g.setColor(Color.red);
                        g.fillRect(spacing+x*30, spacing+y*30+30, 30-2*spacing, 30-2*spacing);
                    }
                    g.drawRect(spacing+x*30, spacing+y*30+30, 30-2*spacing, 30-2*spacing);
                }
            }
            if(running==1){
                generateNewCells();
                repaint();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else{
                repaint();
            }
        }
    }

    public class PaintCell implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e){
            System.out.println("Mouse clicked");
            mx=e.getX();
            my=e.getY();
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
