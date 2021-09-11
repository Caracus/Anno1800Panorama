import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {

        List<Residence> residencesList = new ArrayList<>();

        // 4x4
        /**
        residencesList.add(new Residence(new Coordinate(1, 1)));
        residencesList.add(new Residence(new Coordinate(4, 4)));
        residencesList.add(new Residence(new Coordinate(1, 4)));
        residencesList.add(new Residence(new Coordinate(4, 1)));
        */

        residencesList.add(new Residence(new Coordinate(1, 1)));
        residencesList.add(new Residence(new Coordinate(4, 1)));
        residencesList.add(new Residence(new Coordinate(7, 1)));
        residencesList.add(new Residence(new Coordinate(1, 4)));
        residencesList.add(new Residence(new Coordinate(7, 4)));
        residencesList.add(new Residence(new Coordinate(1, 7)));
        residencesList.add(new Residence(new Coordinate(4, 7)));
        residencesList.add(new Residence(new Coordinate(7, 7)));

        AtomicInteger highscorePopulation = new AtomicInteger();
        AtomicReference<String> highscorePermutation = new AtomicReference<>("");


        Generator
            .permutation(1, 2, 3 ,4 ,5)
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
                    highscorePermutation.set(permutation+"");
                }
            });

            System.out.println("Final result is: " + highscorePermutation + " with " + highscorePopulation + " people.");

        printPermutation(List.of(highscorePermutation.get().replace(" ","").replace("[","").replace("]","").split(",")), residencesList);

      //  System.out.println(generateCoordinatesForNonCenter(new Coordinate(7, 7),3));

        /**
        //Debug a specific combination
        List<Integer> permutation = List.of(5, 5, 4, 3);

        List<Residence> residencesListWithTier = new ArrayList<>();
        for (int i = 0;i < residencesList.size(); i++) {
            residencesListWithTier.add(new Residence(residencesList.get(i).getCenterCoordinate(),permutation.get(i)));
        }

        int totalPopulation = residencesListWithTier.stream().mapToInt(residence -> calculatePopulation(residence, residencesListWithTier)).sum();

        if(totalPopulation > highscorePopulation.get()) {
            System.out.println("New best permutation is: " + permutation + " with " + totalPopulation + " people.");
            highscorePopulation.set(totalPopulation);
        }
         */



    }

    //refactor this *censored*
    public static void printPermutation(List<String> permutation, List<Residence> residencesList) {
        List<PrintCoordinate> allCoordinates = new ArrayList<>();

        //add bunch of x
        residencesList.forEach(residence -> allCoordinates.addAll(generateCoordinatesForNonCenter(residence.getCenterCoordinate(), 3)));

        //add the level numbers
        for(int i = 0; i< residencesList.size(); i++) {
            allCoordinates.add(new PrintCoordinate(residencesList.get(i).getCenterCoordinate(),permutation.get(i)));
        }

        //determine frame
        AtomicInteger largestX = new AtomicInteger();
        AtomicInteger largestY = new AtomicInteger();

        allCoordinates.stream().forEach(printCoordinate -> {
            if (printCoordinate.coordinate.getX() > largestX.get()) {
                largestX.set(printCoordinate.coordinate.getX());
            }
            if (printCoordinate.coordinate.getY() > largestY.get()) {
                largestY.set(printCoordinate.coordinate.getY());
            }
        });

        int yHeight = largestY.get();
        int xWidth = largestX.get();

        List<Coordinate> coordinates = new ArrayList<>();
        allCoordinates.forEach(printCoordinate -> coordinates.add(printCoordinate.coordinate));

        //System.out.println(allCoordinates);

        //print everything

        System.out.println("Building plan:");

        for(int y = yHeight; y > -1; y--) {
            for( int x = 0; x < xWidth+1; x++) {
                int finalX = x;
                int finalY = y;
                allCoordinates.stream().filter(printCoordinate -> printCoordinate.isSameCoordinate(finalX, finalY)).findFirst();

               Optional<PrintCoordinate> coordinateToBePrintedOptional =  allCoordinates.stream().filter(printCoordinate -> printCoordinate.isSameCoordinate(finalX, finalY)).findFirst();
                if(coordinateToBePrintedOptional.isPresent()){
                        System.out.print(coordinateToBePrintedOptional.get().getPrintChar());
                    } else {
                        System.out.print(" ");
                    }

                }
            System.out.println("");
            }

        }



    protected static List<PrintCoordinate> generateCoordinatesForNonCenter(Coordinate centerCoordinate, int size) {
        List<PrintCoordinate> generatedCoordinates = new ArrayList<>();
        int coordinateOffset = (size-1)/2;

        int xStart = centerCoordinate.getX() - coordinateOffset;
        int yStart = centerCoordinate.getY() - coordinateOffset;

        for(int x = xStart; x < size+xStart; x++) {
            for(int y = yStart; y < size+yStart; y++) {
                generatedCoordinates.add(new PrintCoordinate(x,y, "X"));
            }
        }
        generatedCoordinates.remove(new PrintCoordinate(centerCoordinate, "X"));

        return generatedCoordinates;
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

        if(panoramaLevel.get() < 0 ){
            return 0;
        }

        if(panoramaLevel.get() > 5 ){
            return 5;
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
