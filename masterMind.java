import java.awt.*;

import javax.swing.*;

public class masterMind {
    
    private static long oldTime;
    private static int deltaTime;
    private static long currentTime;
    private static float fps = 30;
    private static float delay = 1000 / fps;
    public static boolean isPainting;
    private static int w;
    private static int h;
    private static JFrame f;
    private static myImage endpoint;
    private static float xVel, xPos, yVel, yPos;

    public static void main(String[] args) throws InterruptedException {
        isPainting = false;
        w = 1300;
        h = 700;
        f = new JFrame();
        endpoint = new myImage(w, h, Color.black, true);
        f.setSize(w, h);
        f.setTitle("Vector Grahpics");
        f.add(endpoint);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        xVel = (w/1000f)/10;
        xPos = 0;
        yVel = (w / 1000f) / 10;
        yPos = 0;

        oldTime = System.currentTimeMillis();
        deltaTime = 0;
        runGame();
    }
        
    // Possible future error in the flooring to an int making a difference 
    // in what you see and what the game knows are the positions or something of the sort
    public static void runGame() throws InterruptedException{
        while (true) {
            // do{
                // currentTime = System.currentTimeMillis();
                // System.out.println("Time allows: " + !(currentTime - oldTime < delay) + " Painting allows: " + !isPainting);
                // } while (currentTime - oldTime < delay || isPainting);
            if ((deltaTime = (int) ((currentTime = System.currentTimeMillis()) - oldTime)) < delay) {
                Thread.sleep((int) delay - deltaTime );
            }
            currentTime = System.currentTimeMillis();
            deltaTime = (int) (currentTime - oldTime);
            oldTime = currentTime;
            endpoint.fill(Color.black);
            endpoint.addRect(Color.white, (int) (xPos), (int) yPos, 100, 100);
            xPos += (int) (xVel * deltaTime);
            yPos += (int) (yVel * deltaTime);
            if (xPos > w - 100 || xPos < -25) {
                xVel *= -1;
            }
            if (yPos > h - 100 || yPos < -25) {
                yVel *= -1;
            }
            endpoint.repaint();
        }
        
    }
}
