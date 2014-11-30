package net.grzechocinski.android.droidconkrakow.util;

public class Delay {

    public static void delayThreadForSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

}
