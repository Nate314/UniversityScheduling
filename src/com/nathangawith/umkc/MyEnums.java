package com.nathangawith.umkc;

/**
 * class containing static enumerations used throughout this application
 */
public class MyEnums {

    /**
	 * Enumeration of available classes
     */
    public static enum MyClass {
        CS101A, CS101B, CS201A, CS201B,
        CS191A, CS191B, CS291A, CS291B,
        CS303, CS341, CS449, CS461
    }

    /**
	 * Enumeration of available time slots
     */
    public static enum MyTimeSlot {
        M10, M11, M12, M13, M14, M15, M16
    }

    /**
	 * Enumeration of available rooms
     */
    public static enum MyRoom {
        HAAG301, HAAG206, ROYALL204, KATZ209,
        FLARSHEIM310, FLARSHEIM260, BLOCH0009
    }

    /**
	 * Enumeration of available instructors
     */
    public static enum MyInstructor {
    	HARE, BINGHAM, KUHAIL, MITCHELL, RAO, STAFF
    }

}
