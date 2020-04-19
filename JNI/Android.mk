LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)

# libcurl.a
LOCAL_MODULE := libcurl
LOCAL_SRC_FILES := libcurl.a
include $(PREBUILT_STATIC_LIBRARY)


include $(CLEAR_VARS)

#libtestjni.so
LOCAL_MODULE := testjni
LOCAL_SRC_FILES := test.cpp login.cpp cJSON.cpp
LOCAL_LDLIBS := -llog -lz
LOCAL_STATIC_LIBRARIES := libcurl

#APP_STL := stlport_static
#APP_CFLAGS := -std=c++11

include $(BUILD_SHARED_LIBRARY)