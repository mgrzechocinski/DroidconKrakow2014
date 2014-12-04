package net.grzechocinski.android.droidconkrakow.demo9;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.octo.android.robospice.SpiceManager;
import java.util.ArrayList;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;
import net.grzechocinski.android.droidconkrakow.data.MatchDataSource;
import net.grzechocinski.android.droidconkrakow.demo7.DemoSpiceService;
import net.grzechocinski.android.droidconkrakow.ui.MatchesAdapter;
import net.grzechocinski.android.droidconkrakow.util.Delay;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.events.OnClickEvent;
import rx.android.operators.OperatorViewClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivityWithRxAndroid extends FragmentActivity {

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            currentMatches = (ArrayList<Match>) savedInstanceState.getSerializable(KEY_MATCHES);
        }

        adapter = new MatchesAdapter(currentMatches);
        recyclerView.setAdapter(adapter);

        //  bind();
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(1)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Match>() {
                            @Override
                            public void call(Match match) {
                                Delay.delayThreadForSeconds(3);
                                currentMatches.clear();
                                currentMatches.add(match);
                                adapter.setCurrentMatches(currentMatches);
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        });
    }

    private void bind() {
        Observable.create(new OperatorViewClick(refreshButton, false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OnClickEvent>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG_TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OnClickEvent onClickEvent) {
                        Log.d(LOG_TAG, "onNext");


                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_MATCHES, new ArrayList<>(currentMatches));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private Observable<Match> loadData(final int matchID) {

        return Observable.create(new Observable.OnSubscribe<Match>() {

            @Override
            public void call(Subscriber<? super Match> subscriber) {
                try {
                    subscriber.onNext(MatchDataSource.matches.get(matchID));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
