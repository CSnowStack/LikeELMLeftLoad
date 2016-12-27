package csnowstack.load;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cqll on 2016/12/22.
 */

public class LoadingBehavior extends CoordinatorLayout.Behavior {
    private int mParentDistanceMax;
    public LoadingBehavior() {
    }

    public LoadingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mParentDistanceMax=context.getResources().getDimensionPixelOffset(R.dimen.item_img);
    }

    //移动到右边
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        child.offsetLeftAndRight(parent.getWidth()-child.getWidth());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()==R.id.view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //列表移动的比例
        float fraction=Math.abs(dependency.getTranslationX())/mParentDistanceMax;
        //改变view
        ((LoadingView)child).setFraction(fraction,dependency.getTranslationX()/2);
        return true;
    }
}
