package com.example.subwayinfo;

// 调用JNI C++接口类
public class SubwayJNI {

    //单例
    public static SubwayJNI getInstance(){
        if(instance == null){
            instance = new SubwayJNI();
        }

        return instance;
    }

    private static SubwayJNI instance = null;

    // 登录
    public native boolean login(String username, String password, boolean regist);

    // 注册
    public native boolean regist(String username, String password);

    // 提供调用JNI接口方法
    public native void hello_jni();

    //加载cpp动态库
    static {
        System.loadLibrary("testjni"); //libtestjni.so
    }

}
