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

import com.nea.nehe.lesson08.Run;
import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CaseNeheLesson08 extends Case {

    public static int mNeheRepeat = 2;
    public static int mNeheRound  = 1000;

    CaseNeheLesson08() {
        super("NeheLesson08", Run.FullName, mNeheRepeat, mNeheRound);

        mType = "3d-fps";

        String [] _tmp = {
            "render",
            "opengl",
            "nehe",
            "gltexture",
            "glblending",
            "3d",
        };
        mTags = _tmp;
    }

    public String getTitle() {
        return "OpenGL Blending";
    }

    public String getDescription() {
        return "A very famous OpenGL tutorial to demo OpenGL blending";
    }

    @Override
    public String getResultOutput() {
        if (!couldFetchReport()) {
            return "Nehe Lesson 8 has no report";
        }

        String result = "";
        float total = 0;
        int length = mResult.length;

        for (int i = 0; i < length; i++) {
            float fps = mCaseRound / (mResult[i] / 1000f); // milliseconds to seconds
            result += "Round " + i + ": fps = " + fps + "\n";
            total  += fps;
        }

        result += "Average: fps = " + (total/length) + "\n";
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
            double fps = (double) mCaseRound / second;
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
            float fps = (float)mCaseRound /  (mResult[i] / 1000f);
            s.mResults.add(((Float)fps).doubleValue());
        }

        scenarios.add(s);
        return scenarios;
    }

}
