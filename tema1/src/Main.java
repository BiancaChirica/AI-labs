import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // create instance of Random class
        Random rand = new Random();
        int lgMR = 0, lgMB = 0, lgMA = 0;
        long startTimeR, startTimeB, startTimeA;
        long endTimeR, endTimeB, endTimeA;
        long totalTimeR = 0, totalTimeB = 0, totalTimeA = 0;
        ArrayList<Integer> listOfRand = new ArrayList<Integer>();

        for (int i = 1; i <= 5; ) {
            // Generate random integers in range 3 to 15
            int m = rand.nextInt(5) + 3;
            int c = rand.nextInt(5) + 3;
            int b = rand.nextInt(3) + 2;




            //check if the data from rand can be used
            Game game = new Game(m, c, b);

            if (game.dateOK == 1) {
                i++;

                System.out.println(m + " " + c + " "+ b);
      ;

                listOfRand.add(m);
                listOfRand.add(c);
                listOfRand.add(b);

                //statistici random
                startTimeR = System.nanoTime();
                lgMR += game.startGame("random");
                endTimeR = System.nanoTime();
                totalTimeR += (endTimeR - startTimeR);


                // statistici bkt
                startTimeB = System.nanoTime();
                 lgMB +=game.startGame("bkt");
                endTimeB = System.nanoTime();
                totalTimeB += (endTimeB - startTimeB);

                // statistici AStar
                startTimeA = System.nanoTime();
                lgMA += game.startGame("AStar");
                endTimeA = System.nanoTime();
                totalTimeA += (endTimeA - startTimeA);
            }
        }

        //random medie
        lgMR /= 5;
        //transformare in sec
       // totalTimeR /= 1000000000;
        //media timp
        totalTimeR /= 5;

        //bkt medie
        System.out.println(lgMB);
        lgMB /= 5.0;
        //transformare in sec
      //  totalTimeB /= 1000000000;
        //media timp
        totalTimeB /= 5;

        //AStar medie
        lgMA /= 5;
        //transformare in sec
      //  totalTimeA /= 1000000000;
        //media timp
        totalTimeA /= 5;

        System.out.println("Valorile random: ");
        for (int i = 0; i < listOfRand.size() - 2; i=i+3)
            System.out.println(listOfRand.get(i) + " " + listOfRand.get(i + 1) + " " + listOfRand.get(i + 2));


        System.out.println("Random : solution " + lgMR + " time: " + totalTimeR);
        System.out.println("Bkt : solution " + lgMB + " time: " + totalTimeB);
        System.out.println("AStar : solution " + lgMA + " time: " + totalTimeA);

    }

}
