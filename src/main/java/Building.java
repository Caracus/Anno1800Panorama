import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Building {
    protected List<Coordinate> coordinates;

    // 3x3, 5x5 etc.
    protected List<Coordinate> generateCoordinatesForUnevenSquareCenter(Coordinate centerCoordinate, int size) {
        List<Coordinate> generatedCoordinates = new ArrayList<>();
        int coordinateOffset = (size-1)/2;

        int xStart = centerCoordinate.getX() - coordinateOffset;
        int yStart = centerCoordinate.getY() - coordinateOffset;

        for(int x = xStart; x < size; x++) {
            for(int y = yStart; y < size;y++) {
                generatedCoordinates.add(new Coordinate(x,y));
                System.out.println(x +":"+ y);
            }
        }

        return generatedCoordinates;
    }
}
