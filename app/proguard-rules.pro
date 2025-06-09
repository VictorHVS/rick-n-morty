# ----------------------------
# GENERAL KEEP RULES
# ----------------------------
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, Exceptions, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, AnnotationDefault

# Kotlin metadata and reflection
-keep class kotlin.Metadata { *; }
-keepclassmembers class ** { @kotlin.Metadata <fields>; ** Companion; }
-keepclassmembers class **$WhenMappings { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.**

# Jetpack Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
-keep class androidx.compose.ui.tooling.** { *; }
-dontwarn androidx.compose.ui.tooling.**
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }

# Dagger / Hilt
-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory$ViewModelFactoryHelper
-keep class * extends androidx.lifecycle.ViewModel
-keep @dagger.hilt.android.lifecycle.HiltViewModel class *

# Retrofit / OkHttp
-keep interface retrofit2.** { *; }
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepclassmembers class * { @retrofit2.http.* <methods>; }
-dontwarn okhttp3.**
-dontwarn okio.**
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation interface * extends <1>

# Kotlinx Serialization
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**
-keep @kotlinx.serialization.Serializable class ** { *; }

# Paging 3
-keep class androidx.paging.** { *; }
-dontwarn androidx.paging.**

# Landscapist / Glide
-keep class com.skydoves.landscapist.** { *; }
-dontwarn com.skydoves.landscapist.**

# Application-specific classes
-keep class com.victorhvs.rnm.** { *; }
-dontwarn com.victorhvs.rnm.**

# Kotlin Coroutines
-keepclassmembers class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Retrofit suspend function return types
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# Logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}