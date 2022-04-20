package com.example.easymed;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_FIRSTNAME = "firstname";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_HEALTHCARD = "healthcard";
        public static final String COLUMN_NAME_ADDRESS = "address";

    }
    public static class FeedEntryLocations implements BaseColumns {
        public static final String TABLE_NAME = "locations";
        public static final String COLUMN_NAME_LOCATIONNAME = "locationname";
    }
    public static class FeedEntryAppointments implements BaseColumns {
        public static final String TABLE_NAME = "appointments";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DOCTOR = "doctor";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_TIME = "time";
    }

    public static class FeedEntryDoctors implements BaseColumns {
        public static final String TABLE_NAME = "doctors";
        public static final String COLUMN_NAME_DOCTORNAME = "doctorname";
    }
}

