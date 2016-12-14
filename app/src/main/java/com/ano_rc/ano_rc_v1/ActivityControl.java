package com.ano_rc.ano_rc_v1;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import unit.thr_bar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.text.DecimalFormat;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class ActivityControl extends Activity {
//声明控件
    int tx,tx_cnt;
    public ToggleButton tb_lock_c;
    private ImageView vb_lock_c;
    private ImageView vb_sbc_c,v_cble_c;

    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private RadioButton State_ins;
    private RadioButton State_GPS;
    private RadioButton State_H;

    buttonListener btnliListener1 = new buttonListener();

    private int VAL_THR,VAL_YAW=1500,VAL_ROL,VAL_PIT;
    private int DeadAngle = 1;
    private int MAXAngle = 50;
    private int YAW_D = 1200;
    private int YAW_A = 1800;
    private char lock=1;
    Timer send_timer = new Timer( );

    private WebView webView;
    private float acc_x,acc_y,acc_z;

    private TextView text_thr;
    private TextView text_yaw;
    private  TextView text_rol;
    private  TextView text_pit;
    private TextView tv_ang_rol;
    private  TextView tv_ang_pit;
    private TextView tv_ang_yaw;

    private  TextView tv_votage1;
    private TextView tv_votage2;
    private TextView tv_high;
    private  TextView tv_gpsf_j;
    private TextView tv_gpsf_w;

    private  ImageView tv_pitrol;
    private ImageView tv_yaw;
    private ImageView tv_high_jiant,v_no1;

    private ProgressBar bar_thr;
    private final Handler ui_handler = new Handler();
    private final Runnable ui_task;

    private  int cnt_sb_connect=0,cnt_ble_connect=0,ble_rotate=0;
    private  float alfa_sb_connect=1,alfa_ble_connect=1;
    private  Animation anim = null;
    private  Animation anim_pitrol = null;
    private Animation anim_yaw = null;
    private Animation anim_high = null;

    private SeekBar SeekBar_rendery;
    private SeekBar SeekBar_renderx;
    int constNum = 100;
    private Timer timer = new Timer();
    private GraphicalView chart;
    private TimerTask task;
    private float addY = -1;
    private long addX;
    private float addroly = -1;
    private long addrolx;
    private float addyawy = -1;
    private long addyawx;
    private TimeSeries series;
    private TimeSeries series1,series2,series3;
    private XYMultipleSeriesDataset dataset;
    Date[] xcache = new Date[constNum];
    float[] ycache = new float[constNum];
    Date[] rol_X = new Date[constNum];
    float[] rol_Y = new float[constNum];
    Date[] yaw_X = new Date[constNum];
    float[] yaw_Y = new float[constNum];
    private Handler handler;
    java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    {
        ui_task = new Runnable() {

            @Override
            public void run() {




                // TODO Auto-generated method stub
                ui_handler.postDelayed(this, 30);//ms

                VAL_THR = thr_bar.Value * 1000 / thr_bar.Value_max + 1000;//255
                double temp_rol = Math.atan2(acc_y, acc_z) * 180 / Math.PI;
                double temp_pit = -Math.atan2(acc_x, acc_z) * 180 / Math.PI;

                if (temp_rol > MAXAngle)
                    temp_rol = MAXAngle;
                else if (temp_rol < -MAXAngle)
                    temp_rol = -MAXAngle;
                if (temp_rol < DeadAngle && temp_rol > -DeadAngle)
                    temp_rol = 0;

                if (temp_pit > MAXAngle)
                    temp_pit = MAXAngle;
                else if (temp_pit < -MAXAngle)
                    temp_pit = -MAXAngle;
                if (temp_pit < DeadAngle && temp_pit > -DeadAngle)
                    temp_pit = 0;

                VAL_ROL = (int) (temp_rol * 500 / MAXAngle + 1500);
                VAL_PIT = (int) (temp_pit * 500 / MAXAngle + 1500);
                text_thr.setText("" + (int)(MainActivity.YOUMEN/(float)MainActivity.MAX_YOUMEN*100)+"%");
                text_yaw.setText("" + VAL_YAW+"°");
                text_rol.setText("" + VAL_ROL+"°");
                text_pit.setText("" + VAL_PIT+"°");
                tv_ang_rol.setText("" +df.format( MainActivity.VAL_ANG_X)+"°");
                tv_ang_pit.setText("" +df.format(  MainActivity.VAL_ANG_Y)+"°");
                tv_ang_yaw.setText("" + df.format( MainActivity.VAL_ANG_Z)+"°");
              //  if( MainActivity.VAL_VOTAGE1>6) MainActivity.VAL_VOTAGE1= MainActivity.VAL_VOTAGE1/2;
                if( MainActivity.VAL_VOTAGE1<3.71) tv_votage1.setText("" + "LowBAT!");
                else
                tv_votage1.setText("" + (int)((MainActivity.VAL_VOTAGE1-3.7)/0.51*100)+"%");
                if( MainActivity.VAL_VOTAGE2<3.71) tv_votage2.setText("" + "LowBAT!");
                else
                tv_votage2.setText("" + (int)((MainActivity.VAL_VOTAGE2-3.7)/0.51*100)+"%");
                tv_high.setText("" + MainActivity.HIGH_F+"m");
                tv_gpsf_j.setText("" + MainActivity.GPS_JIN+"°");
                tv_gpsf_w.setText("" + MainActivity.GPS_WEI+"°");//添加文本
                bar_thr.setProgress((int)(MainActivity.YOUMEN/800.f*100));
                bar_thr.setSecondaryProgress((int)(MainActivity.YOUMEN/(float)MainActivity.MAX_YOUMEN*100));
                if (MainActivity.LOCK == 1) {
                    vb_lock_c.setBackgroundResource(android.R.drawable.ic_lock_idle_lock);
                } else {
                    vb_lock_c.setBackgroundResource(android.R.drawable.ic_partial_secure);
                }

                if (MainActivity.SB_CONNECT == 1) {
                    vb_sbc_c.setBackgroundResource(android.R.drawable.stat_notify_sync);
                    vb_sbc_c.setAlpha(1f);
                    anim=ani_rotate(ble_rotate,ble_rotate+5,0.5f,0.5f,30);
                    ble_rotate+=5;
                     if(ble_rotate>=360)
                         ble_rotate=0;
                    vb_sbc_c.startAnimation(anim);
                } else {
                    vb_sbc_c.setBackgroundResource(R.drawable.ic_control);
                    if(cnt_sb_connect++>2)
                    {cnt_sb_connect=0; alfa_sb_connect-=0.4;
                        if(alfa_sb_connect<0)alfa_sb_connect=1;
                        vb_sbc_c.setAlpha(alfa_sb_connect);
                        v_no1.setAlpha(alfa_sb_connect);
                    }
                   anim=ani_rotate(0,0,0.5f,0.5f,30);
                   vb_sbc_c.startAnimation(anim);
                }

                if (MainActivity.IS_CBLE == 1) {
                    v_cble_c.setBackgroundResource(android.R.drawable.stat_sys_data_bluetooth);
                    if(cnt_ble_connect++>2)
                    {cnt_ble_connect=0; alfa_ble_connect-=0.4;
                        if(alfa_ble_connect<0)alfa_ble_connect=1;
                        v_cble_c.setAlpha(alfa_ble_connect);
                    }
                } else {
                    v_cble_c.setBackgroundResource(android.R.drawable.ic_menu_search);
                    if(cnt_ble_connect++>2)
                    {cnt_ble_connect=0; alfa_ble_connect-=0.4;
                        if(alfa_ble_connect<0)alfa_ble_connect=1;
                        v_cble_c.setAlpha(alfa_ble_connect);
                    }
                }

                if (MainActivity.EN_FIX_INSF == 1) {
                    State_ins.setChecked(true);
                } else {
                    State_ins.setChecked(false);
                }
                if (MainActivity.EN_FIX_GPSF == 1) {
                    State_GPS.setChecked(true);
                } else {
                    State_GPS.setChecked(false);
                }
                if (MainActivity.EN_FIX_HIGHF == 1) {
                    State_H.setChecked(true);
                } else {
                    State_H.setChecked(false);
                }



                {
                    tv_pitrol.setRotation(MainActivity.VAL_ANG_X);
                    tv_yaw.setRotation(-MainActivity.VAL_ANG_Z);
                }


            }
        };
    }//-30ms循环
    private void updateChart() {
        //设定长度为20

        int length = series.getItemCount();
        if(length>=constNum) length = constNum;
        if(tx_cnt++>tx) {tx_cnt=0;
            addY = MainActivity.VAL_ANG_X;//random.nextInt()%5+10;
            addroly = MainActivity.VAL_ANG_Y;
            addyawy = MainActivity.HIGH_F / 4.f * 160*(float)2.5; //MainActivity.VAL_ANG_Z-180;
        }
          addX = new Date().getTime();   addyawx = addX; addrolx = addX;
        //将前面的点放入缓存
        dataset.removeSeries(series);
        dataset.removeSeries(series1);
        dataset.removeSeries(series2);
        for (int i = 0; i < length; i++) {
            xcache[i] =  new Date((long)series.getX(i));
            ycache[i] = (float) series.getY(i);
            rol_X[i] =  new Date((long)series1.getX(i));
            rol_Y[i] = (float) series1.getY(i);
            yaw_X[i] =  new Date((long)series2.getX(i));
            yaw_Y[i] = (float) series2.getY(i);
        }
        series.clear();
        series1.clear();
        series2.clear();
        //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
        series.add(new Date(addX), addY);
        series1.add(new Date(addrolx), addroly);
        series2.add(new Date(addyawx), addyawy);
        for (int k = 0; k < length; k++) {
            series.add(xcache[k], ycache[k]);
        }
        for (int k = 0; k < length; k++) {
            series1.add(xcache[k], rol_Y[k]);
        }
        for (int k = 0; k < length; k++) {
            series2.add(xcache[k], yaw_Y[k]);
        }
        //在数据集中添加新的点集

        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        //曲线更新
        chart.invalidate();
    }
    //---------------------曲线初始化
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    private XYMultipleSeriesRenderer getDemoRenderer() {

        renderer.setChartTitle("传感器数据");//标题
        renderer.setChartTitleTextSize(20);
        renderer.setXTitle("时间");    //x轴说明
        renderer.setYTitle("角度值（°）");
        renderer.setAxisTitleTextSize(16);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsTextSize(15);    //数轴刻度字体大小
        renderer.setLabelsColor(Color.BLACK);
        renderer.setLegendTextSize(15);    //曲线说明
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0,Color.BLACK);
        renderer.setYLabels(30);
        renderer.setShowLegend(false);
        renderer.setMargins(new int[] {15, 30, 25, 2});//上左下右{ 20, 30, 100, 0 })

        XYSeriesRenderer r = new XYSeriesRenderer();


        r.setColor(Color.BLUE);
        //r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        //r.setFillBelowLine(true);
        // r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);

        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.RED);
        //  r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        //r.setFillBelowLine(true);
        //r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);

        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.BLACK);
        ///  r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        //r.setFillBelowLine(true);
        // r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);

        renderer.setMarginsColor(Color.WHITE);
        renderer.setPanEnabled(false,false);
        renderer.setShowGrid(true);
        renderer.setYAxisMax(180);//纵坐标最大值
        renderer.setYAxisMin(-180);//纵坐标最小值
        renderer.setInScroll(true);
        return renderer;
    }
    private XYMultipleSeriesDataset getDateDemoDataset() {//初始化的数据
        dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        long value = new Date().getTime();
        Random r = new Random();
        series = new TimeSeries("PIT" +  1);
        for (int k = 0; k < nr; k++) {
            series.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }


        series1 = new TimeSeries("ROL " +  1);
        for (int k = 0; k < nr; k++) {
            series1.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }


        series2 = new TimeSeries("YAW " +  1);
        for (int k = 0; k < nr; k++) {
            series2.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//窗口建立
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
        setContentView(R.layout.activity_control);
        //--标题进度条初始化
        // requestWindowFeature(Window.FEATURE_PROGRESS);
        //  setProgressBarVisibility(true);
        //  setProgress(500);//max=9999


//初始化控件
        // tb_lock_c = (ToggleButton) findViewById(R.id.b_lock_c);
        SeekBar_renderx=  (SeekBar)findViewById(R.id.i_seekBar_renderx);
        SeekBar_rendery=  (SeekBar)findViewById(R.id.i_seekBar_rendery);
        vb_lock_c = (ImageView) findViewById(R.id.im_lock_c);
        vb_sbc_c = (ImageView) findViewById(R.id.iv_sbc_c);
        v_cble_c = (ImageView) findViewById(R.id.i_cble_c);
        tv_pitrol = (ImageView) findViewById(R.id.i_impitrol_c);
        tv_yaw = (ImageView) findViewById(R.id.i_imyaw_c);
        tv_high_jiant = (ImageView) findViewById(R.id.i_jiantou_high);
        v_no1 = (ImageView) findViewById(R.id.i_no1);
        bar_thr = (ProgressBar) findViewById(R.id.i_pro_youmen);


//设置按键监听器

       /* tb_lock_c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked)
                {   lock = 1;tb_lock_c.setBackgroundResource(R.color.firebrick);
                  //  Toast.makeText( "选中了数学", 1).show();
                }
                else
                {   lock = 0;tb_lock_c.setBackgroundResource(R.color.silver);}
            }
        });*/
        WebView layout1 = (WebView) findViewById(R.id.webView);
        //生成图表
        chart = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer(), "mm:ss");
        layout1.removeAllViews();
        layout1.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


      /*  webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
//      webView.getSettings().setDefaultFontSize(5);
        webView.loadUrl("http://www.google.cn/maps");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }

            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                //    Toast.makeText(WebViewTest.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });*/


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
        if (null == mSensorManager) {
            Log.d(TAG, "deveice not support SensorManager");
        }
        // ��������ľ�׼��
        mSensorManager.registerListener(myAccelerometerListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME


        text_thr = (TextView) findViewById(R.id.text_col_thr);
        text_yaw = (TextView) findViewById(R.id.text_col_yaw);
        text_rol = (TextView) findViewById(R.id.text_col_rol);
        text_pit = (TextView) findViewById(R.id.text_col_pit);
        tv_ang_rol = (TextView) findViewById(R.id.text_val_rol);
        tv_ang_pit = (TextView) findViewById(R.id.text_val_pit);
        tv_ang_yaw = (TextView) findViewById(R.id.text_val_yaw);

        tv_votage1 = (TextView) findViewById(R.id.text_val_votage1);
        tv_votage2 = (TextView) findViewById(R.id.text_val_votage2);
        tv_high = (TextView) findViewById(R.id.TextView);
        tv_gpsf_j = (TextView) findViewById(R.id.text_tv_gpsf_j);
        tv_gpsf_w = (TextView) findViewById(R.id.text_tv_gpsf_w);//对应控件
        State_ins = (RadioButton) findViewById(R.id.radioButton_ins);
        State_GPS = (RadioButton) findViewById(R.id.radioButton_gps);
        State_H = (RadioButton) findViewById(R.id.radioButton_high);


        Button mbutton = (Button) findViewById(R.id.btn_yaw_d);
        mbutton.setOnTouchListener(btnliListener1);
        mbutton = (Button) findViewById(R.id.btn_yaw_a);
        mbutton.setOnTouchListener(btnliListener1);

        mbutton = (Button) findViewById(R.id.btn_stop);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {

                MainActivity.Send_Command((byte) 0xa0);

            }
        });
        mbutton = (Button) findViewById(R.id.btn_suoding);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lock_dilog();

            }
        });
        mbutton = (Button) findViewById(R.id.btn_jiesuo);
        mbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unlock_dilog();
            }
        });//按键 发送

        mbutton = (Button) findViewById(R.id.text_val_pitg);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityControl.this, ActivityGPS.class);
                startActivity(intent);
            }
        });

        mbutton = (Button) findViewById(R.id.b_sensor_c);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityControl.this, ActivitySensor.class);
                startActivity(intent);
            }
        });

        mbutton = (Button) findViewById(R.id.b_pid_c);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityControl.this, ActivityPID.class);
                startActivity(intent);
            }
        });

        mbutton = (Button) findViewById(R.id.b_set_c);
        mbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityControl.this, ActivitySet.class);
                startActivity(intent);
            }
        });

        ImageButton imbutton = (ImageButton) findViewById(R.id.b_back_c);
        imbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityControl.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SeekBar_rendery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int temp;
                //progress为当前数值的大小
                temp=(int)((float)(progress-50)*2);
                renderer.setYAxisMax(180+temp);//纵坐标最大值
                renderer.setYAxisMin(-180-temp);//纵坐标最小值

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


        SeekBar_renderx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                //progress为当前数值的大小
                tx=progress/20;
                // timer.schedule(task, 0,70+tx);
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

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //刷新图表
                updateChart();
                super.handleMessage(msg);
            }
        };
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 200;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 2*70,70);//1s------------------------------------------------------------------------------图表时间修改
       // timer.schedule(task, 0,70);//1s------------------------------------------------------------------------------图表时间修改
       // ui_handler.postDelayed(ui_task, 100);
        //send_timer.schedule(send_task, 1000, 50);
        ui_handler.postDelayed(ui_task, 100);
    }//----end onCreat
    /*
    @Override   //默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
//---Toast方法
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
 //---确认框方法
    private void unlock_dilog() {
        AlertDialog.Builder queren=new AlertDialog.Builder(this);
        queren.setTitle("确认解锁电机?");
        queren.setIcon(R.drawable.ic_launcher);
        queren.setMessage("危险请注意!!");

        queren.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //null
            }
        }).create();
        queren.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.Send_Command((byte)0xa1);
                toast_task(3);
            }
        }).create();
        queren.show();
    }

    private void lock_dilog() {
        AlertDialog.Builder queren=new AlertDialog.Builder(this);
        queren.setTitle("确认电机上锁?");
        queren.setIcon(R.drawable.ic_launcher);
        //queren.setMessage("危险请注意!!");

        queren.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //null
            }
        }).create();
        queren.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.Send_Command((byte)0xa0);
                toast_task(4);
            }
        }).create();
        queren.show();
    }
//动画方法
private Animation ani_move(float x1,float x2 ,float y1 ,float y2,int t) {
    TranslateAnimation anim=new TranslateAnimation( x1, x2 , y1 , y2);
    anim.setDuration(t);
    anim.setFillAfter(true);
    //anim.setInterpolator(this,android.R.anim.accelerate_interpolator);
    return anim;
}

private Animation ani_rotate(float angle1 ,float angle2,float x1,float y1 ,int t) {
    RotateAnimation anim=new RotateAnimation( angle1,angle2,RotateAnimation.RELATIVE_TO_SELF,x1, RotateAnimation.RELATIVE_TO_SELF, y1 );
    anim.setDuration(t);
    anim.setFillAfter(true);
    //anim.setInterpolator(this,android.R.anim.accelerate_interpolator);
    return anim;
}
    //-------------------------------------//
    class buttonListener implements OnTouchListener{
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if(v.getId() == R.id.btn_yaw_d){
                if(event.getAction() == MotionEvent.ACTION_UP){
                    VAL_YAW = 1500;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    VAL_YAW = 1500;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    VAL_YAW = YAW_D;
                }
            }
            if(v.getId() == R.id.btn_yaw_a){
                if(event.getAction() == MotionEvent.ACTION_UP){
                    VAL_YAW = 1500;
                }
                if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    VAL_YAW = 1500;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    VAL_YAW = YAW_A;
                }
            }
            return false;
        }
    }
    /*
   * SensorEventListener�ӿڵ�ʵ�֣���Ҫʵ����������
   * ����1 onSensorChanged ����ݱ仯��ʱ�򱻴�������
   * ����2 onAccuracyChanged �������ݵľ��ȷ���仯��ʱ�򱻵��ã�����ͻȻ�޷�������ʱ
   * */
    final SensorEventListener myAccelerometerListener = new SensorEventListener(){

        //��дonSensorChanged����  
        public void onSensorChanged(SensorEvent sensorEvent){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                Log.i(TAG,"onSensorChanged");

                //ͼ�����Ѿ��������ֵ�ĺ���  
                acc_x = sensorEvent.values[0];
                acc_y = sensorEvent.values[1];
                acc_z = sensorEvent.values[2];
                Log.i(TAG,"\n heading "+acc_x);
                Log.i(TAG,"\n pitch "+acc_y);
                Log.i(TAG,"\n roll "+acc_z);
            }
        }
        //��дonAccuracyChanged����  
        public void onAccuracyChanged(Sensor sensor , int accuracy){
            Log.i(TAG, "onAccuracyChanged");
        }
    };

    public void onPause(){  
        /* 
         * �ܹؼ�Ĳ��֣�ע�⣬˵���ĵ����ᵽ����ʹactivity���ɼ��ʱ�򣬸�Ӧ����Ȼ�����Ĺ��������Ե�ʱ����Է��֣�û�����ˢ��Ƶ�� 
         * Ҳ��ǳ��ߣ�����һ��Ҫ��onPause�����йرմ����������򽲺ķ��û������������ܲ����� 
         * */
        mSensorManager.unregisterListener(myAccelerometerListener);
        super.onPause();
    }

    TimerTask send_task = new TimerTask( ) {
        byte[] bytes = new byte[25];
        public void run ( )
        {
            /*byte sum=0;

            bytes[0] = (byte) 0xaa;
            bytes[1] = (byte) 0xaf;
            bytes[2] = (byte) 0x03;
            bytes[3] = (byte) 20;
            bytes[4] = (byte) (VAL_THR/0xff);
            bytes[5] = (byte) (VAL_THR%0xff);
            bytes[6] = (byte) (VAL_YAW/0xff);
            bytes[7] = (byte) (VAL_YAW%0xff);
            bytes[8] = (byte) (VAL_ROL/0xff);
            bytes[9] = (byte) (VAL_ROL%0xff);
            bytes[10] = (byte) (VAL_PIT/0xff);
            bytes[11] = (byte) (VAL_PIT%0xff);
            bytes[12] = 1;
            bytes[13] = 2;
            bytes[14] = 3;
            bytes[15] = 4;
            bytes[16] = 0;
            bytes[17] = 0;
            bytes[18] = 0;
            bytes[19] = 0;
            bytes[20] = 0;
            bytes[21] = 0;
            bytes[22] = 0;
            bytes[23] = 0;
            for(int i=0;i<24;i++) sum += bytes[i];
            bytes[24] = sum;

            MainActivity.SendData_Byte(bytes);   */

        }





    };

    protected void onDestroy ( ) {
        timer.cancel();
        if (send_timer != null)
        {
            send_timer.cancel( );
            send_timer = null;
        }
        super.onDestroy( );
    }

}


//-------------
