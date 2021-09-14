# Anno1800Panorama

<!-- ABOUT THE PROJECT -->
## What does this do?

### It gives you the best layout to maximize your panorama effect

## Background

Anno 1800 added skycrapers to its repertoire of buildings as part of the high life dlc.
Skyscrapers themselves can cost quite some upkeep while they maximize the amount of people you can fit onto a limited number of building space. 
Naturally you want to make the most money or the most out of your city space.
In order to create a nice skyline the devs added the panorama effect which increases your maximum capactity of a skyscraper if has sufficient view on the surroundings or punishes you for the lack off by not making your skyscrapers very profitable.

While trying to calculate the best layout it quickly becomes apparent that this is no simple task as those buildings and their respective panorama levels influence each other.
This program was made to get around that headache.

## What you can expect
At the time of writing this it works like the following:
- (The example is for a 8 building barcelona square with a 3x3 empty tile space in the middle)
- Step 1: Enter your desired building layout like this:
  ```sh
         residencesList.add(new Residence(new Coordinate(1, 1)));
         residencesList.add(new Residence(new Coordinate(1, 4)));
         residencesList.add(new Residence(new Coordinate(1, 8)));
         residencesList.add(new Residence(new Coordinate(1, 11)));
         residencesList.add(new Residence(new Coordinate(4, 1)));
         residencesList.add(new Residence(new Coordinate(4, 4)));
         residencesList.add(new Residence(new Coordinate(4, 8)));
         residencesList.add(new Residence(new Coordinate(4, 11)));
   ```
   The coordinates start at (0|0) but you only enter the center coordinate of your residence which would be (1|1) if you start in a corner.
   This way the space roads create can be accounted for without actually having to enter them ;)
- Step 2: Adjust building tier limit
  ```sh
        Generator.permutation(1, 2, 3, 4 ,5)
  ```
  this code line shows which skyscraper tiers should be accounted for, just taking tier 4 and 5 for example will make the program run faster but might have adverse effects on the optimal layout. This is the only way to account for larger layouts currently!!
  - Step 3: Run the algorithm
  It will calculate all possible combinations (with repetition) for your given layout. More on this in the disclaimer!
  While its running you will see output like this:
    ```sh
       ....
       New best permutation is: [5, 5, 5, 4, 3, 3, 3, 3] with 1725 people.
       New best permutation is: [5, 5, 5, 5, 3, 3, 3, 3] with 1750 people.
       New best permutation is: [5, 5, 5, 4, 4, 3, 3, 3] with 1775 people.
       New best permutation is: [5, 5, 5, 5, 4, 3, 3, 3] with 1850 people.
       ....
    ```
  The numbers stand for the tier while the position is based upon your order when you entered new buildings.
  - Step 4: Result
  Once it finishes the calculation it will display the final result and print a building plan if you are unable to read the permutation.
      ```sh
        Final result is: [3, 5, 4, 5, 5, 4, 5, 3] with 2000 people.
        Building plan:
        XXXXXXXXX
        X4XX5XX3X
        XXXXXXXXX
        XXX   XXX
        X5X   X5X
        XXX   XXX
        XXXXXXXXX
        X3XX5XX4X
        XXXXXXXXX
    ```
    The numbers represent the coordinates you entered in step 1 but this time they contain the level your skyscraper should be on that spot while the X just show blocked building space.
    Roads and empty space will just be empty space.
    
### Disclaimer
Here comes the big caveat:
This algorithm cant be the one to rule them all. This algorithm falls prey to a an exponential calculation. Permuation with repetition for all possible outcomes mean that if you calculate the best solution for a layout of 4 buildings with 5 tiers it will run 5^4 possible permuations which equals 625. Thats easy for modern pcs. 
Even the 5^8 which is 390.625 solutions is still a no brainer but you might see where this is going. Soon it will be millions and billions and no current pc will be able to terminate during your lifetime. 
Hence there is a limit to what you can do. 
Limiting the base from 5 to 2 tiers will seriously increase the amount of buildings you can calculate for but it wont give you the best solution which only a brute force algorithm can give you. 

### Food for thought
Brute forcing while giving you the best solution will be unable to optimize large scale layouts which are probably the ones you were looking for in the first place.
Maybe its time to step away from the "best" solution and try to achieve a good solution.
Neuronal nets, evolutionary algorithms and all the fancy new stuff might be the way forwards at this point. 
 
### Built With

- Java
- Maven 
- IntelliJ IDEA

### How to use it
I suggest using the IntelliJ IDEA which has a free community edition. Download the latest version from GitHub and import the project into the IDEA via the pom.xml which will then be used to build the project. You will also need the latest Java JDK.

### How to contribute
If you want to optimize this or add to it feel free to open a pull request. Obviously knowing your way around Git and Java is a requirement.
