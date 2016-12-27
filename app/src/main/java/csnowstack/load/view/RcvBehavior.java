package csnowstack.load.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import csnowstack.load.R;

/**
 * Created by cqll on 2016/12/22.
 */

public class RcvBehavior extends CoordinatorLayout.Behavior {
    public RcvBehavior() {
    }

    public RcvBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()== R.id.ele_be_dependent;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationX(dependency.getTranslationX());
        return true;
    }
}
