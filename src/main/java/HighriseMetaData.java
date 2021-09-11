import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HighriseMetaData {
    private int tier;
    private int cost;
    private int basePeople;
    private int increasePerPanoramaLevel;
    private double radius;
}
