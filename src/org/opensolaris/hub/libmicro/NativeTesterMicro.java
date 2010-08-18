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

public class NativeTesterMicro extends NativeTester {

    public static final String REPORT = "REPORT";
    public static final String RESULT = "RESULT";
    private static final String Opts = "-E -C 70 -L -S -W";
    private static final String Path = "/system/bin/bench_";
    public static final List<String> COMMANDS  = Arrays.asList(

        Path + "getpid " + Opts + " -N getpid -I 5",

        Path + "getenv " + Opts + " -N getenv   -s 100 -I 100",
        Path + "getenv " + Opts + " -N getenvT2   -s 100 -I 100 -T 2",

        Path + "gettimeofday " + Opts + " -N gettimeofday",

        Path + "log " + Opts + " -N log  -I 20",

        Path + "exp " + Opts + " -N exp  -I 20",

        Path + "lrand48 " + Opts + " -N lrand48",

        Path + "memset " + Opts + " -N memset_10	-s 10	-I 10 ",
        Path + "memset " + Opts + " -N memset_256	-s 256	-I 20",
        Path + "memset " + Opts + " -N memset_256_u	-s 256	 -a 1 -I 20 ",
        Path + "memset " + Opts + " -N memset_1k	-s 1k	 -I 100",
        Path + "memset " + Opts + " -N memset_4k    -s 4k    -I 250",
        Path + "memset " + Opts + " -N memset_4k_uc -s 4k    -u -I 400",
        Path + "memset " + Opts + " -N memset_10k	-s 10k	-I 600	 ",
        Path + "memset " + Opts + " -N memset_1m	-s 1m	-I 200000",
//        Path + "memset " + Opts + " -N memset_10m	-s 10m -I 2000000 ",
//        Path + "memset " + Opts + " -N memsetP2_10m	-s 10m -P 2 -I 2000000 ",

//        Path + "memrand " + Opts + " -N memrand	-s 128m -B 10000",

        Path + "isatty " + Opts + " -N isatty_yes   ",
//        Path + "isatty " + Opts + " -N isatty_no  -f $IFILE",

        Path + "malloc " + Opts + " -N malloc_10    -s 10    -g 10 -I 50",
        Path + "malloc " + Opts + " -N malloc_100   -s 100   -g 10 -I 50",
        Path + "malloc " + Opts + " -N malloc_1k    -s 1k    -g 10 -I 50",
        Path + "malloc " + Opts + " -N malloc_10k   -s 10k   -g 10 -I 50",
        Path + "malloc " + Opts + " -N malloc_100k  -s 100k  -g 10 -I 2000",

        Path + "malloc " + Opts + " -N mallocT2_10    -s 10   -g 10 -T 2 -I 200",
        Path + "malloc " + Opts + " -N mallocT2_100   -s 100  -g 10 -T 2 -I 200",
        Path + "malloc " + Opts + " -N mallocT2_1k    -s 1k   -g 10 -T 2 -I 200",
        Path + "malloc " + Opts + " -N mallocT2_10k   -s 10k  -g 10 -T 2 -I 200",
        Path + "malloc " + Opts + " -N mallocT2_100k  -s 100k -g 10 -T 2 -I 10000",

        Path + "close " + Opts + " -N close_bad		-B 32		-b",
//        Path + "close " + Opts + " -N close_tmp		-B 32		-f $TFILE",
//        Path + "close " + Opts + " -N close_usr		-B 32		-f $VFILE",
        Path + "close " + Opts + " -N close_zero		-B 32		-f /dev/zero",

        Path + "memcpy " + Opts + " -N memcpy_10	-s 10	-I 10 ",
        Path + "memcpy " + Opts + " -N memcpy_1k	-s 1k	-I 50",
        Path + "memcpy " + Opts + " -N memcpy_10k	-s 10k	-I 800",
//        Path + "memcpy " + Opts + " -N memcpy_1m	-s 1m   -I 500000",
//        Path + "memcpy " + Opts + " -N memcpy_10m	-s 10m  -I 5000000",

        Path + "strcpy " + Opts + " -N strcpy_10	-s 10   -I 5 ",
        Path + "strcpy " + Opts + " -N strcpy_1k	-s 1k   -I 100",

        Path + "strlen " + Opts + " -N strlen_10	-s 10   -I 5",
        Path + "strlen " + Opts + " -N strlen_1k	-s 1k   -I 100",

        Path + "strchr " + Opts + " -N strchr_10	-s 10   -I 5",
        Path + "strchr " + Opts + " -N strchr_1k	-s 1k   -I 200",
        Path + "strcmp " + Opts + " -N strcmp_10	-s 10   -I 10",
        Path + "strcmp " + Opts + " -N strcmp_1k	-s 1k   -I 200",

        Path + "strcasecmp " + Opts + " -N scasecmp_10	-s 10 -I 50",
        Path + "strcasecmp " + Opts + " -N scasecmp_1k	-s 1k -I 20000",

        Path + "strtol " + Opts + " -N strtol      -I 20      ",

        Path + "mutex " + Opts + " -N mutex_st	-I 10",
        Path + "mutex " + Opts + " -N mutex_mt	-t -I 10	",
        Path + "mutex " + Opts + " -N mutex_T2     -T 2  -I 100",

//        Path + "longjmp " + Opts + " -N longjmp	-I 10",
//        Path + "siglongjmp " + Opts + " -N siglongjmp	-I 20",

        Path + "getrusage " + Opts + " -N getrusage	-I 200",

        Path + "times " + Opts + " -N times	-I 200",
        Path + "time " + Opts + " -N time		-I 50",
        Path + "localtime_r " + Opts + " -N localtime_r	-I 200  ",
        Path + "strftime " + Opts + " -N strftime -I 10000  ",

        Path + "mktime " + Opts + " -N mktime       -I 500   ",
        Path + "mktime " + Opts + " -N mktimeT2 -T 2 -I 1000 ",

//        Path + "cascade_mutex " + Opts + " -N c_mutex_1	-I 50",
//        Path + "cascade_mutex " + Opts + " -N c_mutex_10	-T 10 -I 5000",
//        Path + "cascade_mutex " + Opts + " -N c_mutex_200	-T 200	-I 2000000",

//        Path + "cascade_cond " + Opts + " -N c_cond_1	-I 100",
//        Path + "cascade_cond " + Opts + " -N c_cond_10	-T 10	-I 3000",
//        Path + "cascade_cond " + Opts + " -N c_cond_200	-T 200	-I 2000000",

        Path + "cascade_flock " + Opts + " -N c_flock	-I 1000	",
        Path + "cascade_flock " + Opts + " -N c_flock_10	-P 10   -I 50000",
        Path + "cascade_flock " + Opts + " -N c_flock_200	-P 200	-I 5000000",

        Path + "cascade_fcntl " + Opts + " -N c_fcntl_1	-I 2000 	",
        Path + "cascade_fcntl " + Opts + " -N c_fcntl_10	-P 10 -I 20000",
        Path + "cascade_fcntl " + Opts + " -N c_fcntl_200	-P 200	-I 5000000",

        Path + "file_lock " + Opts + " -N file_lock   -I 1000         ",

        Path + "getsockname " + Opts + " -N getsockname	-I 100",
        Path + "getpeername " + Opts + " -N getpeername	-I 100",

//        Path + "chdir " + Opts + " -N chdir_tmp	-I 2000		$TDIR1 $TDIR2",
//        Path + "chdir " + Opts + " -N chdir_usr	-I 2000		$VDIR1 $VDIR2",
//
//        Path + "chdir " + Opts + " -N chgetwd_tmp	-I 3000	-g $TDIR1 $TDIR2",
//        Path + "chdir " + Opts + " -N chgetwd_usr	-I 3000	-g $VDIR1 $VDIR2",
//
//        Path + "realpath " + Opts + " -N realpath_tmp -I 3000		-f $TDIR1",
//        Path + "realpath " + Opts + " -N realpath_usr	-I 3000	-f $VDIR1",
//
//        Path + "stat " + Opts + " -N stat_tmp -I 1000		-f $TFILE",
//        Path + "stat " + Opts + " -N stat_usr -I 1000		-f $VFILE",

//        Path + "fcntl " + Opts + " -N fcntl_tmp	-I 100	-f $TFILE",
//        Path + "fcntl " + Opts + " -N fcntl_usr	-I 100	-f $VFILE",
        Path + "fcntl_ndelay " + Opts + " -N fcntl_ndelay	-I 100	",

//        Path + "lseek " + Opts + " -N lseek_t8k	-s 8k	-I 50	-f $TFILE",
//        Path + "lseek " + Opts + " -N lseek_u8k	-s 8k	-I 50	-f $VFILE",

//        Path + "open " + Opts + " -N open_tmp		-B 256		-f $TFILE",
//        Path + "open " + Opts + " -N open_usr		-B 256		-f $VFILE",
        Path + "open " + Opts + " -N open_zero		-B 256		-f /dev/zero",

        Path + "dup " + Opts + " -N dup			-B 512   ",

        Path + "socket " + Opts + " -N socket_u		-B 256",
        Path + "socket " + Opts + " -N socket_i		-B 256		-f PF_INET",

        Path + "socketpair " + Opts + " -N socketpair		-B 256",

        Path + "setsockopt " + Opts + " -N setsockopt		-I 200",

        Path + "bind " + Opts + " -N bind			-B 100",

        Path + "listen " + Opts + " -N listen		-B 100",

//        Path + "connection " + Opts + " -N connection		-B 256 ",

        Path + "poll " + Opts + " -N poll_10	-n 10	-I 500",
        Path + "poll " + Opts + " -N poll_100	-n 100	-I 1000",
        Path + "poll " + Opts + " -N poll_1000	-n 1000	-I 5000",

        Path + "poll " + Opts + " -N poll_w10	-n 10	-I 500		-w 1",
        Path + "poll " + Opts + " -N poll_w100	-n 100	-I 2000		-w 10",
        Path + "poll " + Opts + " -N poll_w1000	-n 1000	-I 40000	-w 100",

        Path + "select " + Opts + " -N select_10	-n 10	-I 500",
        Path + "select " + Opts + " -N select_100	-n 100	-I 1000",
        Path + "select " + Opts + " -N select_1000	-n 1000	-I 5000",

        Path + "select " + Opts + " -N select_w10	-n 10	-I 500		-w 1",
        Path + "select " + Opts + " -N select_w100	-n 100	-I 2000		-w 10",
        Path + "select " + Opts + " -N select_w1000	-n 1000	-I 40000        -w 100",

        Path + "sigaction " + Opts + " -N sigaction -I 100",
        Path + "signal " + Opts + " -N signal -I 1000",
        Path + "sigprocmask " + Opts + " -N sigprocmask -I 200",

        Path + "pthread_create   " + Opts + " -N pthread_8		-B 8",
        Path + "pthread_create   " + Opts + " -N pthread_32		-B 32",
        Path + "pthread_create   " + Opts + " -N pthread_128		-B 128",
//        Path + "pthread_create   " + Opts + " -N pthread_512		-B 512",

        Path + "fork " + Opts + " -N fork_10		-B 10",
        Path + "fork " + Opts + " -N fork_100		-B 100  -C 100",
//        Path + "fork " + Opts + " -N fork_1000		-B 1000 -C 50",

        Path + "exit " + Opts + " -N exit_10		-B 10",
        Path + "exit " + Opts + " -N exit_100		-B 100",
//        Path + "exit " + Opts + " -N exit_1000		-B 1000 -C 50",

        Path + "exit " + Opts + " -N exit_10_nolibc	-e -B 10",

        Path + "exec " + Opts + " -N exec -B 10",

        Path + "system " + Opts + " -N system -I 1000000",

        Path + "recurse " + Opts + " -N recurse		-B 512",

//        Path + "read " + Opts + " -N read_t1k	-s 1k			-f $TFILE",
//        Path + "read " + Opts + " -N read_t10k	-s 10k			-f $TFILE",
//        Path + "read " + Opts + " -N read_t100k	-s 100k			-f $TFILE",

//        Path + "read " + Opts + " -N read_u1k	-s 1k			-f $VFILE",
//        Path + "read " + Opts + " -N read_u10k	-s 10k			-f $VFILE",
//        Path + "read " + Opts + " -N read_u100k	-s 100k			-f $VFILE",

        Path + "read " + Opts + " -N read_z1k	-s 1k			-f /dev/zero ",
        Path + "read " + Opts + " -N read_z10k	-s 10k			-f /dev/zero ",
        Path + "read " + Opts + " -N read_z100k	-s 100k			-f /dev/zero ",
        Path + "read " + Opts + " -N read_zw100k	-s 100k	         -w	-f /dev/zero ",

//        Path + "write " + Opts + " -N write_t1k	-s 1k			-f $TFILE",
//        Path + "write " + Opts + " -N write_t10k	-s 10k			-f $TFILE",
//        Path + "write " + Opts + " -N write_t100k	-s 100k			-f $TFILE",

//        Path + "write " + Opts + " -N write_u1k	-s 1k			-f $VFILE",
//        Path + "write " + Opts + " -N write_u10k	-s 10k			-f $VFILE",
//        Path + "write " + Opts + " -N write_u100k	-s 100k			-f $VFILE",

        Path + "write " + Opts + " -N write_n1k	-s 1k	-I 100 -B 0	-f /dev/null ",
        Path + "write " + Opts + " -N write_n10k	-s 10k	-I 100 -B 0	-f /dev/null ",
        Path + "write " + Opts + " -N write_n100k	-s 100k	-I 100 -B 0	-f /dev/null ",

//        Path + "writev " + Opts + " -N writev_t1k	-s 1k			-f $TFILE",
//        Path + "writev " + Opts + " -N writev_t10k	-s 10k		        -f $TFILE",
//        Path + "writev " + Opts + " -N writev_t100k	-s 100k			-f $TFILE",

//        Path + "writev " + Opts + " -N writev_u1k	-s 1k			-f $VFILE",
//        Path + "writev " + Opts + " -N writev_u10k	-s 10k			-f $VFILE",
//        Path + "writev " + Opts + " -N writev_u100k	-s 100k			-f $VFILE",

        Path + "writev " + Opts + " -N writev_n1k	-s 1k	-I 100 -B 0	-f /dev/null ",
        Path + "writev " + Opts + " -N writev_n10k	-s 10k	-I 100 -B 0	-f /dev/null ",
        Path + "writev " + Opts + " -N writev_n100k	-s 100k	-I 100 -B 0	-f /dev/null ",

//        Path + "pread " + Opts + " -N pread_t1k	-s 1k	-I 300		-f $TFILE",
//        Path + "pread " + Opts + " -N pread_t10k	-s 10k	-I 1000		-f $TFILE",
//        Path + "pread " + Opts + " -N pread_t100k	-s 100k	-I 10000	-f $TFILE",

//        Path + "pread " + Opts + " -N pread_u1k	-s 1k	-I 300		-f $VFILE",
//        Path + "pread " + Opts + " -N pread_u10k	-s 10k	-I 1000		-f $VFILE",
//        Path + "pread " + Opts + " -N pread_u100k	-s 100k	-I 10000	-f $VFILE",

        Path + "pread " + Opts + " -N pread_z1k	-s 1k	-I 300		-f /dev/zero ",
        Path + "pread " + Opts + " -N pread_z10k	-s 10k	-I 1000		-f /dev/zero ",
        Path + "pread " + Opts + " -N pread_z100k	-s 100k	-I 2000	-f /dev/zero ",
        Path + "pread " + Opts + " -N pread_zw100k	-s 100k	-w -I 10000	-f /dev/zero ",

//        Path + "pwrite " + Opts + " -N pwrite_t1k	-s 1k	-I 500		-f $TFILE",
//        Path + "pwrite " + Opts + " -N pwrite_t10k	-s 10k	-I 1000		-f $TFILE",
//        Path + "pwrite " + Opts + " -N pwrite_t100k	-s 100k	-I 10000	-f $TFILE",

//        Path + "pwrite " + Opts + " -N pwrite_u1k	-s 1k	-I 500		-f $VFILE",
//        Path + "pwrite " + Opts + " -N pwrite_u10k	-s 10k	-I 1000		-f $VFILE",
//        Path + "pwrite " + Opts + " -N pwrite_u100k	-s 100k	-I 20000	-f $VFILE",

        Path + "pwrite " + Opts + " -N pwrite_n1k	-s 1k	-I 100		-f /dev/null ",
        Path + "pwrite " + Opts + " -N pwrite_n10k	-s 10k	-I 100		-f /dev/null ",
        Path + "pwrite " + Opts + " -N pwrite_n100k	-s 100k	-I 100		-f /dev/null ",

        Path + "mmap " + Opts + " -N mmap_z8k	-l 8k   -I 1000		-f /dev/zero",
        Path + "mmap " + Opts + " -N mmap_z128k	-l 128k	-I 2000		-f /dev/zero",
//        Path + "mmap " + Opts + " -N mmap_t8k	-l 8k	-I 1000		-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_t128k	-l 128k	-I 1000		-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_u8k	-l 8k	-I 1000		-f $VFILE",
//        Path + "mmap " + Opts + " -N mmap_u128k	-l 128k	-I 1000		-f $VFILE",
        Path + "mmap " + Opts + " -N mmap_a8k	-l 8k	-I 200		-f MAP_ANON",
        Path + "mmap " + Opts + " -N mmap_a128k	-l 128k	-I 200		-f MAP_ANON",


        Path + "mmap " + Opts + " -N mmap_rz8k	-l 8k	-I 2000 -r	-f /dev/zero",
        Path + "mmap " + Opts + " -N mmap_rz128k	-l 128k	-I 2000 -r	-f /dev/zero",
//        Path + "mmap " + Opts + " -N mmap_rt8k	-l 8k	-I 2000 -r	-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_rt128k	-l 128k	-I 20000 -r	-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_ru8k	-l 8k	-I 2000 -r	-f $VFILE",
        Path + "mmap " + Opts + " -N mmap_ru128k	-l 128k	-I 20000 -r	-f $VFILE",
        Path + "mmap " + Opts + " -N mmap_ra8k	-l 8k	-I 2000 -r	-f MAP_ANON",
        Path + "mmap " + Opts + " -N mmap_ra128k	-l 128k	-I 20000 -r	-f MAP_ANON",

        Path + "mmap " + Opts + " -N mmap_wz8k	-l 8k	-I 5000 -w	-f /dev/zero",
        Path + "mmap " + Opts + " -N mmap_wz128k	-l 128k	-I 50000 -w	-f /dev/zero",
//        Path + "mmap " + Opts + " -N mmap_wt8k	-l 8k	-I 5000 -w	-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_wt128k	-l 128k	-I 50000 -w	-f $TFILE",
//        Path + "mmap " + Opts + " -N mmap_wu8k	-l 8k	-I 5000 -w	-f $VFILE",
//        Path + "mmap " + Opts + " -N mmap_wu128k	-l 128k	-I 500000 -w	-f $VFILE",
        Path + "mmap " + Opts + " -N mmap_wa8k	-l 8k	-I 3000 -w	-f MAP_ANON",
        Path + "mmap " + Opts + " -N mmap_wa128k	-l 128k	-I 50000 -w	-f MAP_ANON",

        Path + "munmap " + Opts + " -N unmap_z8k	-l 8k   -I 500		-f /dev/zero",
        Path + "munmap " + Opts + " -N unmap_z128k	-l 128k	-I 500		-f /dev/zero",
//        Path + "munmap " + Opts + " -N unmap_t8k	-l 8k	-I 500		-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_t128k	-l 128k	-I 500		-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_u8k	-l 8k	-I 500		-f $VFILE",
//        Path + "munmap " + Opts + " -N unmap_u128k	-l 128k	-I 500		-f $VFILE",
        Path + "munmap " + Opts + " -N unmap_a8k	-l 8k	-I 500		-f MAP_ANON",
        Path + "munmap " + Opts + " -N unmap_a128k	-l 128k	-I 500		-f MAP_ANON",

        Path + "munmap " + Opts + " -N unmap_rz8k	-l 8k	-I 1000	-r	-f /dev/zero",
        Path + "munmap " + Opts + " -N unmap_rz128k	-l 128k	-I 2000 -r	-f /dev/zero",
//        Path + "munmap " + Opts + " -N unmap_rt8k	-l 8k	-I 1000	-r	-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_rt128k	-l 128k	-I 3000	-r	-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_ru8k	-l 8k	-I 1000	-r	-f $VFILE",
//        Path + "munmap " + Opts + " -N unmap_ru128k	-l 128k	-I 3000	-r	-f $VFILE",
        Path + "munmap " + Opts + " -N unmap_ra8k	-l 8k	-I 1000	-r	-f MAP_ANON",
        Path + "munmap " + Opts + " -N unmap_ra128k	-l 128k	-I 2000	-r	-f MAP_ANON",

//        Path + "connection " + Opts + " -N conn_connect		-B 256 	-c",

        Path + "munmap " + Opts + " -N unmap_wz8k	-l 8k	-I 1000	-w	-f /dev/zero",
        Path + "munmap " + Opts + " -N unmap_wz128k	-l 128k	-I 8000	-w	-f /dev/zero",
//        Path + "munmap " + Opts + " -N unmap_wt8k	-l 8k	-I 1000	-w	-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_wt128k	-l 128k	-I 10000	-w	-f $TFILE",
//        Path + "munmap " + Opts + " -N unmap_wu8k	-l 8k	-I 1000	-w	-f $VFILE",
//        Path + "munmap " + Opts + " -N unmap_wu128k	-l 128k	-I 50000	-w	-f $VFILE",
        Path + "munmap " + Opts + " -N unmap_wa8k	-l 8k	-I 1000	-w	-f MAP_ANON",
        Path + "munmap " + Opts + " -N unmap_wa128k	-l 128k	-I 10000	-w	-f MAP_ANON",


        Path + "mprotect " + Opts + " -N mprot_z8k	-l 8k  -I 300			-f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_z128k	-l 128k	-I 500		-f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_wz8k	-l 8k	-I 500	-w	-f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_wz128k	-l 128k	-I 1000	-w	-f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_twz8k  -l 8k   -I 1000 -w -t   -f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_tw128k -l 128k -I 2000 -w -t   -f /dev/zero",
        Path + "mprotect " + Opts + " -N mprot_tw4m   -l 4m   -w -t -B 1  -f /dev/zero",

        Path + "pipe " + Opts + " -N pipe_pst1	-s 1	-I 1000	-x pipe -m st",
        Path + "pipe " + Opts + " -N pipe_pmt1	-s 1	-I 8000	-x pipe -m mt",
        Path + "pipe " + Opts + " -N pipe_pmp1	-s 1	-I 8000	-x pipe -m mp",
        Path + "pipe " + Opts + " -N pipe_pst4k	-s 4k	-I 1000	-x pipe -m st",
        Path + "pipe " + Opts + " -N pipe_pmt4k	-s 4k	-I 8000	-x pipe -m mt",
        Path + "pipe " + Opts + " -N pipe_pmp4k	-s 4k	-I 8000	-x pipe -m mp",

        Path + "pipe " + Opts + " -N pipe_sst1	-s 1	-I 1000	-x sock -m st",
        Path + "pipe " + Opts + " -N pipe_smt1	-s 1	-I 8000	-x sock -m mt",
        Path + "pipe " + Opts + " -N pipe_smp1	-s 1	-I 8000	-x sock -m mp",
        Path + "pipe " + Opts + " -N pipe_sst4k	-s 4k	-I 1000	-x sock -m st",
        Path + "pipe " + Opts + " -N pipe_smt4k	-s 4k	-I 8000	-x sock -m mt",
        Path + "pipe " + Opts + " -N pipe_smp4k	-s 4k	-I 8000	-x sock -m mp",

        Path + "pipe " + Opts + " -N pipe_tst1	-s 1	-I 1000	-x tcp  -m st",
        Path + "pipe " + Opts + " -N pipe_tmt1	-s 1	-I 8000	-x tcp  -m mt",
        Path + "pipe " + Opts + " -N pipe_tmp1	-s 1	-I 8000	-x tcp  -m mp",
        Path + "pipe " + Opts + " -N pipe_tst4k	-s 4k	-I 1000	-x tcp  -m st",
        Path + "pipe " + Opts + " -N pipe_tmt4k	-s 4k	-I 8000	-x tcp  -m mt",
        Path + "pipe " + Opts + " -N pipe_tmp4k	-s 4k	-I 8000	-x tcp  -m mp",

//        Path + "connection " + Opts + " -N conn_accept		-B 256      -a",

        Path + "close_tcp " + Opts + " -N close_tcp		-B 32  "

    );

    
    @Override
    protected String getTag() {
        return "Native Micro";
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
            String name = lines[0].trim().split("\t")[0];
            StringBuilder list = new StringBuilder();;
            for(String line: lines) {
                String [] sp = line.trim().split("\t");
                if (sp.length != 2) {
                    Log.w(TAG, "error line: " + line.trim());
                    continue;
                }
                if(!name.equals(sp[0]))
                    Log.i(TAG, "Incompatible bench name in socket out: " + name + " v.s. " + sp[0]);

                //TODO  changing from string to float will use up too much memory
                //      causing outOfMemory exception.
                //      should save in string format to bundle. easier to generate xml, too.
                try {
                    int toInt = (int)Float.parseFloat(sp[1]);
                    list.append(toInt + " ");
                } catch (Exception e) { // error format
                    Log.e(TAG, "cannot parse '" + sp[1] + "' in line: " +  line);
                    continue;
                }
            }
            bundle.putString(command+"S", name);
            bundle.putString(command+"FA", list.toString().trim());

        }
//        bundle.putString(REPORT, report.toString());
        intent.putExtra(RESULT, bundle);
        return true;
    }
}
