import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
public class Canvas extends JFrame {

    public int mx = -100;
    public int my = -100;
    boolean cells[][] = new boolean[1201][601];

    public Canvas() {
        setSize(1440, 900);
        setVisible(true);
        Grid grid=new Grid();
        add(grid);
        PaintCell paintCell = new PaintCell();
        this.addMouseListener(paintCell);
        for (int x = 120; x <= 1200; x += 30) {
            for (int y = 120; y <= 600; y += 30) {
                cells[x][y] = false;
            }
        }

    }

    public class Grid extends JPanel {


        public void paint(Graphics g) {
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
