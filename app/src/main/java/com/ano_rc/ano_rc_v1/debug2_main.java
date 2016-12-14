package com.ano_rc.ano_rc_v1;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class debug2_main extends Activity {
    String s;
    int add;
    private boolean load_data=false;
    private boolean init_flag=false;
    private WebView table_d2;
    private TextView[] text_debug=new TextView[18];
   // chart
    private Handler handler;
    Timer send_timer = new Timer( );
    private Timer timer = new Timer();
    private GraphicalView chart;
    private TimerTask task;
    private TimeSeries series,series1,series2,series3,series4,series5,series6,series7,series8,series9;
    private XYMultipleSeriesDataset dataset;
    int constNum = 100;
    private int auto1  ;
    private int[] en_char =new  int[10] ;
    private float[] addY =new  float[10] ;
    private long [] addX =new   long [10];
    int tx,tx_cnt;
    Date[][] x = new Date[constNum][10];
    float[][] y = new float[constNum][10];
    float[][] y_reg = new float[constNum][10];
    float MAXZ=180,MAXF=-180;
    float SEL=0;
    //
    java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    java.text.DecimalFormat df2=new java.text.DecimalFormat("#.##");
    java.text.DecimalFormat df3=new java.text.DecimalFormat("#.###");
    //------------------Edittext--------------------
    private EditText max_char;
    private EditText min_char;
    private EditText sel_char;
    //---------------------Seekbar--------------------
    private SeekBar Seek_move;
    private SeekBar Seek_size;
    //-----------------------Button------------------
    private ImageButton close_d2;
    private Button to_debug1_d2;
    private Button to_pid_d2;
    private Button resize;
    private Button set;
    private CheckBox auto;
    private CheckBox[] en_chart=new CheckBox[10];
    private final Handler ui_handler = new Handler();
    private final Runnable ui_task = new Runnable() {
        int i;
        @Override
        public void run() {

            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
            if(init_flag==false) {
                init_flag = true;
                en_char[4]=en_char[1]=en_char[2]=en_char[3]=1;
                MAXZ=MainActivity.MAXZ_GOLABE;
                MAXF=MainActivity.MAXF_GOLABE;
                SEL=MainActivity.DEBUG_SEL;
                s = String.valueOf(MainActivity.MAXZ_GOLABE);
                max_char.setText(s);
                s = String.valueOf(MainActivity.MAXF_GOLABE);
                min_char.setText(s);
                s = String.valueOf(MainActivity.DEBUG_SEL);
                sel_char.setText(s);



                    renderer.setYAxisMax(MAXZ);//纵坐标最大值
                    renderer.setYAxisMin(MAXF);//纵坐标最小值

            }
            for(i=1;i<=9;i++)
                text_debug[i].setText(df3.format(MainActivity.DEBUG[i]));

            //for(int i=1;i<=9;i++)
           //     y_reg[add][i]=(float)MainActivity.DEBUG[i];
            //if(add++>constNum-1) add=0;
        }
    };
    //---DRAW

    private void updateChart() {
        //设定长度为20
        int i;
        int length = series.getItemCount();
        if(length>=constNum) length = constNum;
        if(tx_cnt++>tx) {tx_cnt=0;

            for(i=1;i<=9;i++) if(en_char[i]==1)
             //addY[i] =12+i;//(float) MainActivity.DEBUG[i];
                addY[i] =(float) MainActivity.DEBUG[i];
             else
             addY[i] =0;


        }
        for(i=1;i<=9;i++)
           addX[i] = new Date().getTime();

        //将前面的点放入缓存
        dataset.removeSeries(series);
        dataset.removeSeries(series1);
        dataset.removeSeries(series2);
        dataset.removeSeries(series3);
        dataset.removeSeries(series4);
        dataset.removeSeries(series5);
        dataset.removeSeries(series6);
        dataset.removeSeries(series7);
        dataset.removeSeries(series8);
        for ( i = 0; i < length; i++) {
            x[i][1] =  new Date((long)series.getX(i));
            y[i][1] = (float) series.getY(i);
            x[i][2] =  new Date((long)series1.getX(i));
            y[i][2] = (float) series1.getY(i);
            x[i][3] =  new Date((long)series2.getX(i));
            y[i][3] = (float) series2.getY(i);
            x[i][4] =  new Date((long)series3.getX(i));
            y[i][4] = (float) series3.getY(i);
            x[i][5] =  new Date((long)series4.getX(i));
            y[i][5] = (float) series4.getY(i);
            x[i][6] =  new Date((long)series5.getX(i));
            y[i][6] = (float) series5.getY(i);
            x[i][7] =  new Date((long)series6.getX(i));
            y[i][7] = (float) series6.getY(i);
            x[i][8] =  new Date((long)series7.getX(i));
            y[i][8] = (float) series7.getY(i);
            x[i][9] =  new Date((long)series8.getX(i));
            y[i][9] = (float) series8.getY(i);
        }
        series.clear();
        series1.clear();
        series2.clear();
        series3.clear();
        series4.clear();
        series5.clear();
        series6.clear();
        series7.clear();
        series8.clear();

        //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
        series.add(new Date(addX[1]), addY[1]);
        series1.add(new Date(addX[2]), addY[2]);
        series2.add(new Date(addX[3]), addY[3]);
        series3.add(new Date(addX[4]), addY[4]);
        series4.add(new Date(addX[5]), addY[5]);
        series5.add(new Date(addX[6]), addY[6]);
        series6.add(new Date(addX[7]), addY[7]);
        series7.add(new Date(addX[8]), addY[8]);
        series8.add(new Date(addX[9]), addY[9]);



        for (int k = 0; k < length; k++) {
            series.add(x[k][1], y[k][1]);
        }
        for (int k = 0; k < length; k++) {
            series1.add(x[k][2], y[k][2]);
        }
        for (int k = 0; k < length; k++) {
            series2.add(x[k][3], y[k][3]);
        }
        for (int k = 0; k < length; k++) {
            series3.add(x[k][4], y[k][4]);
        }for (int k = 0; k < length; k++) {
            series4.add(x[k][5], y[k][5]);
        }
        for (int k = 0; k < length; k++) {
            series5.add(x[k][6], y[k][6]);
        }
        for (int k = 0; k < length; k++) {
            series6.add(x[k][7], y[k][7]);
        }
        for (int k = 0; k < length; k++) {
            series7.add(x[k][8], y[k][8]);
        }
        for (int k = 0; k < length; k++) {
            series8.add(x[k][9], y[k][9]);
        }


        //在数据集中添加新的点集

        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        dataset.addSeries(series6);
        dataset.addSeries(series7);
        dataset.addSeries(series8);


        //曲线更新
        chart.invalidate();
        float temp=(float)Math.abs((MainActivity.DEBUG[1]));
        if(auto.isChecked()) {
            for (i = 1; i <= 9; i++) {
                if (en_char[i] == 1) {
                    if ((float) (Math.abs(MainActivity.DEBUG[i])) > temp)
                        temp = (float) (Math.abs(MainActivity.DEBUG[i]));
                }
            }
            MAXZ = (float) (temp * 1.1);//(float)Integer.parseInt(max_char.getText().toString())/100;
            MAXF = (float) (temp * 1.1);//(float)Integer.parseInt(min_char.getText().toString())/100;
            if (MAXZ <= MAXF) MAXZ = MAXF + 1;
            renderer.setYAxisMax(MAXZ);//纵坐标最大值
            renderer.setYAxisMin(-MAXF);//纵坐标最小值
        }
    }
    //---------------------曲线初始化
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    private XYMultipleSeriesRenderer getDemoRenderer() {

        renderer.setChartTitle("Debug数据");//标题
        renderer.setChartTitleTextSize(20);
        renderer.setXTitle("时间");    //x轴说明
        renderer.setYTitle("值");
        renderer.setAxisTitleTextSize(16);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsTextSize(15);    //数轴刻度字体大小
        renderer.setLabelsColor(Color.BLACK);
        renderer.setLegendTextSize(15);    //曲线说明
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setYLabels(30);
        renderer.setShowLegend(false);
        renderer.setMargins(new int[]{15, 30, 25, 2});//上左下右{ 20, 30, 100, 0 })

        XYSeriesRenderer r = new XYSeriesRenderer();

 //1
        r.setColor(Color.BLUE);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //2
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.RED);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //3
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.BLACK);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //4
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.GREEN);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //5
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.BLUE);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //6
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.BLACK);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //7
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.RED);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //8
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.GREEN);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        //9
        r =new XYSeriesRenderer();//第二条
        r.setColor(Color.LTGRAY);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);



        //------
        renderer.setMarginsColor(Color.WHITE);
        renderer.setPanEnabled(false,false);
        renderer.setShowGrid(true);
        renderer.setYAxisMax(MAXZ);//纵坐标最大值
        renderer.setYAxisMin(MAXF);//纵坐标最小值
        renderer.setInScroll(true);
        return renderer;
    }
    private XYMultipleSeriesDataset getDateDemoDataset() {//初始化的数据
        dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        long value = new Date().getTime();
        Random r = new Random();
        series = new TimeSeries("DAT_1" +  1);
        for (int k = 0; k < nr; k++) {
            series.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series1 = new TimeSeries("DAT_2 " +  1);
        for (int k = 0; k < nr; k++) {
            series1.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series2 = new TimeSeries("DAT_3 " +  1);
        for (int k = 0; k < nr; k++) {
            series2.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series3 = new TimeSeries("DAT_4 " +  1);
        for (int k = 0; k < nr; k++) {
            series3.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series4 = new TimeSeries("DAT_5 " +  1);
        for (int k = 0; k < nr; k++) {
            series4.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series5 = new TimeSeries("DAT_5 " +  1);
        for (int k = 0; k < nr; k++) {
            series5.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series6 = new TimeSeries("DAT_6 " +  1);
        for (int k = 0; k < nr; k++) {
            series6.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series7 = new TimeSeries("DAT_7 " +  1);
        for (int k = 0; k < nr; k++) {
            series7.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series8 = new TimeSeries("DAT_8 " +  1);
        for (int k = 0; k < nr; k++) {
            series8.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }
        series9 = new TimeSeries("DAT_9 " +  1);
        for (int k = 0; k < nr; k++) {
            series9.add(new Date(value+k*1000),0);//初值Y轴以20为中心，X轴初值范围再次定义
        }

        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        dataset.addSeries(series6);
        dataset.addSeries(series7);
        dataset.addSeries(series8);
        return dataset;
    }

    //---creat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����ģʽ
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ֹ��Ļ�䰵
        setContentView(R.layout.debug2);



        text_debug[1] = (TextView)findViewById(R.id.data1_d21);
        text_debug[2] = (TextView)findViewById(R.id.data2_d21);
        text_debug[3] = (TextView)findViewById(R.id.data3_d21);
        text_debug[4] = (TextView)findViewById(R.id.data4_d21);
        text_debug[5] = (TextView)findViewById(R.id.data5_d21);
        text_debug[6] = (TextView)findViewById(R.id.data6_d21);
        text_debug[7] = (TextView)findViewById(R.id.data7_d21);
        text_debug[8] = (TextView)findViewById(R.id.data8_d21);
        text_debug[9] = (TextView)findViewById(R.id.data9_d21);
       // text_debug[10] = (TextView)findViewById(R.id.data10_d2);
       // text_debug[11] = (TextView)findViewById(R.id.data11_d2);
        /*text_debug[12] = (TextView)findViewById(R.id.data12_d2);
        text_debug[13] = (TextView)findViewById(R.id.data13_d2);
        text_debug[14] = (TextView)findViewById(R.id.data14_d2);
        text_debug[15] = (TextView)findViewById(R.id.data15_d2);
        text_debug[16] = (TextView)findViewById(R.id.data16_d2);
        text_debug[17] = (TextView)findViewById(R.id.data17_d2);*/
        //seekbar
        Seek_move=  (SeekBar)findViewById(R.id.seekBar_move_d2);
        Seek_size=  (SeekBar)findViewById(R.id.seekBar_size_b2);

        Seek_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                float temp;
                //progress为当前数值的大小
                temp=((float)(progress-10)*2);
                renderer.setYAxisMax(MAXZ+temp);//纵坐标最大值
                renderer.setYAxisMin(MAXF-temp);//纵坐标最小值

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

        Seek_move.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //在拖动中--即值在改变
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                float temp;
                //progress为当前数值的大小
                temp=((float)(progress-5)*2);
                renderer.setYAxisMax(MAXZ+temp);//纵坐标最大值
                renderer.setYAxisMin(MAXF+temp);//纵坐标最小值

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
        //edit text
        max_char= (EditText) findViewById(R.id.editText_max_b2);
        min_char= (EditText) findViewById(R.id.editText_min_b2);
        sel_char= (EditText) findViewById(R.id.editText_sel_b2);

        auto= (CheckBox) findViewById(R.id.checkBox_auto_id);
//check box
        en_chart[1] = (CheckBox) findViewById(R.id.check1_b2);
        en_chart[2] = (CheckBox) findViewById(R.id.check2_b2);
        en_chart[3] = (CheckBox) findViewById(R.id.check3_b2);
        en_chart[4] = (CheckBox) findViewById(R.id.check4_b2);
        en_chart[5] = (CheckBox) findViewById(R.id.check5_b2);
        en_chart[6] = (CheckBox) findViewById(R.id.check6_b2);
        en_chart[7] = (CheckBox) findViewById(R.id.check7_b2);
        en_chart[8] = (CheckBox) findViewById(R.id.check8_b2);
        en_chart[9] = (CheckBox) findViewById(R.id.check9_b2);

        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    auto1 = 1;
                } else {
                    auto1 = 0;
                }
            }
        });

        en_chart[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[1] = 1;
                } else {
                    en_char[1] = 0;
                }
            }
        });
        en_chart[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[2] = 1;
                } else {
                    en_char[2] = 0;
                }
            }
        });
        en_chart[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[3] = 1;
                } else {
                    en_char[3] = 0;
                }
            }
        });

        en_chart[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[4] = 1;
                } else {
                    en_char[4] = 0;
                }
            }
        });
        en_chart[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[5] = 1;
                } else {
                    en_char[5] = 0;
                }
            }
        });
        en_chart[6].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[6] = 1;
                } else {
                    en_char[6] = 0;
                }
            }
        });
        en_chart[7].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[7] = 1;
                } else {
                    en_char[7] = 0;
                }
            }
        });
        en_chart[8].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[8] = 1;
                } else {
                    en_char[8] = 0;
                }
            }
        });
        en_chart[9].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//点击后触发方法
                if (isChecked) {
                    en_char[9] = 1;
                } else {
                    en_char[9] = 0;
                }
            }
        });


        //chart
        WebView table_d2 = (WebView) findViewById(R.id.editText__b2);
        chart = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer(), "mm:ss");
        table_d2.removeAllViews();
        table_d2.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//去调试1


//去PID
        ImageButton close_d2 = (ImageButton) findViewById(R.id.close_b2);
        close_d2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(debug2_main.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button back_d2  = (Button) findViewById(R.id.back_d1);
        back_d2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(debug2_main.this, ActivityPID.class);
                startActivity(intent);
            }
        });
        //set
        Button set= (Button) findViewById(R.id.set_b2);
        set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i,l;
                float MAXZ_R,MAXF_R;
                MAXZ_R=MAXZ;
                MAXF_R=MAXF;

                MAXZ=(float)Integer.parseInt(max_char.getText().toString());
                MainActivity.MAXZ_GOLABE=(int)MAXZ;
                MAXF=(float)Integer.parseInt(min_char.getText().toString());
                MainActivity.MAXF_GOLABE=(int)MAXF;
                SEL=(float)Integer.parseInt(sel_char.getText().toString());
                MainActivity.DEBUG_SEL=(int)SEL;
                if(MAXZ<=MAXF) MAXZ=MAXF+1;
                renderer.setYAxisMax(MAXZ);//纵坐标最大值
                renderer.setYAxisMin(MAXF);//纵坐标最小值
                MainActivity.Send_Command02((byte) MainActivity.DEBUG_SEL);//零偏采集
            }
        });
        //resize
        Button resize= (Button) findViewById(R.id.resize_b2);
        resize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i,l;
                float MAXZ_R,MAXF_R;
                MAXZ_R=MAXZ;
                MAXF_R=MAXF;
                for(i=1;i<=9;i++){
                //
                 //       if (MainActivity.DEBUG[i]> y_reg[l-1][i])
                 //           MAXZ = (float)y_reg[l][i];
                 //       if (y_reg[l][i] < y_reg[l-1][i])
                 //           MAXF = (float)y_reg[l][i];
                  //  }
                  //
                   //if(en_char[i]==1){
                    //MAXZ=(float)MainActivity.DEBUG[i]+5;
                   // MAXF=(float)MainActivity.DEBUG[i]-5;}
                }
                MAXZ_R=(float)Math.abs((MainActivity.DEBUG[1]));


                for(i=1;i<=9;i++) {
                    if(en_char[i]==1){
                if((float)(Math.abs(MainActivity.DEBUG[i]))>MAXZ_R)
                    MAXZ_R=(float)(Math.abs(MainActivity.DEBUG[i]));
                    }
                }
                MAXZ=(float)(MAXZ_R*1.1);//(float)Integer.parseInt(max_char.getText().toString())/100;
                MAXF=(float)(MAXZ_R*1.1);//(float)Integer.parseInt(min_char.getText().toString())/100;
                if(MAXZ<=MAXF) MAXZ=MAXF+1;
                    renderer.setYAxisMax(MAXZ);//纵坐标最大值
                    renderer.setYAxisMin(-MAXF);//纵坐标最小值

            }
        });


        //ui_handler.postDelayed(ui_task, 100);


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


        // timer.schedule(task, 2*70,70);//1s------------------------------------------------------------------------------图表时间修改
        timer.schedule(task, 0,50);//1s------------------------------------------------------------------------------图表时间修改
        ui_handler.postDelayed(ui_task, 50);
       // ui_handler.postDelayed(ui_task, 100);
    }//--end creat
}
//-------------
