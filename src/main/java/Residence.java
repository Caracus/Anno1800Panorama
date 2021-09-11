import lombok.Data;

@Data
public class Residence {

    //3x3 building, so center can be used to shorten the input
    private Coordinate centerCoordinate;
    private int tier;

    public Residence(Coordinate centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
        this.tier = 0;

    }

    public Residence(Coordinate centerCoordinate, int tier) {
        this.centerCoordinate = centerCoordinate;
        this.tier = tier;
    }

}
