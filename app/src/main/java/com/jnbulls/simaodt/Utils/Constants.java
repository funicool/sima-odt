package com.jnbulls.simaodt.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Constants {

    // Name of Notification Channel for verbose notifications of background work
    public static final CharSequence VERBOSE_NOTIFICATION_CHANNEL_NAME =
            "Verbose WorkManager Notifications";
    public static final String KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA";
    public static String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts";
    public static final CharSequence NOTIFICATION_TITLE = "Subiendo ODT al Servidor";
    public static final String CHANNEL_ID = "VERBOSE_NOTIFICATION";
    public static final int NOTIFICATION_ID = 1;

    // The name of the Sync Data work
    public static final String SYNC_DATA_WORK_NAME = "Datos Finales";
    public static final String SYNC_DATA_WORK_NAME_ODT = "ODT_";
    public static final String SYNC_DATA_WORK_NAME_DETAIL = "Detalles ODT";
    // Other keys
    public static final long DELAY_TIME_MILLIS = 3000;

    public static final String TAG_SYNC_DATA = "Sync Data From Datos Finales";
    public static final String TAG_SYNC_ODT = "Sync Ordenes de Trabajo";
    public static final String TAG_SYNC_DETAIL = "Sync Detalles de la ODT";

    public static String getDate(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Ensures this class is never instantiated
    private Constants() {
    }
}