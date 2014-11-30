package net.grzechocinski.android.droidconkrakow.demo1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.data.MatchDataSource;
import net.grzechocinski.android.droidconkrakow.ui.MatchesAdapter;
import net.grzechocinski.android.droidconkrakow.util.Delay;

public class ActivityWithInnerAsyncTask extends Activity implements View.OnClickListener {

    private static final String KEY_MATCHES = "currentMatches";

    private Button refreshButton;

    private MatchesAdapter adapter;

    private ArrayList<Match> currentMatches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        refreshButton = (Button) findViewById(R.id.id_btn_refresh);
        refreshButton.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(savedInstanceState != null){
            currentMatches = (ArrayList<Match>) savedInstanceState.getSerializable(KEY_MATCHES);
        }

        adapter = new MatchesAdapter(currentMatches);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_MATCHES, new ArrayList<>(currentMatches));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_refresh:
                loadMatches();
                break;
        }
    }

    private void loadMatches() {
        currentMatches.clear();
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();

        final int matchID = 0;

        new AsyncTask<Void, Void, Match>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(this.getClass().getSimpleName(), "Loading match with id: " + matchID);
                refreshButton.setEnabled(false);
            }

            @Override
            protected Match doInBackground(Void... params) {
                Delay.delayThreadForSeconds(5);
                Match match = MatchDataSource.matches.get(matchID);
                Log.d(this.getClass().getSimpleName(), "Loaded match (id " + matchID + "): " + match);
                return match;
            }

            @Override
            protected void onPostExecute(Match match) {
                super.onPostExecute(match);
                currentMatches.add(match);
                adapter.setCurrentMatches(currentMatches);
                adapter.notifyDataSetChanged();
                ActivityWithInnerAsyncTask.this.refreshButton.setEnabled(true);
            }
        }.execute();
    }
}
