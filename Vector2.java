public class Vector2 {
    public double x, y, theta, magnitude;
    public static Vector2 zero = new Vector2(0, 0, 0, 0);

    
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
    
    public static Vector2 add(Vector2 a, Vector2 b) {
        return Vector2.cartesianInit(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return Vector2.cartesianInit(a.x - b.x, a.y - b.y);
    }

    public static Vector2 scale(Vector2 vector, double scalar) {
        return Vector2.cartesianInit(vector.x * scalar, vector.y * scalar);
    }

    public static double distance(Vector2 a, Vector2 b) {
        return (double) (Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)));
    }
    
    public static Vector2 rotate(Vector2 vector, double theta) {
        return Vector2.polarInit(vector.theta + theta, vector.magnitude);
    }

    public static Vector2 rotate(Vector2 vector, Vector2 origin, double theta) {
        return Vector2.add(rotate(Vector2.subtract(vector, origin), theta), origin);
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
