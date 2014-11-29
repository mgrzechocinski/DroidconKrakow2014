-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-optimizations !class/unboxing/enum,!code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature
 
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService

-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class roboguice.** { *; }
-keep class **.FragmentManager
-keep @com.google.inject.Singleton class *
-keep @javax.inject.Singleton class *

-keepclasseswithmembernames class * { native <methods>; }

-keepclasseswithmembers class * { native <methods>; }
-keepclasseswithmembers class * { public <init> (android.content.Context, android.util.AttributeSet); }
-keepclasseswithmembers class * { public <init> (android.content.Context, android.util.AttributeSet, int); }

-keepclasseswithmembers class *{ @roboguice.inject.Inject* <fields>; }
-keepclasseswithmembers class *{ @com.google.inject.Inject <fields>; }
-keepclasseswithmembers class *{ @javax.inject.Inject <fields>; }
-keepclasseswithmembers class *{ @com.google.inject.Inject <init>(...); }
-keepclasseswithmembers class *{ @javax.inject.Inject <init>(...); }

-keepclassmembernames class *{
    @roboguice.inject.Inject* <fields>;
    @com.google.inject.Inject <fields>;
    @javax.inject.Inject <fields>;
    @com.google.inject.Inject <init>(...);
    @javax.inject.Inject <init>(...);
}

-keepclassmembers class * implements android.os.Parcelable { static android.os.Parcelable$Creator *; }
-keepclassmembers class **.R$* { public static <fields>; }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
}
-keepclassmembers class * {
	public <init>(...);
}
