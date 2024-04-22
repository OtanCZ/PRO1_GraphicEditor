package component;

public class Triangle extends BaseComponent {
    private static int triangleCount = 1;

    public Triangle(){
        setName("Triangle " + triangleCount);
        triangleCount++;
    }

    public Triangle(int x, int y) {
        this();
        setX(x);
        setY(y);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }
}
