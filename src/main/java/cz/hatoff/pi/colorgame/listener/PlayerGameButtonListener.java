package cz.hatoff.pi.colorgame.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import cz.hatoff.pi.colorgame.GameState;
import cz.hatoff.pi.colorgame.model.Color;
import cz.hatoff.pi.colorgame.model.Player;

public class PlayerGameButtonListener implements GpioPinListenerDigital {

    private GameState gameState;
    private Color color;
    private Player player;

    public PlayerGameButtonListener(GameState gameState, Color color, Player player) {
        this.gameState = gameState;
        this.color = color;
        this.player = player;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        switch (player) {
            case PLAYER_1:
                gameState.setP1Color(color);
                break;
            case PLAYER_2:
                gameState.setP2Color(color);
                break;
        }

    }
}
