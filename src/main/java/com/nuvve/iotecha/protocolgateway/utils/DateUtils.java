package com.nuvve.iotecha.protocolgateway.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DateUtils {

    public static LocalDateTime epochMilliToLocaDateTime(long epoch) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
    }
}
