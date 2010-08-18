LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= hello.c

LOCAL_MODULE:= hello
LOCAL_MODULE_TAGS := optional

 
LOCAL_C_INCLUDES := \
    $(JNI_H_INCLUDE)

include $(BUILD_EXECUTABLE)
