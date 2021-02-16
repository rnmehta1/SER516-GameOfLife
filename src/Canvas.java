import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Canvas extends JFrame {

    public int mx = -100;
    public int my = -100;
    boolean cells[][] = new boolean[1201][601];


    public Canvas() {
        setSize(1440, 900);
        setUpButtons();
        setVisible(true);
        Grid grid=new Grid();
        add(grid);
        PaintCell paintCell = new PaintCell();
        this.addMouseListener(paintCell);
        Move move = new Move();
        this.addMouseMotionListener(move);
        for (int x = 120; x <= 1200; x += 30) {
            for (int y = 120; y <= 600; y += 30) {
                cells[x][y] = false;
            }
        }

    }

    public void setUpButtons(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1440, 200));
        JButton startPauseButton = new JButton("Start");
        JButton resetButton = new JButton("Reset");

        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startPauseButton.getText().startsWith("S")) {
                    startPauseButton.setText("Pause");
                    System.out.println("Start has been clicked!");
                }else {
                    startPauseButton.setText("Start");
                    System.out.println("Pause has been clicked!");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset has been clicked!");
            }
        });

        buttonPanel.add(startPauseButton);
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.PAGE_END);
    }

    public class Grid extends JPanel {

        public void paint(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(120,120,1110,510);
            g.setColor(Color.gray);
            for (int x = 120; x <= 1200; x += 30) {
                for (int y = 120; y <= 600; y += 30) {
                    if (mx >= x && mx < x + 30) {
                        g.setColor(Color.red);
                    }
                    g.drawRect(x, y, 30, 30);

                }
            }
        }
    }
    public static void main( String args[] ) {
        Canvas application = new Canvas();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );


    }

    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            System.out.println("mouse moved");
            mx=e.getX();
            my=e.getY();
        }
    }

    public class PaintCell implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e){
            System.out.println("Mouse clicked");
            mx=e.getX();
            my=e.getY();
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
