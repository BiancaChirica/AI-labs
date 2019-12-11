import java.util.ArrayList;

public class Board {
    int board[][] = new int[5][5];
    int linii;
    int coloane;
    int rezultat[] = new int[5];

    public Board(int linii, int coloane) {
        if (linii > 2 && coloane > 2 && linii == coloane)
            this.linii = linii;
        this.coloane = coloane;
        initializare();
    }

    public void initializare() {
        for (int i = 0; i < linii; i++) {
            for (int j = 0; j < coloane; j++)
                board[i][j] = 0;
            rezultat[i] = -1;
        }

    }

    public void alg(int k) {

        if(k==linii)
        {
            afiseaza();
            System.exit(0);
        }
        else {
            int cpyBoard[][] = new int[5][5];
            copyBoard(cpyBoard, board);
            for (int col = 0; col < coloane; col++) // alege valori posibile din domeniul variabilei x[k]
            {
                int temp = forwardChecking(k,col);
                if (temp != -1) {
                    rezultat[k]=col;
                    alg(k+1);
                    rezultat[k]=-1;
                    copyBoard(board, cpyBoard);

                }
            }
        }
    }

    public int forwardChecking(int linie,int col) {


        if(board[linie][col] == 0)
            {
                board[linie][col] = 3;

            boolean emptyDomain = false;

                int cpyBoard[][] = new int[5][5];
                copyBoard(cpyBoard, board);

            for (int linieNext = linie + 1; linieNext < linii; linieNext++) // ia toate varibilele care urmeaza sa fie alese
            {
                for (int colNext = 0; colNext < coloane ; colNext++) // pentru fiecare valoare ia valorile posibile
                if(board[linieNext][colNext] == 0)
                    {
                    if (!valid(linie, col, linieNext, colNext))
                        board[linieNext][colNext] = 1;
                }

                if (checkDomainEmpty(linieNext)) {
                    emptyDomain = true;
                }
            }

                afiseazaMatrice(board);

            if (!emptyDomain) {
                return col;
            }
            else
                copyBoard(board, cpyBoard);

    }
        return -1;


    }

    private boolean valid(int lin, int col, int nxtLin, int nxtCol) {
        if (col == nxtCol || java.lang.Math.abs(col - nxtCol) == java.lang.Math.abs(lin - nxtLin) )
            return false;
        return true;
    }


    private boolean checkDomainEmpty(int linie) {
        for (int j = 0; j < coloane; j++)
            if (board[linie][j] == 0)
                return false;
        return true;
    }

    private void copyBoard(int[][] copy, int[][] board) {
        for (int i = 0; i < linii; i++)
            for (int j = 0; j < coloane; j++)
                copy[i][j] = board[i][j];
    }

    public void afiseaza() {
        for (int i = 0; i < linii; i++) {
            for (int j = 0; j < coloane; j++)
                if (rezultat[i] == j)
                    System.out.print("|R");
                else System.out.print("|0");
            System.out.println("|");
        }
        System.out.println();

    }

    public void afiseazaMatrice(int [][] cpyBoard) {
        for (int i = 0; i < linii; i++)
            {
                for (int j = 0; j < coloane; j++)
                    System.out.print(cpyBoard[i][j]);
                System.out.println();
            }
        System.out.println();
    }
}
