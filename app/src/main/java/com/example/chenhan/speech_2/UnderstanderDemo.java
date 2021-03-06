package com.example.chenhan.speech_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenhan.speech_2.setting.UnderstanderSetting;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Chen Han on 2016/7/31.
 */
public class UnderstanderDemo extends Activity implements View.OnClickListener {

    private static String TAG = UnderstanderDemo.class.getSimpleName();
    // 语义理解对象（语音到语义）。
    private SpeechUnderstander mSpeechUnderstander;
    // 语义理解对象（文本到语义）。
    private TextUnderstander mTextUnderstander;
    private Toast mToast;
    private EditText mUnderstanderText;

    String name;
    String code;
    MainActivity mainActivity;

    public static String service = null ,operation = null;

    private SharedPreferences mSharedPreferences;
    @SuppressLint("ShowToast")

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.understander);


        initLayout();

        // 初始化对象
        mSpeechUnderstander = SpeechUnderstander.createUnderstander(UnderstanderDemo.this, mSpeechUdrInitListener);
        mTextUnderstander = TextUnderstander.createTextUnderstander(UnderstanderDemo.this, mTextUdrInitListener);

        mToast = Toast.makeText(UnderstanderDemo.this, "", Toast.LENGTH_SHORT);

    }

    /**
     * 初始化Layout。
     */
    private void initLayout(){

        findViewById(R.id.ubutton).setOnClickListener(UnderstanderDemo.this);
        findViewById(R.id.setting).setOnClickListener(UnderstanderDemo.this);
        mUnderstanderText = (EditText)findViewById(R.id.understander_text);


        mSharedPreferences = getSharedPreferences(UnderstanderSetting.PREFER_NAME, Activity.MODE_PRIVATE);
    }


    /**
     * 初始化监听器（语音到语义）。
     */
    private InitListener mSpeechUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "speechUnderstanderListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code);
            }
        }
    };

    /**
     * 初始化监听器（文本到语义）。
     */
    private InitListener mTextUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "textUnderstanderListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code);
            }
        }
    };


    int ret = 0;// 函数调用返回值
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ubutton:
            mUnderstanderText.setText("");
            // 设置参数
            setParam();

            if (mSpeechUnderstander.isUnderstanding()) {// 开始前检查状态
                mSpeechUnderstander.stopUnderstanding();
                showTip("停止录音");
            } else {
                ret = mSpeechUnderstander.startUnderstanding(mSpeechUnderstanderListener);
                if (ret != 0) {
                    showTip("语义理解失败,错误码:" + ret);
                } else {
                    showTip("请开始说话");
                }
            }
            break;

            case R.id.setting:
                Intent intent = new Intent(UnderstanderDemo.this, UnderstanderSetting.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                // 显示
                String text = result.getResultString();

                if (!TextUtils.isEmpty(text)) {
                   mUnderstanderText.setText(text);
                }
            } else {
                Log.d(TAG, "understander result:null");
                showTip("识别结果不正确。");
            }
        }

        @Override
        public void onError(SpeechError error) {
            // 文本语义不能使用回调错误码14002，请确认您下载sdk时是否勾选语义场景和私有语义的发布
            showTip("onError Code："	+ error.getErrorCode());

        }
    };

    private void parseJson (String jsonData){

//        try {
//            JSONTokener jsonParser = new JSONTokener(jsonData);
//
//                JSONObject jsonObject = (JSONObject)jsonParser.nextValue();
//                JSONObject jsonObject1= jsonObject.getJSONObject("answer");
//
//                JSONArray jsonArray = new JSONArray( jsonObject1.getString("text"));
//            for(int i = 0;i < jsonArray.length();i++){
//                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                String json_text = jsonObject2.getString("text");
//                mUnderstanderText.setText(json_text);
//////////////////////////////////////////////////////////////////////////////////////

//            JSONTokener jsonParser = new JSONTokener(jsonData);
//            JSONObject jsonObject = (JSONObject)jsonParser.nextValue();
//            String json1 =   jsonObject.getString("answer");

            //8.4
//            operation = jsonObject.optString("operation");
//            service = jsonObject.optString("service");
//            Log.d(TAG, "parseJson: "+operation+service);
            //8.4

//            JSONTokener jsonParser1 = new JSONTokener(json1);
//            JSONObject jsonObject1 = (JSONObject)jsonParser1.nextValue();
//            String json_text = jsonObject1.getString("text");
//            mUnderstanderText.setText(json_text);
//                Log.d(TAG, "parseJson: "+json_text);

//            mUnderstanderText.setText(jsonData);


//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            JSONObject jb = new JSONObject(jsonData);
            operation = jb.optString("operation");
            service = jb.optString("service");

            if(service.equals("telephone")&&operation.equals("CALL")){

                CallAction callAction=new CallAction(name,code,mainActivity);//目前可根据名字或电话号码拨打电话
                callAction.start();
                mUnderstanderText.setText(jsonData);

            }
            else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 语义理解回调。
     */
    private SpeechUnderstanderListener mSpeechUnderstanderListener = new SpeechUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                Log.d(TAG, result.getResultString());

                // 显示
                String text = result.getResultString();
                parseJson(text);
                if (!TextUtils.isEmpty(text)) {
//                    mUnderstanderText.setText(text);
                }
            } else {
                showTip("识别结果不正确。");
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, data.length+"");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    public void setParam(){
        String lang = mSharedPreferences.getString("understander_language_preference", "mandarin");
        if (lang.equals("en_us")) {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
        }else {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lang);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("understander_vadbos_preference", "4000"));
//
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("understander_vadeos_preference", "1000"));
//
        // 设置标点符号，默认：1（有标点）
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("understander_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/sud.wav");
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(UnderstanderDemo.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(UnderstanderDemo.this);
        super.onPause();
    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
