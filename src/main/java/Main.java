import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {

        List<Residence> residencesList = new ArrayList<>();
        residencesList.add(new Residence(new Coordinate(1, 1)));
        residencesList.add(new Residence(new Coordinate(4, 4)));
        residencesList.add(new Residence(new Coordinate(1, 4)));
        residencesList.add(new Residence(new Coordinate(4, 1)));

        //todo: dont trigger from memory but do it during stream
        //this is basically 5 options with repetition, should be like 625 combinations for just 4 buildings (wont scale.....)
        //maybe scrap this entire approach and look into fancy stuff with best but not perfect solutions
        AtomicInteger highscorePopulation = new AtomicInteger();




        Generator
                .permutation(1, 2, 3, 4, 5)
                .withRepetitions(residencesList.size())
                .stream()
                .forEach(permutation -> {
                    //System.out.println(permutation);
                    // assign the tiers to the buildings
                    List<Residence> residencesListWithTier = new ArrayList<>();
                    for (int i = 0;i < residencesList.size(); i++) {
                        residencesListWithTier.add(new Residence(residencesList.get(i).getCenterCoordinate(),permutation.get(i)));
                    }

                    int totalPopulation = residencesListWithTier.stream().mapToInt(residence -> calculatePopulation(residence, residencesListWithTier)).sum();

                    if(totalPopulation > highscorePopulation.get()) {
                        System.out.println("New best permutation is: " + permutation + " with " + totalPopulation + " people.");
                        highscorePopulation.set(totalPopulation);
                    }
                });



        //todo: for each permutation trigger population calculation and save the permutation combination aswell as the score if it has higher results than current highscore
        //todo: print final result, maybe as ascii art like
        //xxx,xxx
        //x5x,x4x
        //xxx,xxx
        //xxx,xxx
        //x3x,x2x
        //xxx,xxx
    }

    public static int calculatePopulation(Residence centerResidence, List<Residence> allResidences) {

        HighriseMetaData highriseMetaData = getHighriseMetaDataForTier(centerResidence.getTier());

        return highriseMetaData.getBasePeople() + highriseMetaData.getIncreasePerPanoramaLevel() * calculatePanoramaLevel(centerResidence, allResidences);
    }

    //maybe later
    public void calculateUpkeep() {
        // todo: iterate through buildings and add upkeep
    }

    public static int calculatePanoramaLevel(Residence centerResidence, List<Residence> allResidences){
        //base panorama free standing is equal to tier
        AtomicInteger panoramaLevel = new AtomicInteger(centerResidence.getTier());

        List<Residence> intersectingBuildingsList = getAllIntersectingBuildings(centerResidence, allResidences);

        intersectingBuildingsList.forEach(residence -> {
            if(residence.getTier() >= centerResidence.getTier()){
                panoramaLevel.getAndDecrement();
            } else {
                panoramaLevel.getAndIncrement();
            }
        });

        if(panoramaLevel.get() <0 ){
            return 0;
        }
        return panoramaLevel.get();
    }

    public static List<Residence> getAllIntersectingBuildings(Residence centerResidence, List<Residence> allResidences) {
        Coordinate centerCoordinate = centerResidence.getCenterCoordinate();
        double radius = getHighriseMetaDataForTier(centerResidence.getTier()).getRadius();

        return allResidences.stream()
                .filter(residence ->
                        isWithinRadius(centerCoordinate, residence.getCenterCoordinate(), radius)
                        && centerCoordinate != residence.getCenterCoordinate())
                .collect(Collectors.toList());
    }

    public static boolean isWithinRadius(Coordinate coordinateOne, Coordinate coordinateTwo, double radius) {
    double a = Math.abs(coordinateOne.getX()-coordinateTwo.getX());
    double b = Math.abs(coordinateOne.getY()-coordinateTwo.getY());

        return !(Math.sqrt(a * a + b * b) > radius);
    }

    public static HighriseMetaData getHighriseMetaDataForTier(int tier){
        // initialize MetaData
        Map<Integer, HighriseMetaData> highMetaDataMap = new HashMap<Integer, HighriseMetaData>();

        //tested the radius on tier 1,2,5 buildings and seems to be correct
        highMetaDataMap.put(1, new HighriseMetaData(1, 0, 75, 25, 4.0));
        highMetaDataMap.put(2, new HighriseMetaData(2, 0, 100, 25, 4.25));
        highMetaDataMap.put(3, new HighriseMetaData(3, 0, 125, 25, 5));
        highMetaDataMap.put(4, new HighriseMetaData(4, 0, 150, 25, 6));
        highMetaDataMap.put(5, new HighriseMetaData(5, 0, 175, 25, 6.75));

        return highMetaDataMap.get(tier);
    }

}
