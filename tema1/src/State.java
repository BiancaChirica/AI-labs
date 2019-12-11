public class State {
    int numarMisionariM1;
    int numarCanibaliM1;
    int numarMisionariM2;
    int numarCanibaliM2;
    int pozBarca;
    int f =0;
    int g =0;
    int h=0;

    public State(int numarMisionariM1, int numarCanibaliM1, int numarMisionariM2, int numarCanibaliM2, int pozBarca) {
        this.numarMisionariM1 = numarMisionariM1;
        this.numarCanibaliM1 = numarCanibaliM1;
        this.numarMisionariM2 = numarMisionariM2;
        this.numarCanibaliM2 = numarCanibaliM2;
        this.pozBarca = pozBarca;
    }

    @Override
    public String toString() {
        return "State{" +
                "numarMisionariM1=" + numarMisionariM1 +
                ", numarCanibaliM1=" + numarCanibaliM1 +
                ", numarMisionariM2=" + numarMisionariM2 +
                ", numarCanibaliM2=" + numarCanibaliM2 +
                ", pozBarca=" + pozBarca +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}
