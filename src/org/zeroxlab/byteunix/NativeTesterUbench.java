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

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.util.Collections;

public class NativeTesterUbench extends NativeTester {

    public final String TAG = "TesterUnixBench";
    public static final String REPORT = "REPORT";
    public static final String RESULT = "RESULT";
    public static final List<String> COMMANDS  = Arrays.asList(
        "dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10","dhry2reg 10",
        "whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double","whetstone-double",
        "execl 30","execl 30","execl 30",
        "pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10","pipe 10",
        "context1 10","context1 10","context1 10",
        "spawn 30","spawn 30","spawn 30",
        "syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10","syscall 10",

        "arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10","arithoh 10",
        "double 10","double 10","double 10","double 10","double 10","double 10","double 10","double 10","double 10","double 10",
        "float 10","float 10","float 10","float 10","float 10","float 10","float 10","float 10","float 10","float 10",
        "int 10","int 10","int 10","int 10","int 10","int 10","int 10","int 10","int 10","int 10",
        "long 10","long 10","long 10","long 10","long 10","long 10","long 10","long 10","long 10","long 10",
        "short 10","short 10","short 10","short 10","short 10","short 10","short 10","short 10","short 10","short 10",

        "fstime -c -t 30 -d ./ -b 1024 -m 2000","fstime -c -t 30 -d ./ -b 1024 -m 2000","fstime -c -t 30 -d ./ -b 1024 -m 2000",
        "fstime -c -t 30 -d ./ -b 256 -m 500","fstime -c -t 30 -d ./ -b 256 -m 500","fstime -c -t 30 -d ./ -b 256 -m 500",
        "fstime -c -t 30 -d ./ -b 4096 -m 8000","fstime -c -t 30 -d ./ -b 4096 -m 8000","fstime -c -t 30 -d ./ -b 4096 -m 8000",
        "fstime -r -t 30 -d ./ -b 1024 -m 2000","fstime -r -t 30 -d ./ -b 1024 -m 2000","fstime -r -t 30 -d ./ -b 1024 -m 2000",
        "fstime -r -t 30 -d ./ -b 256 -m 500","fstime -r -t 30 -d ./ -b 256 -m 500","fstime -r -t 30 -d ./ -b 256 -m 500",
        "fstime -r -t 30 -d ./ -b 4096 -m 8000","fstime -r -t 30 -d ./ -b 4096 -m 8000","fstime -r -t 30 -d ./ -b 4096 -m 8000",
        "fstime -w -t 30 -d ./ -b 1024 -m 2000","fstime -w -t 30 -d ./ -b 1024 -m 2000","fstime -w -t 30 -d ./ -b 1024 -m 2000",
        "fstime -w -t 30 -d ./ -b 256 -m 500","fstime -w -t 30 -d ./ -b 256 -m 500","fstime -w -t 30 -d ./ -b 256 -m 500",
        "fstime -w -t 30 -d ./ -b 4096 -m 8000","fstime -w -t 30 -d ./ -b 4096 -m 8000","fstime -w -t 30 -d ./ -b 4096 -m 8000"
    );
    public static final HashMap<String, String> commandToName = new HashMap<String, String>() {
        {
            put("dhry2reg 10", "dhry2reg");
            put("whetstone-double", "whetstone-double");
            put("execl 30", "execl");
            put("pipe 10", "pipe");
            put("context1 10", "context1");
            put("spawn 30", "spawn");
            put("syscall 10", "syscall");

            put("arithoh 10", "Arithoh");
            put("double 10", "Arithmetic:double");
            put("float 10", "Arithmetic:float");
            put("int 10", "Arithmetic:int");
            put("long 10", "Arithmetic:long");
            put("short 10", "Arithmetic:short");

            put("fstime -c -t 30 -d ./ -b 1024 -m 2000", "fstime");
            put("fstime -c -t 30 -d ./ -b 256 -m 500", "fsbuffer");
            put("fstime -c -t 30 -d ./ -b 4096 -m 8000", "fsdisk");
            put("fstime -r -t 30 -d ./ -b 1024 -m 2000", "fstime-r");
            put("fstime -r -t 30 -d ./ -b 256 -m 500", "fsbuffer-r");
            put("fstime -r -t 30 -d ./ -b 4096 -m 8000", "fsdisk-r");
            put("fstime -w -t 30 -d ./ -b 1024 -m 2000", "fstime-w");
            put("fstime -w -t 30 -d ./ -b 256 -m 500", "fsbuffer-w");
            put("fstime -w -t 30 -d ./ -b 4096 -m 8000", "fsdisk-w");
        }
    };

    
    @Override
    protected String getTag() {
        return "Native Ubench";
    };
    @Override
    protected String getPath() {
        return "/system/bin/bench_ubench_";
    }
    protected final List<String> getCommands() {
        return COMMANDS;
    }

    @Override
    protected boolean saveResult(Intent intent) {
        /* The strategy of this function is ported directly from the Run perl script of byte unix */
        Bundle bundle = new Bundle();
        StringBuilder report = new StringBuilder();
        for (String command: getCommands()) {
            if(!mSockets.containsKey(command))
                continue;
            report.append(commandToName.get(command));
            if(mSockets.get(command).trim().length() == 0)
                continue;
            String [] lines = mSockets.get(command).trim().split("\n");

            mSockets.remove(command);
            Log.i(TAG, "line0: " + lines[0]);
            String [] initFields = lines[0].split("[|]");
            // COUNT|2734838|1|lps|10.000082
            StringBuilder list = new StringBuilder();;
            Integer base = (int)Float.parseFloat(initFields[2]);
            String unit = initFields[3];
            ArrayList<Measure> measures = new ArrayList<Measure>();
            for(String line: lines) {
                String [] fields = line.trim().split("[|]");
                if (fields.length != 5) {
                    Log.w(TAG, "error line: " + line.trim());
                    continue;
                }
                Float count = Float.parseFloat(fields[1]);
                Float time = Float.parseFloat(fields[4]);
                if((!fields[3].equals(unit)) || ((int)Float.parseFloat(fields[2]) != base)){
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
            bundle.putString(command+"S", commandToName.get(command) + "&#040;" + unit + "&#041;");
            bundle.putString(command+"FA", list.toString().trim());
            Log.i(TAG, "command: " + command);
            Log.i(TAG, "save `" + command+"S" + "` -> " + commandToName.get(command) + "(" + unit + ")");
            Log.i(TAG, "save `" + command+"FA" + "` -> " + list.toString().trim());
            report.append(" " + list.toString().trim() + "\n");
        }
        bundle.putString(REPORT, report.toString());
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

