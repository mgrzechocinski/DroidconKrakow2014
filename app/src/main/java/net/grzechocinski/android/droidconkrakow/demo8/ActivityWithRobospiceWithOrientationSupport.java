package net.grzechocinski.android.droidconkrakow.demo8;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;
import java.util.ArrayList;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.demo7.DemoSpiceService;
import net.grzechocinski.android.droidconkrakow.demo7.MatchResultRequest;
import net.grzechocinski.android.droidconkrakow.ui.MatchesAdapter;

public class ActivityWithRobospiceWithOrientationSupport extends FragmentActivity implements View.OnClickListener, RequestListener<Match>,
        PendingRequestListener<Match> {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String KEY_MATCHES = "currentMatches";

    private static final String MATCH_ID = "MATCH_ID";

    private Button refreshButton;

    private MatchesAdapter adapter;

    private ArrayList<Match> currentMatches = new ArrayList<>();

    protected SpiceManager spiceManager = new SpiceManager(DemoSpiceService.class);

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

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
        spiceManager.addListenerIfPending(Match.class, "cacheKey", this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void loadMatches() {
        currentMatches.clear();
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();
        int matchID = 1;
        MatchResultRequest request = new MatchResultRequest(Match.class, matchID);
        spiceManager.execute(request, "cacheKey", DurationInMillis.ALWAYS_EXPIRED, this);
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(this, "Robospice failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(Match match) {
        Toast.makeText(this, "Robospice success", Toast.LENGTH_SHORT).show();
        currentMatches.add(match);
        adapter.setCurrentMatches(currentMatches);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestNotFound() {
    }
}
