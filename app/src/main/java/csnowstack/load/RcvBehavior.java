package csnowstack.load;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cqll on 2016/12/22.
 */

public class RcvBehavior extends CoordinatorLayout.Behavior {
    private RecyclerView mRecyclerView;
    private int mDistanceMax;
    private ValueAnimator mValueAnimator;
    public RcvBehavior() {
    }

    public RcvBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDistanceMax=context.getResources().getDimensionPixelOffset(R.dimen.item_img);
        mValueAnimator=ValueAnimator.ofFloat(0,1);
        mValueAnimator.setDuration(500);

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()==R.id.view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationX(dependency.getTranslationX());
        return true;
    }
}
