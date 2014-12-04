package net.grzechocinski.android.droidconkrakow.demo7;

import com.octo.android.robospice.request.SpiceRequest;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.data.MatchDataSource;
import net.grzechocinski.android.droidconkrakow.util.Delay;

public class MatchResultRequest extends SpiceRequest<Match> {

    private final int matchID;

    public MatchResultRequest(Class<Match> clazz, int matchID) {
        super(clazz);
        this.matchID = matchID;
    }

    @Override
    public Match loadDataFromNetwork() throws Exception {
        Delay.delayThreadForSeconds(3);
        return MatchDataSource.matches.get(matchID);
    }

    @Override
    public String toString() {
        return "MatchResultRequest{" +
                "matchID=" + matchID +
                '}';
    }
}
