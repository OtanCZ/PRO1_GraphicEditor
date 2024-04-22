package component;

public class Line extends BaseComponent {
    private static int lineCount = 1;

    public Line(){
        setName("Line " + lineCount);
        lineCount++;
    }

    public Line(int x, int y) {
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
