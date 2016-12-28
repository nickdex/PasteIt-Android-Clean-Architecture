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

package io.github.nickdex.pasteit.messages;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * A class that defines custom behavior of slide EditText when snackbar slides in from bottom
 */
@Keep
class InputFieldBehavior extends CoordinatorLayout.Behavior<EditText> {

    public InputFieldBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, EditText child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    /**
     * Shifts EditText to make room for {@link Snackbar}.
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, EditText child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;

    }

    /**
     * Returns the EditText to its original position
     */
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, EditText child, View dependency) {
        child.setTranslationY(0);
    }

}
