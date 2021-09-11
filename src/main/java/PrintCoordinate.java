import lombok.Data;

@Data
public class PrintCoordinate {
    Coordinate coordinate;
    private String printChar;

    public PrintCoordinate(Coordinate coordinate, String printChar) {
        this.coordinate = coordinate;
        this.printChar = printChar;
    }

    public PrintCoordinate(int x, int y, String printChar) {
        this.coordinate = new Coordinate(x, y);
        this.printChar = printChar;
    }

    public boolean isSameCoordinate(int x, int y) {
        return (this.coordinate.getX() == x && this.coordinate.getY() == y);
    }
}
