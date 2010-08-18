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

import android.os.SystemClock;

import org.zeroxlab.benchmark.Scenario;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import java.nio.*;

import java.util.ArrayList;

public abstract class Case{
    protected String TAG = "Case";

    protected String PACKAGE = Benchmark.PACKAGE;
    protected String TESTER;
    
    /* If mRepeatMax = 3, mRepeatNow will count from 0 to 2*/
    private int mRepeatMax = 1;
    private int mRepeatNow;
    protected boolean mInvolved;
    protected long[] mResult;

    private final static String SOURCE = "SOURCE";
    private final static String INDEX  = "INDEX";
    private final static String RESULT = "RESULT";
    private final static String ROUND  = "ROUND";
    protected int mCaseRound = 30;

    public String mType = "";
    public String[] mTags = {};

    /**
     * Constructor to generate instance.
     *
     * It defines the Case as "Please run Tester for N round, repeat N times"
     * @param tag The tag name of the subclass Case. It is generally the Subclass Name
     * @param tester The taget tester be used by subclass Case. It should be full class name.
     * @param repeat The tester will run *repeat* times.
     * @param round To tell tester to run itself as *round* round.
     */
    protected Case(String tag, String tester, int repeat, int round) {
        TAG    = tag;
        TESTER = tester;
        mRepeatMax = repeat;
        mCaseRound = round;
        reset();
    }

    abstract public String getDescription();
    abstract public String getTitle();

    abstract public ArrayList<Scenario> getScenarios (); 


    public final static void putRound(Intent intent, int round) {
        intent.putExtra(ROUND, round);
    }

    public final static void putIndex(Intent intent, int index) {
        intent.putExtra(INDEX, index);
    }

    public final static void putSource(Intent intent, String source) {
        intent.putExtra(SOURCE, source);
    }

    public final static void putResult(Intent intent, long result) {
        intent.putExtra(RESULT, result);
    }

    public final static int getRound(Intent intent) {
        return intent.getIntExtra(ROUND, 100);
    }

    public final static int getIndex(Intent intent) {
        return intent.getIntExtra(INDEX, -1);
    }

    public final static String getSource(Intent intent) {
        String source = intent.getStringExtra(SOURCE);
        if (source == null) {
            return "unknown";
        }

        if (source.equals("")) {
            return "unknown";
        }

        return source;
    }

    public final static long getResult(Intent intent) {
        long defaultResult = -1;
        return intent.getLongExtra(RESULT, defaultResult);
    }

    public String getTag() {
        return TAG;
    }

    protected Intent generateIntent() {
        /* if run out of the repeat times, go back directly */
        if (mRepeatNow >= mRepeatMax) {
            return null;
        }

        Intent intent = new Intent();
        intent.setClassName(PACKAGE, TESTER);
        Case.putRound(intent, mCaseRound);
        Case.putSource(intent, TAG);
        Case.putIndex(intent, mRepeatNow);

        mRepeatNow = mRepeatNow + 1;

        return intent;
    }

    public void clear() {
        mResult = new long[mRepeatMax];
        mRepeatNow = mRepeatMax; // no more repeating times
        mInvolved  = false;
    }

    /* Reset the repeat time to default value. clear result */
    public void reset() {
        mResult = new long[mRepeatMax];
        mRepeatNow = 0;
        mInvolved  = true;
    }

    public boolean isFinish() {
        /* If mRepeatMax = 3, mRepeatNow will count from 0 to 2*/
        return (mRepeatNow >= mRepeatMax);
    }

    /** To read the SOURCE of this intent to see if this intent belong to this case
     *
     * @return return True if this intent belong to this case, otherwise return false
     */
    public boolean realize(Intent intent) {
        if (intent == null) {
            Log.i(TAG, "Intent is null");
            return false;
        }

        String source = Case.getSource(intent);
        if (source == null || source.equals("")) {
            return false;
        }

        if (source.equals(TAG)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean parseIntent(Intent intent) {
        if (intent == null) {
            Log.i(TAG, "Intent is null");
            return false;
        }

        String tag = Case.getSource(intent);

        if (tag == null || !tag.equals(TAG)) {
            Log.i(TAG,"Unknown intent, cannot parse it");
            return false;
        }

        int  index = Case.getIndex(intent);
        if (index >= mRepeatMax) {
            Log.i(TAG,"Ooooops index >= mRepeatMax("+mRepeatMax+"), how come?");
            return false;
        }

        return saveResult(intent, index);
    }

    /**
     * To Save the result from Tester into this Case
     * If subclass has its own way to analysis result, override this method
     *
     * @param intent The intent will be analysis
     * @param index The repeating time of this intent. (Tester might repeat N times)
     * @return return True if analysis sucessfully
     */
    protected boolean saveResult(Intent intent, int index) {
        long result = Case.getResult(intent);

        if (result == -1) {
            Log.i(TAG,"Oooops! result is " + result);
            return false;
        }

        mResult[index] = result;
        return true;
    }

    public boolean couldFetchReport() {
        if (!isFinish()) {
            return false;
        }

        if (mInvolved == false) {
            return false;
        }

        return true;
        }

        public String getBenchmark() {

        if (!couldFetchReport()) {
            return "No benchmark report";
        }

        String result = "";
        long total = 0;
        int length = mResult.length;
        for (int i = 0; i < length; i++) {
            total  += mResult[i];
            result += "round " + i + ":" + mResult[i] + "\n";
        }

        result += "Average:" + (total/length) + "\n";
        return result;
    }

    public String getXMLBenchmark() {
        if (!couldFetchReport()) {
            Log.e(TAG, "cannot fetch report: " + getTitle() + " : " + isFinish() + " : " + mInvolved);
            return "";
        }

        String result = "";

        ArrayList<Scenario> scenarios = getScenarios();
        Log.e(TAG, "length of scenarios: " + scenarios.size());

        for (Scenario s: scenarios) {
            if (s == null){
                Log.e(TAG, "Scenario is null");
                continue;
            }
            String _result = "";
            _result += "<scenario";
            _result += " benchmark=\"" + s.mName.replace(" ", "") + "\"";
            _result += " unit=\"" + s.mType + "\"";
            _result += " tags=\"";
            for (String tag: s.mTags) 
                _result += tag + ",";
            _result += "\"";
            _result += ">";
            if(!s.useStringResults) {
                Double total = 0.0;
                for (Double value: s.mResults) {
                    _result += value + " ";
                    total += value;
                }
                _result += "</scenario>";
                if (total == 0){
                    Log.e(TAG, "_result total is 0: ");
                    Log.e(TAG, _result);
                    continue;
                }
            } else {
                if(s.mStringResults == null || s.mStringResults.length() == 0) {
                    Log.e(TAG, "string results is empty: " + s.mStringResults);
                    continue;
                }
                _result += s.mStringResults;
                _result += "</scenario>";
            }
            result += _result;
        }
        return result;
    }
}

