package com.bxb.hamrahi_app.util;

/**
 * Enum representing different categories of incidents that can be reported by users.
 * This enum can be used to classify and organize incidents based on their nature,
 * making it easier for authorities to prioritize and address them effectively.
 */
public enum IncidentCategory {

    /** Enum constant for reporting a road accident. */
    ROAD_ACCIDENT,
    /** Enum constant for reporting a pothole on the road. */
    POTHOLE,
/** Enum constant for reporting damage to the road infrastructure. */
    ROAD_DAMAGE,
/** Enum constant for reporting traffic congestion issues. */
    TRAFFIC_CONGESTION,
/** Enum constant for garbage dumping in unauthorized areas. */
    GARBAGE_DUMP,
/** Enum constant for reporting water logging issues. */
    WATER_LOGGING,
/** Enum constant for reporting power outages in the area. */
    STREET_LIGHT_ISSUE,
/** Enum constant for reporting illegal parking activities. */
    ILLEGAL_PARKING,
/** Enum constant for reporting noise pollution issues. */
    CONSTRUCTION_HAZARD,
/** Enum constant for reporting public property damage. */
    PUBLIC_PROPERTY_DAMAGE,
/** Enum constant for reporting incidents of crime or suspicious activities. */
    POOR_SANITATION,
/** Enum constant for reporting any other type of incident that does not
 *  fit into the above categories. */
    OTHER
}
