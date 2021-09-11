import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Residence extends Building {

    //3x3 building, so center can be used to shorten the input
    private Coordinate centerCoordinate;

    public Residence(Coordinate centerCoordinate) {
        super();
        this.centerCoordinate = centerCoordinate;
        this.coordinates = generateCoordinatesForUnevenSquareCenter( centerCoordinate, 3);
    }

}
