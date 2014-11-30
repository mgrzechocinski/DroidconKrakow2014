package net.grzechocinski.android.droidconkrakow.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import net.grzechocinski.android.droidconkrakow.R;
import net.grzechocinski.android.droidconkrakow.data.Match;

public class MatchesAdapter extends RecyclerView.Adapter<MatchResultViewHolder> {

    private List<Match> matchList;

    public MatchesAdapter(List<Match> matchList) {
        setCurrentMatches(matchList);
    }

    @Override
    public MatchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match_result, parent, false);
        return new MatchResultViewHolder((MatchResultLayout) container);
    }

    @Override
    public void onBindViewHolder(MatchResultViewHolder holder, int position) {
        holder.containerView.setResult(matchList.get(position));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public void setCurrentMatches(List<Match> currentMatches) {
        this.matchList = new ArrayList<>(currentMatches);
    }
}
