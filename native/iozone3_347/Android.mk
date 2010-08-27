LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CFLAGS:= -O3 -Dunix -DHAVE_ANSIC_C -DASYNC_IO -DHAVE_PREAD -DNAME='"linux-arm"' -DLINUX_ARM -DSHARED_MEM -Dlinux -D_LARGEFILE64_SOURCE -lrt -lpthread 

LOCAL_SRC_FILES:= iozone.c libasync.c libbif.c libasync.c

LOCAL_MODULE:= bench_iozone_iozone
LOCAL_MODULE_TAGS := optional

include $(BUILD_EXECUTABLE)

