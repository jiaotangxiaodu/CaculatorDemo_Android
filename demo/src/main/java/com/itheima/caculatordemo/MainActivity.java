package com.itheima.caculatordemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.caculatorlib.CaculatorView;
import com.itheima.caculatorlib.InputHelper;

public class MainActivity extends Activity implements InputHelper.Listener {


    private CheckBox mCbScience;
    private SeekBar mSbTextSize;
    private SeekBar mSbMargin;
    private SeekBar mSbDarkRate;
    private ImageView mIvSetting;
    private TextView mTvInput;
    private TextView mTvResult;
    private CaculatorView mCv;
    private View mLlSetting;
    private ScrollView mSv;
    private TextWatcher mWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();

    }




    private void initView() {
        mCbScience = (CheckBox) findViewById(R.id.cb_science_mode);
        mSbTextSize = (SeekBar) findViewById(R.id.sb_textsize);
        mSbMargin = (SeekBar) findViewById(R.id.sb_margin);
        mSbDarkRate = (SeekBar) findViewById(R.id.sb_darkrate);
        mIvSetting = (ImageView) findViewById(R.id.iv_setting);
        mTvInput = (TextView) findViewById(R.id.tv_input);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mCv = (CaculatorView) findViewById(R.id.cv);
        mLlSetting = findViewById(R.id.ll_setting);
        mSv = (ScrollView) findViewById(R.id.sv);
    }

    private void initData() {
        mSbTextSize.setProgress((int) mCv.getTextSize());
        mSbMargin.setProgress((int) (mCv.getButtonMargin() * 10));
        mSbDarkRate.setProgress((int) (mCv.getPressDarkRate() * 100));
    }

    private void initEvent() {


        mCbScience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCv.setMode(isChecked?CaculatorView.MODE_SCIENCE:CaculatorView.MODE_NORMAL);
            }
        });

        mSbTextSize.setOnSeekBarChangeListener(new SimpleOnSeekChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCv.setTextSize(progress);
            }
        });

        mSbMargin.setOnSeekBarChangeListener(new SimpleOnSeekChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCv.setButtonMargin(progress/10);
            }
        });

        mSbDarkRate.setOnSeekBarChangeListener(new SimpleOnSeekChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCv.setPressDarkRate(progress/100.0f);
            }
        });

        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlSetting.setVisibility(mLlSetting.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            }
        });

        mCv.setListener(this);


        mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        };
        mTvInput.addTextChangedListener(mWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTvInput.removeTextChangedListener(mWatcher);
    }

    @Override
    public void onExpressionChanged(String expression) {
        mTvInput.setText(expression);
    }

    @Override
    public void onGetResult(String expression, String result) {
        mTvResult.setText(result);
    }




    static abstract class SimpleOnSeekChangeListener implements SeekBar.OnSeekBarChangeListener {


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
