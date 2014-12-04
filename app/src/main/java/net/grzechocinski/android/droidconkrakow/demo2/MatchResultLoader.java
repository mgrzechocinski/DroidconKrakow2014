package net.grzechocinski.android.droidconkrakow.demo2;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.data.MatchDataSource;
import net.grzechocinski.android.droidconkrakow.util.Delay;

public class MatchResultLoader extends AsyncTaskLoader<Match> {

    private final int matchId;

    public MatchResultLoader(Context context, int matchId) {
        super(context);
        this.matchId = matchId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d("", "onStartLoading");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d("", "onStopLoading");
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d("", "onForceLoad");
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.d("", "onReset");
    }

    @Override
    public Match loadInBackground() {
        Log.d("", "Loading match with id " + matchId);
        Delay.delayThreadForSeconds(3);
        Match result = MatchDataSource.matches.get(matchId);
        Log.d("", "Loaded match with id " + matchId + " => " + result);
        return result;
    }

    @Override
    public void deliverResult(Match data) {
        super.deliverResult(data);
        Log.d("", "deliverResult: " + data);
    }
}
