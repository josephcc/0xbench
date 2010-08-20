
LOCAL_CFLAGS:= -DTIME -pedantic -O2 -fomit-frame-pointer -fforce-addr -ffast-math

ifdef EXTRA_FLAGS
LOCAL_CFLAGS:= $(LOCAL_CFLAGS) $(EXTRA_FLAGS)
endif

ifndef LOCAL_SRC_FILES
LOCAL_SRC_FILES:= src/$(TARGET_MODULE).c 
endif

LOCAL_MODULE:= bench_ubench_$(TARGET_MODULE)
LOCAL_MODULE_TAGS := optional

include $(BUILD_EXECUTABLE)

