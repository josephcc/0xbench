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

public class CaseCanvas extends Case{

    public static int CanvasRound = 300;

    CaseCanvas() {
        super("CaseCanvas", TesterCanvas.getFullClassName(), 3, CanvasRound);

        mType = "2d-fps";
        String [] _tmp = {
            "2d",
            "render",
            "view",
        };
        mTags = _tmp;

    }

    public String getTitle() {
        return "Draw Canvas";
    }

    public String getDescription() {
        return "call canvas.drawRGB to fill the canvas repeatedly. " +
               "It redraw for " + CanvasRound + " times";
    }

    @Override
    public String getResultOutput() {
        if (!couldFetchReport()) {
            return "Cavas has no report";
        }

        String result = "";
        float total = 0;
        int length = mResult.length;

        for (int i = 0; i < length; i++) {
            float second = (mResult[i] / 1000f);
            float fps = (float)mCaseRound / second; // milliseconds to seconds
            result += "Round " + i +" fps = " + fps + "\n";
            total  += fps;
        }

        result += "Average: fps = " + ((float)total/length) + "\n";
        return result;
    }

    /*
     *  Get Average Benchmark
     */
    public double getBenchmark(Scenario s) {
        double total = 0;
        int length = mResult.length;
        for (int i = 0; i < length; i++) {
            double second = (mResult[i] / 1000f);
            double fps = (double)mCaseRound / second;
            total  += fps;
        }
        return total / length;
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        Scenario s = new Scenario(getTitle(), mType, mTags);
        s.mLog = getResultOutput();
        for (int i = 0; i < mResult.length; i++) {
            float second = (mResult[i] / 1000f);
            float fps = (float)mCaseRound / second;
            s.mResults.add(((Float)fps).doubleValue());
        }

        scenarios.add(s);
        return scenarios;
    }

}
