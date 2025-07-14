# Reglas básicas para Compose Multiplatform
-keep class androidx.compose.** { *; }
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Mantener tu paquete principal
-keep class org.catsproject.project.** { *; }

# Reglas para kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.** { *; }
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Reglas para OkHttp (ignorar warnings de Android)
-dontwarn okhttp3.internal.platform.Android**
-dontwarn okhttp3.internal.platform.ConscryptPlatform**
-dontwarn okhttp3.internal.platform.BouncyCastlePlatform**
-dontwarn okhttp3.internal.platform.OpenJSSEPlatform**
-dontwarn okhttp3.internal.platform.android.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn android.**
-dontwarn dalvik.**

# Reglas para Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Reglas para Realm (MongoDB)
-keep class io.realm.kotlin.** { *; }
-keep class org.mongodb.** { *; }
-dontwarn io.realm.**
-dontwarn org.mongodb.**

# Reglas para SLF4J
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# Mantener serializadores generados automáticamente
-keepclassmembers class * {
    *** Companion;
}
-keepclassmembers class **$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class **$serializer {
    *;
}

# Evitar warnings de clases dinámicas
-dontnote **

# Mantener metadatos de reflexión
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Reglas para R8 compatibility
-dontwarn java.lang.invoke.StringConcatFactory