package csnowstack.load.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import csnowstack.load.R;

/**
 * Created by cq on 2016/12/27.
 */

public class PullLeftLoadMoreLayout extends CoordinatorLayout {
    private LoadingView mLoadingView;
    private TextView mTxt;
    private View mBeDependent;
    private Context mContext;
    public PullLeftLoadMoreLayout(Context context) {
        super(context);
    }

    public PullLeftLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        if(getChildCount()>1){
            throw new RuntimeException("PullLeftLoadMoreLayout can host only one direct child");
        }

    }



   public void addView(int loadingHeight){


       LayoutParams layoutParamsLoading=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParamsLoading.setBehavior(new LoadingBehavior(loadingHeight));
       mLoadingView=new LoadingView(mContext);
       mLoadingView.setId(R.id.ele_loading);
       addView(mLoadingView,layoutParamsLoading);

       LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParams.setBehavior(new TxtBehavior(mContext));
       mTxt=new TextView(mContext);
       mTxt.setEms(1);
       mTxt.setTextColor(Color.WHITE);
       mTxt.setTextSize(10);
       mTxt.setId(R.id.ele_txt);
       addView(mTxt,layoutParams);


       ((CoordinatorLayout.LayoutParams)getChildAt(0).getLayoutParams()).setBehavior(new BeDependentBehavior(mContext));

   }



   public void setOnGoListener(LoadingView.OnNoticeGoListener onGoListener){
       mLoadingView.setListener(onGoListener);
   }


   public void setFillLoadingColor(@ColorInt int color){
       mLoadingView.setColor(color);
   }
}
