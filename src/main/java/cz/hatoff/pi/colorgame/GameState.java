package cz.hatoff.pi.colorgame;


import cz.hatoff.pi.colorgame.model.Color;
import cz.hatoff.pi.colorgame.model.Player;

public class GameState {

    private Boolean player1Ready = false;
    private Boolean player2Ready = false;

    private int player1Score = 0;
    private int player2Score = 0;

    private Color selectedColor;
    private Color p1Color;
    private Color p2Color;

    private int round = 0;

    public Boolean getPlayer1Ready() {
        return player1Ready;
    }

    public void setPlayer1Ready(Boolean player1Ready) {
        this.player1Ready = player1Ready;
    }

    public Boolean getPlayer2Ready() {
        return player2Ready;
    }

    public void setPlayer2Ready(Boolean player2Ready) {
        this.player2Ready = player2Ready;
    }

    public boolean bothPlayersAreReady() {
        return player1Ready && player2Ready;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public Player getWinner() {

        if (player1Score >= 3 && player2Score >= 3) {
            return Player.BOTH;
        }
        if (player1Score >= 3) {
            return Player.PLAYER_1;
        }
        if (player2Score >= 3) {
            return Player.PLAYER_2;
        }
        return null;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getP1Color() {
        return p1Color;
    }

    public void setP1Color(Color p1Color) {
        this.p1Color = p1Color;
    }

    public Color getP2Color() {
        return p2Color;
    }

    public void setP2Color(Color p2Color) {
        this.p2Color = p2Color;
    }

    public int getRound() {
        return round;
    }

    public void incerementRound() {
        round++;
    }
}
