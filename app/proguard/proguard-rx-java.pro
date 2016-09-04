-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keep class rx.internal.util.unsafe.** { *; }
-keep class com.jakewharton.rxbinding.** { *; }
-dontwarn com.jakewharton.rxbinding.**