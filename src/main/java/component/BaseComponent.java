package component;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = component.Circle.class, name = "Circle"),
        @JsonSubTypes.Type(value = component.Line.class, name = "Line"),
        @JsonSubTypes.Type(value = component.Rectangle.class, name = "Rectangle"),
        @JsonSubTypes.Type(value = component.Square.class, name = "Square"),
        @JsonSubTypes.Type(value = component.Triangle.class, name = "Triangle")
})
public abstract class BaseComponent {
    private int x;

    private int y;

    private int width;

    private int height;

    private String name;

    private double rotation;

    private Color color;

    public BaseComponent() {
    }

    public BaseComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
