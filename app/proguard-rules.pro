#
# by Weikton
#

-dontoptimize
-keep public class com.nvidia.devtech.* { *; }
-keep public class com.wardrumstudios.utils.* { *; }
-keep public class com.mati.game.gui.* { *; }
-keep public class com.mati.game.gui.util.* { *; }
-keep public class com.mati.game.core.* { *; }
#-keep public class com.byreytiz.launcher.* { *; }
-keep public class com.mati.launcher.activity.* { *; }
-keep public class com.mati.launcher.adapter.* { *; }
-keep public class com.mati.launcher.fragment.* { *; }
-keep public class com.mati.launcher.data.model.* { *; }
-keep public class com.mati.launcher.utils.* { *; }
-keep public class com.mati.mati.reg.* { *; }

#
-keep class com.google.gson.stream.** { *; }

#
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# 
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.** 
-dontwarn java.nio.file.*

