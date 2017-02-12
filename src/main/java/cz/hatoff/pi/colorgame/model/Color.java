package cz.hatoff.pi.colorgame.model;


import java.util.Random;

public enum Color {

    RED(1,0,0), GREEN(0,1,0), BLUE(0,0,1);

    private final int r;
    private final int b;
    private final int g;

    Color(int r, int g, int b) {
        this.r = r;
        this.b = g;
        this.g = b;
    }

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public static Color getRandom(){
        int random = new Random().nextInt(3);
        switch (random) {
            case 0: return RED;
            case 1: return GREEN;
            case 2: return BLUE;
            default: throw new RuntimeException("Nemam barvu pro cislo " + random);
        }
    }
}
