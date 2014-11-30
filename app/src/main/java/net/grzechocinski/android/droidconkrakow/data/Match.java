package net.grzechocinski.android.droidconkrakow.data;

import java.io.Serializable;

public class Match implements Serializable {

    public final String homeName;
    public final String awayName;
    public final int homeGoals;
    public final int awayGoals;

    public Match(String homeName, int homeGoals, String awayName, int awayGoals) {
        this.awayGoals = awayGoals;
        this.homeGoals = homeGoals;
        this.awayName = awayName;
        this.homeName = homeName;
    }

    @Override
    public String toString() {
        return  homeName + " - " + awayName + "   " + homeGoals + " : " + awayGoals;
    }
}
