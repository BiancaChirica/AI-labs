import java.sql.Struct;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    // dimensiune standard
    private int linii = 6;
    private int coloane = 7;
    private int depth = 5;

    private State currentState;
    private int player = 1; // player to make a move , 1 = AI, 2= human


    public Game() {
        if (linii <= 4 || coloane <= 4) {
            System.out.println("Dimensiuni incorecte. Nu se poate juca");
            System.exit(-1);
        } else {
            currentState = new State(linii, coloane);
        }
    }

    public void startGame() {
        while (currentState.existaMutari() && currentState.isFianl() == 0) {
            if (player == 1) {
                //  calculatorul alege mutarea
                //   pickAMove();
                currentState = miniMax(currentState, depth);
            } else {
                // astapta mutarea omului
                while (true) {
                    int myint = -1;
                    Scanner keyboard = new Scanner(System.in);
                    System.out.print("Enter the collumn number: ");
                    myint = keyboard.nextInt();

                    //  verifica mutarea omului
                    if (currentState.checkMove(myint) == false)
                        continue;

                    // save changes
                    currentState.saveMove(myint, player);
                    //break from while
                    break;
                }

            }

            currentState.printBoard();
            player = 3 - player;

        }
        if (!currentState.existaMutari()) {
            System.out.println("Remiza. Nu mai exista mutari valabile.");
        } else {

            if (currentState.isFianl() == 1)
                System.out.println("Ai pierdut. Mai incearca.");
            else System.out.println("Felicitari! Ai castigat");
        }
    }

    // functia pentru alegerea mutarii calculatorului : verifica o singura mutare inainte
  /*  public void pickAMove() {

        ArrayList<State> listOfStates = new ArrayList<>();
        listOfStates.clear();

        // generez lista cu vecini (mutari posibile) le pun o valoare cu functia euristica si le pun intr-o lista
        for (int contor = 0; contor < coloane; contor++)
            if (currentState.board[0][contor] == 0) {
                State nxtState = new State(linii, coloane);

                for (int i = 0; i < linii; i++)
                    for (int j = 0; j < coloane; j++)
                        nxtState.board[i][j] = currentState.board[i][j];

                for (int contorLinie = linii - 1; contorLinie >= 0; contorLinie--)
                    if (currentState.board[contorLinie][contor] == 0) {
                        nxtState.board[contorLinie][contor] = 1;
                        nxtState.euristic(contorLinie, contor);
                        listOfStates.add(nxtState);
                        break;
                    }

            }

        ArrayList<State> mv = new ArrayList<>();
        // if more are equally high : make a list choose random
        // aleg starea cu val cea mai mare la heuristicValue
        for (State s : listOfStates) {
            if (mv.isEmpty() || s.heuristicValue > mv.get(0).heuristicValue) {
                mv.clear();
                mv.add(s);
            } else if (s.heuristicValue == mv.get(0).heuristicValue)
                mv.add(s);
        }

        Random rand = new Random();
        mv.trimToSize();
        // Generate random integers in range 0 to size
        int index = rand.nextInt(mv.size());


        currentState = mv.get(index);

    }
*/


    public State miniMax(State currentState, int depth) {
        miniMaxResult mmResult = new miniMaxResult();
        mmResult = alphaBeta(currentState, -1000000000, 1000000000, 0, depth, 1);
        return mmResult.nextState;
    }


    public miniMaxResult alphaBeta(State currentState, int alpha, int beta, int currentDepth, int maxDepth, int player) {
        miniMaxResult mmRes = new miniMaxResult();

        if (currentDepth >= maxDepth || currentState.isFianl() > 0) {
            mmRes.nextState = currentState;
            currentState.euristic();
            mmRes.value = currentState.heuristicValue;
            return mmRes;
        }


        if (player == 1) {
            miniMaxResult optimalState = new miniMaxResult();
            optimalState.value = -10000000;
            optimalState.nextState = null;


            // generez lista cu vecini (mutari posibile)
            for (int contor = 0; contor < coloane; contor++)
                if (currentState.board[0][contor] == 0) {
                    State nxtState = new State(linii, coloane);
                    for (int i = 0; i < linii; i++)
                        for (int j = 0; j < coloane; j++) {
                            nxtState.board[i][j] = currentState.board[i][j];
                        }

                    nxtState.saveMove(contor, 1);
                    //

                    mmRes = alphaBeta(nxtState, alpha, beta, currentDepth + 1, maxDepth, 2);

                    if (mmRes.value > optimalState.value)
                    {optimalState.value = mmRes.value;
                    optimalState.nextState = nxtState;}
/*
                    alpha = Math.max(alpha, optimalState.value);
                   if (alpha >= beta)
                        break;
*/
                }
            return optimalState;
        }


        miniMaxResult optimalState = new miniMaxResult();
        optimalState.value = 10000000;
        optimalState.nextState = null;

        // generez lista cu vecini (mutari posibile)
        for (int contor = 0; contor < coloane; contor++)
            if (currentState.board[0][contor] == 0) {
                State nxtState = new State(linii, coloane);
                for (int i = 0; i < linii; i++)
                    for (int j = 0; j < coloane; j++) {
                        nxtState.board[i][j] = currentState.board[i][j];
                    }

                nxtState.saveMove(contor, 2);
                //

                mmRes = alphaBeta(nxtState, alpha, beta, currentDepth + 1, maxDepth, 1);
                if (mmRes.value < optimalState.value)
                {optimalState.value = mmRes.value;
                    optimalState.nextState = nxtState;}

           /*     beta = Math.min(beta, optimalState.value);
                if (alpha >= beta)
                    break;
*/
            }
        return optimalState;


    }


}
