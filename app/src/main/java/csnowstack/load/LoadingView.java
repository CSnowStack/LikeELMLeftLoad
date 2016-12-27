package csnowstack.load;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cqll on 2016/12/22.
 */

public class LoadingView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mWidth, mHeight;
    private float mTranslation/*依赖的view偏移的大小*/, mLineXLeft, mFraction = 0;//进行的比例
    private OnNoticeGoListener mListener;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        mPath = new Path();
    }

    //在外面直接指定大小了
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;


        mLineXLeft = mWidth;
    }

    public void setListener(OnNoticeGoListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //小于3／4

        mLineXLeft = mWidth + mTranslation;

        mLineXLeft=mLineXLeft>mWidth/4*3?mWidth+mTranslation:mWidth/4*3;

        //代入二节贝塞尔曲线公式，圆弧的顶点最后要在1／2宽度的话，控制点要移动 mWidth/2
        float lineXLeftControl = mLineXLeft - mFraction * mWidth / 2;//最左边控制点的x坐标

        mPath.reset();
        //首先把点移动到右上角
        mPath.moveTo(mWidth, 0);
        //然后画图
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(mLineXLeft, mHeight);
        //画圆弧
        mPath.quadTo(lineXLeftControl, mHeight / 2, mLineXLeft, 0);
        mPath.close();//闭合

        canvas.drawPath(mPath, mPaint);
    }

    //更新View
    public void setFraction(float fraction, float translationX) {
        mFraction = fraction;
        mTranslation = translationX;
        invalidate();
    }

    public void go() {
        if(mListener!=null)
            mListener.go();
    }

    public static interface  OnNoticeGoListener{
        void  go();
    }
}
