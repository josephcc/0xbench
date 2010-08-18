#include <jni.h>
 
#define LOG_TAG "TestLib"
#undef LOG
#include <utils/Log.h>
 
 
JNIEXPORT void JNICALL Java_org_zeroxlab_benchmark_Benchmark_printHello(JNIEnv * env, jobject jobj)
{
    LOGD("Hello LIB!\n");
}
