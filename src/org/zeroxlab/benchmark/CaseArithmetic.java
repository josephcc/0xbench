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

import java.util.ArrayList;

import android.util.Log;

import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;

import org.zeroxlab.zeroxbenchmark.TesterArithmetic;

public class CaseArithmetic extends Case {

    public static String LIN_RESULT = "LIN_RESULT";
    protected Bundle mInfo[];

    public static int Repeat = 1;
    public static int Round  = 3;

    CaseArithmetic() {
        super("CaseArithmetic", "org.zeroxlab.zeroxbenchmark.TesterArithmetic", Repeat, Round);

        mType = "mflops";
        String [] _tmp = {
            "numeric",
            "mflops",
            "scientific",
        };
        mTags = _tmp;

        generateInfo();
    }

    public String getTitle() {
        return "Linpack";
    }

    public String getDescription() {
        return "The Linpack Benchmark is a numerically intensive test that has been used for years to measure the floating point performance of computers.";
    }

    private void generateInfo() {
        mInfo = new Bundle[Repeat];
        for (int i = 0; i < mInfo.length; i++) {
            mInfo[i] = new Bundle();
        }
    }

    @Override
    public void clear() {
        super.clear();
        generateInfo();
    }

    @Override
    public void reset() {
        super.reset();
        generateInfo();
    }

    @Override
    public String getResultOutput() {
        String result = "\n";
        for (int i = 0; i < mInfo.length; i++) {
            result += TesterArithmetic.bundleToString(mInfo[i]);
            result += "\n";
        }
        return result;
    }

    /*
     *  Get Average Benchmark
     */
    public double getBenchmark(Scenario s) {
        double total = 0;
        int length = mInfo.length;
        for (int i = 0; i < length; i++) {
            total  += mInfo[i].getDouble(TesterArithmetic.MFLOPS);
        }
        return total / length;
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        Scenario s = new Scenario(getTitle(), mType, mTags);
        s.mLog = getResultOutput();
        for (int i=0; i<mInfo.length; i++)
            s.mResults.add(mInfo[i].getDouble(TesterArithmetic.MFLOPS));

        scenarios.add(s);

        return scenarios;
    }

    @Override
    protected boolean saveResult(Intent intent, int index) {
        Bundle info = intent.getBundleExtra(LIN_RESULT);
        if (info == null) {
            Log.i(TAG, "Weird! cannot find LinpackInfo");
            return false;
        } else {
            mInfo[index] = info;
        }

        return true;
    }
}
