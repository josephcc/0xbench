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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class Util {
    public final static int PREF_MODE = Context.MODE_PRIVATE;
    public final static boolean DEFAULT_PREF_BOOLEAN = false;
    public final static int     DEFAULT_PREF_INT     = -1;
    public final static String  DEFAULT_PREF_STRING  = "";

    public static void launchActivity(Activity parent, String targetFullName) {
        final Intent intent = new Intent();
        intent.setClassName(parent, targetFullName);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        parent.startActivity(intent);
    }

    public static void storePrefBool(Context context, String name, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        pref.edit().putBoolean(key, value).commit();
    }

    public static void storePrefInt(Context context, String name, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        pref.edit().putInt(key, value).commit();
    }

    public static void storePrefString(Context context, String name, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        pref.edit().putString(key, value).commit();
    }

    public static boolean restorePrefBool(Context context, String name, String key, boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        boolean value = pref.getBoolean(key, defaultValue);
        return value;
    }

    public static int restorePrefInt(Context context, String name, String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        int value = pref.getInt(key, defaultValue);
        return value;
    }

    public static String restorePrefString(Context context, String name, String key, String defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        String value = pref.getString(key, defaultValue);
        return value;
    }

    public static boolean restorePrefBool(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        boolean value = pref.getBoolean(key, DEFAULT_PREF_BOOLEAN);
        return value;
    }

    public static int restorePrefInt(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        int value = pref.getInt(key, DEFAULT_PREF_INT);
        return value;
    }

    public static String restorePrefString(Context context, String name, String key) {
        SharedPreferences pref = context.getSharedPreferences(name, PREF_MODE);
        String value = pref.getString(key, DEFAULT_PREF_STRING);
        return value;
    }
}
