import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Canvas extends JFrame {

    public int mx = -100;
    public int my = -100;
    boolean cells[][] = new boolean[40][20];
    int spacing=1;


    public Canvas() {
        setSize(1440, 900);
        setUpButtons();
        setVisible(true);
        Grid grid=new Grid();
        this.setContentPane(grid);
        PaintCell paintCell = new PaintCell();
        this.addMouseListener(paintCell);
        Move move = new Move();
        this.addMouseMotionListener(move);
        for (int x = 0; x < 40; x ++) {
            for (int y = 0; y < 20; y ++) {
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
