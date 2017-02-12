package gc_game;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class MainGame implements KeyListener, AudioClip {

    private JFrame frame;
    private Canvas can;

    int paddle[] = new int[4];
    int ball[] = new int[4];

    int pvX; // Paddlegeschwindigkeit
    int bvX, bvY; // Ballgeschwindigkeit in X und Y Richtung
    //InputStream is = new InputStream();
    //AudioInputStream as = new AudioInputStream();
    //com.sun.media.sound.JavaSoundAudioClip
    
    public void run() throws Exception{
        
        frame = new JFrame();
        can = new Canvas();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.addKeyListener(this);
        Dimension dC = new Dimension();
        dC.setSize(600, 600);
        //frame.setResizable(false);
        can.setSize(dC);
        frame.setSize(can.getWidth() + can.getWidth() / 10, can.getHeight() + can.getHeight() / 10);
        frame.setContentPane(can);
        
        /*
        System.out.println(can.getWidth());
        System.out.println(frame.getWidth());
        System.out.println(can.getHeight());
        System.out.println(frame.getHeight());
        */
        
        paddle[0] = (can.getWidth() / 2) - (can.getWidth() / 20);
        paddle[1] = can.getHeight() - can.getHeight() / 20;
        paddle[2] = (can.getWidth() / 20)*4;
        paddle[3] = can.getHeight() / 20;
        
        ball[2] = 20;
        ball[3] = 20;
        ball[0] = can.getWidth()/2-ball[2];
        ball[1] = 1;
        

        bvY = bvX = can.getHeight()/50;
        pvX = can.getWidth()/50;
        
        frame.setTitle("Bricks");
        frame.repaint();
        
        frame.setVisible(true);
        
        /*
        System.out.println(can.getWidth());
        System.out.println(frame.getWidth());
        System.out.println(can.getHeight());
        System.out.println(frame.getHeight());
        */
        
        int lifes = 3;
        while (lifes > 0) { // GameLoop
            lifes -= gLoop();
        }

    }

    public int gLoop() {
        int gameOver = 0;
        while (gameOver == 0) {
            
            if(
                    (ball[0]+ball[2] > can.getWidth()) || // rechts 
                    (ball[0] <= 0) // links
               )
            {
                bvX *= -1;
            }
            else if(
                    (ball[1] <= 0) || // oben
                    ((ball[1]+ball[3] >= paddle[1]) &&
                    (ball[0] >= paddle[0]) && (ball[0] <= paddle[0]+paddle[2])) // Schläger
                    )
            {
                bvY *= -1;
            }
            ball[0] += bvX;
                ball[1] += bvY;
            
            can.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }

            if (ball[1] + ball[3] >= paddle[1]+paddle[3]) { // unten
                gameOver = 1;
            }
        }

        return gameOver;
    }

    public static void main(String[] args) throws Exception{
        MainGame game = new MainGame();
        
    game.run();
    }

    @Override
    public void play() {}

    @Override
    public void stop() {}

    @Override
    public void loop() {}

    
    public class Canvas extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Stroke bs = new BasicStroke(10);
            g2d.setStroke(bs);

            /* Background */
            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());

            /* Paddle */
            if (paddle != null) {
                g2d.setColor(Color.WHITE);
                paddle[1] = can.getHeight() - can.getHeight() / 20;
                paddle[2] = (can.getWidth() / 20)*4;
                //g2d.fillRect(paddle[0], paddle[1], paddle[2], paddle[3]);
                g2d.fillRoundRect(paddle[0], paddle[1], paddle[2], paddle[3], 20, 20);
            }
            if(ball != null){
                g2d.fillOval(ball[0], ball[1], ball[2], ball[3]);
            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37) {// <-
            if (paddle[0] > 0) {
                paddle[0] -= pvX;
            }
        } else if (e.getKeyCode() == 39) { // ->
            if (paddle[0] + paddle[2] < can.getWidth()) {
                paddle[0] += pvX;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
