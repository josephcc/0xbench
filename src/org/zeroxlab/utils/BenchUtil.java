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

package org.zeroxlab.utils;

import android.util.Log;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

public class BenchUtil extends Util {

    public final static String ACTIVITY_SETTINGS = "org.zeroxlab.zeroxbenchmark.ActivitySettings";
    public final static String PREF_FILENAME = "ZeroxBench_Preference";
    public final static String KEY_RESULT_CUSTOM_DIR = "KEY_RESULT_CUSTOM_DIR";
    public final static String KEY_RESULT_SELECTION = "KEY_RESULT_SELECTION";

    private final static String EXTERNAL_DIR = Environment.getExternalStorageDirectory().getPath();
    public final static String DEFAULT_RESULT_DIR = EXTERNAL_DIR;

    public final static int RESULT_SELECTION_SDCARD = 0;
    public final static int RESULT_SELECTION_CUSTOM = 1;

    public static void launchSettings(Activity parent) {
        launchActivity(parent, ACTIVITY_SETTINGS);
    }

    public static int getResultSelection(Context context) {
        return Util.restorePrefInt(context, PREF_FILENAME, KEY_RESULT_SELECTION, RESULT_SELECTION_SDCARD);
    }

    public static void setResultSelection(Context context, int selection) {
        Util.storePrefInt(context, PREF_FILENAME, KEY_RESULT_SELECTION, selection);
    }

    public static String getCustomDir(Context context) {
        String dir = Util.restorePrefString(context, PREF_FILENAME, KEY_RESULT_CUSTOM_DIR, DEFAULT_RESULT_DIR);
        return dir;
    }

    public static void setCustomDir(Context context, String dir) {
        Util.storePrefString(context, PREF_FILENAME, KEY_RESULT_CUSTOM_DIR, dir);
    }

    public static String getResultDir(Context context) {
        int selection = getResultSelection(context);
        if (selection == RESULT_SELECTION_SDCARD) {
            return EXTERNAL_DIR;
        } else if (selection == RESULT_SELECTION_CUSTOM) {
            return getCustomDir(context);
        }

        Log.e("Benchmark", "BenchUtils - unknown selection");
        return DEFAULT_RESULT_DIR;
    }
}
