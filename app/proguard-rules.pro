# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.itechcom.passwordgenerator.presenter.**{*;}


# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# Room Database
-keep class androidx.room.RoomDatabase
-keep class * extends androidx.room.RoomDatabase {
    public abstract *Dao UserEntity();
}

# Room Entity Classes
-keep class com.example.app.database.entity.** { *; }
-keepclassmembers class com.example.app.database.entity.** { *; }

# Room DAOs
-keep class com.example.app.database.dao.** { *; }
-keepclassmembers class com.example.app.database.dao.** { *; }

# Room Annotations
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Keep the SQLite database
-keep class androidx.sqlite.db.SupportSQLiteDatabase {
    <init>(...);
}

# Room database Schema information
-keep class * implements androidx.room.RoomDatabase$SchemaInfo {
    public static java.lang.String INSTANCE;
}

# Dagger
-keep class dagger.* { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }

# Keep Dagger modules
-keep class *Module

# Keep Dagger components
-keep interface *Component
-keep class *Dagger* { *; }

# Keep Dagger-specific annotations
-keepattributes *Annotation*

# Keep the Dagger annotation processor
-keep class dagger.internal.codegen.ComponentProcessor

# Hilt
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.android.** { *; }
-keep class javax.annotation.** { *; }

# Keep Hilt components
-keep interface dagger.hilt.android.components.*Component

# Keep Hilt-generated classes (typically ending with _HiltComponents)
-keep class *Hilt* { *; }

# Keep Hilt-specific annotations
-keepattributes *Annotation*

# If you use Hilt with ViewModel, you may need to include the following rules:
-keep class androidx.hilt.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Gson specific interface
-keep interface com.google.gson.** { *; }

# Keep the class that Gson uses to bind to JSON
-keep class com.google.gson.Gson { *; }

# Keep the names of classes/members we can use for serialization/deserialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep enum types with @SerializedName annotations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    @com.google.gson.annotations.SerializedName *;
}



