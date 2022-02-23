//all polygons must be concave
import java.awt.Color;
public class Polygon {
    //adjacent verticies are to have lines between them, last vertex is to be connected with the first 
    private Line[] lines, rotatedLines;
    private Vector2[] verticies, rotatedVerticies;
    private Vector2 leftMost, rightMost, bottomMost, topMost;
    public Vector2 position, velocity, origin;
    public double theta;
    private Color color;
    public Color[][] pixelsFilled;
    public Color[][] pixelsOutline;
    private boolean isOrientationUpdated = false, isFilledUpdated = false, isOutlineUpdated = false;

   /**
    * 
    * @param verticies
    * @param position
    * @param origin
    * @param velocity
    * @param theta
    * @param color
    */
   public Polygon(Vector2[] verticies, Vector2 position, Vector2 origin, Vector2 velocity, double theta, Color color) {
       this.position = position;
       // since velocity will be in pixels per second and time is measured in milliseconds I need to divide by a thousand here
       // to change from p/s to p/ms
       this.velocity = velocity.scale(1f / 1000f);
       this.theta = theta;
       this.verticies = verticies;
       this.origin = origin;
       this.color = color;
       updateOrientation();
       this.lines = vectors2Lines(verticies);
   }
    
   public Polygon(Vector2[] verticies, Color color) {
       this.position = Vector2.ZERO;
       // since velocity will be in pixels per second and time is measured in milliseconds I need to divide by a thousand here
       // to change from p/s to p/ms
       this.velocity = Vector2.ZERO;
       this.theta = 0;
       this.verticies = verticies;
       this.origin = Vector2.ZERO;
       this.color = color;
       updateOrientation();
       this.lines = vectors2Lines(verticies);
   }

    public void moveAllVerts(Vector2 moveBy) {
        if (!moveBy.equals(Vector2.ZERO)) {
            for (int i = 0; i < verticies.length; i++) {
                verticies[i] = verticies[i].subtract(moveBy);
            }
        }
    }
    
    public static Line[] vectors2Lines(Vector2[] verticies) {
        Line[] temp = new Line[verticies.length];
        // System.out.println("");
        for (int i = 0; i < verticies.length - 1; i++) {
            // System.out.print("line " + (i + 1) + "starting: " + verticies[i].floor() + "  | ends at: " + verticies[i + 1].floor());
            temp[i] = Line.initCartesian(verticies[i], verticies[i + 1]);
            // System.out.print("line " + (i+1) + temp[i].floor());
        }
        temp[verticies.length - 1] = Line.initCartesian(verticies[verticies.length - 1], verticies[0]);
        // System.out.print("line " + (verticies.length) + "starting: " + verticies[verticies.length - 1].floor() + "  | ends at: " + verticies[0].floor());
        // System.out.print("line " + (temp.length) + temp[temp.length-1].floor());
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
        isOutlineUpdated = false;
    }

    public Polygon updateOrientation() {
        if (!isOrientationUpdated) {
            rotatedVerticies = new Vector2[verticies.length];
            for (int i = 0; i < verticies.length; i++) {
                rotatedVerticies[i] = verticies[i].rotate(theta, origin).floor();
            }
            rotatedLines = vectors2Lines(rotatedVerticies);
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
            moveAllVerts(Vector2.cartesianInit(leftMost.x, topMost.y));
            origin = origin.add(Vector2.cartesianInit(-leftMost.x, -topMost.y));
            // System.out.print("left: " + leftMost);
            // System.out.print("   right: " + rightMost);
            // System.out.print("   bottom: " + bottomMost);
            // System.out.println("   top: " + topMost);
            isOrientationUpdated = true; 
        }
        return this;
    }
    
    public Color[][] updateOutline() {
        if (!isOutlineUpdated) {
            if (!isOrientationUpdated) {
                updateOrientation();
            }
            pixelsOutline = new Color[(int) (rightMost.x) - (int) (leftMost.x) + 1][(int) (bottomMost.y)
                    - (int) (topMost.y) + 1];
            // System.out.println(pixelsOutline.length + " by " + pixelsOutline[0].length);
            for (Line line : lines) {
                line.drawOnTo(pixelsOutline, color);
                // gotta move it
            }
        }
        // if (!isOutlineUpdated) {
        //     if (!isOrientationUpdated) {
        //         updateOrientation();
        //     }
        //     pixelsOutline = new Color[(int) (rightMost.x) - (int) (leftMost.x) + 1][(int) (bottomMost.y) - (int) (topMost.y) + 1];
        //     // System.out.print("\n" + pixelsOutline.length + " by ");
        //     // System.out.println(pixelsOutline[0].length);
        //     for (Line line : rotatedLines) {
        //         // color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f);
        //         Line temp = line.floor();
        //         // System.out.println(temp.slope);
        //         if (temp.slope > 0) {
        //             int y = (int) (temp.top);
        //             for (int x = (int) (temp.left); x < (int) (temp.right); x++) {
        //                 while (y <= temp.predictY(x) && y<temp.bottom) {
        //                     // System.out.println("Trying at " + Vector2.cartesianInit(x, y));
        //                     pixelsOutline[(int) (x - leftMost.x)][(int) (y - topMost.y)] = color;
        //                     y++;
        //                 }
        //                 y--;
        //             }
        //         } else {
        //             int y = (int) (temp.bottom);
        //             for (int x = (int) (temp.left); x < (int) (temp.right); x++) {
        //                 while (y >= temp.predictY(x) && y > temp.top) {
        //                     // System.out.print("Trying at " + Vector2.cartesianInit(x, y));
        //                     // System.out.println("  |  resulting in  " + Vector2.cartesianInit((int) (x - leftMost.x),(int) (y - topMost.y)));
        //                     pixelsOutline[(int) (x - leftMost.x)][(int) (y - topMost.y)] = color;
        //                     y--;
        //                 }
        //                 y++;
        //             }
        //         }
        //     }
        //     // for (int x = 0; x < pixelsOutline.length; x++) {
        //     //     for (int y = 0; y < pixelsOutline[0].length; y++) {
        //     //         System.out.print((pixelsOutline[x][y] != null) ? "x" : "o");
        //     //     }
        //     //     System.out.println("");
        //     // }
        // }
        
        isOutlineUpdated = true;
    return pixelsOutline;
    }

    // if need be could optimized by not using the outline but instead copy the code
    // but while youre making the outline you also remember the starting point after moving to the right
    // this would result in having those points already filled in and no doubling up, but honestly isn't gonna help thaaat much so nahhhhhh 
    public Color[][] updateFilled() {
        if (!isFilledUpdated) {
            if (!isOutlineUpdated) {
                updateOutline();
            }
            // code here
        }
        return pixelsFilled;
    }

    public String toString() {
        String str = "Polygon has\n";
        for (Vector2 vertex : verticies) {
            str += "Vertex at: " + vertex + "\n";
            
        }
        return str;
    }
    

}
