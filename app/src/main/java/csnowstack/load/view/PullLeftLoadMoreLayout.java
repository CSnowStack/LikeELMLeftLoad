package csnowstack.load.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import csnowstack.load.R;

/**
 * Created by cq on 2016/12/27.
 */

public class PullLeftLoadMoreLayout extends CoordinatorLayout {
    private LoadingView mLoadingView;
    private View mList;
    private TextView mTxt;
    private Context mContext;
    private int mTxtWidth,mLoadingHeight,mTranslation;
    private String [] mTxtRes;
    private boolean mInAnimator=false,mGo=false/*标记是否需要跳转*/;
    private ValueAnimator mValueAnimator;
    private OnNoticeGoListener mOnNoticeGoListener;
    public PullLeftLoadMoreLayout(Context context) {
        super(context);
    }

    public PullLeftLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        if(getChildCount()>1){
            throw new RuntimeException("PullLeftLoadMoreLayout can host only one direct child");
        }

        mTxtRes=new String[]{context.getString(R.string.txt_normal),context.getString(R.string.txt_chage)};

        mValueAnimator= ValueAnimator.ofFloat(0,1);
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
                mTranslation=0;
                mValueAnimator.removeAllUpdateListeners();
            }
        });

    }





   public void addView(int loadingHeight){
       mLoadingHeight=loadingHeight;
       LayoutParams layoutParamsLoading=new LayoutParams(loadingHeight,loadingHeight);
       mLoadingView=new LoadingView(mContext);
       mLoadingView.setId(R.id.ele_loading);
       addView(mLoadingView,layoutParamsLoading);

       LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       mTxt=new TextView(mContext);
       mTxt.setEms(1);
       mTxt.setTextColor(Color.WHITE);
       mTxt.setTextSize(10);
       mTxt.setId(R.id.ele_txt);
       addView(mTxt,layoutParams);

       mList=getChildAt(0);

   }


    @Override
    public void onLayoutChild(View child, int layoutDirection) {
        super.onLayoutChild(child,layoutDirection);
        if(child==mTxt){
            mTxtWidth=child.getWidth();
            //移动到最右边
            child.offsetLeftAndRight(getWidth()-mTxtWidth);
            //居中
            child.offsetTopAndBottom(mLoadingHeight/2-child.getHeight()/2);
            //隐藏View
            child.setTranslationX(mTxtWidth);

            ((TextView)child).setText(mTxtRes[0]);
        }else if(child==mLoadingView){
            child.offsetLeftAndRight(getWidth()-child.getWidth());//移动到最右边
        }
    }



    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_HORIZONTAL) != 0 && !mInAnimator;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        if (mList.getWidth()==getWidth() && dx>0&& !ViewCompat.canScrollHorizontally(mList,ViewCompat.SCROLL_INDICATOR_LEFT) || dx<0&&mList.getTranslationX()<0){
            consumed[0]=dx;//全部消耗掉
            int distance =dx/2;

            if(mTranslation-distance<=-mLoadingHeight){
                mTranslation=-mLoadingHeight;
            }else {
                mTranslation= mTranslation-distance;
            }

            //列表移动
            mList.setTranslationX(mTranslation);

            //txt移动
            translationTxt(mTranslation);

            //load改变
            invalidateLoading(mTranslation);


        }else {
            consumed[0]=0;//全部分配给列表
        }
    }


    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        if(mTranslation!=0){
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translation=(1-animation.getAnimatedFraction())*mTranslation;
                    mList.setTranslationX(translation);
                    translationTxt(translation);
                    invalidateLoading(translation);

                }
            });

            mValueAnimator.start();
            if(mGo&&mOnNoticeGoListener!=null){
                mOnNoticeGoListener.go();
            }
        }

    }

    private void translationTxt(float translationList) {
        float distance=translationList/2;
        if(distance<=-mTxtWidth/3*4){
            mGo=true;
            mTxt.setText(mTxtRes[1]);
            mTxt.setTranslationX(-1/3f*mTxtWidth);
        }else {
            mGo=false;
            mTxt.setText(mTxtRes[0]);
            mTxt.setTranslationX(distance+mTxtWidth);

        }
    }


    private void invalidateLoading(float translationList) {
        Log.e("-->>","translationList   "+translationList);
        Log.e("-->>","mLoadingHeight   "+mLoadingHeight);
        //列表移动的比例
        float fraction=Math.abs(translationList)/mLoadingHeight;
        //改变view
        mLoadingView.setFraction(fraction,translationList/2);
    }


    public void setOnGoListener(OnNoticeGoListener onGoListener){
       mOnNoticeGoListener=onGoListener;
   }

    public static interface  OnNoticeGoListener{
        void  go();
    }

   public void setFillLoadingColor(@ColorInt int color){
       mLoadingView.setColor(color);
   }
}
