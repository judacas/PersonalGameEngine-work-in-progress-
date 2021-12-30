import java.awt.Color;

//OK I get it, its a line segment not a line whatever
public class Line {
    public Vector2 startPoint, endPoint;
    private double theta, magnitude;

    public Line(Vector2 startPoint, Vector2 endPoint) {
        this.startPoint = startPoint;
        this.endPoint = startPoint;
        magnitude = Vector2.distance(startPoint, endPoint);
        theta = (double)(Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x));
    }

    public Line(Vector2 starpoint, double theta, double magnitude) {
        this.theta = theta;
        this.magnitude = magnitude;
        this.startPoint = starpoint;
        endPoint = Vector2.add(starpoint, new Vector2(theta, magnitude));
    }
    
    // Might make it a bit more efficient not having to calculate the magnitude each time
    // But for right now I don't really care though sooooooo frick it
    // public double getLength() {
    //     if (magnitude == -1) {
    //         magnitude = Vector2.distance(startPoint, endPoint);
    //     }
    //     return magnitude;
    // }

    public Color[][] addToImage(myImage image) {
        Color[][] temp = image.masterPiece;



        return temp;
    }
}
