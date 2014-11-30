package net.grzechocinski.android.droidconkrakow.ui;

import android.support.v7.widget.RecyclerView;
import net.grzechocinski.android.droidconkrakow.ui.MatchResultLayout;

public class MatchResultViewHolder extends RecyclerView.ViewHolder {

    public MatchResultLayout containerView;

    public MatchResultViewHolder(MatchResultLayout containerView) {
        super(containerView);
        this.containerView = containerView;
    }
}
