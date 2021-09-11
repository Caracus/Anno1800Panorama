import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        // initialize MetaData
        Map<Integer, HighriseMetaData> highMetaDataMap = new HashMap<Integer, HighriseMetaData>();

        //info taken from reddit, verify later
        //should be 3 from center as contingent residence didnt count towards panorama I assume 4/9 clip, over the street works due to 6/9, and over street one down or up does not work
        //needs testing
        highMetaDataMap.put(1, new HighriseMetaData(1, 0, 75, 25, 4.0));
        highMetaDataMap.put(2, new HighriseMetaData(2, 0, 100, 25, 4.25));
        highMetaDataMap.put(3, new HighriseMetaData(3, 0, 125, 25, 5));
        highMetaDataMap.put(4, new HighriseMetaData(4, 0, 150, 25, 6));
        highMetaDataMap.put(5, new HighriseMetaData(5, 0, 175, 25, 6.75));


        List<Residence> residencesList = new ArrayList<>();
        residencesList.add(new Residence(new Coordinate(1, 1)));
        residencesList.add(new Residence(new Coordinate(4, 4)));
        residencesList.add(new Residence(new Coordinate(1, 4)));
        residencesList.add(new Residence(new Coordinate(4, 1)));

        //todo: dont trigger from memory but do it during stream
        //this is basically 5 options with repetition, should be like 625 combinations for just 4 buildings (wont scale.....)
        //maybe scrap this entire approach and look into fancy stuff with best but not perfect solutions
        List<List<Integer>> permutations = Generator
                .permutation(1, 2, 3, 4, 5)
                .withRepetitions(4)
                .stream()
                .collect(Collectors.<List<Integer>>toList());

        System.out.println(permutations.size());
        permutations.stream().forEach(System.out::println);

        //todo: for each permutation trigger population calculation and save the permutation combination aswell as the score if it has higher results than current highscore
        //todo: print final result, maybe as ascii art like
        //xxx,xxx
        //x5x,x4x
        //xxx,xxx
        //xxx,xxx
        //x3x,x2x
        //xxx,xxx
    }

    public void calculatePopulation() {
        // todo: iterate through buildings and add their population
    }

    //maybe later
    public void calculateUpkeep() {
        // todo: iterate through buildings and add upkeep
    }

    public void calculatePanoramaLevel(Residence residence) {
        List<Coordinate> intersectionCoordinates;
        // todo: basePanorama level, get the buildings that intersect and based on their level add or subtract panorama
    }

    public void calculateIntersectingBuildings(Residence residence, double radius) {
        //todo: remove coordinates of the building in question
        //todo: iterate through buildings and check if buildingCoords have at least 5/9 matches with the ones return from the radius calculation
    }

    public void calculateRadiusCoordinates(Coordinate coordinate, double radius) {
        //todo: either find some fancy math or hardcode squares that count(which might be more performant anyway)
    }

    public  List<Residence> getAllIntersectingBuildings(Coordinate centerCoordinate, List<Residence> allResidences, double radius) {
        return allResidences.stream()
                .filter(residence ->
                        isWithinRadius(centerCoordinate, residence.getCenterCoordinate(), radius)
                        && centerCoordinate != residence.getCenterCoordinate())
                .collect(Collectors.toList());
    }

    public boolean isWithinRadius(Coordinate coordinateOne, Coordinate coordinateTwo, double radius) {
    double a = Math.abs(coordinateOne.getX()-coordinateTwo.getX());
    double b = Math.abs(coordinateOne.getY()-coordinateTwo.getY());

        return !(Math.sqrt(a * a + b * b) > radius);
    }

}
