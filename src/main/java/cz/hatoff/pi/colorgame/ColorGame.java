package cz.hatoff.pi.colorgame;

import cz.hatoff.pi.colorgame.controller.ColorGameController;
import cz.hatoff.pi.colorgame.listener.PlayerGameButtonListener;
import cz.hatoff.pi.colorgame.listener.PlayerLoginListener;
import cz.hatoff.pi.colorgame.model.Color;
import cz.hatoff.pi.colorgame.model.Player;
import org.apache.log4j.Logger;

import static cz.hatoff.pi.colorgame.model.Color.BLUE;
import static cz.hatoff.pi.colorgame.model.Color.GREEN;
import static cz.hatoff.pi.colorgame.model.Color.RED;
import static cz.hatoff.pi.colorgame.model.Player.PLAYER_1;
import static cz.hatoff.pi.colorgame.model.Player.PLAYER_2;

public class ColorGame {

    private final static Logger log = Logger.getLogger(ColorGame.class);

    private ColorGameController controller;
    private GameState gameState;

    public static void main(String[] args) throws InterruptedException {
        new ColorGame().start();
    }

    private void start() throws InterruptedException {

        log.info("Hra začíná.");
        controller = new ColorGameController();
        gameState = new GameState();

        //Phase 1
        log.info("Čekám na hráče až se přihlásí do hry.");
        controller.blink(PLAYER_1);
        controller.blink(PLAYER_2);

        controller.setPlayer1LoginListener(new PlayerLoginListener(gameState, PLAYER_1, controller));
        controller.setPlayer2LoginListener(new PlayerLoginListener(gameState, PLAYER_2, controller));

        while (!gameState.bothPlayersAreReady()) {
            Thread.sleep(100);
        }
        log.info("Oba hráči úspěšně přihlášeni.");
        Thread.sleep(2000);

        //Phase 2
        log.info("Hra začíná za 3 sekundy.");
        Thread.sleep(1000);
        controller.setScore(PLAYER_1, 2);
        controller.setScore(PLAYER_2, 2);
        log.info("Hra začíná za 2 sekundy.");
        Thread.sleep(1000);
        controller.setScore(PLAYER_1, 1);
        controller.setScore(PLAYER_2, 1);
        log.info("Hra začíná za 1 sekundy.");
        Thread.sleep(1000);
        controller.setScore(PLAYER_1, 0);
        controller.setScore(PLAYER_2, 0);

        //Phase3

        controller.setPlayer1GameButtonListeners(
            new PlayerGameButtonListener(gameState, RED, PLAYER_1),
            new PlayerGameButtonListener(gameState, GREEN, PLAYER_1),
            new PlayerGameButtonListener(gameState, BLUE, PLAYER_1)
        );
        controller.setPlayer2GameButtonListeners(
            new PlayerGameButtonListener(gameState, RED, PLAYER_2),
            new PlayerGameButtonListener(gameState, GREEN, PLAYER_2),
            new PlayerGameButtonListener(gameState, BLUE, PLAYER_2)
        );

        log.info("Hra začíná ...");
        Player winner;
        while ((winner = gameState.getWinner()) == null) {
            gameState.incerementRound();
            log.info("Kolo " + gameState.getRound());
            log.info("Losuji barvu.");
            Color selectedColor = controller.spinRgbLed();
            gameState.setSelectedColor(selectedColor);

            Thread.sleep(1000);
            controller.gameLedSetColor(0,0,0);

            log.info("Vybraná barva je " + selectedColor.name());

            if (gameState.getP1Color() != null) {
                log.info("Hráč 1 označil barvu " + gameState.getP1Color().name());
            } else {
                log.info("Hráč 1 neoznačil žádnou barvu.");
            }
            if (gameState.getP2Color() != null) {
                log.info("Hráč 2 označil barvu " + gameState.getP2Color().name());
            } else {
                log.info("Hráč 2 neoznačil žádnou barvu.");
            }

            if (selectedColor.equals(gameState.getP1Color())) {
                gameState.setPlayer1Score(gameState.getPlayer1Score() + 1);
                controller.setScore(PLAYER_1, gameState.getPlayer1Score());
                log.info("Hráč 1 získává bod. Celkem jich má " + gameState.getPlayer1Score());
            }
            if (selectedColor.equals(gameState.getP2Color())) {
                gameState.setPlayer2Score(gameState.getPlayer2Score() + 1);
                controller.setScore(PLAYER_2, gameState.getPlayer2Score());
                log.info("Hráč 2 získává bod. Celkem jich má " + gameState.getPlayer2Score());
            }

            gameState.setSelectedColor(null);
            gameState.setP1Color(null);
            gameState.setP2Color(null);


            Thread.sleep(5000);
        }

        //Phase 4
        controller.setScore(PLAYER_1, 0);
        controller.setScore(PLAYER_2, 0);

        log.info("Vítězem se stává " + winner.getName());

        switch (winner) {
            case PLAYER_1:
                controller.blink(PLAYER_1);
                break;
            case PLAYER_2:
                controller.blink(PLAYER_2);
                break;
            case BOTH:
                controller.blink(PLAYER_1);
                controller.blink(PLAYER_2);
        }

        Thread.sleep(5000);


        controller.stop();

    }

}
