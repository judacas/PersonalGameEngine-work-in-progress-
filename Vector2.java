public class Vector2 {
    public double x, y, theta, magnitude;
    public static Vector2 ZERO = new Vector2(0, 0, 0, 0);

    
    // use this method signature for cartesian coordinate definition
    public Vector2(double x, double y, double theta, double magnitude) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.magnitude = magnitude;
    }
    
    public static Vector2 cartesianInit(double x, double y) {
        double tempTheta = Math.atan2(y, x);
        double tempMagnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Vector2(x, y, tempTheta, tempMagnitude);

    }
    
    public static Vector2 polarInit(double theta, double magnitude) {
        double tempX = magnitude * Math.cos(theta);
        double tempY = magnitude * Math.sin(theta);
        return new Vector2(tempX, tempY, theta, magnitude);
    }
    
    public Vector2 add(Vector2 a) {
        return Vector2.cartesianInit(this.x + a.x, this.y + a.y);
    }

    public Vector2 subtract(Vector2 a) {
        return this.add(a.scale(-1));
    }

    public Vector2 scale(double scalar) {
        return Vector2.cartesianInit(this.x * scalar, this.y * scalar);
    }

    public static double distance(Vector2 a, Vector2 b) {
        return (double) (Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)));
    }
    
    public Vector2 rotate(double theta) {
        return Vector2.polarInit((this.theta + theta)%(2*Math.PI), this.magnitude);
    }

    public Vector2 rotate(double theta, Vector2 origin) {
        return Line.initCartesian(origin, this).rotate(theta).endPoint;
    }

    public Vector2 floor() {
        return Vector2.cartesianInit((int)(x), (int)(y));
    }
    
    public static Vector2 random(Vector2 minimum, Vector2 maximum) {
        return Vector2.cartesianInit(minimum.x + Math.random() * (maximum.x - minimum.x), minimum.y + Math.random() * (maximum.y - minimum.y));
    }

    public boolean equals(Vector2 vector) {
        return (x == vector.x) && (y == vector.y);
    }

    public String toString() {
        return "X: " + (Math.round(x*100)/100f) + " Y: " + (Math.round(y*100)/100f);
    }
    
}
