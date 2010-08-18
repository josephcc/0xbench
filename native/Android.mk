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


TOP_LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

include $(TOP_LOCAL_PATH)/hello_jni/Android.mk

include $(TOP_LOCAL_PATH)/gl_finish/Android.mk
include $(TOP_LOCAL_PATH)/hello_bin/Android.mk

include $(TOP_LOCAL_PATH)/libMicro-0.4.0/Android.mk
