package com.itheima.caculatorlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;


/**
 * Created by jiaotangxiaodu on 2017-1-18.
 */

public class CaculatorView extends View {


    private int mMode;//计算器模式

    //自定义属性相关
    private float mTextSize;
    private float mButtonMargin;
    private float mPressDarkRate;//按钮按下后变暗的程度，1表示完全变黑，0表示不变暗


    private DrawerHelper mDrawerHelper;
    private InputHelper mInputHelper;
    private Paint mButtonBackGroundPaint;
    private Paint mButtonTextPaint;


    public CaculatorView(Context context) {
        this(context, null);
    }

    public CaculatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaculatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CaculatorView);
        mMode = typedArray.getInt(R.styleable.CaculatorView_mode, MODE_NORMAL);
        mTextSize = typedArray.getDimension(R.styleable.CaculatorView_textSize, 48);
        mButtonMargin = typedArray.getDimension(R.styleable.CaculatorView_buttonMargin, 4);
        mPressDarkRate = typedArray.getFloat(R.styleable.CaculatorView_pressDarkRate, 0.5f);
        typedArray.recycle();

        mDrawerHelper = new DrawerHelper();
        mInputHelper = new InputHelper(mDrawerHelper);

        mButtonBackGroundPaint = new Paint();
        mButtonTextPaint = new Paint();
        mButtonTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 布局改变的回调，重新调整按钮的大小
     */
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            notifyDrawerLayoutChanged();
            postInvalidate();
        }
    };

    private void notifyDrawerLayoutChanged() {
        mDrawerHelper.setLayout(mMode, getMeasuredWidth(), getMeasuredHeight(), mButtonMargin, mTextSize);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
        }else{
            getViewTreeObserver().removeGlobalOnLayoutListener(mGlobalLayoutListener);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int rowCount = mDrawerHelper.getLayoutInfo().getRow();
        int colCount = mDrawerHelper.getLayoutInfo().getCol();
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {

                DrawerHelper.ButtonInfo buttonInfo = mDrawerHelper.getButtonInfo(row, col);
//                Log.d("CaculatorView" , "row = " + row + ", col =" + col + ",buttonInfo = " + buttonInfo);
                DrawerHelper.Bound bound = mDrawerHelper.getBound(row, col);
                updatePaint(buttonInfo);
                canvas.drawRect(bound.left, bound.top, bound.right, bound.bottom, mButtonBackGroundPaint);
                canvas.drawText(buttonInfo.getText(), (bound.left + bound.right) / 2, (bound.top + bound.bottom) / 2, mButtonTextPaint);
            }
        }
    }

    /**
     * 更新画笔信息
     */
    private void updatePaint(DrawerHelper.ButtonInfo buttonInfo) {
        if (mButtonBackGroundPaint.getColor() != buttonInfo.getBackGroundColor()) {
            mButtonBackGroundPaint.setColor(buttonInfo.getBackGroundColor());
        }
        if (mButtonTextPaint.getColor() != buttonInfo.getTextColor()) {
            mButtonTextPaint.setColor(buttonInfo.getTextColor());
        }
        if (mButtonTextPaint.getTextSize() != buttonInfo.getTextSize()) {
            mButtonTextPaint.setTextSize(buttonInfo.getTextSize());
        }
    }

    DrawerHelper.ButtonInfo mButtonInfoTemp;
    int mButtonColorTemp;
    boolean mReleased = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mReleased = false;
                mButtonInfoTemp = mDrawerHelper.getButtonInfoByPosition(x, y);
                if(mButtonInfoTemp == null){
                    return false;
                }
                mButtonColorTemp = mButtonInfoTemp.getBackGroundColor();
                int red = (int) ((1 - mPressDarkRate) * Color.red(mButtonColorTemp));
                int green = (int) ((1 - mPressDarkRate) * Color.green(mButtonColorTemp));
                int blue = (int) ((1 - mPressDarkRate) * Color.blue(mButtonColorTemp));
                mButtonInfoTemp.setBackGroundColor(Color.rgb(red, green, blue));
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDrawerHelper.getButtonInfoByPosition(x, y) != mButtonInfoTemp && !mReleased) {
                    mButtonInfoTemp.setBackGroundColor(mButtonColorTemp);
                    mButtonInfoTemp = null;
                    mButtonColorTemp = 0;
                    mReleased = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mReleased) {
                    return false;
                }
                input(mButtonInfoTemp.getIndex());
                mButtonInfoTemp.setBackGroundColor(mButtonColorTemp);
                mButtonInfoTemp = null;
                mButtonColorTemp = 0;
                break;
        }
        invalidate();
        return true;
    }

    private void input(int index) {
        mInputHelper.input(index);
    }


    public DrawerHelper.ButtonInfo getButtonInfo(int buttonIndex) {
        return mDrawerHelper.getButtonInfo(buttonIndex);
    }

    public int getButtonIndex(int row, int col) {
        return mDrawerHelper.getButtonIndex(row, col);
    }

    public void setListener(InputHelper.Listener l) {
        this.mInputHelper.setListener(l);
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(int mode) {
        mMode = mode;
        notifyDrawerLayoutChanged();
        invalidate();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        notifyDrawerLayoutChanged();
        invalidate();
    }

    public float getButtonMargin() {
        return mButtonMargin;
    }

    public void setButtonMargin(float buttonMargin) {
        mButtonMargin = buttonMargin;
        notifyDrawerLayoutChanged();
        invalidate();
    }

    public float getPressDarkRate() {
        return mPressDarkRate;
    }

    public void setPressDarkRate(float pressDarkRate) {
        mPressDarkRate = pressDarkRate;
        notifyDrawerLayoutChanged();
        invalidate();
    }

    //计算器模式相关
    public static final int MODE_NORMAL = 0;//普通计算器模式
    public static final int MODE_SCIENCE = 1;//科学计算器模式

    public static final int INDEX_NORMAL_AC = 0x000;
    public static final int INDEX_NORMAL_BACK = 0x001;
    public static final int INDEX_NORMAL_DIVIDE = 0x002;
    public static final int INDEX_NORMAL_MUL = 0x003;
    public static final int INDEX_NORMAL_7 = 0x010;
    public static final int INDEX_NORMAL_8 = 0x011;
    public static final int INDEX_NORMAL_9 = 0x012;
    public static final int INDEX_NORMAL_MINUS = 0x013;
    public static final int INDEX_NORMAL_4 = 0x020;
    public static final int INDEX_NORMAL_5 = 0x021;
    public static final int INDEX_NORMAL_6 = 0x022;
    public static final int INDEX_NORMAL_PLUS = 0x023;
    public static final int INDEX_NORMAL_1 = 0x030;
    public static final int INDEX_NORMAL_2 = 0x031;
    public static final int INDEX_NORMAL_3 = 0x032;
    public static final int INDEX_NORMAL_0 = 0x033;
    public static final int INDEX_NORMAL_LEFT = 0x040;
    public static final int INDEX_NORMAL_RIGHT = 0x041;
    public static final int INDEX_NORMAL_DOT = 0x042;
    public static final int INDEX_NORMAL_EQUAL = 0x043;

    public static final int INDEX_SCIENCE_FAC = 0X100;
    public static final int INDEX_SCIENCE_POW = 0X101;
    public static final int INDEX_SCIENCE_SQRT = 0X102;
    public static final int INDEX_SCIENCE_PI = 0X103;
    public static final int INDEX_SCIENCE_CLEAR = 0X104;
    public static final int INDEX_SCIENCE_SIN = 0X110;
    public static final int INDEX_SCIENCE_LEFT = 0X111;
    public static final int INDEX_SCIENCE_RIGHT = 0X112;
    public static final int INDEX_SCIENCE_E = 0X113;
    public static final int INDEX_SCIENCE_BACK = 0X114;
    public static final int INDEX_SCIENCE_COS = 0X120;
    public static final int INDEX_SCIENCE_7 = 0X121;
    public static final int INDEX_SCIENCE_8 = 0X122;
    public static final int INDEX_SCIENCE_9 = 0X123;
    public static final int INDEX_SCIENCE_DIV = 0X124;
    public static final int INDEX_SCIENCE_TAN = 0X130;
    public static final int INDEX_SCIENCE_4 = 0X131;
    public static final int INDEX_SCIENCE_5 = 0X132;
    public static final int INDEX_SCIENCE_6 = 0X133;
    public static final int INDEX_SCIENCE_MUL = 0X134;
    public static final int INDEX_SCIENCE_LN = 0X140;
    public static final int INDEX_SCIENCE_1 = 0X141;
    public static final int INDEX_SCIENCE_2 = 0X142;
    public static final int INDEX_SCIENCE_3 = 0X143;
    public static final int INDEX_SCIENCE_MIN = 0X144;
    public static final int INDEX_SCIENCE_LG = 0X150;
    public static final int INDEX_SCIENCE_0 = 0X151;
    public static final int INDEX_SCIENCE_DOT = 0X152;
    public static final int INDEX_SCIENCE_EQUAL = 0X153;
    public static final int INDEX_SCIENCE_PLUS = 0X154;
}
