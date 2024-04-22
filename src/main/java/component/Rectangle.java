package component;

public class Rectangle extends BaseComponent {
    private static int rectangleCount = 1;

    public Rectangle(){
        setName("Rectangle " + rectangleCount);
        rectangleCount++;
    }

    public Rectangle(int x, int y) {
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
