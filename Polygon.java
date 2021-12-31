//all polygons must be concave
import java.awt.Color;
public class Polygon {
    //adjacent verticies are to have lines between them, last vertex is to be connected with the first 
    private Line[] lines, rotatedLines;
    private Vector2[] verticies, rotatedVerticies;
    private Vector2 leftMost, rightMost, bottomMost, topMost;
    public Vector2 position, velocity;
    public double theta;
    private Color color;
    public Color[][] pixelsFilled;
    public Color[][] pixelsOutline;
    private boolean isOrientationUpdated = false, isFilledUpdated = false, isOutLineUpdated = false;

    public Polygon(Vector2[] verticies, Vector2 origin, double theta, Color color, Vector2 position, Vector2 velocity) {
        this.position = position;
        // since velocity will be in pixels per second and time is measured in milliseconds I need to divide by a thousand here
        // to change from p/s to p/ms
        this.velocity = Vector2.scale(velocity, 1f/1000f);
        this.theta = theta;
        this.verticies = verticies;
        resetOrigin(origin);
        this.lines = vectors2Lines(verticies);
        this.color = color;
        updateOrientation();
        leftMost = rotatedVerticies[0];
        rightMost = rotatedVerticies[0];
        bottomMost = rotatedVerticies[0];
        topMost = rotatedVerticies[0];
        for (int i = 0; i < rotatedVerticies.length; i++) {
            if (rotatedVerticies[i].x < leftMost.x) {
                leftMost = rotatedVerticies[i];
            } else if (rotatedVerticies[i].x > rightMost.x) {
                rightMost = rotatedVerticies[i];
            }
            if (rotatedVerticies[i].y < topMost.y) {
                topMost = rotatedVerticies[i];
            } else if (rotatedVerticies[i].y > bottomMost.y) {
                bottomMost = rotatedVerticies[i];
            }
        }
    }

    public void resetOrigin(Vector2 origin) {
        if (!origin.equals(Vector2.zero)) {
            for (int i = 0; i < verticies.length - 1; i++) {
                verticies[i] = Vector2.subtract(verticies[i], origin);
            }
        }
    }
    

    public static Line[] vectors2Lines(Vector2[] verticies) {
        Line[] temp = new Line[verticies.length];
        for (int i = 0; i < verticies.length - 1; i++) {
            temp[i] = Line.initCartesian(verticies[i], verticies[i + 1]);
        }
        temp[verticies.length - 1] = Line.initCartesian(verticies[verticies.length - 1], verticies[0]);
        return temp;
    }

    public static Vector2[] lines2Vectors(Line[] lines) {
        Vector2[] temp = new Vector2[lines.length];
        for (int i = 0; i < lines.length; i++) {
            temp[i] = lines[i].startPoint;
        }
        return temp;
    }

    public void rotate(double theta) {
        rotateTo(this.theta+theta);
        
    }

    public void rotateTo(double theta) {
        this.theta = theta;
        isOrientationUpdated = false;
        isFilledUpdated = false;
        isOutLineUpdated = false;
    }


    public Polygon updateOrientation() {
        if (!isOrientationUpdated) {
            for (int i = 0; i < verticies.length; i++) {
                rotatedVerticies[i] = Vector2.rotate(verticies[i], theta);
            }
            rotatedLines = vectors2Lines(rotatedVerticies);
            isOrientationUpdated = true;
        }
        return this;
    }
    
    public Color[][] updateOutLine() {
        if (!isOutLineUpdated) {
            if (!isOrientationUpdated) {
                updateOrientation();
            }
            pixelsOutline = new Color[(int) (rightMost.x) - (int) (leftMost.x)][(int) (bottomMost.y) - (int) (topMost.y)];
            for (Line line : rotatedLines) {
                Line temp = line.floor();
                int y = (int) (temp.startPoint.x);
                if (temp.slope > 0) {
                    for (int x = (int) (temp.startPoint.x); x < (int) (temp.endPoint.x); x++) {
                        while (y <= temp.predictY(x)) {
                            pixelsOutline[(int) (x + leftMost.floor().x)][(int) (y + topMost.floor().y)] = color;
                            y++;
                        }
                        y--;
                    }
                }
                else{
                    for (int x = (int) (temp.startPoint.x); x < (int) (temp.endPoint.x); x++) {
                        while (y >= temp.predictY(x)) {
                            pixelsOutline[(int) (x + leftMost.floor().x)][(int) (y + topMost.floor().y)] = color;
                            y--;
                        }
                        y++;
                    }
                }
            }
            

        }
        return pixelsOutline;
    }

    // if need be could optimized by not using the outline but instead copy the code
    // but while youre making the outline you also remember the starting point after moving to the right
    // this would result in having those points already filled in and no doubling up, but honestly isn't gonna help thaaat much so nahhhhhh 
    public Color[][] updateFilled() {
        if (!isFilledUpdated) {
            if (!isOutLineUpdated) {
                updateOutLine();
            }
            // code here
        }
        return pixelsFilled;
    }
    

}
