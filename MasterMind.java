import java.awt.*;

import javax.swing.*;

public class MasterMind {
    
    private static int deltaTime;
    private static long oldTime, currentTime, startTime;
    private static double fps = 30, delay = 1000 / fps, theta;
    public  static boolean isPainting;
    public  static int w,h;
    private static JFrame f;
    private static myImage endpoint;
    private static Polygon sprite, xAxis, yAxis, testLine;
    private static Line line;

    public static void main(String[] args) throws InterruptedException {
        startTime = System.currentTimeMillis();
        isPainting = false;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        w = (int) (screen.width * 0.8);
        h = (int) (screen.height * 0.8);
        // System.out.print(line);
        f = new JFrame();
        endpoint = new myImage(w, h, Color.black, true);
        f.setSize(w + 15, h + 40);
        // I honestly don't know why this is necessary but it seems to work (the adding 15 and 40)
        f.setTitle("Vector Graphics");
        f.add(endpoint);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        Vector2[] verticies = new Vector2[(int) (Math.random() * 0 + 4)];

        for (int i = 0; i < verticies.length; i++) {
            verticies[i] = Vector2.polarInit(((double) i / (double) verticies.length) * ((2 * Math.PI)), 100);
        }
        // verticies[0] = Vector2.cartesianInit(20, 0);
        // verticies[1] = Vector2.cartesianInit(50, 20);
        // verticies[2] = Vector2.cartesianInit(-40, 25);
        // verticies[3] = Vector2.cartesianInit(25, 25);
        sprite = new Polygon(verticies, Vector2.cartesianInit(w / 2, h / 2), Vector2.ZERO,Vector2.cartesianInit(w / 5, h / 7), 0, new Color(255, 255, 255, 255));
        xAxis = new Polygon(Line.initPolar(Vector2.cartesianInit(0, h / 2), 0, w).toVectors(), Color.red);
        yAxis = new Polygon(Line.initPolar(Vector2.cartesianInit(w / 2, 0), Math.PI / 2f, h).toVectors(), Color.green);
        testLine= new Polygon(Line.initPolar(Vector2.ZERO, 0, 200).toVectors(), Color.yellow);
        oldTime = System.currentTimeMillis();
        deltaTime = 0;
        // endpoint.fill(Color.black);
        // endpoint.myAdd(xAxis, false);
        // endpoint.myAdd(yAxis, false);
        // endpoint.myAdd(sprite, false);
        endpoint.paint(endpoint.getGraphics());
        endpoint.repaint();
        line = Line.initPolar(Vector2.cartesianInit(w / 1.5, h / 2), 0 * Math.PI / 4f, 200);
        theta = 0;
        gameLoop();
    }
    
    public static void gameLoop() throws InterruptedException {
        while (true) {
            if ((deltaTime = (int) ((currentTime = System.currentTimeMillis()) - oldTime)) < delay) {
                Thread.sleep((int) delay - deltaTime);
            }
            currentTime = System.currentTimeMillis();
            deltaTime = (int) (currentTime - oldTime);
            oldTime = currentTime;
            // runFrame();
            testLineRotation();
            endpoint.repaint();
        }
    }
    
    public static void testLineRotation() {
        theta += deltaTime / 2000f;
        testLine.rotate(theta);
        System.out.println(line);
        System.out.println("Theta is " + theta);
        endpoint.fill(Color.black);
        endpoint.myAdd(xAxis, false);
        endpoint.myAdd(yAxis, false);
        line.rotate(theta, Vector2.cartesianInit(w/2,h/2)).drawOnTo(endpoint.masterPiece, Color.blue);
        try {
            endpoint.myAdd(testLine, false);
        } catch (Exception e) {}
        // line.rotate(theta).floor().drawOnTo(endpoint.masterPiece, Color.white);
    }
        
    // Possible future error in the flooring to an int making a difference 
    // in what you see and what the game knows are the positions or something of the sort
    public static void runFrame() throws InterruptedException {
        // sprite.rotate(deltaTime/1000f);
        //rotating kills it so uhhh FIX IT
        endpoint.fill(Color.black);
        endpoint.myAdd(xAxis, false);
        endpoint.myAdd(yAxis, false);
        endpoint.myAdd(sprite, false);
        sprite.position = sprite.position.add(sprite.velocity.scale(deltaTime));

        if (sprite.position.x > w - 00 || sprite.position.x < -0) {
            sprite.velocity.x *= -1;
        }
        if (sprite.position.y > h - 00 || sprite.position.y < -0) {
            sprite.velocity.y *= -1;
        }
    }
}
