public class Line {
    public Vector2 startPoint, endPoint;
    private float length;

    public Line(Vector2 startPoint, Vector2 endPoint) {
        this.startPoint = startPoint;
        this.endPoint = startPoint;
        length = -1;
    }

    public float getLength() {
        if (length == -1) {
            length = Vector2.distance(startPoint, endPoint);
        }
        return length;
    }
}
