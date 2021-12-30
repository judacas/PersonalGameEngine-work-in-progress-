public class Vector2 {
    public double x, y, theta, magnitude;
    public static Vector2 zero = new Vector2(0, 0);

    
    // use this method signature for cartesian coordinate definition
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
        this.theta = Vector2.getTheta(this);
        this.magnitude = Vector2.distance(Vector2.zero, this);
    }
    
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 scale(Vector2 vector, double scalar) {
        return new Vector2(vector.x * scalar, vector.y * scalar);
    }

    public static double distance(Vector2 a, Vector2 b) {
        return (double) (Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)));
    }
    
    public static double getTheta(Vector2 vector) {
        return Math.atan2(vector.y, vector.x);
    }
    
    public static Vector2 Polar2Cartesian(double theta, double magnitude){
        return new Vector2(magnitude * Math.cos(theta), magnitude * Math.sin(theta));
    }
    
}
