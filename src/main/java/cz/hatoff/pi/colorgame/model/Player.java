package cz.hatoff.pi.colorgame.model;

public enum Player {

    PLAYER_1("Hráč 1"), PLAYER_2("Hráč 2"), BOTH("oba hráči");

    private final String name;

    Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
