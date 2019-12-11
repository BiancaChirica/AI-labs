import static java.lang.System.exit;

public class Game {
    public int numarCanibali;
    public int numarMisionari;
    public int boatCapacity;
    public State currentState;
    public int dateOK = 1;

    //constructorul, setez datele initiale, apelez functia de initializare in el
    public Game(int numarMisionari, int numarCanibali, int boatCapacity) {
        currentState = null;
        this.numarCanibali = numarCanibali;
        this.numarMisionari = numarMisionari;
        this.boatCapacity = boatCapacity;
        currentState = initializeGame(numarMisionari, numarCanibali, boatCapacity);
        if (currentState == null)
            dateOK = 0;
        else dateOK = 1;

    }

    // functia de intializare, starea de inceput
    public State initializeGame(int numarMisionari, int numarCanibali, int boatCapacity) {
        int ok = 1;
        if (numarMisionari < numarCanibali) {
            System.out.println("Date invalide, nu se poate juca! :(");
            ok = 0;
        }
        if (boatCapacity < 2) {
            System.out.println("Date invalide, nu se poate juca! :(");
            ok = 0;
        }
        if(numarCanibali + numarMisionari == 0){
            System.out.println("Date invalide, nu se poate juca! :(");
            ok = 0;
        }

        if (ok == 1)
            return new State(numarMisionari, numarCanibali, 0, 0, 0);

        return null;
    }

    //verifica daca a ajuns in starea finala (de castig)
    public boolean isFinal(State s) {
        if ((s.numarMisionariM1 == 0) && (s.numarCanibaliM1 == 0) && (s.pozBarca == 1))
            return true;
        else
            return false;
    }


    // validare a starii s
    public boolean Validation(State s, int misionariMutati, int canibaliMutati) {
        //daca pe barca sunt mai multi misionari decat canibali
        if (misionariMutati < canibaliMutati && misionariMutati != 0)
            return false;

        //daca nu mut nicio persoana sau prea multe
        if (misionariMutati + canibaliMutati == 0 || misionariMutati + canibaliMutati > boatCapacity)
            return false;

        // pentru plecarea de pe malul stang
        if (s.pozBarca == 0) {
            // daca vreau sa mut mai multi misionari/canibali decat am pe mal
            if (misionariMutati > s.numarMisionariM1 || canibaliMutati > s.numarCanibaliM1)
                return false;

            //verific mal stang dupa mutare
            if ((s.numarMisionariM1 - misionariMutati > 0) && ((s.numarMisionariM1 - misionariMutati) < s.numarCanibaliM1 - canibaliMutati))
                return false;
            // verific mal drept dupa mutare
            if (s.numarMisionariM2 + misionariMutati != 0 && s.numarMisionariM2 + misionariMutati < s.numarCanibaliM2 + canibaliMutati)
                return false;
        }

        //pentru malul drept
        if (s.pozBarca == 1) {
            // daca vreau sa mut mai multi misionari/canibali decat am
            if (misionariMutati > s.numarMisionariM2 || canibaliMutati > s.numarCanibaliM2)
                return false;

            // verific mal drept
            if ((s.numarMisionariM2 - misionariMutati > 0) && (s.numarMisionariM2 - misionariMutati < s.numarCanibaliM2 - canibaliMutati))
                return false;
            // verific mal stang
            if ((s.numarMisionariM1 + misionariMutati > 0) && (s.numarMisionariM1 + misionariMutati < s.numarCanibaliM1 + canibaliMutati))
                return false;
        }

        return true;
    }


    public State stateTransition(State s, int misionariMutati, int canibaliMutati) {
        if (s.pozBarca == 0)
            return new State(s.numarMisionariM1 - misionariMutati, s.numarCanibaliM1 - canibaliMutati, s.numarMisionariM2 + misionariMutati, s.numarCanibaliM2 + canibaliMutati, 1);
        else
            return new State(s.numarMisionariM1 + misionariMutati, s.numarCanibaliM1 + canibaliMutati, s.numarMisionariM2 - misionariMutati, s.numarCanibaliM2 - canibaliMutati, 0);

    }

    // pentru fiecare algoritm returneaza lungimea solutiei
    public int startGame(String strategy) {
        if (dateOK == 0)
            return 0;
        Algoritm alg = new Algoritm(this);
        if (strategy.equals("random")) {
            return alg.playRandom();
        }
        else if (strategy.equals("bkt")) {
            //apelam o functie pentru algoritmul bkt
            return alg.playBkt();
        }
        else if (strategy.equals("iddfs")) {
            //apelam o functie pentru algoritmul iddfs
            return alg.playIddfs();

        } else if (strategy.equals("AStar"))
            return alg.playAStar();
       return 0;
    }


}
