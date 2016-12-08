/*
 * Copyright © 2016 Nikhil Warke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nickdex.pasteit.core.presentation.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Base class for View Holders.
 *
 * @param <VIEW>  View of the single element.
 * @param <MODEL> Model class representing the element.
 */
public abstract class BaseViewHolder<VIEW extends View, MODEL> extends RecyclerView.ViewHolder {

    protected View view;

    public BaseViewHolder(VIEW view) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
    }

    /**
     * Binds model to the xml layout.
     *
     * @param model The presentation layer model object being used for binding.
     * @param position The position of model object in RecyclerView.
     */
    public abstract void bind(MODEL model, int position);
}
