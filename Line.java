//OK I get it, its a line segment not a line whatever
public class Line {
    public Vector2 startPoint, endPoint;
    public double theta, length, normal, width, slope;

    public Line(Vector2 startPoint, Vector2 endPoint, double theta, double length) {
        this.startPoint = startPoint;
        this.endPoint = startPoint;
        this.length = length;
        this.theta = theta;
        normal = -1 / theta;
        slope = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);
    }

    public static Line initPolar(Vector2 startPoint, double theta, double length) {
        Vector2 tempEndPoint = Vector2.add(startPoint, Vector2.polarInit(theta, length));
        return new Line(startPoint, tempEndPoint, theta, length);

    }

    public static Line initCartesian(Vector2 startPoint, Vector2 endPoint) {
        double tempTheta = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
        double tempLength = Vector2.distance(startPoint, endPoint);
        return new Line(startPoint, endPoint, tempTheta, tempLength);

    }
    
    public Line floor() {
        return initCartesian(startPoint.floor(), endPoint.floor());
    }

    public double predictY(double x) {
        return (x - startPoint.x) * slope + startPoint.y;
    }
}
