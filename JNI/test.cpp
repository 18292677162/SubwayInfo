//
// Created by Machenike on 2020/4/13.
//
#include <jni.h>
#include <android/log.h>

void testJNI_Hello()
{
    __android_log_print(ANDROID_LOG_ERROR, "testjni", "JNI:hello jni");
    return;
}

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_subwayinfo_SubwayJNI
 * Method:    hello_jni
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_subwayinfo_SubwayJNI_hello_1jni
  (JNIEnv * env, jobject obj)
  {
      testJNI_Hello();
  }

#ifdef __cplusplus
}
#endif

