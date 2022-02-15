import java.awt.*;

import javax.swing.*;

public class MasterMind {
    
    private static long oldTime;
    private static int deltaTime;
    private static long currentTime;
    private static double fps = 60;
    private static double delay = 1000 / fps;
    public static boolean isPainting;
    private static int w;
    private static int h;
    private static JFrame f;
    private static myImage endpoint;
    static Polygon sprite;
    static Line line;

    public static void main(String[] args) throws InterruptedException {
        isPainting = false;
        w = 1000;
        h = 1000;
        line = Line.initPolar(Vector2.cartesianInit(w / 2, h / 2), 0 * Math.PI/2, Math.min(w, h) * 0.1);
        System.out.print(line);
        f = new JFrame();
        endpoint = new myImage(w, h, Color.black, true);
        f.setSize(w, h);
        f.setTitle("Vector Grahpics");
        f.add(endpoint);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        Vector2[] verticies = new Vector2[(int) (Math.random() * 0 + 4)];
        
        // for (int i = 0; i < verticies.length; i++) {
        //     verticies[i] = Vector2.random(
        //             Vector2.polarInit(((double) i / (double) verticies.length) * ((2 * Math.PI)), 100),
        //             Vector2.polarInit(((double) i / (double) verticies.length) * ((2 * Math.PI)), 100));
        //     // System.out.print("  |  vertex " + (i+1) + " at: " + verticies[i].floor());
        // }
        verticies[0] = Vector2.cartesianInit(0, 25);
        verticies[1] = Vector2.cartesianInit(25, 0);
        verticies[2] = Vector2.cartesianInit(0, -25);
        verticies[3] = Vector2.cartesianInit(-25, 0);
        // sprite = new Polygon(verticies, Vector2.cartesianInit(0, 0), Math.random() * 2 * Math.PI, Color.white, Vector2.cartesianInit(0,0), Vector2.cartesianInit(w/16f, w/8f));
        oldTime = System.currentTimeMillis();
        deltaTime = 0;
        // endpoint.masterPiece = Line.initPolar(Vector2.cartesianInit(w / 2, h / 2), 0, w / 4).floor().drawOnTo(endpoint.masterPiece, Color.white);
        endpoint.masterPiece = line.floor().drawOnTo(endpoint.masterPiece, Color.white);
        endpoint.paint(endpoint.getGraphics());
        // endpoint.repaint();
        runGame();
    }
        
    // Possible future error in the flooring to an int making a difference 
    // in what you see and what the game knows are the positions or something of the sort
    public static void runGame() throws InterruptedException {
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
            // sprite.rotate(deltaTime/1000f);
            oldTime = currentTime;
            endpoint.fill(Color.black);
            // endpoint.myAdd(sprite);
            // System.out.println(deltaTime / 1000d);
            // System.out.println(line);
            endpoint.masterPiece = line.floor().drawOnTo(endpoint.masterPiece, Color.white);
            line = Line.rotate(line, +deltaTime / 5000d);
            // System.out.println(Math.round(line.theta * 10000d)/10000d);
            // sprite.position = Vector2.add(sprite.position, Vector2.scale(sprite.velocity, deltaTime));
            // // System.out.println(sprite.position.floor());

            // if (sprite.position.x > w - 00 || sprite.position.x < -0) {
            //     sprite.velocity.x *= -1;
            // }
            // if (sprite.position.y > h - 00 || sprite.position.y < -0) {
            //     sprite.velocity.y *= -1;
            // }
            endpoint.repaint();
        }
        
    }
}
