package net.grzechocinski.android.droidconkrakow.data;

import android.util.SparseArray;

public class MatchDataSource {

    public static final SparseArray<Match> matches = new SparseArray<Match>() {{
        put(0, new Match("Borussia", 2, "Bayern", 0));
        put(1, new Match("Shalke", 0, "Bayer", 1));
        put(2, new Match("Eintracht", 1, "Werder", 4));
        put(3, new Match("Hertha", 4, "Wolsburg", 3));
    }};
}
