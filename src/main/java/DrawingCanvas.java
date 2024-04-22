import component.*;
import component.Rectangle;
import listener.ComponentChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class DrawingCanvas extends JComponent {
    private final int width;
    private final int height;

    private boolean bCreating = false;

    private final ComponentChangeListener componentChangeListener;

    private final ComponentList componentList;

    private int selectedComponentIndex = 0;
    private int componentEnumSelectedIndex;


    public DrawingCanvas(int w, int h, ComponentChangeListener componentChangeListener) {
        this.width = w;
        this.height = h;
        setSize(width, height);
        this.componentChangeListener = componentChangeListener;

        setDoubleBuffered(true);

        componentList = ComponentList.getINSTANCE();

        MouseListener myMouseListener = new MouseListener();
        addMouseMotionListener(myMouseListener);
        addMouseListener(myMouseListener);

    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform reset = g.getTransform();

        for (int i = 0; i < componentList.getComponents().size(); i++) {
            BaseComponent p = componentList.getComponents().get(i);

            if (p instanceof Square) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.fillRect(-p.getWidth() / 2, -p.getHeight() / 2, p.getWidth(), p.getHeight());
                g.setTransform(reset);
            }
            else if (p instanceof Rectangle) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.fillRect(-p.getWidth() / 2, -p.getHeight() / 2, p.getWidth(), p.getHeight());
                g.setTransform(reset);
            } else if (p instanceof Circle) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.fillOval(-p.getWidth() / 2, -p.getHeight() / 2, p.getWidth(), p.getHeight());
                g.setTransform(reset);
            } else if (p instanceof component.Line) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.drawLine(-p.getWidth() / 2, -p.getHeight() / 2, p.getWidth() / 2, p.getHeight() / 2);
                g.setTransform(reset);
            } else if (p instanceof component.Triangle) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                int[] x = {0, p.getWidth() / 2, -p.getWidth() / 2};
                int[] y = {-p.getHeight() / 2, p.getHeight() / 2, p.getHeight() / 2};
                g.fillPolygon(x, y, 3);
                g.setTransform(reset);
            }

        }

    }

    public void createComponent(int red, int green, int blue) {
        System.out.print("Creating a component: ");
        Components selectedComponent = Components.values()[componentEnumSelectedIndex];
        bCreating = true;
        switch (selectedComponent) {
            case SQUARE -> {
                System.out.println(Components.SQUARE.name());
                component.Square s = new component.Square();
                s.setColor(new Color(red, green, blue)); //todo try

                componentList.add(s);
                selectedComponentIndex = componentList.getComponents().size() - 1;
            }

            case RECTANGLE -> {
                System.out.println(Components.RECTANGLE.name());
                component.Rectangle r = new component.Rectangle();
                r.setColor(new Color(red, green, blue)); //todo try

                componentList.add(r);
                selectedComponentIndex = componentList.getComponents().size() - 1;
            }
            case CIRCLE -> {
                System.out.println(Components.CIRCLE.name());
                Circle c = new Circle();
                c.setColor(new Color(red, green, blue)); //todo try

                componentList.add(c);
                selectedComponentIndex = componentList.getComponents().size() - 1;
            }
            case LINE -> {
                System.out.println(Components.LINE.name());

                component.Line l = new component.Line();
                l.setColor(new Color(red, green, blue)); //todo try

                componentList.add(l);
                selectedComponentIndex = componentList.getComponents().size() - 1;
            }
            case TRIANGLE -> {
                System.out.println(Components.TRIANGLE.name());

                component.Triangle t = new component.Triangle();
                t.setColor(new Color(red, green, blue)); //todo try

                componentList.add(t);
                selectedComponentIndex = componentList.getComponents().size() - 1;
            }
            default -> {
                System.out.print("idk");
            }
        }
        repaint();
    }

    public int getSelectedComponentIndex() {
        return selectedComponentIndex;
    }

    public void setSelectedComponentIndex(int selectedComponentIndex) {
        this.selectedComponentIndex = selectedComponentIndex;
    }


    class MouseListener extends MouseAdapter {
        private int startX = -1;

        private int startY = -1;


        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                createComponent(255, 0, 0);

                if (bCreating) {
                    startX = e.getX();
                    startY = e.getY();
                    BaseComponent p = componentList.getComponents().get(selectedComponentIndex);
                    bCreating = true;

                    p.setX(startX);
                    p.setY(startY);

                    componentChangeListener.updateTableRow();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            componentChangeListener.onComponentsChange();
            bCreating = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //euklidovská vzdálenost dvou bodů
            //vzdalenost = odmocina (x1 - x2)^2 + odmocnina (y1 - y2)^2
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (selectedComponentIndex != -1 && bCreating) {
                    BaseComponent p = componentList.getComponents().get(selectedComponentIndex);

                    if (p instanceof component.Rectangle) {
                        p.setWidth(e.getX() - startX);
                        p.setHeight(e.getY() - startY);
                    } else if (p instanceof Circle) {
                        int size = (int) Math.round(Math.sqrt(Math.pow(startX - e.getX(), 2) + Math.pow(startY - e.getY(), 2)));
                        p.setWidth(size);
                        p.setHeight(size);
                    } else if (p instanceof component.Line) {
                        p.setWidth(e.getX() - startX);
                        p.setHeight(e.getY() - startY);
                    } else if (p instanceof component.Triangle) {
                        p.setWidth(e.getX() - startX);
                        p.setHeight(e.getY() - startY);
                    } else if (p instanceof component.Square) {
                        int size = (int) Math.round(Math.sqrt(Math.pow(startX - e.getX(), 2) + Math.pow(startY - e.getY(), 2)));

                        p.setWidth(size);
                        p.setHeight(size);
                    }
                    repaint();
                    componentChangeListener.onComponentsChange();
                }

            }

            if (SwingUtilities.isRightMouseButton(e)) {
                //rotation
                if (selectedComponentIndex != -1) {
                    BaseComponent p = componentList.getComponents().get(selectedComponentIndex);

                    int rectCenterX = p.getX();
                    int rectCenterY = p.getY();
                    double dx = e.getX() - rectCenterX;
                    double dy = e.getY() - rectCenterY;
                    double rotation = Math.atan2(dy, dx);
                    p.setRotation(rotation);
                    repaint();
                }
            }

        }
    }

    public int getComponentEnumSelectedIndex() {
        return componentEnumSelectedIndex;
    }

    public void setComponentEnumSelectedIndex(int componentEnumSelectedIndex) {
        this.componentEnumSelectedIndex = componentEnumSelectedIndex;
    }
}
