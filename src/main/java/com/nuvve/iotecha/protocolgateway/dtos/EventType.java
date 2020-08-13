package com.nuvve.iotecha.protocolgateway.dtos;

public enum EventType {
    EVSE_CREATE(0),
    EVSE_UPDATE(1),
    EVSE_DELETE(2),
    EVSE_DELETE_BY_ID(3),
    EVSE_FIND_BY_ID(4),
    ENERGY_METER_CREATE(5),
    ENERGY_METER_UPDATE(6),
    ENERGY_METER_DELETE(7),
    ENERGY_METER_DELETE_BY_ID(8),
    ENERGY_METER_FIND_BY_ID(9),
    PLUG_IN(10),
    UNPLUGGED(11),
    EVSE_INFO(12),
    DISPATCH(13),
    METER_INF(14),
    RESPONSE(15),
    ERROR(16);

    private final int value;

    /**
     * Internal Ctor
     *
     * @param value
     */
    EventType(int value) {
        this.value = value;
    }

    /**
     * @param value Value to convert
     * @return EventType
     */
    public static EventType fromValue(int value) {
        switch (value) {
            case 0:
                return EVSE_CREATE;
            case 1:
                return EVSE_UPDATE;
            case 2:
                return EVSE_DELETE;
            case 3:
                return EVSE_DELETE_BY_ID;
            case 4:
                return EVSE_FIND_BY_ID;
            case 5:
                return ENERGY_METER_CREATE;
            case 6:
                return ENERGY_METER_UPDATE;
            case 7:
                return ENERGY_METER_DELETE;
            case 8:
                return ENERGY_METER_DELETE_BY_ID;
            case 9:
                return ENERGY_METER_FIND_BY_ID;
            case 10:
                return PLUG_IN;
            case 11:
                return UNPLUGGED;
            case 12:
                return EVSE_INFO;
            case 13:
                return DISPATCH;
            case 14:
                return METER_INF;
            case 15:
                return RESPONSE;
            case 16:
                return ERROR;
            default:
                return ERROR;
        }
    }

    /**
     * Volatile value
     *
     * @return value
     */
    public int getValue() {
        return this.value;
    }
}
