import java.awt.Color;
public class Vector4 {
    public double i, j, k, l;

    public Vector4(double i, double j, double k, double l) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }
    
    public Vector4(Color c){
        this.i = c.getRed();
        this.j = c.getGreen();
        this.k = c.getBlue();
        this.l = c.getAlpha();
    }

    public Color toColor() {
        return new Color((int)i, (int)j, (int)k, (int)l);
    }

    
}
