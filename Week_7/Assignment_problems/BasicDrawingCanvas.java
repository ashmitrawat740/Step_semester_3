abstract class Shape {

    private static int counter = 0;

    private final String shapeId;

    // Constructor
    public Shape() {
        counter++;
        shapeId = "SH-" + counter;
    }

    public abstract double calculateArea();

    // Overloaded method 1
    public void scale(double factor) {
        // Overridden by subclasses
    }

    // Overloaded method 2
    public void scale(double xFactor, double yFactor) {
        // Default implementation
        scale(xFactor);
        scale(yFactor);
    }

    public String getShapeId() {
        return shapeId;
    }

    // Static polymorphic method
    public static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }
}


class CircleShape extends Shape {

    private double radius;

    public CircleShape(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        radius *= xFactor;
        radius *= yFactor;
    }
}


class SquareShape extends Shape {

    private double side;

    public SquareShape(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        side *= xFactor;
        side *= yFactor;
    }
}


public class Problem1 {

    public static void main(String[] args) {

        CircleShape c = new CircleShape(5.0);

        System.out.println("Circle area: " + c.calculateArea());
        System.out.println("Circle ID: " + c.getShapeId());

        SquareShape sq = new SquareShape(4.0);

        System.out.println("Square area: " + sq.calculateArea());

        // One-argument overload
        sq.scale(2.0);

        System.out.println("Square area after scaling: "
                + sq.calculateArea());

        // Runtime polymorphism
        Shape s = c;
        Shape.printArea(s);
    }
}