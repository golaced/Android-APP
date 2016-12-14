package com.ano_rc.ano_rc_v1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.ano_rc.ano_rc_v1.R.id.ib_read;

public class ActivitySet extends Activity {
    private boolean load_data=false;
    private CheckBox check_gx;
    private CheckBox check_ax;
    private CheckBox check_hm;
    private CheckBox check_ypr;
    private CheckBox check_gps;
    private CheckBox check_high;

    public Switch switch_gps;
    public Switch switch_high;
    public Switch switch_lockw;
    public Switch switch_ins;
    public Switch switch_imuc;

    private Button button_rx;
    private Button button_tx;

    private final Handler ui_handler = new Handler();
    private final Runnable ui_task = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
        if(load_data==true)
        {load_data=false;

            MainActivity.EN_FIX_GPS=MainActivity.EN_FIX_GPSF;
            MainActivity. EN_FIX_LOCKW=MainActivity.EN_FIX_LOCKWF;
            MainActivity. EN_CONTROL_IMU=MainActivity.EN_CONTROL_IMUF;
            MainActivity. EN_FIX_INS=MainActivity.EN_FIX_INSF;
            MainActivity.EN_FIX_HIGH=MainActivity.EN_FIX_HIGHF;
        }

            if (MainActivity.EN_TX_GX == 1) {
                check_gx.setChecked(true);
            } else {
                check_gx.setChecked(false);
            }
            if (MainActivity.EN_TX_AX == 1) {
                check_ax.setChecked(true);
            } else {
                check_ax.setChecked(false);
            }
            if (MainActivity.EN_TX_HIGH == 1) {
                check_high.setChecked(true);
            } else {
                check_high.setChecked(false);
            }
            if (MainActivity.EN_TX_HM == 1) {
                check_hm.setChecked(true);
            } else {
                check_hm.setChecked(false);
            }
            if (MainActivity.EN_TX_GPS == 1) {
                check_gps.setChecked(true);
            } else {
                check_gps.setChecked(false);
            }
            if (MainActivity.EN_TX_YRP == 1) {
                check_ypr.setChecked(true);
            } else {
                check_ypr.setChecked(false);
            }


            if (MainActivity.EN_FIX_LOCKW == 1) {
                switch_lockw.setChecked(true);
            } else {
                switch_lockw.setChecked(false);
            }
            if (MainActivity.EN_FIX_GPS == 1) {
                switch_gps.setChecked(true);
            } else {
                switch_gps.setChecked(false);
            }
            if (MainActivity.EN_FIX_HIGH == 1) {
                switch_high.setChecked(true);
            } else {
                switch_high.setChecked(false);
            }
            if (MainActivity.EN_FIX_INS == 1) {
                switch_ins.setChecked(true);
            } else {
                switch_ins.setChecked(false);
            }
            if (MainActivity.EN_CONTROL_IMU == 1) {
                switch_imuc.setChecked(true);
            } else {
                switch_imuc.setChecked(false);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
        setContentView(R.layout.activity_set);

        check_gx = (CheckBox) findViewById(R.id.checkBox_gx);
        check_ax = (CheckBox) findViewById(R.id.checkBox_ax);
        check_high = (CheckBox) findViewById(R.id.checkBox_high);
        check_hm = (CheckBox) findViewById(R.id.checkBox_hm);
        check_gps = (CheckBox) findViewById(R.id.checkBox_gps);
        check_ypr = (CheckBox) findViewById(R.id.checkBox_ypr);

        check_gx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_GX = 1;
                } else {
                    MainActivity.EN_TX_GX = 0;
                }
            }

        });

        check_ax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_AX = 1;
                } else {
                    MainActivity.EN_TX_AX = 0;
                }
            }

        });

        check_hm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_HM = 1;
                } else {
                    MainActivity.EN_TX_HM = 0;
                }
            }

        });

        check_high.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_HIGH = 1;
                } else {
                    MainActivity.EN_TX_HIGH = 0;
                }
            }

        });

        check_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_GPS = 1;
                } else {
                    MainActivity.EN_TX_GPS = 0;
                }
            }

        });

        check_ypr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_TX_YRP = 1;
                } else {
                    MainActivity.EN_TX_YRP = 0;
                }
            }

        });
        //---------------------switch-------------------
        switch_gps = (Switch) findViewById(R.id.Switch_gps);
        switch_high = (Switch) findViewById(R.id.Switch_high);
        switch_imuc = (Switch) findViewById(R.id.Switch_imuc);
        switch_ins = (Switch) findViewById(R.id.Switch_ins);
        switch_lockw = (Switch) findViewById(R.id.Switch_lockw);

        switch_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_FIX_GPS = 1;
                } else {
                    MainActivity.EN_FIX_GPS = 0;
                }
            }

        });

        switch_high.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_FIX_HIGH = 1;
                } else {
                    MainActivity.EN_FIX_HIGH = 0;
                }
            }

        });

        switch_ins.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_FIX_INS = 1;
                } else {
                    MainActivity.EN_FIX_INS = 0;
                }
            }

        });

        switch_lockw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_FIX_LOCKW = 1;
                } else {
                    MainActivity.EN_FIX_LOCKW = 0;
                }
            }

        });

        switch_imuc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_CONTROL_IMU = 1;
                } else {
                    MainActivity.EN_CONTROL_IMU = 0;
                }
            }

        });
        //---------------------button-------------------
        Button mbutton = (Button) findViewById(R.id.b_back_gps);
        mbutton = (Button) findViewById(R.id.b_back_gps);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivitySet.this, ActivityControl.class);
                startActivity(intent);
            }
        });


        mbutton = (Button) findViewById(R.id.ib_write);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Send_Setting();
                MainActivity.Send_Command((byte) 0xa2);
                toast_task(1);
            }
        });

        mbutton = (Button) findViewById(ib_read);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                load_data=true;
                toast_task(2);
            }
        });
        ui_handler.postDelayed(ui_task, 100);

        // tv_gpsf_jg.setTextColor(Color.WHITE);
        //  tv_gpsf_wg.setTextColor(Color.YELLOW);
    }//--on creat



    private void toast_task(int sel) {
        Toast toast_wr;
        if(sel==1)
        {toast_wr = Toast.makeText(this, "数据写入飞控", Toast.LENGTH_SHORT);
        toast_wr.show();}
        else if(sel==2)
        {toast_wr = Toast.makeText(this, "读出飞控数据", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==3)
        {toast_wr = Toast.makeText(this, "电机解锁请小心！", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==4)
        {toast_wr = Toast.makeText(this, "电机上锁", Toast.LENGTH_SHORT);
            toast_wr.show();}
    }


}


//-------------
