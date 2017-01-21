package com.itheima.caculatorlib;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jiaotangxiaodu on 2017-1-18.
 */

public class DrawerHelper {

    public static final int COLOR_ORANGE = 0xFFFF6600;

    private LayoutInfo mLayoutInfo;

    private int mMode;
    private float mButtonMargin;
    private float mTextSize;

    private Map<Integer, ButtonInfo> mButtonInfos;

    private Bound mBound;



    public void initButtonsInfo() {
        if (mButtonInfos != null) {
            mButtonInfos.clear();
        } else {
            mButtonInfos = new HashMap<>();
        }
        switch (mMode) {
            case CaculatorView.MODE_NORMAL:
                initNormalButtonInfos();
                break;
            case CaculatorView.MODE_SCIENCE:
                initScienceButtonInfos();
                break;
        }
    }

    private void initNormalButtonInfos() {
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_AC,     new ButtonInfo(Color.WHITE,  "AC", mTextSize, COLOR_ORANGE, CaculatorView.INDEX_NORMAL_AC));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_BACK,   new ButtonInfo(Color.WHITE,  "←",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_BACK));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_DIVIDE, new ButtonInfo(Color.WHITE,  "÷",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_DIVIDE));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_MUL,    new ButtonInfo(Color.WHITE,  "×",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_MUL));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_7,      new ButtonInfo(Color.WHITE,  "7",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_7));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_8,      new ButtonInfo(Color.WHITE,  "8",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_8));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_9,      new ButtonInfo(Color.WHITE,  "9",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_9));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_MINUS,  new ButtonInfo(Color.WHITE,  "－", mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_MINUS));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_4,      new ButtonInfo(Color.WHITE,  "4",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_4));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_5,      new ButtonInfo(Color.WHITE,  "5",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_5));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_6,      new ButtonInfo(Color.WHITE,  "6",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_6));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_PLUS,   new ButtonInfo(Color.WHITE,  "＋", mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_PLUS));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_1,      new ButtonInfo(Color.WHITE,  "1",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_1));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_2,      new ButtonInfo(Color.WHITE,  "2",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_2));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_3,      new ButtonInfo(Color.WHITE,  "3",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_3));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_0,      new ButtonInfo(Color.WHITE,  "0",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_0));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_LEFT,   new ButtonInfo(Color.WHITE,  "(",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_LEFT));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_RIGHT,  new ButtonInfo(Color.WHITE,  ")",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_RIGHT));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_DOT,    new ButtonInfo(Color.WHITE,  ".",  mTextSize, Color.BLACK, CaculatorView.INDEX_NORMAL_DOT));
        mButtonInfos.put(CaculatorView.INDEX_NORMAL_EQUAL,  new ButtonInfo(COLOR_ORANGE, "＝", mTextSize, Color.WHITE, CaculatorView.INDEX_NORMAL_EQUAL));
    }

    private void initScienceButtonInfos() {
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_FAC  , new ButtonInfo(Color.WHITE,  "!"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_FAC  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_POW  , new ButtonInfo(Color.WHITE,  "^"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_POW  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_SQRT, new ButtonInfo(Color.WHITE,  "√"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_SQRT));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_PI   , new ButtonInfo(Color.WHITE,  "π"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_PI   ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_CLEAR, new ButtonInfo(Color.WHITE,  "C"  , mTextSize, COLOR_ORANGE,CaculatorView.INDEX_SCIENCE_CLEAR));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_SIN  , new ButtonInfo(Color.WHITE,  "sin", mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_SIN  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_LEFT , new ButtonInfo(Color.WHITE,  "("  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_LEFT ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_RIGHT, new ButtonInfo(Color.WHITE,  ")"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_RIGHT));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_E    , new ButtonInfo(Color.WHITE,  "e"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_E    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_BACK , new ButtonInfo(Color.WHITE,  "←"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_BACK ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_COS  , new ButtonInfo(Color.WHITE,  "cos", mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_COS  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_7    , new ButtonInfo(Color.WHITE,  "7"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_7    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_8    , new ButtonInfo(Color.WHITE,  "8"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_8    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_9    , new ButtonInfo(Color.WHITE,  "9"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_9    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_DIV  , new ButtonInfo(Color.WHITE,  "÷"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_DIV  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_TAN  , new ButtonInfo(Color.WHITE,  "tan", mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_TAN  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_4    , new ButtonInfo(Color.WHITE,  "4"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_4    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_5    , new ButtonInfo(Color.WHITE,  "5"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_5    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_6    , new ButtonInfo(Color.WHITE,  "6"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_6    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_MUL  , new ButtonInfo(Color.WHITE,  "×"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_MUL  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_LN   , new ButtonInfo(Color.WHITE,  "ln" , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_LN   ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_1    , new ButtonInfo(Color.WHITE,  "1"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_1    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_2    , new ButtonInfo(Color.WHITE,  "2"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_2    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_3    , new ButtonInfo(Color.WHITE,  "3"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_3    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_MIN  , new ButtonInfo(Color.WHITE,  "－" , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_MIN  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_LG   , new ButtonInfo(Color.WHITE,  "lg" , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_LG   ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_0    , new ButtonInfo(Color.WHITE,  "0"  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_0    ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_DOT  , new ButtonInfo(Color.WHITE,  "."  , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_DOT  ));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_EQUAL, new ButtonInfo(COLOR_ORANGE, "＝" , mTextSize, Color.WHITE, CaculatorView.INDEX_SCIENCE_EQUAL));
        mButtonInfos.put(CaculatorView.INDEX_SCIENCE_PLUS ,   new ButtonInfo(Color.WHITE,"＋" , mTextSize, Color.BLACK, CaculatorView.INDEX_SCIENCE_PLUS ));
    }

    public void setLayout(int mode, int measuredWidth, int measuredHeight, float buttonMargin, float textSize) {
        mLayoutInfo = new LayoutInfo();
        this.mTextSize = textSize;
        this.mButtonMargin = buttonMargin;
        this.mMode = mode;
        mLayoutInfo.mWidth = measuredWidth;
        mLayoutInfo.mHeight = measuredHeight;
        initButtonsInfo();
        updateLayoutInfo();
    }

    private void updateLayoutInfo() {
        switch (mMode) {
            case CaculatorView.MODE_NORMAL:
                mLayoutInfo.mRow = 5;
                mLayoutInfo.mCol = 4;
                break;
            case CaculatorView.MODE_SCIENCE:
                mLayoutInfo.mRow = 6;
                mLayoutInfo.mCol = 5;
                break;
        }
        mLayoutInfo.mPerWidth = (getLayoutInfo().getWidth() - (getLayoutInfo().getCol() + 1) * mButtonMargin) / getLayoutInfo().getCol();
        mLayoutInfo.mPerHeight = (getLayoutInfo().getHeight() - (getLayoutInfo().getRow() + 1) * mButtonMargin) / getLayoutInfo().getRow();
    }

    public LayoutInfo getLayoutInfo() {
        return mLayoutInfo;
    }


    public ButtonInfo getButtonInfo(int buttonIndex) {
        return mButtonInfos.get(buttonIndex);
    }


    public ButtonInfo getButtonInfo(int row, int col) {
        return getButtonInfo(getButtonIndex(row, col));
    }

    /**
     * 三位十六进制表示，第一位0代表MODE_NORMAL，1代表MODE_SCIENCE
     * 第二位表示第几行（row）
     * 第三位表示第几列（col）
     *
     * @param row
     * @param col
     * @return
     */
    public int getButtonIndex(int row, int col) {
        return (mMode << 8) + (row << 4) + col;
    }

    public Bound getBound(int row, int col) {
        Bound bound = obtainBound();
        bound.left = col * getLayoutInfo().getPerWidth() + (col + 1) * mButtonMargin;
        bound.top = row * getLayoutInfo().getPerHeight() + (row + 1) * mButtonMargin;
        bound.right = bound.left + getLayoutInfo().getPerWidth();
        bound.bottom = bound.top + getLayoutInfo().getPerHeight();
        return bound;
    }

    public Bound obtainBound() {
        if (mBound == null) {
            mBound = new Bound();
        }
        return mBound;
    }

    public ButtonInfo getButtonInfoByPosition(float x, float y) {
        int col = (int) (x / (getLayoutInfo().getPerWidth() + mButtonMargin));
        col = col > getLayoutInfo().getCol() ? getLayoutInfo().getCol() : col;
        col = col < 0 ? 0 : col;

        int row = (int) (y / (getLayoutInfo().getPerHeight() + mButtonMargin));
        row = row > getLayoutInfo().getRow() ? getLayoutInfo().getRow() : row;
        row = row < 0 ? 0 : row;

        return getButtonInfo(row, col);
    }

    public static class LayoutInfo {

        private int mRow;
        private int mCol;

        private float mPerWidth;
        private float mPerHeight;

        private int mWidth;
        private int mHeight;

        public int getRow() {
            return mRow;
        }

        public int getCol() {
            return mCol;
        }

        public float getPerWidth() {
            return mPerWidth;
        }
        public float getPerHeight() {
            return mPerHeight;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getHeight() {
            return mHeight;
        }
    }

    public static class ButtonInfo {

        public ButtonInfo(int backGround, String text, float textSize, int textColor, int index) {
            mBackGround = backGround;
            mText = text;
            mTextSize = textSize;
            mTextColor = textColor;
            this.mIndex = index;
        }

        private int mBackGround;
        private String mText;
        private float mTextSize;
        private int mTextColor;
        private int mIndex;

        public int getBackGroundColor() {
            return mBackGround;
        }

        public void setBackGroundColor(int backGround) {
            mBackGround = backGround;
        }

        public String getText() {
            return mText;
        }

        public void setText(String text) {
            mText = text;
        }

        public float getTextSize() {
            return mTextSize;
        }

        public void setTextSize(float textSize) {
            mTextSize = textSize;
        }

        public int getTextColor() {
            return mTextColor;
        }

        public void setTextColor(int textColor) {
            mTextColor = textColor;
        }

        public int getIndex() {
            return mIndex;
        }

        @Override
        public String toString() {
            return "ButtonInfo{" +
                    "mText='" + mText + '\'' +
                    ", mIndex=" + mIndex +
                    '}';
        }
    }

    public static class Bound {
        float left;
        float top;
        float right;
        float bottom;
    }


}
