package com.example.chenhan.speech_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button button;
    private Button tab_button;
    private EditText etext;

    private RecognizerDialog mIatDialog;
//    private SharedPreferences mSharedPreferences;

    private com.iflytek.cloud.SpeechRecognizer mIat;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mIat = com.iflytek.cloud.SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);
        mIatDialog = new RecognizerDialog(MainActivity.this, mInitListener);

        SpeechUtility.createUtility(MainActivity.this,"appid=579ae6ac");


    }






    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.button:
                Toast.makeText(MainActivity.this,"请说话",Toast.LENGTH_LONG).show();
                FlowerCollector.onEvent(MainActivity.this, "iat_recognize");

                etext.setText(null);// 清空显示内容
                mIatResults.clear();
                myRecognize();
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
                break;

            case R.id.tab_button:
                Intent intent = new Intent(MainActivity.this,UnderstanderDemo.class);
                startActivity(intent);
            break;
            default :
                break;

        }

    }





    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("MainActivity", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败，错误码：" + code);
                Toast.makeText(MainActivity.this, "init success",Toast.LENGTH_LONG).show();
                Log.d("MainActivity", "onInit: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
        }

    };




    private void init(){

        button = (Button)findViewById(R.id.button);
        tab_button = (Button)findViewById(R.id.tab_button);
        etext = (EditText) findViewById(R.id.text);
        button.setOnClickListener(this);
        tab_button.setOnClickListener(this);
    }




    private void myRecognize()
    {
        Log.d("MainActivity", "onError: qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
//        recognizerDialog =new RecognizerDialog(this,mInitListener);
//        //设置引擎为转写
//        recognizerDialog.setParameter(SpeechConstant.DOMAIN, "iat");
//        //设置识别语言为中文
//        recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//        //设置方言为普通话
//        recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
//        //设置录音采样率为
//        recognizerDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
//        //设置监听对象
//        recognizerDialog.setListener(recognizerDialogListener);
//        //开始识别
//        recognizerDialog.show();

        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS,null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
//        if (lag.equals("en_us")) {
            // 设置语言
//            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
//        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
//            mIat.setParameter(SpeechConstant.ACCENT, lag);
//        }

//        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
//
//        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
//
//        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
//        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

//    private RecognizerDialogListener recognizerDialogListener= new RecognizerDialogListener() {
//        @Override
//        public void onResult(RecognizerResult recognizerResult, boolean b) {
//
//            Log.d("MainActivity", "onResult: "+recognizerResult.getResultString());
//            etext.setText(recognizerResult.getResultString());
//            Log.d("MainActivity", "onError: aaaaaaaaaaaaaaaaaaaaaaaaaa");
//        }
//
//        @Override
//        public void onError(SpeechError speechError) {
//
//            Log.d("MainActivity", "onError: zzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
//
//        }
//
//
//    };


    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            Toast.makeText(MainActivity.this,error.getPlainDescription(true),Toast.LENGTH_LONG).show();
        }

    };


    private void printResult(RecognizerResult results) {
        String text = JasonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        etext.setText(resultBuffer.toString());
        etext.setSelection(text.length());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(MainActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(MainActivity.this);
        super.onPause();
    }

}
