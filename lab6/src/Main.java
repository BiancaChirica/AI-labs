public class Main {
    public static void main(String[] args)
    {
        //Problema reginelor cu forward checking
        // tabla are n linii si n coloane
        // vectorul sol are n elemente
        // variabila : rezultat[i] = coloane pe care se afla regina de la linia i
        // domeniul variabilei [0,coloane-1] va fi reprezentat de matricea board, fiecare variabila va avea linia proprie
        // constrangere :
        // rezultat[i] != rezultat[j]
        // abs(rezultat[i] -rezultat[j]) != abs(i-j)

        Board board = new Board(4,4);
        board.alg(0);

    }
}
