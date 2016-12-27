package csnowstack.load.view;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import csnowstack.load.R;

/**
 * Created by cqll on 2016/12/22.
 */

public class LoadingBehavior extends CoordinatorLayout.Behavior {



    private int mHeight;

    public LoadingBehavior(int height) {
        mHeight = height;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        child.measure(View.MeasureSpec.makeMeasureSpec(mHeight, View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(mHeight, View.MeasureSpec.EXACTLY));
        return true;
    }

    //移动到右边
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        child.offsetLeftAndRight(parent.getWidth()-child.getWidth());//移动到屏幕外
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()== R.id.ele_be_dependent;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //列表移动的比例
        float fraction=Math.abs(dependency.getTranslationX())/mHeight;
        //改变view
        ((LoadingView)child).setFraction(fraction,dependency.getTranslationX()/2);
        return true;
    }
}
