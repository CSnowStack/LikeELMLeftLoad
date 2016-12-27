package csnowstack.load;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cqll on 2016/12/22.
 */

public class TxtBehavior extends CoordinatorLayout.Behavior{
    private int mWidth;
    private String mTxtNormal,mTxtChange;
    private boolean mGo=false;
    private LoadingView mLoadingView;
    public TxtBehavior() {
    }

    public TxtBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTxtNormal=context.getResources().getString(R.string.txt_normal);
        mTxtChange=context.getResources().getString(R.string.txt_chage);
    }

    //移动到看不到的地方
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        mLoadingView= (LoadingView) parent.findViewById(R.id.loading);
        mWidth=child.getWidth();
        //移动到最左边
        child.offsetLeftAndRight(parent.getWidth()-mWidth);
        //隐藏View
        child.setTranslationX(mWidth);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()==R.id.view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float distance=dependency.getTranslationX()/2;

        //向左移动，最大不能超过 4／3个文字的宽度
        if(distance<=-mWidth/3*4){
            mGo=true;
            ((TextView)child).setText(mTxtChange);
            child.setTranslationX(-1/3f*mWidth);
        }else {
            mGo=false;
            ((TextView)child).setText(mTxtNormal);
            child.setTranslationX(distance+mWidth);

        }

        return true;
    }

    public void onStopNestedScroll(){
        if(mGo)
            mLoadingView.go();
    }
}
