package net.grzechocinski.android.droidconkrakow.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;

public class MatchResultLayout extends LinearLayout {

    private TextView homeNameTextView;

    private TextView homeGoalsTextView;

    private TextView awayNameTextView;

    private TextView awayGoalsTextView;

    public MatchResultLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        homeNameTextView = (TextView) findViewById(R.id.id_tv_home_name);
        homeGoalsTextView = (TextView) findViewById(R.id.id_tv_home_goals);
        awayNameTextView = (TextView) findViewById(R.id.id_tv_away_name);
        awayGoalsTextView = (TextView) findViewById(R.id.id_tv_away_goals);
    }

    public void setResult(Match match) {
        homeNameTextView.setText(match.homeName);
        homeGoalsTextView.setText(""+match.homeGoals);
        awayNameTextView.setText(match.awayName);
        awayGoalsTextView.setText(""+match.awayGoals);
    }
}
