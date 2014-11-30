package net.grzechocinski.android.droidconkrakow.demo5;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.ui.MatchesAdapter;

public class ActivityWithLoaderWhichSupportsOnStopAndRedelivery extends FragmentActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Match> {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String KEY_MATCHES = "currentMatches";

    private static final String MATCH_ID = "MATCH_ID";

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

        if (savedInstanceState != null) {
            currentMatches = (ArrayList<Match>) savedInstanceState.getSerializable(KEY_MATCHES);
            initLoader();
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
        initLoader();
    }

    private void initLoader() {
        Bundle bundle = prepareBundle();
        getSupportLoaderManager().initLoader(1, bundle, this);
    }

    private Bundle prepareBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(MATCH_ID, 1);
        return bundle;
    }

    @Override
    public Loader<Match> onCreateLoader(int id, Bundle args) {
        return new MatchResultLoader(this, args.getInt(MATCH_ID));
    }

    @Override
    public void onLoadFinished(Loader<Match> loader, Match match) {
        Log.d(LOG_TAG, "onLoadFinished: " + match);
        currentMatches.add(match);
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Match> loader) {

    }
}
