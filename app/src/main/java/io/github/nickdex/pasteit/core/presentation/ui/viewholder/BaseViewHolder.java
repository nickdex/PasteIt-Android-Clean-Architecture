package io.github.nickdex.pasteit.core.presentation.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<VIEW extends View, MODEL> extends RecyclerView.ViewHolder {

    protected View view;

    public BaseViewHolder(VIEW view) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public abstract void bind(MODEL model, int position);
}
