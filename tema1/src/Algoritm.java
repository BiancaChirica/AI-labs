import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algoritm {
    Game game;
    ArrayList<State> tranzitii = new ArrayList<>();


    //in constructor pun referinta la game ca sa putem folosi functiile cu game.isFinal sau game.validation
    public Algoritm(Game game) {
        this.game = game;
    }

    public int playRandom() {
        int nrIteratie = 0;
        int nrIncercare = 0;
        int nrTranzitii = 0;

        while (!game.isFinal(game.currentState)) {
            // initializare incercare random (resetare)
            nrIncercare++;
            // System.out.println("Incercarea random numarul: " + nrIncercare);
            nrIteratie = 1;
            nrTranzitii = 0;
            game.currentState = game.initializeGame(game.numarMisionari, game.numarCanibali, game.boatCapacity);
            tranzitii.clear();
            tranzitii.add(game.currentState);
            //   System.out.println(game.currentState.toString());

            // incercarea random
            while (!game.isFinal(game.currentState) && nrIteratie < 200) {

                Random randomGenerator = new Random();
                int misionariMutati = randomGenerator.nextInt(game.boatCapacity + 1);
                int canibaliMutati = randomGenerator.nextInt(game.boatCapacity - misionariMutati + 1);

                if (!game.Validation(game.currentState, misionariMutati, canibaliMutati)) {
                    nrIteratie++;
                    continue;
                }

                State temporarState = game.stateTransition(game.currentState, misionariMutati, canibaliMutati);

                if (!oldState(temporarState, tranzitii)) {
                    nrIteratie++;
                    nrTranzitii++;
                    game.currentState = temporarState;
                    tranzitii.add(game.currentState);
                    //         System.out.println(game.currentState.toString());
                }


            }

        }
        if (game.isFinal(game.currentState) == true) {
            //    System.out.println("Am gasit starea finala");
            //    System.out.println("Numarul incercarilor= " + nrIncercare);
            System.out.println("Numarul tranzitie random = " + nrTranzitii);
            //  System.out.println("Numarul iteratii = " + nrIteratie);
            return nrTranzitii;

        }

        return 0;
    }

    public int playBkt() {
        //initializare
        game.currentState = game.initializeGame(game.numarMisionari, game.numarCanibali, game.boatCapacity);
        tranzitii.clear();
        tranzitii.add(game.currentState);

        return bkt(1);

    }

    public int playIddfs() {
        return 0;
    }

    public int playAStar() {

        ArrayList<State> closedList = new ArrayList();

        //initializare
        game.currentState = game.initializeGame(game.numarMisionari, game.numarCanibali, game.boatCapacity);
        tranzitii.clear();
        tranzitii.add(game.currentState);
        game.currentState.f=0;
        game.currentState.g=0;

        while(!tranzitii.isEmpty())
        {
            //find the state with the lowest f from tranzitii
            State parinte = lowestF(tranzitii);
            tranzitii.remove(parinte);

            for(State successor : getTransition(parinte))
            {
                // if successor = final
                if(game.isFinal(successor))
                {
                    System.out.println("Am gasit numarul de tranzitii A*: " + (parinte.g +1));
                    return parinte.g +1 ;
                }

                successor.g = parinte.g + 1;
                successor.h = euristicDistance(successor);
                successor.f = successor.g + successor.h;


                //if a node with the same position as successor is in the OPEN list which has a
                //    lower f than successor, skip this successor
                State oldSt=null;
                for (State stateInTransition : tranzitii)
                    if (stateInTransition.numarMisionariM1 == successor.numarMisionariM1 && stateInTransition.numarCanibaliM1 == successor.numarCanibaliM1 && stateInTransition.numarMisionariM2 == successor.numarMisionariM2 && stateInTransition.numarCanibaliM2 == successor.numarCanibaliM2 && stateInTransition.pozBarca == successor.pozBarca)
                       oldSt= stateInTransition;
                if( oldSt != null && successor.f > oldSt.f)
                    continue;

               // if a node with the same position as successor  is in the CLOSED list which has
              // a lower f than successor, skip this successor
                oldSt=null;
                for (State stateInTransition : closedList)
                    if (stateInTransition.numarMisionariM1 == successor.numarMisionariM1 && stateInTransition.numarCanibaliM1 == successor.numarCanibaliM1 && stateInTransition.numarMisionariM2 == successor.numarMisionariM2 && stateInTransition.numarCanibaliM2 == successor.numarCanibaliM2 && stateInTransition.pozBarca == successor.pozBarca)
                        oldSt= stateInTransition;
                if( oldSt != null && successor.f > oldSt.f)
                    continue;

                tranzitii.add(successor);

            }
            closedList.add(parinte);
        //    System.out.println(parinte);
        }
        return 0;
    }

    public State lowestF(ArrayList<State> tranzitii)
    {
        State min= null;
        for (State st : tranzitii)
            min=(min ==null||st.f< min.f)? st:min;
        return min;
    }

    public int euristicDistance(State s) {
        //idee : intrucat scopul e ca pe malul stang sa avem 0 persoane:
        // vom lua numarul total de persoane de pe malul
        // initial si vom considera ca la fiecare transport barca va lua de pe
        // malul stang numarul maxim de persoane posibil si se va intoarce cu numarul minim (=1)
        //(int) h(s) = (m1 + c1) / (capacitate_barca-1)
        if (s.pozBarca == 0)
            return (int) (s.numarMisionariM1 + s.numarCanibaliM1) / (game.boatCapacity - 1);
        else
            return (int) (s.numarMisionariM1 + s.numarCanibaliM1 + 1) / (game.boatCapacity - 1);

    }


    public boolean oldState(State newState, ArrayList<State> listOfTransition) {
        for (State st : listOfTransition) {
            if (st.numarMisionariM1 == newState.numarMisionariM1 && st.numarCanibaliM1 == newState.numarCanibaliM1 && st.numarMisionariM2 == newState.numarMisionariM2 && st.numarCanibaliM2 == newState.numarCanibaliM2 && st.pozBarca == newState.pozBarca)
                return true;
        }
        return false;
    }

    // functia de bkt efectiv
    public int bkt(int k) {
        if (game.isFinal(game.currentState))
        //stop
        {
            System.out.println("Nr tranzitii bkt :" + k);
            return k;
        } else
            for (State st : getTransition(game.currentState)) {
                if (!oldState(st, tranzitii)) {
                game.currentState = st;
                tranzitii.add(st);
           //     System.out.println(tranzitii);
                if (bkt(k + 1) != 0) return k;
                tranzitii.remove(tranzitii.size() - 1);
                game.currentState = tranzitii.get(tranzitii.size() - 1);
                          }
            }
        return 0;
    }


    //returneaza toate tranzitiile posibile din starea st
    public ArrayList<State> getTransition(State st) {
        ArrayList<State> possibleTransitions = new ArrayList<State>();
        possibleTransitions.clear();
        if (st.pozBarca == 0) {
            for (int m = 0; m <= game.boatCapacity && m <= st.numarMisionariM1; m++)
                for (int c = 0; c <= game.boatCapacity && c <= st.numarCanibaliM1; c++)
                    if (game.Validation(st, m, c)) {
                        State nxtState = game.stateTransition(st, m, c);
                            possibleTransitions.add(nxtState);
                    }
        } else
            for (int m = 0; m <= game.boatCapacity && m <= st.numarMisionariM1; m++)
                for (int c = 0; c <= game.boatCapacity && c <= st.numarCanibaliM2; c++)
                    if (game.Validation(st, m, c)) {
                        State nxtState = game.stateTransition(st, m, c);
                            possibleTransitions.add(nxtState);
                    }
        return possibleTransitions;

    }
}
