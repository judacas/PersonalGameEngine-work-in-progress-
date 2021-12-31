import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class myImage extends JPanel {

    private BufferedImage canvas;
    public Color[][] masterPiece;
    public int width, height;
    private boolean isPaintable;

    public myImage(int width, int height, Color col, boolean isPaintable) {
        this.isPaintable = isPaintable;
        this.width = width;
        this.height = height;
        if (isPaintable) {
            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        fill(col);
        // masterpiece = addRect(masterpiece, new Color (255,0,255,255), width / 4, height / 8, width / 2, height / 4);
        // canvas.setRGB(0, 0, masterpiece.length, masterpiece[0].length, ColorMaths.convertTo1D(masterpiece), 0, masterpiece.length);
        // repaint();
    }

    public myImage(Color[][] image) {
        this.masterPiece = image;
        isPaintable = false;
        width = image.length;
        height = image[0].length;
        setBackground(new Color(0,0,0,0));
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        if (isPaintable) {
            MasterMind.isPainting = true;
            canvas.setRGB(0, 0, width, height, convertTo1D(masterPiece), 0, width);
            super.paintComponent(g);
            ((Graphics2D) g).drawImage(canvas, null, null);
            MasterMind.isPainting = false;
        }
        else {
            System.out.println("ayo your painting a nonpaintable image bruh");
        }
    }


    // public void fill(Color c) {
    //     for (int x = 0; x < masterPiece.length; x++) {
    //         for (int y = 0; y < masterPiece[0].length; y++) {
    //             masterPiece[x][y] = c;
    //         }
    //     }
    // }
    public void fill(Color color) {
        masterPiece = new Color[width][height];
        setBackground(color);
    }

    public void setGradient(Color c) {
        Vector4 col = new Vector4(c);
        for (int x = 0; x < masterPiece.length; x++) {
            for (int y = 0; y < masterPiece[0].length; y++) {
                col.l = (int)((double)(x+y)/(double)(masterPiece.length + masterPiece[0].length) * 255);
                masterPiece[x][y] = col.toColor();
            }
        }
    }


    public void drawLine(Color c, int x1, int y1, int x2, int y2) {
        // Implement line drawing
        repaint();
    }

    public void addRect(Color c, int x1, int y1, int width, int height) {
        if(width > 0 && height > 0)
            myAdd(new myImage(width, height, c, false), x1, y1);
    }

    public void drawOval(Color c, int x1, int y1, int width, int height) {
        // Implement oval drawing
        repaint();
    }

    public void myAdd(myImage adde, int xStart, int yStart) {
        for (int x = (Math.min(xStart, 0) * -1); x < adde.width && x + xStart < width; x++) {
            for (int y = (Math.min(yStart, 0) * -1); y < adde.height && y + yStart < height; y++) {
                // You gotta check if the adde is null because of the getalpha
                masterPiece[x + xStart][y + yStart] = (adde.masterPiece[x][y] != null) ? interpolate(masterPiece[x + xStart][y + yStart], adde.masterPiece[x][y], adde.masterPiece[x][y].getAlpha() / 255f) : adde.getBackground();
            }
        }
    }

    public void myAdd(Polygon polygon) {
        myAdd(new myImage(polygon.pixelsOutline), (int)polygon.position.floor().x, (int)polygon.position.floor().y);
    }
    




    /** we are going of the basis that we will always have a solid background and 
     * only add semi transperent objects on top.
     * thus we can interpolate between the two colors where the interporlation factor
     * is just the alpha value. 
     * thus in essence it is c1 + (c2-c1) * c2.a but for each color individually
    */
    public static Color interpolate(Color c1, Color c2, double t) {
        if (c1 == null) {
            return c2;
        }
        if (c2 == null) {
            return c1;
        }
        int r, g, b;
        r = c1.getRed() + (int) ((c2.getRed() - c1.getRed()) * t);
        g = c1.getGreen() + (int) ((c2.getGreen() - c1.getGreen()) * t);
        b = c1.getBlue() + (int) ((c2.getBlue() - c1.getBlue()) * t);

        return new Color(r, g, b);
    }
    

    public static int[] convertTo1D(Color[][] old) {
        // System.out.println(old.length + "  |  " + old[0].length);
        int[] temp = new int[old.length * old[0].length];
        // System.out.println(temp.length);
        int i = 0;
        for (int y = 0; y < old[0].length; y++) {
            for (int x = 0; x < old.length; x++) {
                // System.out.println(i);
                // if (old[x][y] != null) {
                //     System.out.println(old[x][y].toString());
                // }
                // else {
                //     System.out.println(x + "  |  " + y);
                // }
                temp[i] = (old[x][y] != null) ? old[x][y].getRGB() : new Color(0,0,0,0).getRGB();
                i++;
            }
        }
        return temp;

    }


}