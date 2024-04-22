package component;

public class Square extends BaseComponent {
    private static int squareCount = 1;

    public Square(){
        setName("Square " + squareCount);
        squareCount++;
    }

    public Square(int x, int y, int side) {
        this();
        setX(x);
        setY(y);
        setSide(side);
    }

    public int getSide() {
        return getWidth();
    }

    public void setSide(int side) {
        super.setWidth(side);
        super.setHeight(side);
    }

    @Override
    public void setWidth(int width) {
        super.setHeight(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
}
