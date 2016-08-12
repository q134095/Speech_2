package com.example.chenhan.speech_2.setting;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Window;

import com.example.chenhan.speech_2.R;
import com.iflytek.sunflower.FlowerCollector;

import util.SettingTextWatcher;

/**
 * Created by Chen Han on 2016/8/4.
 */
public class UnderstanderSetting extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    private static final String TAG = UnderstanderSetting.class.getSimpleName();
    public  static final String PREFER_NAME = "com.iflytek.setting";
    private EditTextPreference mVadbosPreference;
    private EditTextPreference mVadeosPreference;

    public void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(PREFER_NAME);
        addPreferencesFromResource(R.xml.understand_setting);

        mVadbosPreference = (EditTextPreference)findPreference("understander_vadbos_preference");
        mVadbosPreference.getEditText().addTextChangedListener(new SettingTextWatcher(
                UnderstanderSetting.this,mVadbosPreference,0,10000));

        mVadeosPreference = (EditTextPreference)findPreference("understander_vadeos_preference");
        mVadeosPreference.getEditText().addTextChangedListener(new SettingTextWatcher(
                UnderstanderSetting.this,mVadeosPreference,0,10000));



    }


    @Override
    protected void onPause() {
            // 开放统计 移动数据统计分析
            FlowerCollector.onPageEnd(TAG);
            FlowerCollector.onPause(UnderstanderSetting.this);
            super.onPause();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(UnderstanderSetting.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return true;
    }
}
