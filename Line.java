import java.awt.Color;
//OK I get it, its a line segment not a line whatever
public class Line {
    public Vector2 startPoint, endPoint;
    public double theta, length, normal, width, slope;
    public final Vector2 left, right, top, bottom;

    public enum Type {
        vertical,
        horizontal,
        normal
    }

    public Type type;

    public Line(Vector2 startPoint, Vector2 endPoint, double theta, double length) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        // System.out.println("\nAyo test  " + this.startPoint + " another test  " + this.endPoint);
        this.length = length;
        this.theta = theta;
        normal = theta + Math.PI;
        slope  = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);
        left   = (startPoint.x < endPoint.x) ? startPoint : endPoint;
        right  = (startPoint.x > endPoint.x) ? startPoint : endPoint;
        top    = (startPoint.y > endPoint.y) ? startPoint : endPoint;
        bottom = (startPoint.y < endPoint.y) ? startPoint : endPoint;
        if (endPoint.y - startPoint.y == 0) {
            type = Type.horizontal;
        }
        else if (endPoint.x - startPoint.x == 0) {
            type = Type.vertical;
        }
        // else if (Math.abs(endPoint.y - startPoint.y) > Math.abs(endPoint.x - startPoint.x)) {
        //     type = Type.normal;
        // }
        else {
            type = Type.normal;
        }
        // System.out.println("made a " + type + " line");
    }

    public static Line initPolar(Vector2 startPoint, double theta, double length) {
        // System.out.println("polat line initiated");
        Vector2 tempEndPoint = startPoint.add(Vector2.polarInit(theta, length));
        return new Line(startPoint, tempEndPoint, theta, length);

    }

    public static Line initCartesian(Vector2 startPoint, Vector2 endPoint) {
        // System.out.println("\nAyo test  " + startPoint + " another test  " + endPoint);
        double tempTheta = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
        double tempLength = Vector2.distance(startPoint, endPoint);
        return new Line(startPoint, endPoint, tempTheta, tempLength);

    }
    
    public Line floor() {
        return initCartesian(startPoint.floor(), endPoint.floor());
    }

    public Line move(Vector2 moveBy) {
        return Line.initCartesian(startPoint.add(moveBy), endPoint.add(moveBy));
    }

    public Line scale(double scalar){
        return Line.initPolar(startPoint, theta, length * scalar);
    }

    public Line rotate(double theta) {
        // System.out.println("rotating");
        // Line temp = line;
        // temp.endPoint = Vector2.rotate(t3emp.endPoint, temp.startPoint, theta);
        return Line.initPolar(this.startPoint, (this.theta + theta)%(2*Math.PI), this.length);
    }

    public Line rotate(double theta, Vector2 origin) {
        // Line temp = line;
        // temp.endPoint = Vector2.rotate(temp.endPoint, origin, theta);
        // temp.startPoint = Vector2.rotate(temp.startPoint, origin, theta);
        return Line.initCartesian(startPoint.rotate(theta, origin), endPoint.rotate(theta, origin));
    }

    public double predictY(double x) {
        return (x - startPoint.x) * slope + startPoint.y;
    }

    public double predictX(double y) {
        return ((y - startPoint.y) / slope) + startPoint.x;
    }

    public String toString() {
        return "starts at: " + startPoint + " | ends at: " + endPoint + "\n\n";
    }

    public Vector2[] toVectors() {
        Vector2[] vectors = new Vector2[2];
        vectors[0] = startPoint;
        vectors[1] = endPoint;
        return vectors;
    }
    public Color[][] drawOnTo(Color[][] image, Color color) {
        // make a debug statement saying if your trying to draw a non integer vector
        switch (type) {
            case horizontal:
                for (int x = Math.max((int)left.x, 0); x < Math.min((int)right.x, MasterMind.w); x++) {
                    image[x][(int) startPoint.y] = myImage.interpolate(image[x][(int) startPoint.y], color, color.getAlpha()/255f);
                }
                break;
            case vertical:
                for (int y = Math.max((int) bottom.y, 0); y < Math.min((int) top.y, MasterMind.h); y++) {
                    image[(int)startPoint.x][y] = myImage.interpolate(image[(int)startPoint.x][y], color, color.getAlpha()/255f);
                }
                break;
            case normal:
            int xStart = (int) bottom.x;
                int xEnd = xStart;
                // image[(int)bottom.x][(int)bottom.y] = color;
                int xDirection = (top.x > bottom.x) ? 1 : -1;
                for (int y = Math.max((int) bottom.y, 0); y <= Math.min((int) top.y, MasterMind.h); y++) {
                    xStart = xEnd;
                    xEnd = (int) predictX(y);
                    
                    for (int x = 0; Math.abs(x) <= (int) Math.abs(xEnd - xStart); x += xDirection) {
                        // System.out.println("xStart:" + xStart + " | xEnd: " + xEnd + " | X: " + x + " | Y: " + y);
                        try {
                            image[xStart + x][y] = myImage.interpolate(image[xStart + x][y], color, color.getAlpha()/255f);
                        } catch (Exception e) {
                            System.out.println("went out of the screen");
                        }
                    }
                }
                // int xStart = (int) bottom.x;
                // int xEnd = xStart;
                // // image[(int)bottom.x][(int)bottom.y] = color;
                // int xDirection = (top.x > bottom.x) ? 1 : -1;
                // for (int y = (int) bottom.y; y <= (int) top.y; y++) {
                //     xStart = xEnd;
                //     xEnd = (int)predictX(y);
                //     for (int x = 0; x <= (int) Math.abs(xEnd - xStart); x += xDirection) {
                //         image[xStart + x][y] = myImage.interpolate(image[xStart + x][y], color, color.getAlpha());;;
                //     }
                // }
                break;
        }
        return image;
    }
}
