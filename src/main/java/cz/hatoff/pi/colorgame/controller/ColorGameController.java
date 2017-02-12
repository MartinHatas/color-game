package cz.hatoff.pi.colorgame.controller;


import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
import cz.hatoff.pi.colorgame.listener.PlayerGameButtonListener;
import cz.hatoff.pi.colorgame.model.Color;
import cz.hatoff.pi.colorgame.model.Player;
import cz.hatoff.pi.colorgame.layout.PinLayout;
import cz.hatoff.pi.colorgame.listener.PlayerLoginListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

public class ColorGameController {

    private static final int BLINK_DELAY = 800;
    private static final int SLEEP = 200;

    private GpioPinDigitalOutput p1Score1Led, p1Score2Led, p1Score3Led;
    private GpioPinDigitalInput p1RedButton, p1GreenButton, p1BlueButton;
    private List<Future> player1LedScheduledTasks = new ArrayList<>();
    private List<Future> player2LedScheduledTasks = new ArrayList<>();

    private GpioPinDigitalOutput p2Score1Led, p2Score2Led, p2Score3Led;
    private GpioPinDigitalInput p2RedButton, p2GreenButton, p2BlueButton;

    private final GpioController gpio;

    public ColorGameController() {

        gpio = GpioFactory.getInstance();

        // Init RGB
        final GpioPinDigitalOutput ledRed = gpio.provisionDigitalOutputPin(PinLayout.red);
        final GpioPinDigitalOutput ledGreen = gpio.provisionDigitalOutputPin(PinLayout.green);
        final GpioPinDigitalOutput ledBlue = gpio.provisionDigitalOutputPin(PinLayout.blue);

        ledRed.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledGreen.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledBlue.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        SoftPwm.softPwmCreate(PinLayout.red.getAddress(), 0, 50);
        SoftPwm.softPwmCreate(PinLayout.green.getAddress(), 0, 50);
        SoftPwm.softPwmCreate(PinLayout.blue.getAddress(), 0, 50);
        gameLedOff();

        //Init score leds
        p1Score1Led = gpio.provisionDigitalOutputPin(PinLayout.p1Score1);
        p1Score2Led = gpio.provisionDigitalOutputPin(PinLayout.p1Score2);
        p1Score3Led = gpio.provisionDigitalOutputPin(PinLayout.p1Score3);

        p1Score1Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        p1Score2Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        p1Score3Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        p2Score1Led = gpio.provisionDigitalOutputPin(PinLayout.p2Score1);
        p2Score2Led = gpio.provisionDigitalOutputPin(PinLayout.p2Score2);
        p2Score3Led = gpio.provisionDigitalOutputPin(PinLayout.p2Score3);

        p2Score1Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        p2Score2Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        p2Score3Led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        resetScore(Player.PLAYER_1);
        resetScore(Player.PLAYER_2);


        //Init buttons
        p1RedButton = gpio.provisionDigitalInputPin(PinLayout.p1RedButton, PinPullResistance.PULL_UP);
        p1RedButton.setDebounce(20);
        p1RedButton.setShutdownOptions(true);
        p1GreenButton = gpio.provisionDigitalInputPin(PinLayout.p1GreenButton, PinPullResistance.PULL_UP);
        p1GreenButton.setDebounce(20);
        p1GreenButton.setShutdownOptions(true);
        p1BlueButton = gpio.provisionDigitalInputPin(PinLayout.p1BluedButton, PinPullResistance.PULL_UP);
        p1BlueButton.setDebounce(20);
        p1BlueButton.setShutdownOptions(true);

        p2RedButton = gpio.provisionDigitalInputPin(PinLayout.p2RedButton, PinPullResistance.PULL_UP);
        p2RedButton.setDebounce(20);
        p2RedButton.setShutdownOptions(true);
        p2GreenButton = gpio.provisionDigitalInputPin(PinLayout.p2GreenButton, PinPullResistance.PULL_UP);
        p2GreenButton.setDebounce(20);
        p2GreenButton.setShutdownOptions(true);
        p2BlueButton = gpio.provisionDigitalInputPin(PinLayout.p2BluedButton, PinPullResistance.PULL_UP);
        p2BlueButton.setDebounce(20);
        p2BlueButton.setShutdownOptions(true);

    }

    public void resetScore(Player player) {
        switch (player) {
            case PLAYER_1:
                p1Score1Led.low();
                p1Score2Led.low();
                p1Score3Led.low();
                break;
            case PLAYER_2:
                p2Score1Led.low();
                p2Score2Led.low();
                p2Score3Led.low();
                break;
        }
    }

    public void gameLedOff() {
        gameLedSetColor(0,0,0);
    }

    public void gameLedSetColor(int r, int g, int b) {
        SoftPwm.softPwmWrite(PinLayout.red.getAddress(), (int) (r * 50f));
        SoftPwm.softPwmWrite(PinLayout.green.getAddress(), (int) (g * 50f));
        SoftPwm.softPwmWrite(PinLayout.blue.getAddress(), (int) (b * 50f));
    }

    public void blink(Player player) {
        switch (player) {
            case PLAYER_1:
                player1LedScheduledTasks.add(p1Score1Led.blink(BLINK_DELAY));
                player1LedScheduledTasks.add(p1Score2Led.blink(BLINK_DELAY));
                player1LedScheduledTasks.add(p1Score3Led.blink(BLINK_DELAY));
                break;
            case PLAYER_2:
                player2LedScheduledTasks.add(p2Score1Led.blink(BLINK_DELAY));
                player2LedScheduledTasks.add(p2Score2Led.blink(BLINK_DELAY));
                player2LedScheduledTasks.add(p2Score3Led.blink(BLINK_DELAY));
                break;
        }

    }

    public void stop(){
        gpio.shutdown();
    }

    public void turnOnPlayerLeds(Player player) {
        switch (player) {
            case PLAYER_1:
                p1Score1Led.high();
                p1Score2Led.high();
                p1Score3Led.high();
                break;
            case PLAYER_2:
                p2Score1Led.high();
                p2Score2Led.high();
                p2Score3Led.high();
                break;
        }
    }

    public void removeLoginButtonListsner(Player player) {
        switch (player) {
            case PLAYER_1:
                p1GreenButton.removeAllListeners();
                break;
            case PLAYER_2:
                p2GreenButton.removeAllListeners();
                break;
        }
    }

    public void setPlayer1LoginListener(PlayerLoginListener playerLoginListener) {
        p1GreenButton.addListener(playerLoginListener);
    }

    public void setPlayer2LoginListener(PlayerLoginListener playerLoginListener) {
        p2GreenButton.addListener(playerLoginListener);
    }

    public void stopBlinkingPlayer1(){
        for (Future task : player1LedScheduledTasks) {
            task.cancel(true);
        }
    }

    public void stopBlinkingPlayer2(){
        for (Future task : player2LedScheduledTasks) {
            task.cancel(true);

        }
    }

    public void setScore(Player player, int score) {
        switch (player) {
            case PLAYER_1:
                switch (score) {
                    case 0:
                        p1Score1Led.low();
                        p1Score2Led.low();
                        p1Score3Led.low();
                        break;
                    case 1:
                        p1Score1Led.high();
                        p1Score2Led.low();
                        p1Score3Led.low();
                        break;
                    case 2:
                        p1Score1Led.high();
                        p1Score2Led.high();
                        p1Score3Led.low();
                        break;
                    case 3:
                        p1Score1Led.high();
                        p1Score2Led.high();
                        p1Score3Led.high();
                        break;
                }
                break;
            case PLAYER_2:
                switch (score) {
                    case 0:
                        p2Score1Led.low();
                        p2Score2Led.low();
                        p2Score3Led.low();
                        break;
                    case 1:
                        p2Score1Led.high();
                        p2Score2Led.low();
                        p2Score3Led.low();
                        break;
                    case 2:
                        p2Score1Led.high();
                        p2Score2Led.high();
                        p2Score3Led.low();
                        break;
                    case 3:
                        p2Score1Led.high();
                        p2Score2Led.high();
                        p2Score3Led.high();
                        break;
                }
                break;
        }
    }

    public Color spinRgbLed() throws InterruptedException {
        float maxTime = new Random().nextFloat() * 7000 + 3000;
        int time = 0;

        Color selectedColor = null;
        while (maxTime > time) {
            selectedColor = Color.getRandom();
            gameLedSetColor(selectedColor.getR(), selectedColor.getG(), selectedColor.getB());
            time = time + SLEEP;
            Gpio.delay(SLEEP);
        }
        return selectedColor;
    }

    public void setPlayer1GameButtonListeners(PlayerGameButtonListener redListener, PlayerGameButtonListener greenLister, PlayerGameButtonListener blueListener) {
        p1RedButton.addListener(redListener);
        p1GreenButton.addListener(greenLister);
        p1BlueButton.addListener(blueListener);
    }

    public void setPlayer2GameButtonListeners(PlayerGameButtonListener redListener, PlayerGameButtonListener greenLister, PlayerGameButtonListener blueListener) {
        p2RedButton.addListener(redListener);
        p2GreenButton.addListener(greenLister);
        p2BlueButton.addListener(blueListener);
    }
}
