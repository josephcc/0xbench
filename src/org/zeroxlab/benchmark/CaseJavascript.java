/*
 * Copyright (C) 2011 0xlab - http://0xlab.org/
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
 *
 * Authored by Julian Chu <walkingice@0xlab.org>
 */

package org.zeroxlab.benchmark;

import java.util.ArrayList;

import android.util.Log;

import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;

public class CaseJavascript extends Case {

    public static String SUNSPIDER_RESULT = "SUNSPIDER_RESULT";
    public static String SUNSPIDER_TOTAL  = "SUNSPIDER_TOTAL";

    public static int sRepeat = 1;
    public static int sRound  = 1;

    private double mTotal = 0.0;

    protected String[] mJSResults;
    CaseJavascript() {
        super("CaseJavascript", "org.zeroxlab.benchmark.TesterJavascript", sRepeat, sRound);
        mType = "msec";
        mTags = new String[]{new String("javascript")};
    }

    public String getTitle() {
        return "SunSpider";
    }

    public String getDescription() {
        return "This benchmark tests the core JavaScript language only, not the DOM or other browser APIs. It is designed to compare different versions of the same browser, and different browsers to each other.";
    }

    @Override
    public void clear() {
        super.clear();
        mJSResults = new String[sRepeat];
    }

    @Override
    public void reset() {
        super.reset();
        mJSResults = new String[sRepeat];
    }

    @Override
    public String getResultOutput() {
        String result = "\n";
        for (int i = 0; i < mJSResults.length; i++) {
            result += mJSResults[i];
            result += "\n";
        }
        return result;
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        Scenario s = new Scenario(getTitle(), mType, mTags);
        s.mLog = getResultOutput();
        s.mResults.add(mTotal);

        scenarios.add(s);

        return scenarios;
    }

    @Override
    protected boolean saveResult(Intent intent, int index) {
        String result = intent.getStringExtra(SUNSPIDER_RESULT);
        mTotal = intent.getDoubleExtra(SUNSPIDER_TOTAL, 0.0);

        if (result == null) {
            Log.e(TAG, "Weird! cannot find SunSpiderInfo");
            return false;
        } else {
            mJSResults[index] = result;
        }

        return true;
    }
}
