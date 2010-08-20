#
# Copyright (C) 2010 0xlab - http://0xlab.org/
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

EXEs :=	\
	hanoi	\
	syscall	\
	context1	\
	pipe	\
	spawn	\
	execl	\
	looper	\
	fstime	

$(foreach TARGET_MODULE, $(EXEs), $(eval include $(LOCAL_PATH)/_Android.mk))

include $(CLEAR_VARS)
EXTRA_FLAGS := -Darithoh
TARGET_MODULE := arithoh
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum='register int'
TARGET_MODULE := register
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum=short
TARGET_MODULE := short
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum=int
TARGET_MODULE := int
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum=long
TARGET_MODULE := long
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum=float
TARGET_MODULE := float
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -Ddatum=double
TARGET_MODULE := double
LOCAL_SRC_FILES := src/arith.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -DHZ= 
TARGET_MODULE := dhry2
LOCAL_SRC_FILES := src/dhry_1.c src/dhry_2.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -DREG=register
TARGET_MODULE := dhry2reg
LOCAL_SRC_FILES := src/dhry_1.c src/dhry_2.c
include $(LOCAL_PATH)/_Android_NoClear.mk

include $(CLEAR_VARS)
EXTRA_FLAGS := -DDP    -DUNIX  -DUNIXBENCH -lm
TARGET_MODULE := whetstone-double
LOCAL_SRC_FILES := src/whets.c
include $(LOCAL_PATH)/_Android_NoClear.mk

