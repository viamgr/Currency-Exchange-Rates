# Serialization
-keepattributes InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    #noinspection ShrinkerUnresolvedReference
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.ebayk.**$$serializer { *; }
-keepclassmembers class app.vahid.** {
    *** Companion;
}
-keepclasseswithmembers class app.vahid.** {
    #noinspection ShrinkerUnresolvedReference
    kotlinx.serialization.KSerializer serializer(...);
}
