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

package org.zeroxlab.zeroxbenchmark;

import android.util.Log;

import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import java.nio.*;
import java.util.ArrayList;

public class CaseGC extends Case {

    String mStringBuf = "";
    public static String GCRESULT = "GC_RESULT";
    public static String TIME = "GC_RUNTIME";
    public static double time = 0.0;

    CaseGC() {
        super("CaseGC", "org.zeroxlab.zeroxbenchmark.TesterGC", 1, 1); // GC benchmark only run once

        mType = "msec";
        String [] _tmp = {
            "dalvik",
            "garbagecollection",
        };
        mTags = _tmp;
    }

    public String getTitle() {
        return "Garbage Collection";
    }

    public String getDescription() {
        return "It create long-live binary tree of depth and array of doubles to test GC";
    }

    @Override
    public void clear() {
        super.clear();
        mStringBuf = "";
    }

    @Override
    public void reset() {
        super.reset();
        mStringBuf = "";
    }

    @Override
    public String getResultOutput() {

        if (!couldFetchReport()) {
            return "No benchmark report";
        }

        return mStringBuf;
    }

    public double getBenchmark(Scenario s) {
        return time;
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        Scenario s = new Scenario(getTitle(), mType, mTags);
        s.mLog = getResultOutput();
        s.mResults.add(time);
        scenarios.add(s);

        return scenarios;
    }

    @Override
    protected boolean saveResult(Intent intent, int index) {
        String result = intent.getStringExtra(GCRESULT);
        time = intent.getDoubleExtra(TIME, 0.0);

        if (result == null || result.equals("")) {
            mStringBuf += "\nReport not found\n";
        } else {
            mStringBuf += "\n"+result+"\n";
        }

        return true;
    }
}
