package com.ano_rc.ano_rc_v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.ano_rc.ano_rc_v1.R.id.ib_read;

public class ActivityPID extends Activity {
    String s;
    private boolean load_data=false;
    private boolean init_flag=false;
    private EditText ed_spid_op;
    private EditText ed_spid_oi;
    private EditText ed_spid_od;
    private EditText ed_spid_ip;
    private EditText ed_spid_ii;
    private EditText ed_spid_id;
    private EditText ed_spid_yp;
    private EditText ed_spid_yi;
    private EditText ed_spid_yd;
    private EditText ed_hp_p;
    private EditText ed_hp_i;
    private EditText ed_hp_d;
    private TextView pit;
    private TextView rol;
    private TextView yaw;
    private  TextView thr;
    private TextView fix_p;
    private  TextView fix_r;
    private ProgressBar bar_thr;

    private CheckBox check_enspid;
    private ImageView lock;
    java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    java.text.DecimalFormat df2=new java.text.DecimalFormat("#.##");
    private SeekBar SeekBar_mx;
    private SeekBar SeekBar_my;
    private TextView mx;
    private TextView my;

    private Button clr_pid;
    private Button clr_m;
    private Button to_d1_pid;
    private Button to_d2_pid;
    private final Handler ui_handler = new Handler();
    private final Runnable ui_task = new Runnable() {

        @Override
        public void run() {

            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
if(init_flag==false) {
    init_flag = true;
    s = String.valueOf(MainActivity.SH_SPID_OP);
    ed_spid_op.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_OI);
    ed_spid_oi.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_OD);
    ed_spid_od.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_IP);
    ed_spid_ip.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_II);
    ed_spid_ii.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_ID);
    ed_spid_id.setText(s);
    s = String.valueOf(MainActivity.SH_HPID_OP);
    ed_hp_p.setText(s);
    s = String.valueOf(MainActivity.SH_HPID_OI);
    ed_hp_i.setText(s);
    s = String.valueOf(MainActivity.SH_HPID_OD);
    ed_hp_d.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_YP);
    ed_spid_yp.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_YI);
    ed_spid_yi.setText(s);
    s = String.valueOf(MainActivity.SH_SPID_YD);
    ed_spid_yd.setText(s);


    }
            if (MainActivity.LOCK == 1) {
                lock.setBackgroundResource(android.R.drawable.ic_lock_idle_lock);
            } else {
                lock.setBackgroundResource(android.R.drawable.ic_partial_secure);
            }

            pit.setText(""+df.format(MainActivity.VAL_ANG_X)+"°");
            rol.setText(""+df.format(MainActivity.VAL_ANG_Y)+"°");
            yaw.setText(""+df.format(MainActivity.VAL_ANG_Z)+"°");
            thr.setText("" + (int)(MainActivity.YOUMEN/(float)MainActivity.MAX_YOUMEN*100)+"%");

            mx.setText("当前值:" +df.format(MainActivity.MX)+"°");
            my.setText("当前值:" +df.format(MainActivity.MY)+"°");

            fix_p.setText("当前值:" +df2.format(MainActivity.FIX_PIT)+"°");
            fix_r.setText("当前值:" +df2.format(MainActivity.FIX_ROL)+"°");
          //  bar_thr.setProgress((int)(MainActivity.YOUMEN/800.f*100));
          //  bar_thr.setSecondaryProgress((int)(MainActivity.YOUMEN/(float)MainActivity.MAX_YOUMEN*100));
        }
    };
    @Override
           protected void onCreate(Bundle savedInstanceState) {
               // TODO Auto-generated method stub
               super.onCreate(savedInstanceState);
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
               setContentView(R.layout.activity_pid);

        ed_spid_op= (EditText) findViewById(R.id.i_spid_op);
        ed_spid_oi= (EditText) findViewById(R.id.i_spid_oi);
        ed_spid_od= (EditText) findViewById(R.id.i_spid_od);
        ed_spid_ip= (EditText) findViewById(R.id.i_spid_ip);
        ed_spid_ii= (EditText) findViewById(R.id.i_spid_ii);
        ed_spid_id= (EditText) findViewById(R.id.i_spid_id);
        ed_spid_yp= (EditText) findViewById(R.id.i_spid_yp);
        ed_spid_yi= (EditText) findViewById(R.id.i_spid_yi);
        ed_spid_yd= (EditText) findViewById(R.id.i_spid_yd);

        ed_hp_p= (EditText) findViewById(R.id.i_hpid_p);
        ed_hp_i= (EditText) findViewById(R.id.i_hpid_i);
        ed_hp_d= (EditText) findViewById(R.id.i_hpid_d);

        pit = (TextView)findViewById(R.id.i_pit_pid);
        rol = (TextView)findViewById(R.id.i_rol_pid);
        yaw = (TextView)findViewById(R.id.i_yaw_pid);
        lock=  (ImageView)findViewById(R.id.i_lock_pid);
        thr= (TextView)findViewById(R.id.i_thr_pid);
        check_enspid = (CheckBox) findViewById(R.id.i_check_espid);
        SeekBar_mx=  (SeekBar)findViewById(R.id.i_seekBar_mx);
        SeekBar_my=  (SeekBar)findViewById(R.id.i_seekBar_my);
        mx=  (TextView)findViewById(R.id.i_mx);
        my=  (TextView)findViewById(R.id.i_my);
        fix_p=  (TextView)findViewById(R.id.i_fix_p);
        fix_r=  (TextView)findViewById(R.id.i_fix_r);
         Button clr_pid  = (Button) findViewById(R.id.i_clr_pid);
         Button clr_m  = (Button) findViewById(R.id.i_clr_m);

        clr_pid.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {

                s = String.valueOf(0);
                ed_spid_op.setText(s);

                ed_spid_oi.setText(s);

                ed_spid_od.setText(s);

                ed_spid_ip.setText(s);

                ed_spid_ii.setText(s);

                ed_spid_id.setText(s);

                ed_hp_p.setText(s);

                ed_hp_i.setText(s);

                ed_hp_d.setText(s);

                ed_spid_yp.setText(s);

                ed_spid_yi.setText(s);

                ed_spid_yd.setText(s);

            }
        });
        clr_m.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {

                MainActivity.MX=0;
                MainActivity.MY=0;
                SeekBar_mx.setProgress(50);
                SeekBar_my.setProgress(50);
            }
        });

        ed_hp_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//在输入数据时监听

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,//输入数据之前的监听
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {//输入数据之后监听

            }
        });


        SeekBar_mx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //progress为当前数值的大小
                MainActivity.MX = (float) (progress - 50) / 50.f * 5;
                // mx.setText("当前值:" +df.format( (float)(progress-50)/50.f*5)+"°");
            }

            @Override
            //在拖动中会调用此方法
            public void onStartTrackingTouch(SeekBar seekBar) {
                // mx.setText("mx正在调节");
            }

            @Override
            //停止拖动
            public void onStopTrackingTouch(SeekBar seekBar) {
                //  mx.setText("mx停止调节");

            }
        });

        SeekBar_my.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //progress为当前数值的大小
                MainActivity.MY=(float)(progress-50)/50.f*5;
               // my.setText("当前值:" +df.format( (float)(progress-50)/50.f*5)+"°");
            }
            @Override
            //在拖动中会调用此方法
            public void onStartTrackingTouch(SeekBar seekBar) {
                //my.setText("my正在调节");
            }

            @Override
            //停止拖动
            public void onStopTrackingTouch(SeekBar seekBar) {
              //  my.setText("my停止调节");

            }
        });



        check_enspid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    MainActivity.EN_SPID = 1;
                } else {
                    MainActivity.EN_SPID = 0;
                }
            }

        });

        // 去调试1窗口
                Button mbutton  = (Button) findViewById(R.id.i_fly_pid);
                 mbutton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                       // intent.setClass(ActivityPID.this, debug1_main.class);
                       // startActivity(intent);
                    }
                });
        // 去调试2窗口
         mbutton  = (Button) findViewById(R.id.i_stop_pid);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityPID.this, debug2_main.class);
                MainActivity.SH_SPID_OP=Integer.parseInt(ed_spid_op.getText().toString());
                MainActivity.SH_SPID_OI=Integer.parseInt(ed_spid_oi.getText().toString());
                MainActivity.SH_SPID_OD=Integer.parseInt(ed_spid_od.getText().toString());
                MainActivity.SH_SPID_IP=Integer.parseInt(ed_spid_ip.getText().toString());
                MainActivity.SH_SPID_II=Integer.parseInt(ed_spid_ii.getText().toString());
                MainActivity.SH_SPID_ID=Integer.parseInt(ed_spid_id.getText().toString());
                MainActivity.SH_HPID_OP=Integer.parseInt(ed_hp_p.getText().toString());
                MainActivity.SH_HPID_OI=Integer.parseInt(ed_hp_i.getText().toString());
                MainActivity.SH_HPID_OD=Integer.parseInt(ed_hp_d.getText().toString());
                MainActivity.SH_SPID_YP=Integer.parseInt(ed_spid_yp.getText().toString());
                MainActivity.SH_SPID_YI=Integer.parseInt(ed_spid_yi.getText().toString());
                MainActivity.SH_SPID_YD=Integer.parseInt(ed_spid_yd.getText().toString());
                startActivity(intent);
            }
        });
               mbutton = (Button)findViewById(R.id.b_back_gps);
               mbutton.setOnClickListener(new OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent();
                       intent.setClass(ActivityPID.this,ActivityControl.class);
                       MainActivity.SH_SPID_OP=Integer.parseInt(ed_spid_op.getText().toString());
                       MainActivity.SH_SPID_OI=Integer.parseInt(ed_spid_oi.getText().toString());
                       MainActivity.SH_SPID_OD=Integer.parseInt(ed_spid_od.getText().toString());
                       MainActivity.SH_SPID_IP=Integer.parseInt(ed_spid_ip.getText().toString());
                       MainActivity.SH_SPID_II=Integer.parseInt(ed_spid_ii.getText().toString());
                       MainActivity.SH_SPID_ID=Integer.parseInt(ed_spid_id.getText().toString());
                       MainActivity.SH_HPID_OP=Integer.parseInt(ed_hp_p.getText().toString());
                       MainActivity.SH_HPID_OI=Integer.parseInt(ed_hp_i.getText().toString());
                       MainActivity.SH_HPID_OD=Integer.parseInt(ed_hp_d.getText().toString());
                       MainActivity.SH_SPID_YP=Integer.parseInt(ed_spid_yp.getText().toString());
                       MainActivity.SH_SPID_YI=Integer.parseInt(ed_spid_yi.getText().toString());
                       MainActivity.SH_SPID_YD=Integer.parseInt(ed_spid_yd.getText().toString());
                       startActivity(intent);
                   }
               });


        mbutton = (Button) findViewById(R.id.ib_write);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unlock_dilog();
            }
        });

        mbutton = (Button) findViewById(ib_read);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                load_data=true;
                s = String.valueOf(MainActivity.SPID_OP);
                ed_spid_op.setText(s);
                s = String.valueOf(MainActivity.SPID_OI);
                ed_spid_oi.setText(s);
                s = String.valueOf(MainActivity.SPID_OD);
                ed_spid_od.setText(s);
                s = String.valueOf(MainActivity.SPID_IP);
                ed_spid_ip.setText(s);
                s = String.valueOf(MainActivity.SPID_II);
                ed_spid_ii.setText(s);
                s = String.valueOf(MainActivity.SPID_ID);
                ed_spid_id.setText(s);
                s = String.valueOf(MainActivity.HPID_OP);
                ed_hp_p.setText(s);
                s = String.valueOf(MainActivity.HPID_OI);
                ed_hp_i.setText(s);
                s = String.valueOf(MainActivity.HPID_OD);
                ed_hp_d.setText(s);
                s = String.valueOf(MainActivity.SPID_YP);
                ed_spid_yp.setText(s);
                s = String.valueOf(MainActivity.SPID_YI);
                ed_spid_yi.setText(s);
                s = String.valueOf(MainActivity.SPID_YD);
                ed_spid_yd.setText(s);
                toast_task(2);
            }
        });




             ui_handler.postDelayed(ui_task, 100);

       // tv_gpsf_jg.setTextColor(Color.WHITE);
      //  tv_gpsf_wg.setTextColor(Color.YELLOW);
        }//--end creat


    private void unlock_dilog() {
        AlertDialog.Builder queren=new AlertDialog.Builder(this);
        queren.setTitle("确认写入PID？");
        queren.setIcon(R.drawable.ic_launcher);
        queren.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //null
            }
        }).create();
        queren.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.Tx_SPID_OP=Integer.parseInt(ed_spid_op.getText().toString());
                MainActivity.Tx_SPID_OI=Integer.parseInt(ed_spid_oi.getText().toString());
                MainActivity.Tx_SPID_OD=Integer.parseInt(ed_spid_od.getText().toString());
                MainActivity.Tx_SPID_IP=Integer.parseInt(ed_spid_ip.getText().toString());
                MainActivity.Tx_SPID_II=Integer.parseInt(ed_spid_ii.getText().toString());
                MainActivity.Tx_SPID_ID=Integer.parseInt(ed_spid_id.getText().toString());
                MainActivity.Tx_HPID_OP=Integer.parseInt(ed_hp_p.getText().toString());
                MainActivity.Tx_HPID_OI=Integer.parseInt(ed_hp_i.getText().toString());
                MainActivity.Tx_HPID_OD=Integer.parseInt(ed_hp_d.getText().toString());
                MainActivity.Tx_SPID_YP=Integer.parseInt(ed_spid_yp.getText().toString());
                MainActivity.Tx_SPID_YI=Integer.parseInt(ed_spid_yi.getText().toString());
                MainActivity.Tx_SPID_YD=Integer.parseInt(ed_spid_yd.getText().toString());
                MainActivity.Send_PID1();
                MainActivity.Send_PID2();
                MainActivity.Send_Command((byte) 0xa3);
                MainActivity.Send_PID1();
                MainActivity.Send_PID2();
                MainActivity.Send_Command((byte) 0xa3);
                MainActivity.Send_PID1();
                MainActivity.Send_PID2();
                MainActivity.Send_Command((byte) 0xa3);
                MainActivity.Send_PID1();
                MainActivity.Send_PID2();
                MainActivity.Send_Command((byte) 0xa3);
                toast_task(1);
            }
        }).create();
        queren.show();
    }

    private void toast_task(int sel) {
        Toast toast_wr;
        if(sel==1)
        {toast_wr = Toast.makeText(this, "PID写入飞控", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==2)
        {toast_wr = Toast.makeText(this, "读出飞控PID", Toast.LENGTH_SHORT);
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
