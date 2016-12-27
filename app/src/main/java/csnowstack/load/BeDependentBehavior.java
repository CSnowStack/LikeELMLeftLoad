package csnowstack.load;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cqll on 2016/12/22.
 */

public class BeDependentBehavior extends CoordinatorLayout.Behavior {
    private RecyclerView mRecyclerView;
    private int mDistanceMax;
    private ValueAnimator mValueAnimator;
    private float mDistance=0;
    private boolean mInAnimator=false;

    public BeDependentBehavior(Context context) {
       this(context,null);
    }


    public BeDependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDistanceMax=context.getResources().getDimensionPixelOffset(R.dimen.item_img);
        mValueAnimator=ValueAnimator.ofFloat(0,1);
        mValueAnimator.setDuration(500);
        mValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mInAnimator=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mInAnimator=false;
                mDistance=0;
                mValueAnimator.removeAllUpdateListeners();
            }
        });

    }





    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.rcv);
        return true;
    }

    //水平方向
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_HORIZONTAL) != 0 && !mInAnimator;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        if (dx>0&&!childRequestScroll() || dx<0&&child.getTranslationX()<0){ //向左滑动且列表不需要移动,向右滑且没在初始位置
            consumed[0]=dx;//全部消耗掉
            float distance =dx/2f;

            if(mDistance-distance<=-mDistanceMax){
                mDistance=-mDistanceMax;
            }else {
                mDistance=mDistance-distance;
            }
            child.setTranslationX(mDistance);

        }else {
            consumed[0]=0;//全部分配给列表
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        if(child.getTranslationX()!=0){
            reset(child);
            ((TxtBehavior) ((CoordinatorLayout.LayoutParams) coordinatorLayout.findViewById(R.id.ele_txt).getLayoutParams()).getBehavior()).onStopNestedScroll();
        }
    }

    //不给fling
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return true;
    }

    private void reset(final View child) {
        final float translationX=child.getTranslationX();

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setTranslationX((1-animation.getAnimatedFraction())*translationX);
            }
        });

        mValueAnimator.start();

    }

    private boolean childRequestScroll() {
        return mRecyclerView.getAdapter() != null && //有适配器
                mRecyclerView.getAdapter().getItemCount() > 0 &&//item大于0
                //最后一不完全可见
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() != mRecyclerView.getAdapter().getItemCount()-1;
    }
}
