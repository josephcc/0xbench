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

package org.opensolaris.hub.libmicro;

import org.zeroxlab.zeroxbenchmark.*;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class NativeTesterMicro extends NativeTester {

    public final String TAG = "TesterLibMicro";
    private final double ERROR_VALUE = -1.0;
    public static final String REPORT = "REPORT";
    public static final String RESULT = "RESULT";
    private static final String Opts = "-E -C 70 -L -S -W";
    public static final List<String> COMMANDS  = Arrays.asList(

        "getpid " + Opts + " -N getpid -I 5",

        "getenv " + Opts + " -N getenv   -s 100 -I 100",
        "getenv " + Opts + " -N getenvT2   -s 100 -I 100 -T 2",

        "gettimeofday " + Opts + " -N gettimeofday",

        "log " + Opts + " -N log  -I 20",

        "exp " + Opts + " -N exp  -I 20",

        "lrand48 " + Opts + " -N lrand48",

        "memset " + Opts + " -N memset_10	-s 10	-I 10 ",
        "memset " + Opts + " -N memset_256	-s 256	-I 20",
        "memset " + Opts + " -N memset_256_u	-s 256	 -a 1 -I 20 ",
        "memset " + Opts + " -N memset_1k	-s 1k	 -I 100",
        "memset " + Opts + " -N memset_4k    -s 4k    -I 250",
        "memset " + Opts + " -N memset_4k_uc -s 4k    -u -I 400",
        "memset " + Opts + " -N memset_10k	-s 10k	-I 600	 ",
        "memset " + Opts + " -N memset_1m	-s 1m	-I 200000",
//        "memset " + Opts + " -N memset_10m	-s 10m -I 2000000 ",
//        "memset " + Opts + " -N memsetP2_10m	-s 10m -P 2 -I 2000000 ",

//        "memrand " + Opts + " -N memrand	-s 128m -B 10000",

        "isatty " + Opts + " -N isatty_yes   ",
//        "isatty " + Opts + " -N isatty_no  -f $IFILE",

        "malloc " + Opts + " -N malloc_10    -s 10    -g 10 -I 50",
        "malloc " + Opts + " -N malloc_100   -s 100   -g 10 -I 50",
        "malloc " + Opts + " -N malloc_1k    -s 1k    -g 10 -I 50",
        "malloc " + Opts + " -N malloc_10k   -s 10k   -g 10 -I 50",
        "malloc " + Opts + " -N malloc_100k  -s 100k  -g 10 -I 2000",

        "malloc " + Opts + " -N mallocT2_10    -s 10   -g 10 -T 2 -I 200",
        "malloc " + Opts + " -N mallocT2_100   -s 100  -g 10 -T 2 -I 200",
        "malloc " + Opts + " -N mallocT2_1k    -s 1k   -g 10 -T 2 -I 200",
        "malloc " + Opts + " -N mallocT2_10k   -s 10k  -g 10 -T 2 -I 200",
        "malloc " + Opts + " -N mallocT2_100k  -s 100k -g 10 -T 2 -I 10000",

        "close " + Opts + " -N close_bad		-B 32		-b",
//        "close " + Opts + " -N close_tmp		-B 32		-f $TFILE",
//        "close " + Opts + " -N close_usr		-B 32		-f $VFILE",
        "close " + Opts + " -N close_zero		-B 32		-f /dev/zero",

        "memcpy " + Opts + " -N memcpy_10	-s 10	-I 10 ",
        "memcpy " + Opts + " -N memcpy_1k	-s 1k	-I 50",
        "memcpy " + Opts + " -N memcpy_10k	-s 10k	-I 800",
//        "memcpy " + Opts + " -N memcpy_1m	-s 1m   -I 500000",
//        "memcpy " + Opts + " -N memcpy_10m	-s 10m  -I 5000000",

        "strcpy " + Opts + " -N strcpy_10	-s 10   -I 5 ",
        "strcpy " + Opts + " -N strcpy_1k	-s 1k   -I 100",

        "strlen " + Opts + " -N strlen_10	-s 10   -I 5",
        "strlen " + Opts + " -N strlen_1k	-s 1k   -I 100",

        "strchr " + Opts + " -N strchr_10	-s 10   -I 5",
        "strchr " + Opts + " -N strchr_1k	-s 1k   -I 200",
        "strcmp " + Opts + " -N strcmp_10	-s 10   -I 10",
        "strcmp " + Opts + " -N strcmp_1k	-s 1k   -I 200",

        "strcasecmp " + Opts + " -N scasecmp_10	-s 10 -I 50",
        "strcasecmp " + Opts + " -N scasecmp_1k	-s 1k -I 20000",

        "strtol " + Opts + " -N strtol      -I 20      ",

        "mutex " + Opts + " -N mutex_st	-I 10",
        "mutex " + Opts + " -N mutex_mt	-t -I 10	",
        "mutex " + Opts + " -N mutex_T2     -T 2  -I 100",

//        "longjmp " + Opts + " -N longjmp	-I 10",
//        "siglongjmp " + Opts + " -N siglongjmp	-I 20",

        "getrusage " + Opts + " -N getrusage	-I 200",

        "times " + Opts + " -N times	-I 200",
        "time " + Opts + " -N time		-I 50",
        "localtime_r " + Opts + " -N localtime_r	-I 200  ",
        "strftime " + Opts + " -N strftime -I 10000  ",

        "mktime " + Opts + " -N mktime       -I 500   ",
        "mktime " + Opts + " -N mktimeT2 -T 2 -I 1000 ",

//        "cascade_mutex " + Opts + " -N c_mutex_1	-I 50",
//        "cascade_mutex " + Opts + " -N c_mutex_10	-T 10 -I 5000",
//        "cascade_mutex " + Opts + " -N c_mutex_200	-T 200	-I 2000000",

//        "cascade_cond " + Opts + " -N c_cond_1	-I 100",
//        "cascade_cond " + Opts + " -N c_cond_10	-T 10	-I 3000",
//        "cascade_cond " + Opts + " -N c_cond_200	-T 200	-I 2000000",

        "cascade_flock " + Opts + " -N c_flock	-I 1000	",
        "cascade_flock " + Opts + " -N c_flock_10	-P 10   -I 50000",
        "cascade_flock " + Opts + " -N c_flock_200	-P 200	-I 5000000",

        "cascade_fcntl " + Opts + " -N c_fcntl_1	-I 2000 	",
        "cascade_fcntl " + Opts + " -N c_fcntl_10	-P 10 -I 20000",
        "cascade_fcntl " + Opts + " -N c_fcntl_200	-P 200	-I 5000000",

        "file_lock " + Opts + " -N file_lock   -I 1000         ",

        "getsockname " + Opts + " -N getsockname	-I 100",
        "getpeername " + Opts + " -N getpeername	-I 100",

//        "chdir " + Opts + " -N chdir_tmp	-I 2000		$TDIR1 $TDIR2",
//        "chdir " + Opts + " -N chdir_usr	-I 2000		$VDIR1 $VDIR2",
//
//        "chdir " + Opts + " -N chgetwd_tmp	-I 3000	-g $TDIR1 $TDIR2",
//        "chdir " + Opts + " -N chgetwd_usr	-I 3000	-g $VDIR1 $VDIR2",
//
//        "realpath " + Opts + " -N realpath_tmp -I 3000		-f $TDIR1",
//        "realpath " + Opts + " -N realpath_usr	-I 3000	-f $VDIR1",
//
//        "stat " + Opts + " -N stat_tmp -I 1000		-f $TFILE",
//        "stat " + Opts + " -N stat_usr -I 1000		-f $VFILE",

//        "fcntl " + Opts + " -N fcntl_tmp	-I 100	-f $TFILE",
//        "fcntl " + Opts + " -N fcntl_usr	-I 100	-f $VFILE",
        "fcntl_ndelay " + Opts + " -N fcntl_ndelay	-I 100	",

//        "lseek " + Opts + " -N lseek_t8k	-s 8k	-I 50	-f $TFILE",
//        "lseek " + Opts + " -N lseek_u8k	-s 8k	-I 50	-f $VFILE",

//        "open " + Opts + " -N open_tmp		-B 256		-f $TFILE",
//        "open " + Opts + " -N open_usr		-B 256		-f $VFILE",
        "open " + Opts + " -N open_zero		-B 256		-f /dev/zero",

        "dup " + Opts + " -N dup			-B 512   ",

        "socket " + Opts + " -N socket_u		-B 256",
        "socket " + Opts + " -N socket_i		-B 256		-f PF_INET",

        "socketpair " + Opts + " -N socketpair		-B 256",

        "setsockopt " + Opts + " -N setsockopt		-I 200",

        "bind " + Opts + " -N bind			-B 100",

        "listen " + Opts + " -N listen		-B 100",

//        "connection " + Opts + " -N connection		-B 256 ",

        "poll " + Opts + " -N poll_10	-n 10	-I 500",
        "poll " + Opts + " -N poll_100	-n 100	-I 1000",
        "poll " + Opts + " -N poll_1000	-n 1000	-I 5000",

        "poll " + Opts + " -N poll_w10	-n 10	-I 500		-w 1",
        "poll " + Opts + " -N poll_w100	-n 100	-I 2000		-w 10",
        "poll " + Opts + " -N poll_w1000	-n 1000	-I 40000	-w 100",

        "select " + Opts + " -N select_10	-n 10	-I 500",
        "select " + Opts + " -N select_100	-n 100	-I 1000",
        "select " + Opts + " -N select_1000	-n 1000	-I 5000",

        "select " + Opts + " -N select_w10	-n 10	-I 500		-w 1",
        "select " + Opts + " -N select_w100	-n 100	-I 2000		-w 10",
        "select " + Opts + " -N select_w1000	-n 1000	-I 40000        -w 100",

        "sigaction " + Opts + " -N sigaction -I 100",
        "signal " + Opts + " -N signal -I 1000",
        "sigprocmask " + Opts + " -N sigprocmask -I 200",

        "pthread_create   " + Opts + " -N pthread_8		-B 8",
        "pthread_create   " + Opts + " -N pthread_32		-B 32",
        "pthread_create   " + Opts + " -N pthread_128		-B 128",
//        "pthread_create   " + Opts + " -N pthread_512		-B 512",

        "fork " + Opts + " -N fork_10		-B 10",
        "fork " + Opts + " -N fork_100		-B 100  -C 100",
//        "fork " + Opts + " -N fork_1000		-B 1000 -C 50",

        "exit " + Opts + " -N exit_10		-B 10",
        "exit " + Opts + " -N exit_100		-B 100",
//        "exit " + Opts + " -N exit_1000		-B 1000 -C 50",

        "exit " + Opts + " -N exit_10_nolibc	-e -B 10",

        "exec " + Opts + " -N exec -B 10",

        "system " + Opts + " -N system -I 1000000",

        "recurse " + Opts + " -N recurse		-B 512",

//        "read " + Opts + " -N read_t1k	-s 1k			-f $TFILE",
//        "read " + Opts + " -N read_t10k	-s 10k			-f $TFILE",
//        "read " + Opts + " -N read_t100k	-s 100k			-f $TFILE",

//        "read " + Opts + " -N read_u1k	-s 1k			-f $VFILE",
//        "read " + Opts + " -N read_u10k	-s 10k			-f $VFILE",
//        "read " + Opts + " -N read_u100k	-s 100k			-f $VFILE",

        "read " + Opts + " -N read_z1k	-s 1k			-f /dev/zero ",
        "read " + Opts + " -N read_z10k	-s 10k			-f /dev/zero ",
        "read " + Opts + " -N read_z100k	-s 100k			-f /dev/zero ",
        "read " + Opts + " -N read_zw100k	-s 100k	         -w	-f /dev/zero ",

//        "write " + Opts + " -N write_t1k	-s 1k			-f $TFILE",
//        "write " + Opts + " -N write_t10k	-s 10k			-f $TFILE",
//        "write " + Opts + " -N write_t100k	-s 100k			-f $TFILE",

//        "write " + Opts + " -N write_u1k	-s 1k			-f $VFILE",
//        "write " + Opts + " -N write_u10k	-s 10k			-f $VFILE",
//        "write " + Opts + " -N write_u100k	-s 100k			-f $VFILE",

        "write " + Opts + " -N write_n1k	-s 1k	-I 100 -B 0	-f /dev/null ",
        "write " + Opts + " -N write_n10k	-s 10k	-I 100 -B 0	-f /dev/null ",
        "write " + Opts + " -N write_n100k	-s 100k	-I 100 -B 0	-f /dev/null ",

//        "writev " + Opts + " -N writev_t1k	-s 1k			-f $TFILE",
//        "writev " + Opts + " -N writev_t10k	-s 10k		        -f $TFILE",
//        "writev " + Opts + " -N writev_t100k	-s 100k			-f $TFILE",

//        "writev " + Opts + " -N writev_u1k	-s 1k			-f $VFILE",
//        "writev " + Opts + " -N writev_u10k	-s 10k			-f $VFILE",
//        "writev " + Opts + " -N writev_u100k	-s 100k			-f $VFILE",

        "writev " + Opts + " -N writev_n1k	-s 1k	-I 100 -B 0	-f /dev/null ",
        "writev " + Opts + " -N writev_n10k	-s 10k	-I 100 -B 0	-f /dev/null ",
        "writev " + Opts + " -N writev_n100k	-s 100k	-I 100 -B 0	-f /dev/null ",

//        "pread " + Opts + " -N pread_t1k	-s 1k	-I 300		-f $TFILE",
//        "pread " + Opts + " -N pread_t10k	-s 10k	-I 1000		-f $TFILE",
//        "pread " + Opts + " -N pread_t100k	-s 100k	-I 10000	-f $TFILE",

//        "pread " + Opts + " -N pread_u1k	-s 1k	-I 300		-f $VFILE",
//        "pread " + Opts + " -N pread_u10k	-s 10k	-I 1000		-f $VFILE",
//        "pread " + Opts + " -N pread_u100k	-s 100k	-I 10000	-f $VFILE",

        "pread " + Opts + " -N pread_z1k	-s 1k	-I 300		-f /dev/zero ",
        "pread " + Opts + " -N pread_z10k	-s 10k	-I 1000		-f /dev/zero ",
        "pread " + Opts + " -N pread_z100k	-s 100k	-I 2000	-f /dev/zero ",
        "pread " + Opts + " -N pread_zw100k	-s 100k	-w -I 10000	-f /dev/zero ",

//        "pwrite " + Opts + " -N pwrite_t1k	-s 1k	-I 500		-f $TFILE",
//        "pwrite " + Opts + " -N pwrite_t10k	-s 10k	-I 1000		-f $TFILE",
//        "pwrite " + Opts + " -N pwrite_t100k	-s 100k	-I 10000	-f $TFILE",

//        "pwrite " + Opts + " -N pwrite_u1k	-s 1k	-I 500		-f $VFILE",
//        "pwrite " + Opts + " -N pwrite_u10k	-s 10k	-I 1000		-f $VFILE",
//        "pwrite " + Opts + " -N pwrite_u100k	-s 100k	-I 20000	-f $VFILE",

        "pwrite " + Opts + " -N pwrite_n1k	-s 1k	-I 100		-f /dev/null ",
        "pwrite " + Opts + " -N pwrite_n10k	-s 10k	-I 100		-f /dev/null ",
        "pwrite " + Opts + " -N pwrite_n100k	-s 100k	-I 100		-f /dev/null ",

        "mmap " + Opts + " -N mmap_z8k	-l 8k   -I 1000		-f /dev/zero",
        "mmap " + Opts + " -N mmap_z128k	-l 128k	-I 2000		-f /dev/zero",
//        "mmap " + Opts + " -N mmap_t8k	-l 8k	-I 1000		-f $TFILE",
//        "mmap " + Opts + " -N mmap_t128k	-l 128k	-I 1000		-f $TFILE",
//        "mmap " + Opts + " -N mmap_u8k	-l 8k	-I 1000		-f $VFILE",
//        "mmap " + Opts + " -N mmap_u128k	-l 128k	-I 1000		-f $VFILE",
        "mmap " + Opts + " -N mmap_a8k	-l 8k	-I 200		-f MAP_ANON",
        "mmap " + Opts + " -N mmap_a128k	-l 128k	-I 200		-f MAP_ANON",


        "mmap " + Opts + " -N mmap_rz8k	-l 8k	-I 2000 -r	-f /dev/zero",
        "mmap " + Opts + " -N mmap_rz128k	-l 128k	-I 2000 -r	-f /dev/zero",
//        "mmap " + Opts + " -N mmap_rt8k	-l 8k	-I 2000 -r	-f $TFILE",
//        "mmap " + Opts + " -N mmap_rt128k	-l 128k	-I 20000 -r	-f $TFILE",
//        "mmap " + Opts + " -N mmap_ru8k	-l 8k	-I 2000 -r	-f $VFILE",
        "mmap " + Opts + " -N mmap_ru128k	-l 128k	-I 20000 -r	-f $VFILE",
        "mmap " + Opts + " -N mmap_ra8k	-l 8k	-I 2000 -r	-f MAP_ANON",
        "mmap " + Opts + " -N mmap_ra128k	-l 128k	-I 20000 -r	-f MAP_ANON",

        "mmap " + Opts + " -N mmap_wz8k	-l 8k	-I 5000 -w	-f /dev/zero",
        "mmap " + Opts + " -N mmap_wz128k	-l 128k	-I 50000 -w	-f /dev/zero",
//        "mmap " + Opts + " -N mmap_wt8k	-l 8k	-I 5000 -w	-f $TFILE",
//        "mmap " + Opts + " -N mmap_wt128k	-l 128k	-I 50000 -w	-f $TFILE",
//        "mmap " + Opts + " -N mmap_wu8k	-l 8k	-I 5000 -w	-f $VFILE",
//        "mmap " + Opts + " -N mmap_wu128k	-l 128k	-I 500000 -w	-f $VFILE",
        "mmap " + Opts + " -N mmap_wa8k	-l 8k	-I 3000 -w	-f MAP_ANON",
        "mmap " + Opts + " -N mmap_wa128k	-l 128k	-I 50000 -w	-f MAP_ANON",

        "munmap " + Opts + " -N unmap_z8k	-l 8k   -I 500		-f /dev/zero",
        "munmap " + Opts + " -N unmap_z128k	-l 128k	-I 500		-f /dev/zero",
//        "munmap " + Opts + " -N unmap_t8k	-l 8k	-I 500		-f $TFILE",
//        "munmap " + Opts + " -N unmap_t128k	-l 128k	-I 500		-f $TFILE",
//        "munmap " + Opts + " -N unmap_u8k	-l 8k	-I 500		-f $VFILE",
//        "munmap " + Opts + " -N unmap_u128k	-l 128k	-I 500		-f $VFILE",
        "munmap " + Opts + " -N unmap_a8k	-l 8k	-I 500		-f MAP_ANON",
        "munmap " + Opts + " -N unmap_a128k	-l 128k	-I 500		-f MAP_ANON",

        "munmap " + Opts + " -N unmap_rz8k	-l 8k	-I 1000	-r	-f /dev/zero",
        "munmap " + Opts + " -N unmap_rz128k	-l 128k	-I 2000 -r	-f /dev/zero",
//        "munmap " + Opts + " -N unmap_rt8k	-l 8k	-I 1000	-r	-f $TFILE",
//        "munmap " + Opts + " -N unmap_rt128k	-l 128k	-I 3000	-r	-f $TFILE",
//        "munmap " + Opts + " -N unmap_ru8k	-l 8k	-I 1000	-r	-f $VFILE",
//        "munmap " + Opts + " -N unmap_ru128k	-l 128k	-I 3000	-r	-f $VFILE",
        "munmap " + Opts + " -N unmap_ra8k	-l 8k	-I 1000	-r	-f MAP_ANON",
        "munmap " + Opts + " -N unmap_ra128k	-l 128k	-I 2000	-r	-f MAP_ANON",

//        "connection " + Opts + " -N conn_connect		-B 256 	-c",

        "munmap " + Opts + " -N unmap_wz8k	-l 8k	-I 1000	-w	-f /dev/zero",
        "munmap " + Opts + " -N unmap_wz128k	-l 128k	-I 8000	-w	-f /dev/zero",
//        "munmap " + Opts + " -N unmap_wt8k	-l 8k	-I 1000	-w	-f $TFILE",
//        "munmap " + Opts + " -N unmap_wt128k	-l 128k	-I 10000	-w	-f $TFILE",
//        "munmap " + Opts + " -N unmap_wu8k	-l 8k	-I 1000	-w	-f $VFILE",
//        "munmap " + Opts + " -N unmap_wu128k	-l 128k	-I 50000	-w	-f $VFILE",
        "munmap " + Opts + " -N unmap_wa8k	-l 8k	-I 1000	-w	-f MAP_ANON",
        "munmap " + Opts + " -N unmap_wa128k	-l 128k	-I 10000	-w	-f MAP_ANON",


        "mprotect " + Opts + " -N mprot_z8k	-l 8k  -I 300			-f /dev/zero",
        "mprotect " + Opts + " -N mprot_z128k	-l 128k	-I 500		-f /dev/zero",
        "mprotect " + Opts + " -N mprot_wz8k	-l 8k	-I 500	-w	-f /dev/zero",
        "mprotect " + Opts + " -N mprot_wz128k	-l 128k	-I 1000	-w	-f /dev/zero",
        "mprotect " + Opts + " -N mprot_twz8k  -l 8k   -I 1000 -w -t   -f /dev/zero",
        "mprotect " + Opts + " -N mprot_tw128k -l 128k -I 2000 -w -t   -f /dev/zero",
        "mprotect " + Opts + " -N mprot_tw4m   -l 4m   -w -t -B 1  -f /dev/zero",

        "pipe " + Opts + " -N pipe_pst1	-s 1	-I 1000	-x pipe -m st",
        "pipe " + Opts + " -N pipe_pmt1	-s 1	-I 8000	-x pipe -m mt",
        "pipe " + Opts + " -N pipe_pmp1	-s 1	-I 8000	-x pipe -m mp",
        "pipe " + Opts + " -N pipe_pst4k	-s 4k	-I 1000	-x pipe -m st",
        "pipe " + Opts + " -N pipe_pmt4k	-s 4k	-I 8000	-x pipe -m mt",
        "pipe " + Opts + " -N pipe_pmp4k	-s 4k	-I 8000	-x pipe -m mp",

        "pipe " + Opts + " -N pipe_sst1	-s 1	-I 1000	-x sock -m st",
        "pipe " + Opts + " -N pipe_smt1	-s 1	-I 8000	-x sock -m mt",
        "pipe " + Opts + " -N pipe_smp1	-s 1	-I 8000	-x sock -m mp",
        "pipe " + Opts + " -N pipe_sst4k	-s 4k	-I 1000	-x sock -m st",
        "pipe " + Opts + " -N pipe_smt4k	-s 4k	-I 8000	-x sock -m mt",
        "pipe " + Opts + " -N pipe_smp4k	-s 4k	-I 8000	-x sock -m mp",

        "pipe " + Opts + " -N pipe_tst1	-s 1	-I 1000	-x tcp  -m st",
        "pipe " + Opts + " -N pipe_tmt1	-s 1	-I 8000	-x tcp  -m mt",
        "pipe " + Opts + " -N pipe_tmp1	-s 1	-I 8000	-x tcp  -m mp",
        "pipe " + Opts + " -N pipe_tst4k	-s 4k	-I 1000	-x tcp  -m st",
        "pipe " + Opts + " -N pipe_tmt4k	-s 4k	-I 8000	-x tcp  -m mt",
        "pipe " + Opts + " -N pipe_tmp4k	-s 4k	-I 8000	-x tcp  -m mp",

//        "connection " + Opts + " -N conn_accept		-B 256      -a",

        "close_tcp " + Opts + " -N close_tcp		-B 32  "

    );

    
    @Override
    protected String getTag() {
        return "Native Micro";
    };
    @Override
    protected String getPath() {
        return "/system/bin/bench_";
    }
    protected final List<String> getCommands() {
        return COMMANDS;
    }

    /*
     * The unit is usecs/call
     */
    private double getBenchResult(String command) {
        String stdOut = mStdOuts.get(command);
        int index = stdOut.lastIndexOf("mean of 95%");
        double value = 0.0;
        try {
            String summary = stdOut.substring(index, stdOut.length());
            String[] substrings = summary.toString().split("\\s+");
            value = Double.valueOf(substrings[3]);
            return value;
        }
        catch (StringIndexOutOfBoundsException sioobe) {
            Log.d(TAG, "StringIndexOutOfBoundsException");
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            Log.d(TAG, "ArrayIndexOutOfBoundsException");
        }
        catch (NumberFormatException nfe) {
            Log.d(TAG, "NumberFormatException");
        }
        return ERROR_VALUE;
    }

    private String getCommandName(String command) {
        if (command == null || command.equals(""))
            return command;
        String stdErr = mStdErrs.get(command);

        try {
            String[] substrings = stdErr.split("\\s+");
            String commandName = substrings[1];
            return commandName;
        }
        catch (Exception ex) {
            String[] substrings = command.toString().split("\\s+");
            return substrings[0];
        }
    }

    @Override
    protected boolean saveResult(Intent intent) {
        Bundle bundle = new Bundle();
        StringBuilder report = new StringBuilder();
        for (String command: getCommands()) {
            if(!mSockets.containsKey(command))
                continue;
            String [] lines = mSockets.get(command).trim().split("\n");
            String name = lines[0].trim().split("\t")[0];
            if (name.equals("")) {
                report.append(getCommandName(command));
            }
            else {
                report.append(name);
            }
            StringBuilder list = new StringBuilder();;
            for(String line: lines) {
                String [] sp = line.trim().split("\t");
                if (sp.length != 2) {
                    Log.w(TAG, "error line: " + line.trim());
                    continue;
                }
                if(!name.equals(sp[0]))
                    Log.i(TAG, "Incompatible bench name in socket out: " + name + " v.s. " + sp[0]);
                try {
                    int toInt = (int)Float.parseFloat(sp[1]);
                    list.append(toInt + " ");
                } catch (Exception e) { // error format
                    Log.e(TAG, "cannot parse '" + sp[1] + "' in line: " +  line);
                    continue;
                }
            }

            if(!mStdOuts.containsKey(command)) {
                report.append("\n");
                continue;
            }

            double value = getBenchResult(command);
            if (value == ERROR_VALUE) {
                report.append(" FAIL\n");
            }
            else {
                report.append(" " + value + " usecs/call\n");
                bundle.putString(command+"S", name);
                bundle.putString(command+"FA", list.toString().trim());
            }
        }
        bundle.putString(REPORT, report.toString());
        intent.putExtra(RESULT, bundle);
        return true;
    }
}
