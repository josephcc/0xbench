LOCAL_PATH:= $(call my-dir)
 
LOCAL_SRC_FILES:= \
    hellolib.c
 
LOCAL_C_INCLUDES := \
    $(JNI_H_INCLUDE)
 
LOCAL_SHARED_LIBRARIES := \
    libutils
 
LOCAL_PRELINK_MODULE := false
 
LOCAL_MODULE := libhello
 
include $(BUILD_SHARED_LIBRARY)
