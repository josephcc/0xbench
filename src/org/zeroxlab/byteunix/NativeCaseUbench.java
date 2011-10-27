/*
 * Copyright (C) 2011 Linaro Limited
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
 *
 * Authored by Joseph Chang (bizkit) <bizkit@0xlab.org>
 */

package org.zeroxlab.byteunix;

import org.zeroxlab.zeroxbenchmark.*;

import android.util.Log;

import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import org.zeroxlab.byteunix.NativeTesterUbench;

public class NativeCaseUbench  extends Case {

    public static String LIN_RESULT = "LIN_RESULT";
    protected Bundle mInfo[];

    public static int Repeat = 1;
    public static int Round  = 1;

    public NativeCaseUbench() {
        super("NativeCaseUbench", "org.zeroxlab.byteunix.NativeTesterUbench", Repeat, Round);

        mType = "ByteUnix";
        String [] _tmp = {
            "system", 
        };
        mTags = _tmp;

        generateInfo();
    }

    public String getTitle() {
        return "UnixBench";
    }

    public String getDescription() {
        return "(Requires root and pre-deployed binaries) UnixBench is the original BYTE UNIX benchmark suite, updated and revised by many people over the years. Takes about 30 minutes to run.";
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

        return mInfo[0].getString(NativeTesterUbench.REPORT);
    }

    @Override
    public ArrayList<Scenario> getScenarios () {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

        Bundle bundle = mInfo[0]; // only 1 run
        for(String command: NativeTesterUbench.COMMANDS) {
            if(!bundle.containsKey(command+"S") || !bundle.containsKey(command+"FA"))
                continue;
            String name = bundle.getString(command+"S");
            String results = bundle.getString(command+"FA");
            bundle.remove(command+"S");
            bundle.remove(command+"FA");
            if(name == null || results == null)
                continue;
            ArrayList<String> _mTags = new ArrayList<String>();
            int space = command.indexOf(" ");
            if(space > 0)
                _mTags.add((String)("exe:" + command.substring(0, command.indexOf(" "))));
            else
                _mTags.add((String)("exe:" + command));
            //TODO add unit as tag
            Log.i(TAG, "name: " + name);
            _mTags.add("unit:" + name.substring(name.indexOf("&#040;")+6, name.indexOf("&#041;")));

            String [] __mTags =  (String[])(_mTags.toArray(new String[_mTags.size()]));
            Scenario s = new Scenario(name, mType, __mTags, true);
            s.mStringResults = results;
            scenarios.add(s);
        }

        return scenarios;
    }

    @Override
    protected boolean saveResult(Intent intent, int index) {
        Bundle info = intent.getBundleExtra(NativeTesterUbench.RESULT);
        if (info == null) {
            Log.i(TAG, "Cannot find LibUbenchInfo");
            return false;
        } else {
            mInfo[index] = info;
        }

        return true;
    }
}
