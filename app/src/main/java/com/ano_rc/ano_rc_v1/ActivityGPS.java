package com.ano_rc.ano_rc_v1;

import java.util.Timer;
import java.util.TimerTask;
import unit.thr_bar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import android.widget.Toast;
public class ActivityGPS extends Activity {
    MapView mMapView = null;
    private Switch switch_sd;
    private TextView tv_ang_rolg;
    private TextView tv_ang_pitg;
    private TextView tv_ang_yawg;
    private TextView tv_highg;
    private TextView tv_gpsf_jg;
    private TextView tv_gpsf_wg;

    private TextView tv_gpsf_mode;
    private TextView tv_gpsf_sv;
    private TextView tv_gpsf_x;
    private TextView tv_gpsf_y;
    private TextView tv_gpsf_ukfx;
    private TextView tv_gpsf_ukfy;
    java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    private Handler handler;
    private final Handler ui_handler = new Handler();
    private final Runnable ui_task = new Runnable() {

        @Override
        public void run() {

            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
            tv_ang_rolg.setText(""+df.format(MainActivity.VAL_ANG_X)+"°");
            tv_ang_pitg.setText(""+df.format(MainActivity.VAL_ANG_Y)+"°");
            tv_ang_yawg.setText(""+df.format(MainActivity.VAL_ANG_Z)+"°");
            tv_highg.setText(""+MainActivity.HIGH_F);
            tv_gpsf_jg.setText(""+MainActivity.GPS_JIN);
            tv_gpsf_wg.setText(""+MainActivity.GPS_WEI);//添加文本
            tv_gpsf_mode.setText(""+(int)(MainActivity.GPS_MODE));
            tv_gpsf_sv.setText(""+(int)(MainActivity.GPS_SV_NUM));//添加文本
            tv_gpsf_x.setText(""+MainActivity.GPS_X);
            tv_gpsf_y.setText(""+MainActivity.GPS_Y);//添加文本  tv_gpsf_jg.setText(""+MainActivity.GPS_JIN);
            tv_gpsf_ukfx.setText(""+MainActivity.GPS_KUFX);//添加文本  tv_gpsf_jg.setText(""+MainActivity.GPS_JIN);
            tv_gpsf_ukfy.setText(""+MainActivity.GPS_UKFY);//添加文本




        }
    };
    @Override
           protected void onCreate(Bundle savedInstanceState) {
               // TODO Auto-generated method stub
               super.onCreate(savedInstanceState);
              // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
               //getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
                //在使用SDK各组件之前初始化context信息，传入ApplicationContext
                //注意该方法要再setContentView方法之前实现
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
                  setContentView(R.layout.activity_gps);
                 //init_map();


                tv_ang_rolg = (TextView)findViewById(R.id.text_val_rolg);
                tv_ang_pitg = (TextView)findViewById(R.id.text_val_pitg);
                tv_ang_yawg = (TextView)findViewById(R.id.text_val_yawg);
                tv_highg = (TextView)findViewById(R.id.text_tv_highg);
                tv_gpsf_jg = (TextView)findViewById(R.id.text_JIN);
                tv_gpsf_wg = (TextView)findViewById(R.id.text_WEI);//对应控件

                tv_gpsf_mode = (TextView)findViewById(R.id.text_gpsmode);
                tv_gpsf_sv = (TextView)findViewById(R.id.text_svnum);//对应控件
                tv_gpsf_x = (TextView)findViewById(R.id.text_x);
                tv_gpsf_y = (TextView)findViewById(R.id.text_y);//对应控件
                tv_gpsf_ukfx = (TextView)findViewById(R.id.text_ukfx);
                tv_gpsf_ukfy = (TextView)findViewById(R.id.text_ukfy);//对应控件

               switch_sd= (Switch)findViewById(R.id.id_sw_sd);

           switch_sd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.Send_Command((byte) 0xa7);//零偏采集
                    //toast_task(2);
                } else {
                    MainActivity.Send_Command((byte) 0xa8);//零偏采集
                    //toast_task(3);
                }
            }
        });
               Button mbutton = (Button) findViewById(R.id.b_back_gps);
               mbutton = (Button)findViewById(R.id.b_back_gps);
               mbutton.setOnClickListener(new OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent();
                       intent.setClass(ActivityGPS.this,ActivityControl.class);
                       startActivity(intent);
                   }
               });

            ui_handler.postDelayed(ui_task, 100);

        }//--on creat

}
//-------------
