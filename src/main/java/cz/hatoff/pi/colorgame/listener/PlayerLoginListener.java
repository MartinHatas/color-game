package cz.hatoff.pi.colorgame.listener;


import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import cz.hatoff.pi.colorgame.ColorGame;
import cz.hatoff.pi.colorgame.GameState;
import cz.hatoff.pi.colorgame.model.Player;
import cz.hatoff.pi.colorgame.controller.ColorGameController;
import org.apache.log4j.Logger;

public class PlayerLoginListener implements GpioPinListenerDigital {

    private final static Logger log = Logger.getLogger(ColorGame.class);

    private GameState gameState;
    private Player player;
    private ColorGameController colorGameController;

    public PlayerLoginListener(GameState gameState, Player player, ColorGameController colorGameController) {
        this.gameState = gameState;
        this.player = player;
        this.colorGameController = colorGameController;
    }

    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

        colorGameController.removeLoginButtonListsner(player);
        switch (player) {
            case PLAYER_1:
                log.info("Hráč 1 se přihlásil do hry.");
                colorGameController.stopBlinkingPlayer1();
                gameState.setPlayer1Ready(true);
                break;
            case PLAYER_2:
                log.info("Hráč 2 se přihlásil do hry.");
                colorGameController.stopBlinkingPlayer2();
                gameState.setPlayer2Ready(true);
                break;
        }
        colorGameController.turnOnPlayerLeds(player);
    }
}
