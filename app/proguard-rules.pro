# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android-sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class io.codetail.animation.arcanimator.** { *; }
-keep class android.support.v7.widget.SearchView { *; }
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn javax.annotation.**
-dontwarn com.viewpagerindicator.LinePageIndicator

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.GeneratedAppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Uncomment this to preserve the line sectionNumber information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line sectionNumber information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
