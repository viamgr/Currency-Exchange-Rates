#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_app_vahid_datasource_remote_base_SecureKey_publicKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgIb90m7pbOSUAAaK38X9nIIqqRZ0gDOScgAW3EMR+WDmjP6mQkYEFCNnnZTjEpSPN7zr5+JAPamUon6S542iyzZzpBwvaFUW0+XwzvL5DJkeKxgjKHpVnxxasSYoxyaJqLcLSbAcHQI36GbgUPzc4yeBZdpevHCzvCwLVQs0qwQIDAQAB";
    return env->NewStringUTF(publicKey.c_str());
}
