import java.awt.Color;

// since rectangle has symmetry and ooposit sides congruent and parallerl
// there can be some optimization by overriding some methods
// rectangle optimization will be quite useful as it will be most common to render
// remember that lines with a width are really rectangles
public class Rectangle extends Polygon{

    public Rectangle(Vector2[] verticies, Vector2 origin, double theta) {
        super(verticies, Vector2.ZERO, origin, Vector2.ZERO, theta, Color.WHITE);
    }
    
}
