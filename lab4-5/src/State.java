import java.util.ArrayList;

public class State {
    //prima valoare e linia a doua coloana
    public int board[][];
    private int linii;
    private int coloane;
    public int heuristicValue = 0;

    public int lastI;
    public int lastJ;


    public State(int linii, int coloane) {
        this.linii = linii;
        this.coloane = coloane;
        board = new int[linii][coloane];
        lastI=0;
       lastJ=0;
        initializeState();
    }

    private void initializeState() {
        for (int contor = 0; contor < linii; contor++)
            for (int contor2 = 0; contor2 < coloane; contor2++)
                board[contor][contor2] = 0;
    }

    public int isFianl() {
        if (fourInLine(1))
            return 1;
        else if (fourInLine(2))
            return 2;
        return 0;
    }

    public boolean fourInLine(int player) {
        for (int contor = 0; contor < linii; contor++)
            for (int contor2 = 0; contor2 < coloane; contor2++)
                if (board[contor][contor2] == player) {
                    //verific la dreapta
                    if (contor2 + 3 < coloane)
                        if (board[contor][contor2 + 1] == player && board[contor][contor2 + 2] == player && board[contor][contor2 + 3] == player)
                            return true;

                    //verific in jos
                    if (contor + 3 < linii)
                        if (board[contor + 1][contor2] == player && board[contor + 2][contor2] == player && board[contor + 3][contor2] == player)
                            return true;

                    //verific pe diagonala dreapta
                    if (contor2 + 3 < coloane && contor + 3 < linii)
                        if (board[contor + 1][contor2 + 1] == player && board[contor + 2][contor2 + 2] == player && board[contor + 3][contor2 + 3] == player)
                            return true;

                    //verific pe diagonala stanga
                    if (contor2 - 3 >= 0 && contor + 3 < linii)
                        if (board[contor + 1][contor2 - 1] == player && board[contor + 2][contor2 - 2] == player && board[contor + 3][contor2 - 3] == player)
                            return true;
                }

        return false;
    }


    public void euristic() {


        int linie = lastI;
        int coloana = lastJ;

        if (fourInLine(1))
            this.heuristicValue = 1000000;

        if (fourInLine(2) && this.heuristicValue == 0)
            this.heuristicValue = -1000000;

        // calculeaza weight pt o anumita pozitie reprezentata de linie si coloana

        int aiMoveWeight = twoOrthreeInLine(linie, coloana, 1);
        int oppMoveWeight = twoOrthreeInLine(linie, coloana, 2);

        if (heuristicValue == 0)
            this.heuristicValue = aiMoveWeight - oppMoveWeight;


    }




    //functia euristica pentru alegerea mutarii fara minimax

    public void euristic(int linie, int coloana) {
        // if 4 in line : weight =100.000
        //if 4 in line opponen = 10.000
        // if 3 in line : weight = 100
        //if 2 in line : weight  = 10
        //total weight for the move =  number of 3 lines *specific weight + 2 lines*weight for 2s - 3 lines human -  2 lines human

        if (fourInLine(1))
            this.heuristicValue = 100000;

        if (fourInLine(2) && this.heuristicValue == 0)
            this.heuristicValue = 10000;

        // calculeaza weight pt o anumita pozitie reprezentata de linie si coloana

        int aiMoveWeight = twoOrthreeInLine(linie, coloana, 1);
        int oppMoveWeight = twoOrthreeInLine(linie, coloana, 2);

        if (heuristicValue == 0)
            this.heuristicValue = aiMoveWeight + oppMoveWeight;


    }

    /**
     * calculeaza weight pt o anumita pozitie reprezentata de linie si coloana
     *
     * @param linie   - linia la care se afla piesa asezata pentru a-i calcula weight
     * @param coloana - coloana  -"-
     * @param player  - 1 daca mutarea e calculata pentru AI, 2 pentru jucatorul uman
     * @return
     */
    public int twoOrthreeInLine(int linie, int coloana, int player) {

// todo : add : where 3 or 2 has both ends blocked and can't make a four=>weigth low

        int numberOfThree = 0;
        int numberOfTwo = 0;

        //verifica la dreapta : 2 si 3
        if (coloana + 1 < coloane && board[linie][coloana + 1] == player) {
            if (coloana + 2 < coloane && board[linie][coloana + 2] == player)
                numberOfThree++;
            else numberOfTwo++;
        }

        //verifica in jos : 2 si 3
        if (linie + 1 < linii && board[linie + 1][coloana] == player)
            if (linie + 2 < linii && board[linie + 2][coloana] == player)
                numberOfThree++;
            else numberOfTwo++;

        //verifica la stanga :2 si 3
        if (coloana - 1 >= 0 && board[linie][coloana - 1] == player) {
            if (coloana - 2 >= 0 && board[linie][coloana - 2] == player)
                numberOfThree++;
            else numberOfTwo++;
        }

        //verifica in sus : 2 si 3
        if (linie - 1 >= 0 && board[linie - 1][coloana] == player)
            if (linie - 2 >= 0 && board[linie - 2][coloana] == player)
                numberOfThree++;
            else numberOfTwo++;


        //verifica diagonala dreapta sus : 2 si 3
        if (linie - 1 >= 0 && coloana + 1 < coloane && board[linie - 1][coloana + 1] == player)
            if (linie - 2 >= 0 && coloana + 2 < coloane && board[linie - 2][coloana + 2] == player)
                numberOfThree++;
            else numberOfTwo++;

        //verifica diagonala dreapta jos : 2 si 3
        if (linie + 1 < linii && coloana + 1 < coloane && board[linie + 1][coloana + 1] == player)
            if (linie + 2 < linii && coloana + 2 < coloane && board[linie + 2][coloana + 2] == player)
                numberOfThree++;
            else numberOfTwo++;

        //verifica diagonala stanga jos : 2 si 3
        if (linie + 1 < linii && coloana - 1 >= 0 && board[linie + 1][coloana - 1] == player)
            if (linie + 2 < linii && coloana - 2 >= 0 && board[linie + 2][coloana - 2] == player)
                numberOfThree++;
            else numberOfTwo++;

        //verifica diagonala stanga sus : 2 si 3
        if (linie - 1 >= 0 && coloana - 1 >= 0 && board[linie - 1][coloana - 1] == player)
            if (linie - 2 >= 0 && coloana - 2 >= 0 && board[linie - 2][coloana - 2] == player)
                numberOfThree++;
            else numberOfTwo++;

        //verifica la mijloc pe orizontala : 3
        if (coloana - 1 >= 0 && coloana + 1 < coloane && board[linie][coloana - 1] == player && board[linie][coloana + 1] == player)
            numberOfThree++;


        //verifica la mijloc pe verticala : 3
        if (linie - 1 >= 0 && linie + 1 < linii && board[linie - 1][coloana] == player && board[linie + 1][coloana] == player)
            numberOfThree++;

        //verifica la mijoc pe diagonala dreapta : 3
        if (linie - 1 >= 0 && coloana - 1 >= 0 && linie + 1 < linii && coloana + 1 < coloane && board[linie - 1][coloana - 1] == player && board[linie + 1][coloana + 1] == player)
            numberOfThree++;

        //verifica la mijloc pe diagonala stnga : 3
        if (linie - 1 >= 0 && coloana + 1 < coloane && linie + 1 < linii && coloana - 1 >= 0 && board[linie - 1][coloana + 1] == player && board[linie + 1][coloana - 1] == player)
            numberOfThree++;

        if (player == 1)
            return numberOfThree * 100 + numberOfTwo * 10;
// putin mai putin valoreaza sa impiedit o mutare a adversarului
        return numberOfThree * 99 + numberOfTwo * 9;
    }


    //verificari mutare
    public boolean checkMove(int move) {

        if (move < 0 || move >= coloane) {
            System.out.println("Mutare imposibila");
            return false;
        }
        if (board[0][move] != 0) {
            System.out.println("Mutare imposibila");
            return false;
        }

        return true;
    }

    public void saveMove(int move, int player) {

        for (int contor = linii - 1; contor >= 0; contor--)
            if (board[contor][move] == 0) {
                board[contor][move] = player;
                lastI=contor;
                lastJ=move;
                break;
            }

    }


    public boolean existaMutari() {
        for (int contor = 0; contor < linii; contor++)
            for (int contor2 = 0; contor2 < coloane; contor2++)
                if (board[contor][contor2] == 0)
                    return true;

        return false;
    }


    public void printBoard() {

        for (int contor = 0; contor < linii; contor++) {
            for (int contor2 = 0; contor2 < coloane; contor2++)
                System.out.print(" " + board[contor][contor2]);
            System.out.println();
        }
        for (int contor = 0; contor < coloane * 2; contor++)
            System.out.print("-");
        System.out.println();
        for (int nrCol = 0; nrCol < coloane; nrCol++)
            System.out.print(" " + nrCol);
        System.out.println("\n");
    }



}
