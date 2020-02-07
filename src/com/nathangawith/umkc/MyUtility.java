package com.nathangawith.umkc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.nathangawith.umkc.MyEnums.MyClass;
import com.nathangawith.umkc.MyEnums.MyInstructor;
import com.nathangawith.umkc.MyEnums.MyRoom;
import com.nathangawith.umkc.MyEnums.MyTimeSlot;

/**
 * class containing static methods used throughout
 */
public class MyUtility {

    /**
     * @param t1 the first time to check
     * @param t2 the second time to check
	 * @return true if the two time slots passed are adjacent
     */
    public static boolean areTimeSlotsAdjacent(MyTimeSlot t1, MyTimeSlot t2) {
    	int i1 = Arrays.asList(MyTimeSlot.values()).indexOf(t1);
    	int i2 = Arrays.asList(MyTimeSlot.values()).indexOf(t2);
    	return Math.abs(i1 - i2) == 1;
    }
    
    /**
     * @param r1 the first room to check
     * @param r2 the second room to check
     * @return true if the rooms are in the same building
     */
    public static boolean areInSameBuilding(MyRoom r1, MyRoom r2) {
    	if (r1 == MyRoom.HAAG206 || r1 == MyRoom.HAAG301) {
    		return r2 == MyRoom.HAAG206 || r2 == MyRoom.HAAG301;
    	} else if (r1 == MyRoom.FLARSHEIM260 || r1 == MyRoom.FLARSHEIM310) {
    		return r2 == MyRoom.FLARSHEIM260 || r2 == MyRoom.FLARSHEIM310;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 
     * Instructors and what they can teach:
     * 		Hare: CS 101, CS 201, CS 291, CS 303, CS 449, CS 461
     * 		Bingham: CS 101, CS 201, CS 191, CS 291, CS 449
     * 		Kuhail: CS 303, CS 341
     * 		Mitchell: CS 191, CS 291, CS 303, CS 341
     * 		Rao: CS 291, CS 303, CS 341, CS 461
     * 		Staff: any
     * @param i instructor to check
     * @param x class to check
     * @return true if <code>i</code> can teach <code>x</code>
     */
    public static boolean canTeach(MyInstructor i, MyClass x) {
    	HashMap<MyInstructor, List<MyClass>> map = new HashMap<MyInstructor, List<MyClass>>();
    	map.put(MyInstructor.HARE, Arrays.asList(new MyClass[] { MyClass.CS101A, MyClass.CS101B,
    			MyClass.CS201A, MyClass.CS201B, MyClass.CS291A, MyClass.CS291B, MyClass.CS303,
    			MyClass.CS449, MyClass.CS461 }));
		map.put(MyInstructor.BINGHAM, Arrays.asList(new MyClass[] { MyClass.CS101A, MyClass.CS101B,
				MyClass.CS201A, MyClass.CS201B, MyClass.CS191A, MyClass.CS191B, MyClass.CS291A,
				MyClass.CS291B, MyClass.CS449 }));
		map.put(MyInstructor.MITCHELL, Arrays.asList(new MyClass[] { MyClass.CS191A, MyClass.CS191B,
				MyClass.CS291A, MyClass.CS291B, MyClass.CS303, MyClass.CS341 }));
		map.put(MyInstructor.RAO, Arrays.asList(new MyClass[] { MyClass.CS291A, MyClass.CS291B,
				MyClass.CS303, MyClass.CS341, MyClass.CS461 }));
		map.put(MyInstructor.KUHAIL, Arrays.asList(new MyClass[] { MyClass.CS303, MyClass.CS341 }));
		map.put(MyInstructor.STAFF, Arrays.asList(new MyClass[] { MyClass.CS101A, MyClass.CS101B,
				MyClass.CS201A, MyClass.CS201B, MyClass.CS191A, MyClass.CS191B, MyClass.CS291A, MyClass.CS291B,
				MyClass.CS303, MyClass.CS341, MyClass.CS449, MyClass.CS461 }));
		return map.get(i).contains(x);
    }

    /**
     * Courses and expected enrollments are:
     * CS 101A (40), CS 101B (25), CS 201A (30), CS 201B (30),
     * CS 191A (60), CS 191B (20), CS 291B (40), CS 291A (20),
     * CS 303 (50), CS 341 (40), CS 449 (55), CS 461 (40).
     * @param x class to check
     * @return the expected enrollment for class <code>x</code>
     */
    public static int getCapacity(MyClass x) {
        HashMap<MyClass, Integer> map = new HashMap<MyClass, Integer>();
        map.put(MyClass.CS101A, 40);
        map.put(MyClass.CS101B, 25);
        map.put(MyClass.CS201A, 30);
        map.put(MyClass.CS201B, 30);
        map.put(MyClass.CS191A, 60);
        map.put(MyClass.CS191B, 20);
        map.put(MyClass.CS291B, 40);
        map.put(MyClass.CS291A, 20);
        map.put(MyClass.CS303, 50);
        map.put(MyClass.CS341, 40);
        map.put(MyClass.CS449, 55);
        map.put(MyClass.CS461, 40);
        return map.get(x);
    }

    /**
     * Rooms and capacities:
     * Haag 301 (70), Haag 206 (30), Royall 204 (70), Katz 209 (50),
     * Flarsheim 310 (80), Flarsheim 260 (25), Bloch 0009 (30).
     * @param x the room to check
     * @return the capacity of room <code>x</code>
     */
    public static int getCapacity(MyRoom x) {
        HashMap<MyRoom, Integer> map = new HashMap<MyRoom, Integer>();
        map.put(MyRoom.HAAG301, 70);
        map.put(MyRoom.HAAG206, 30);
        map.put(MyRoom.ROYALL204, 70);
        map.put(MyRoom.KATZ209, 50);
        map.put(MyRoom.FLARSHEIM310, 80);
        map.put(MyRoom.FLARSHEIM260, 25);
        map.put(MyRoom.BLOCH0009, 30);
        return map.get(x);
    }
}
