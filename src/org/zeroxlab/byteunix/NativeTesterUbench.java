/*
 * Copyright (C) 2010 0xlab - http://0xlab.org/
 * Authored by: Joseph Chang (bizkit) <bizkit@0xlab.org>
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

package org.opensolaris.hub.libmicro;

import org.zeroxlab.benchmark.*;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.util.Collections;

public class NativeTesterUbench extends NativeTester {

    public static final String REPORT = "REPORT";
    public static final String RESULT = "RESULT";
    private static final String Path = "/system/bin/bench_ubench_";
    public static final List<String> COMMANDS  = Arrays.asList(
        "dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10",
        "whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double",
        "execl 30","execl 30","execl 30",
        "fstime -c -t 30 -d /data/ -b 1024 -m 2000",
        "fstime -c -t 30 -d /data/ -b 256 -m 500",
        "fstime -c -t 30 -d /data -b 4096 -m 8000",
        "pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10",
        "context1 10","context1 10","context1 10",
        "spawn 30","spawn 30","spawn 30",
        "syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10",
        "looper 60 /system/bin/bench_ubench_multi.sh 1",
        "looper 60 /system/bin/bench_ubench_multi.sh 8"
    );
    public static final HashMap<String, String> commandToName = new HashMap<String, String>() {
        {
            put("dhry2reg 10", "dhry2reg");
            put("whetstone-double", "whetstone-double");
            put("execl 30", "execl");
            put("fstime -c -t 30 -d /data/ -b 1024 -m 2000", "fstime");
            put("fstime -c -t 30 -d /data/ -b 256 -m 500", "fsbuffer");
            put("fstime -c -t 30 -d /data -b 4096 -m 8000", "fsdisk-w");
            put("pipe 10", "pipe");
            put("context1 10", "context1");
            put("spawn 30", "spawn");
            put("syscall 10", "syscall");
            put("looper 60 /system/bin/bench_ubench_multi.sh 1", "shell1");
            put("looper 60 /system/bin/bench_ubench_multi.sh 8", "shell8");
        }
    };

    
    @Override
    protected String getTag() {
        return "Native Ubench";
    };
    protected final List<String> getCommands() {
        return COMMANDS;
    }

    @Override
    protected boolean saveResult(Intent intent) {
        Bundle bundle = new Bundle();
//        StringBuilder report = new StringBuilder();
        for (String command: getCommands()) {
//            report.append(mStdErrs.get(command));
//            report.append("---------------------------\n");
//            report.append(mStdOuts.get(command));
//            report.append("---------------------------\n");
            if(!mSockets.containsKey(command))
                continue;
            String [] lines = mSockets.get(command).trim().split("\n");
            StringBuilder list = new StringBuilder();;
            Integer base = (int)Float.parseFloat(lines[0].trim().split("|")[1]);
            String unit = lines[0].trim().split("|")[2];
            ArrayList<Measure> measures = new ArrayList<Measure>();
            for(String line: lines) {
                String [] sp = line.trim().split("|");
                if (sp.length != 4) {
                    Log.w(TAG, "error line: " + line.trim());
                    continue;
                }
                Float count = Float.parseFloat(sp[0]);
                Float time = Float.parseFloat(sp[3]);
                if((!sp[2].equals(unit)) || ((int)Float.parseFloat(sp[1]) != base)){
                    Log.w(TAG, "error line: " + line.trim());
                    continue;
                }
                measures.add(new Measure(count, time));
            }
            Collections.sort(measures);
            List<Measure> topMeasures = measures.subList(0, ( (measures.size()/3)==0? 1: measures.size()/3));
            for(Measure measure: topMeasures) {
                if(base == 0) {
                    list.append(measure.count + " ");
                } else {
                    list.append( (measure.count/(measure.time/base)) + " ");
                }
            }
            bundle.putString(command+"S", commandToName.get(command) + "(" + unit + ")");
            bundle.putString(command+"FA", list.toString().trim());
        }
//        bundle.putString(REPORT, report.toString());
        intent.putExtra(RESULT, bundle);
        return true;
    }
}

class Measure implements Comparable {
    public Float count;
    public Float time;

    public Measure (Float count, Float time) {
        super();
        this.count = count;
        this.time = time;
    }
    

    public int compareTo (Object o) {
        if (((Measure)o).count > this.count)
            return 1;
        if (((Measure)o).count < this.count)
            return -1;
        return 0;
    }
}

