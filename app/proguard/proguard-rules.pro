-include proguard-annotations.pro
-include proguard-google-play-services.pro
-include proguard-gson.pro
-include proguard-retrolambda.pro
-include proguard-rx-java.pro
-include proguard-square-okhttp3.pro
-include proguard-square-picasso.pro
-include proguard-square-retrofit.pro
-include proguard-support-design.pro
-include proguard-support-v7-appcompat.pro
-include proguard-support-v7-cardview.pro

-verbose
-dontobfuscate
-dump class_files.txt
-printseeds seeds.txt
-printmapping mapping.text
-printusage unused.txt

-keepattributes Signature
-keepattributes *Annotation*

-keep,allowobfuscation class com.kayak.**
-keepclassmembers class com.liujuan.destination.** {
  public protected private *;
}

-keep class java.nio.** { *; }
-keep class org.codehaus.** { *; }
-dontwarn java.nio.**
-dontwarn org.codehaus.**