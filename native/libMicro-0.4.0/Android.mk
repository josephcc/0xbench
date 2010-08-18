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
LOCAL_SRC_FILES := \
	libmicro.c \
	libmicro_main.c \
	benchmark_init.c \
	benchmark_fini.c \
	benchmark_finirun.c \
	benchmark_initrun.c \
	benchmark_initbatch.c \
	benchmark_finibatch.c \
	benchmark_initworker.c \
	benchmark_finiworker.c \
	benchmark_optswitch.c \
	benchmark_result.c

LOCAL_MODULE:= libmicro
include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)
include $(LOCAL_PATH)/_Executables.mk

$(foreach TARGET_MODULE, $(EXEs), $(eval include $(LOCAL_PATH)/_Android.mk))


