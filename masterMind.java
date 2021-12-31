import java.awt.*;

import javax.swing.*;

public class MasterMind {
    
    private static long oldTime;
    private static int deltaTime;
    private static long currentTime;
    private static double fps = 30;
    private static double delay = 1000 / fps;
    public static boolean isPainting;
    private static int w;
    private static int h;
    private static JFrame f;
    private static myImage endpoint;
    static Polygon sprite;

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
        Vector2[] verticies = new Vector2[3];
        for (int i = 0; i < verticies.length; i++) {
            verticies[i] = Vector2.random(Vector2.cartesianInit(0, 0), Vector2.cartesianInit(500, 500));
        }
        sprite = new Polygon(verticies, Vector2.zero, 0, Color.white, Vector2.zero, Vector2.cartesianInit(100, 50));
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
            endpoint.myAdd(sprite);
            sprite.position = Vector2.add(sprite.position, Vector2.scale(sprite.velocity, deltaTime));
            if (sprite.position.x > w - 100 || sprite.position.x < -25) {
                sprite.position.x *= -1;
            }
            if (sprite.position.y > h - 100 || sprite.position.y < -25) {
                sprite.position.y *= -1;
            }
            endpoint.repaint();
        }
        
    }
}
