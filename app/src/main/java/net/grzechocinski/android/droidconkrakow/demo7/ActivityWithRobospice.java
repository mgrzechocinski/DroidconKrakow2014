package net.grzechocinski.android.droidconkrakow.demo7;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import java.util.ArrayList;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.ui.MatchesAdapter;

public class ActivityWithRobospice extends FragmentActivity implements View.OnClickListener, RequestListener<Match> {

    public static final String CACHE_KEY = "cacheKey";

    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String KEY_MATCHES = "currentMatches";

    private static final String MATCH_ID = "MATCH_ID";

    private MatchesAdapter adapter;

    private ArrayList<Match> currentMatches = new ArrayList<>();

    protected SpiceManager spiceManager = new SpiceManager(DemoSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        configureUIComponents(savedInstanceState);
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
        flushList();
        int matchID = 1;
        MatchResultRequest request = new MatchResultRequest(Match.class, matchID);
        spiceManager.execute(request, CACHE_KEY, 3000, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_MATCHES, new ArrayList<>(currentMatches));
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void flushList() {
        currentMatches.clear();
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Log.d(LOG_TAG, "Robospice failure", spiceException);
    }

    @Override
    public void onRequestSuccess(Match match) {
        Log.d(LOG_TAG, "Robospice success: " + match);
        currentMatches.add(match);
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();
    }

    private void configureUIComponents(Bundle savedInstanceState) {
        Button refreshButton = (Button) findViewById(R.id.id_btn_refresh);
        refreshButton.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            currentMatches = (ArrayList<Match>) savedInstanceState.getSerializable(KEY_MATCHES);
        }

        adapter = new MatchesAdapter(currentMatches);
        recyclerView.setAdapter(adapter);
    }
}
