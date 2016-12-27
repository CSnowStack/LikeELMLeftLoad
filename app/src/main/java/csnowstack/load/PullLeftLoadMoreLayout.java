package csnowstack.load;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }



   public void addView(int imgHeight){
       LayoutParams layoutParamsBeDependent=new LayoutParams(1, 1);
       layoutParamsBeDependent.setBehavior(new BeDependentBehavior(mContext));
       mBeDependent =new View(mContext);
       mBeDependent.setId(R.id.ele_be_dependent);
       addView(mBeDependent,layoutParamsBeDependent);


       LayoutParams layoutParamsLoading=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParamsLoading.setBehavior(new LoadingBehavior(imgHeight));
       mLoadingView=new LoadingView(mContext);
       mLoadingView.setId(R.id.ele_loading);
       addView(mLoadingView,layoutParamsLoading);

       LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParams.setBehavior(new TxtBehavior(mContext));
       mTxt=new TextView(mContext,null);
       mTxt.setEms(1);
       mTxt.setTextColor(Color.WHITE);
       mTxt.setText("查看更多");
       mTxt.setTextSize(10);
       mTxt.setId(R.id.ele_txt);
       addView(mTxt,layoutParams);


       ((CoordinatorLayout.LayoutParams)getChildAt(0).getLayoutParams()).setBehavior(new RcvBehavior());
   }




}
