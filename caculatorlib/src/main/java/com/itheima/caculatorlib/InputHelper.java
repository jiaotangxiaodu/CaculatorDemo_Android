package com.itheima.caculatorlib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaotangxiaodu on 2017-1-19.
 */

public class InputHelper {

    private final DrawerHelper mDrawerHelper;
    List<String> mInputs;
    StringBuffer mExpression;
    private Listener mListener;

    public InputHelper(DrawerHelper drawerHelper) {
        this.mDrawerHelper = drawerHelper;
        mInputs = new ArrayList<>();
        mExpression = new StringBuffer();
    }

    public void input(int index) {
        switch (index) {
            case CaculatorView.INDEX_NORMAL_AC:
            case CaculatorView.INDEX_SCIENCE_CLEAR:
                performClear();
                break;
            case CaculatorView.INDEX_NORMAL_BACK:
            case CaculatorView.INDEX_SCIENCE_BACK:
                performBack();
                break;
            case CaculatorView.INDEX_NORMAL_EQUAL:
            case CaculatorView.INDEX_SCIENCE_EQUAL:
                performEqual();
                break;
            case CaculatorView.INDEX_SCIENCE_SIN:
            case CaculatorView.INDEX_SCIENCE_COS:
            case CaculatorView.INDEX_SCIENCE_LN:
            case CaculatorView.INDEX_SCIENCE_LG:
                performDefaultInput(index);
                performDefaultInput(CaculatorView.INDEX_SCIENCE_LEFT);
                break;
            default:
                performDefaultInput(index);
        }
    }



    private void performDefaultInput(int index) {
        String inputText = mDrawerHelper.getButtonInfo(index).getText();
        mInputs.add(inputText);
        notifyExpressionChanged();
    }

    private void notifyExpressionChanged() {
        mExpression.delete(0, mExpression.length());
        for (String inputText : mInputs) {
            mExpression.append(inputText);
        }
        if (mListener != null) {
            mListener.onExpressionChanged(mExpression.toString());
        }
    }


    private void performEqual() {
        String result = null;
        try {
            result = CaculatorUtil.getResult(mInputs);
        } catch (CaculatorUtil.MalformExpressionException e) {
            e.printStackTrace();
            result = "undifined";
        }
        if (mListener != null) {
            mListener.onGetResult(mExpression.toString(), result);
        }
    }

    private void performBack() {
        if (mInputs.size() > 0) {
            mInputs.remove(mInputs.size() - 1);
            notifyExpressionChanged();
        }
    }

    private void performClear() {
        mInputs.clear();
        notifyExpressionChanged();
        if (mListener != null) {
            mListener.onGetResult("", "");
        }
    }

    public void setListener(Listener l) {
        this.mListener = l;
    }


    public interface Listener {
        void onExpressionChanged(String expression);

        void onGetResult(String expression, String result);
    }

}
