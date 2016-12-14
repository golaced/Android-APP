package com.ano_rc.ano_rc_v1;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySensor extends Activity {

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
    private Handler handler;
    private Random random=new Random();

    Date[] xcache = new Date[constNum];
    float[] ycache = new float[constNum];
    Date[] rol_X = new Date[constNum];
    float[] rol_Y = new float[constNum];
    Date[] yaw_X = new Date[constNum];
    float[] yaw_Y = new float[constNum];


    TextView tv_acc_xs;
    TextView tv_acc_ys;
    TextView tv_acc_zs;
    TextView tv_gyr_xs;
    TextView tv_gyr_ys;
    TextView tv_gyr_zs;
    TextView tv_hm_xs;
    TextView tv_hm_ys;
    TextView tv_hm_zs;
    TextView pit;
    TextView rol;
    TextView yaw;
    TextView thr;

    public Switch switch_fixhm;
    private final Handler ui_handler = new Handler();
    private final Runnable ui_task = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
            tv_acc_xs.setText(""+MainActivity.VAL_ACC_X);
            tv_acc_ys.setText(""+MainActivity.VAL_ACC_Y);
            tv_acc_zs.setText(""+MainActivity.VAL_ACC_Z);
            tv_gyr_xs.setText(""+MainActivity.VAL_GYR_X);
            tv_gyr_ys.setText(""+MainActivity.VAL_GYR_Y);
            tv_gyr_zs.setText(""+MainActivity.VAL_GYR_Z);
            tv_hm_xs.setText(""+MainActivity.VAL_HM_X);
            tv_hm_ys .setText(""+MainActivity.VAL_HM_Y);
            tv_hm_zs.setText(""+MainActivity.VAL_HM_Z);
            pit.setText(""+MainActivity.VAL_ANG_X+"°");
            rol .setText(""+MainActivity.VAL_ANG_Y+"°");
            yaw.setText(""+MainActivity.VAL_ANG_Z+"°");
        }
    };
    @Override
           protected void onCreate(Bundle savedInstanceState) {
               // TODO Auto-generated method stub
               super.onCreate(savedInstanceState);
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
               setContentView(R.layout.activity_sensor);
       // RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.chart_i);i_c
        WebView layout1 = (WebView)findViewById(R.id.i_c);
        //生成图表
        chart = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer(), "mm:ss");
        layout1.removeAllViews();
        layout1.addView(chart, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

                tv_acc_xs = (TextView)findViewById(R.id.text_val_acc_x);
                tv_acc_ys = (TextView)findViewById(R.id.text_val_acc_y);
                tv_acc_zs = (TextView)findViewById(R.id.text_val_acc_z);
                tv_gyr_xs = (TextView)findViewById(R.id.text_val_gyr_x);
                tv_gyr_ys = (TextView)findViewById(R.id.text_val_gyr_y);
                tv_gyr_zs = (TextView)findViewById(R.id.text_val_gyr_z);
                tv_hm_xs = (TextView)findViewById(R.id.text_val_hx_x);
                tv_hm_ys = (TextView)findViewById(R.id.text_val_hm_y);
                tv_hm_zs = (TextView)findViewById(R.id.text_val_hm_z);
                pit = (TextView)findViewById(R.id.t_pit);
                rol = (TextView)findViewById(R.id.t_rol);
                yaw = (TextView)findViewById(R.id.t_yaw);
                thr = (TextView)findViewById(R.id.i_thr_pid);

               switch_fixhm = (Switch) findViewById(R.id.i_fix_hm);
               switch_fixhm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                        if (isChecked) {
                            MainActivity.Send_Command((byte) 0xa4);//零偏采集
                            toast_task(2);
                        } else {
                            MainActivity.Send_Command((byte) 0xa5);//零偏采集
                            toast_task(3);
                        }
                    }

                });

               Button mbutton = (Button) findViewById(R.id.b_back_gps);
               mbutton = (Button)findViewById(R.id.b_back_gps);
               mbutton.setOnClickListener(new OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent();
                       intent.setClass(ActivitySensor.this,ActivityControl.class);
                       startActivity(intent);
                   }
               });
                mbutton = (Button)findViewById(R.id.i_fix_zero);
                mbutton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        MainActivity.Send_Command((byte) 0xa6);//零偏采集
                        toast_task(1);
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
        timer.schedule(task, 2*70,70);//1s-----------------------图表时间修改
        ui_handler.postDelayed(ui_task, 100);

       // tv_gpsf_jg.setTextColor(Color.WHITE);
      //  tv_gpsf_wg.setTextColor(Color.YELLOW);
        }//--on creat
    private void updateChart() {
        //设定长度为20
        int length = series.getItemCount();
        if(length>=constNum) length = constNum;
        addY=  MainActivity.VAL_ANG_X;//random.nextInt()%5+10;
        addX=new Date().getTime();
        addroly = MainActivity.VAL_ANG_Y;
        addrolx=addX;
        addyawy = MainActivity.VAL_ANG_Z-180;
        addyawx=addX;
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
    private XYMultipleSeriesRenderer getDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
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
    public void onDestroy() {
        //当结束程序时关掉Timer
        timer.cancel();
        super.onDestroy();
    };

    private void toast_task(int sel) {
        Toast toast_wr;
        if(sel==1)
        {toast_wr = Toast.makeText(this, "开始采集数据，请保持飞行器水平", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==2)
        {toast_wr = Toast.makeText(this, "开始磁力校正", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==3)
        {toast_wr = Toast.makeText(this, "停止磁力校正！", Toast.LENGTH_SHORT);
            toast_wr.show();}
        else if(sel==4)
        {toast_wr = Toast.makeText(this, "电机上锁", Toast.LENGTH_SHORT);
            toast_wr.show();}
    }


}
//-------------
