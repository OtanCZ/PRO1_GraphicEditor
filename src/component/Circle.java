package component;

public class Circle extends Primitive{



    public Circle(){
        setName("Circle"); //todo cislovani
        setType(ComponentConst.ComponentType.CIRCLE);
    }

    public Circle(int x, int y, int radius) {
        this();
        setX(x);
        setY(y);
        setRadius(radius); //this.radius = radius;
    }

    public int getRadius() {
        return getWidth() / 2;
    }

    public void setRadius(int radius) {
        super.setWidth(radius * 2);
        super.setHeight(radius * 2);
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
