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
    private int mWidth,mImgHeight;
    private boolean mGo=false;
    private LoadingView mLoadingView;
    private String [] mTxtRes;
    public TxtBehavior(Context context) {
        init(context);
    }
    public TxtBehavior(String normal,String change) {
        init(normal,change);
    }


    public TxtBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    public void init(Context context){
        init(context.getString(R.string.txt_normal),context.getString(R.string.txt_chage));
    }


    public void init(String normal, String change){
        mTxtRes=new String[]{normal,change};
    }


    //移动到看不到的地方
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        mLoadingView= (LoadingView) parent.findViewById(R.id.ele_loading);
        mWidth=child.getWidth();
        //移动到最右边
        child.offsetLeftAndRight(parent.getWidth()-mWidth);
        child.offsetTopAndBottom(mLoadingView.getHeight()/2-child.getHeight()/2);
        //隐藏View
        child.setTranslationX(mWidth);

        ((TextView)child).setText(mTxtRes[0]);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId()==R.id.ele_be_dependent;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float distance=dependency.getTranslationX()/2;
        //向左移动，最大不能超过 4／3个文字的宽度
        if(distance<=-mWidth/3*4){
            mGo=true;
            ((TextView)child).setText(mTxtRes[1]);
            child.setTranslationX(-1/3f*mWidth);
        }else {
            mGo=false;
            ((TextView)child).setText(mTxtRes[0]);
            child.setTranslationX(distance+mWidth);

        }

        return true;
    }

    public void onStopNestedScroll(){
        if(mGo)
            mLoadingView.go();
    }
}
