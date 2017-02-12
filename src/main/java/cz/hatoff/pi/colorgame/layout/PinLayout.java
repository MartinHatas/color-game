package cz.hatoff.pi.colorgame.layout;


import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class PinLayout {

    //  Player 1
    public static Pin p1RedButton = RaspiPin.GPIO_25;
    public static Pin p1GreenButton = RaspiPin.GPIO_24;
    public static Pin p1BluedButton = RaspiPin.GPIO_23;

    public static Pin p1Score1 = RaspiPin.GPIO_29;
    public static Pin p1Score2 = RaspiPin.GPIO_28;
    public static Pin p1Score3 = RaspiPin.GPIO_27;

    //  Player 2
    public static Pin p2RedButton = RaspiPin.GPIO_03;
    public static Pin p2GreenButton = RaspiPin.GPIO_02;
    public static Pin p2BluedButton = RaspiPin.GPIO_00;

    public static Pin p2Score1 = RaspiPin.GPIO_01;
    public static Pin p2Score2 = RaspiPin.GPIO_04;
    public static Pin p2Score3 = RaspiPin.GPIO_05;

    // RGB led
    public static Pin red = RaspiPin.GPIO_22;
    public static Pin green = RaspiPin.GPIO_21;
    public static Pin blue = RaspiPin.GPIO_26;
}
