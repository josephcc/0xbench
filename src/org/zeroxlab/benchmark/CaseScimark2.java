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

import java.util.ArrayList;

import org.zeroxlab.zeroxbenchmark.TesterScimark2;

public class CaseScimark2 extends Case {

    public static String LIN_RESULT = "LIN_RESULT";
    protected Bundle mInfo[];

    public static int Repeat = 1;
    public static int Round  = 1;

    CaseScimark2() {
        super("CaseScimark2", "org.zeroxlab.zeroxbenchmark.TesterScimark2", Repeat, Round);

        mType = "mflops";
        String [] _tmp = {
            "mflops",
            "numeric",
            "scientific",
        };
        mTags = _tmp;

        generateInfo();
    }

    public String getTitle() {
        return "Scimark2";
    }

    public String getDescription() {
        return "SciMark 2.0 is a Java benchmark for scientific and numerical computing. It measures several computational kernels and reports a composite score in approximate Mflops.";
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
        if (!couldFetchReport()) {
            return "No benchmark report";
        }

        String result = "\n";
        for (int i = 0; i < mInfo.length; i++) {
            result += TesterScimark2.bundleToString(mInfo[i]);
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
        String name = s.mName.replaceFirst("Scimark2:", "");
        for (int i = 0; i < length; i++) {
            total += mInfo[i].getDouble(name, 0.0);
        }
        return total/length;
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        ArrayList<String> subBenchmarks = new ArrayList<String>();
        subBenchmarks.add(TesterScimark2.COMPOSITE    );
        subBenchmarks.add(TesterScimark2.FFT          );
        subBenchmarks.add(TesterScimark2.SOR          );
        subBenchmarks.add(TesterScimark2.MONTECARLO   );
        subBenchmarks.add(TesterScimark2.SPARSEMATMULT);
        subBenchmarks.add(TesterScimark2.LU           );

        for (int i = 0; i < subBenchmarks.size(); i++) {
            String benchName = subBenchmarks.get(i);
            Scenario s = new Scenario(getTitle()+":"+benchName, mType, mTags);

            for (int j = 0; j < mInfo.length; j++) {
                double[] _tmp = mInfo[j].getDoubleArray(benchName + "array");
                for (int k = 0; k < _tmp.length; k++)
                    s.mResults.add(_tmp[k]);
            }

            scenarios.add(s);
        }

        return scenarios;
    }

    @Override
    protected boolean saveResult(Intent intent, int index) {
        Bundle info = intent.getBundleExtra(LIN_RESULT);
        if (info == null) {
            Log.i(TAG, "Weird! cannot find Scimark2Info");
            return false;
        } else {
            mInfo[index] = info;
        }

        return true;
    }
}
