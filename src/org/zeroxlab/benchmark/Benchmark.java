/*
 * Copyright (C) 2010 0xlab - http://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zeroxlab.benchmark;

import android.util.Log;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.nio.*;
import java.io.*;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.StringBuffer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.os.SystemClock;
import android.app.ProgressDialog;

import org.opensolaris.hub.libmicro.*;

import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import android.os.Environment;

/* Construct a basic UI */
public class Benchmark extends Activity implements View.OnClickListener {

    public final static String TAG     = "Benchmark";
    public final static String PACKAGE = "org.zeroxlab.benchmark";

    private final static File SDCARD = Environment.getExternalStorageDirectory();
    private final static String mOutputFile = "0xBenchmark";

    private static String mXMLResult;
    private final static String mOutputXMLFile = "0xBenchmark.xml";

    private Button   mRun;
    private Button   mShow;
    private CheckBox mCheckList[];
    private TextView mDesc[];
    private TextView mBannerInfo;

    private ScrollView   mScrollView;
    private LinearLayout mLinearLayout;

    LinkedList<Case> mCases;
    boolean mTouchable = true;

    private WakeLock mWakeLock;

    @Override
    protected void onDestroy() {
        super.onPause();
        mWakeLock.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        mWakeLock.acquire();

        setContentView(R.layout.main);
        mCases = new LinkedList<Case>();
        Case arith  = new CaseArithmetic();
        Case scimark2  = new CaseScimark2();
        Case canvas = new CaseCanvas();
        Case glcube = new CaseGLCube();
        Case circle = new CaseDrawCircle();
        Case nehe08 = new CaseNeheLesson08();
        Case nehe16 = new CaseNeheLesson16();
        Case teapot = new CaseTeapot();
        Case gc     = new CaseGC();
        Case libMicro = new NativeCaseMicro();

        // mflops
        mCases.add(arith);
        mCases.add(scimark2);
        // 2d
        mCases.add(canvas);
        mCases.add(circle);
        // 3d
        mCases.add(glcube);
        mCases.add(nehe08);
        mCases.add(nehe16);
        mCases.add(teapot);
        // vm
        mCases.add(gc);
        // native
        mCases.add(libMicro);

        initViews();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("AUTO", false)) {
            ImageView head = (ImageView)findViewById(R.id.banner_img);
            head.setImageResource(R.drawable.icon_auto);
            mTouchable = false;
            initAuto();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mTouchable) super.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mTouchable) super.dispatchKeyEvent(event);
        return true;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        if (mTouchable) super.dispatchTrackballEvent(event);
        return true;
    }

    private void _checkTagCase(String [] Tags) {
        Arrays.sort(Tags);
        for (int i=0; i<mCheckList.length; i++) {
            String [] caseTags = mCases.get(i).mTags;
            for (String t: caseTags) {
                int search = Arrays.binarySearch(Tags, t);
                if ( search  >= 0) 
                    mCheckList[i].setChecked(true);
            }
        }
    }

    private void _checkCatCase(String [] Cats) {
        Arrays.sort(Cats);
        for (int i=0; i<mCheckList.length; i++) {
            int search = Arrays.binarySearch(Cats, mCases.get(i).mType);
            if ( search  >= 0) 
                mCheckList[i].setChecked(true);
        }
    }

    private void _checkAllCase(boolean check) {
        for (int i=0; i<mCheckList.length; i++) 
            mCheckList[i].setChecked(check);
    }

    private void initAuto() {
        Intent intent = getIntent();
        String TAG = intent.getStringExtra("TAG");
        String CAT = intent.getStringExtra("CAT");


        _checkAllCase(false);
        if (TAG != null)
            _checkTagCase( TAG.split(",") );
        if (CAT != null)
            _checkCatCase( CAT.split(",") );
        if (TAG == null && CAT == null)
            _checkAllCase(true);
        final Handler h = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1234)
                    onClick(mRun);
            }
    };
    
    final ProgressDialog dialog = new ProgressDialog(this).show(this, "Starting Benchmark", "Please wait...", true, false);
    new Thread() {
            public void run() {
                SystemClock.sleep(1000);
                dialog.dismiss();
                Message m = new Message();
                m.what = 0x1234;
                h.sendMessage(m);
            }
        }.start();
        mTouchable = true;
    }

    private void initViews() {
        mRun = (Button)findViewById(R.id.btn_run);
        mRun.setOnClickListener(this);

        mShow = (Button)findViewById(R.id.btn_show);
        mShow.setOnClickListener(this);
        mShow.setClickable(false);

        mLinearLayout = (LinearLayout)findViewById(R.id.list_container);

        mBannerInfo = (TextView)findViewById(R.id.banner_info);
        mBannerInfo.setText("Hello!\nSelect cases to Run.\nUploaded results:\nhttp://0xbenchmark.appspot.com");

        int length = mCases.size();
        mCheckList = new CheckBox[length];
        mDesc      = new TextView[length];
        boolean gray = true;
        for (int i = 0; i < length; i++) {
            mCheckList[i] = new CheckBox(this);
            mCheckList[i].setText(mCases.get(i).getTitle());
            mLinearLayout.addView(mCheckList[i]);
            mDesc[i] = new TextView(this);
            mDesc[i].setText(mCases.get(i).getDescription());
            mDesc[i].setTextSize(mDesc[i].getTextSize() - 2);
            mDesc[i].setPadding(42, 0, 10, 10);
            mLinearLayout.addView(mDesc[i]);

            if (gray) {
            int color = 0xFF333333; //ARGB
            mCheckList[i].setBackgroundColor(color);
            mDesc[i].setBackgroundColor(color);
            }

            gray = !gray;
        }
    }

    public void onClick(View v) {
        if (v == mRun) {
            int numberOfCaseChecked = 0;
            for (int i = 0; i < mCheckList.length; i++) {
                if (mCheckList[i].isChecked()) {
                    mCases.get(i).reset();
                    numberOfCaseChecked++;
                } else {
                    mCases.get(i).clear();
                }
            }
            if(numberOfCaseChecked>0)
                runCase(mCases);
        } else if (v == mShow) {
            String result = getResult();
            Log.i(TAG,"\n\n"+result+"\n\n");
            writeToSDCard(mOutputFile, result);
            Intent intent = new Intent();
            intent.putExtra(Report.REPORT, result);
            intent.putExtra(Report.XML, mXMLResult);
            intent.setClassName(Report.packageName(), Report.fullClassName());
            startActivity(intent);
        }
    }

    public void runCase(LinkedList<Case> list) {
        Case pointer = null;
        boolean finish = true;
        for (int i = 0; i < list.size(); i++) {
            pointer = list.get(i);
            if (!pointer.isFinish()) {
                finish = false;
                break;
            }
        }

        if (finish) {
            mBannerInfo.setText("Benchmarking complete.\nClick Show to upload.\nUploaded results:\nhttp://0xbenchmark.appspot.com");
            String result = getResult();
            writeToSDCard(mOutputFile, result);

            final ProgressDialog dialogGetXml = new ProgressDialog(this).show(this, "Generating XML Report", "Please wait...", true, false);
            new Thread() {
                public void run() {
                    mXMLResult = getXMLResult();
                    Log.e(TAG, "XML: " + mXMLResult);
                    writeToSDCard(mOutputXMLFile, mXMLResult);
                    mShow.setClickable(true);
                    onClick(mShow);
                    mTouchable = true;
                    dialogGetXml.dismiss();
                }
            }.start();
        } else {
            Intent intent = pointer.generateIntent();
            if (intent != null) {
            startActivityForResult(intent, 0);
            }
        }
    }

    public String getXMLResult() {
        if (mCases.size() == 0)
            return "";

        Date date = new Date();
        //2010-05-28T17:40:25CST
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");

        String xml = "";
        xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<result";
        xml += " executedTimestamp=\"" + sdf.format(date) + "\"";
        xml += " manufacturer=\"" + Build.MANUFACTURER.replace(' ', '_') + "\"";
        xml += " model=\"" + Build.MODEL.replace(' ', '_') + ":" + Build.DISPLAY + "\"";
        xml += " buildTimestamp=\"" + sdf.format(new Date(Build.TIME)) + "\"";

        try { // read kernel version
            BufferedReader procVersion = new BufferedReader( new FileReader("/proc/version") );
            StringBuffer sbuff = new StringBuffer();
            String tmp;
            while ( (tmp = procVersion.readLine()) != null)
                sbuff.append(tmp);
            procVersion.close();
            tmp = sbuff.toString().replace("[\n\r]+", " ").replace(" +", ".");
            xml += " version=\"" + tmp + "\"";
        } catch (IOException e){
            Log.e(TAG, "opening /proc/version failed: " + e.toString());
        }

        try { // read and parse cpu info
            BufferedReader procVersion = new BufferedReader( new FileReader("/proc/cpuinfo") );
            StringBuffer sbuff = new StringBuffer();
            String tmp;
            while ( (tmp = procVersion.readLine()) != null)
                sbuff.append(tmp + "\n");
            procVersion.close();

            tmp = sbuff.toString();

            sbuff = new StringBuffer();

            Pattern p1 = Pattern.compile("(Processor\\s*:\\s*(.*)\\s*[\n\r]+)");
            Matcher m1 = p1.matcher(tmp);
            if (m1.find()) sbuff.append(m1.group(2));

            Pattern p2 = Pattern.compile("(Hardware\\s*:\\s*(.*)\\s*[\n\r]+)");
            Matcher m2 = p2.matcher(tmp);
            if (m2.find()) sbuff.append(":"+m2.group(2));

            Pattern p3 = Pattern.compile("(Revision\\s*:\\s*(.*)\\s*[\n\r]+)");
            Matcher m3 = p3.matcher(tmp);
            if (m3.find()) sbuff.append(":"+m3.group(2));

            Log.e(TAG, sbuff.toString());
            xml += " cpu=\"" + sbuff.toString() + "\"";
        } catch (IOException e){
            Log.e(TAG, "opening /proc/version failed: " + e.toString());
        }

        xml += ">";

        Case mycase;
        for (int i = 0; i < mCases.size(); i++) {
            mycase = mCases.get(i);
            xml += mycase.getXMLBenchmark();
        }

        xml += "</result>";
        return xml;
    }

    public String getResult() {
        String result = "";
        Case mycase;
        for (int i = 0; i < mCases.size(); i++) {
            mycase = mCases.get(i);
            if ( !mycase.couldFetchReport() ) continue;
            result += "============================================================\n";
            result += mycase.getTitle() + "\n";
            result += "------------------------------------------------------------\n";
            result += mycase.getBenchmark().trim() + "\n";
        }
        result += "============================================================\n";

        return result;
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            Log.i(TAG,"oooops....Intent is null");
            return;
        }

        Case mycase;
        for (int i = 0; i < mCases.size(); i++) {
            mycase = mCases.get(i);
            if (mycase.realize(data)) {
            mycase.parseIntent(data);
            break;
            }
        }
        runCase(mCases);
    }

    private boolean writeToSDCard(String filename, String output) {
        if ( !SDCARD.canWrite() ) {
            Log.i(TAG, "Permission denied, maybe SDCARD mounted to PC?");
            return false;
        }

        File file = new File(SDCARD, filename);

        if (file.exists()) {
            Log.i(TAG, "File exists, delete SDCARD/" + filename);
            file.delete();
        }

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(output.getBytes());
            fos.flush();
        } catch (Exception e) {
            Log.i(TAG, "Write Failed.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
