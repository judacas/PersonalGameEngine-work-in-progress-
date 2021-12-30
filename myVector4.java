import java.awt.Color;
public class myVector4 {
    public float i, j, k, l;

    public myVector4(float i, float j, float k, float l) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }
    
    public myVector4(Color c){
        this.i = c.getRed();
        this.j = c.getGreen();
        this.k = c.getBlue();
        this.l = c.getAlpha();
    }

    public Color toColor() {
        return new Color((int)i, (int)j, (int)k, (int)l);
    }

    
}
