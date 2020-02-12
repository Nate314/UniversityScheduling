package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.nathangawith.umkc.MyEnums.MyClass;
import com.nathangawith.umkc.MyEnums.MyInstructor;
import com.nathangawith.umkc.MyEnums.MyRoom;

public class MySchedule {

	private Collection<MyCourse> _courses;
	public Collection<MyCourse> getCourses() {
		Collection<MyCourse> result = new ArrayList<MyCourse>();
		for (MyCourse course : _courses )
			result.add(new MyCourse(
				course.getClasss(),
				course.getRoom(),
				course.getInstructor(),
				course.getTimeSlot()));
		return result;
	}

	public MySchedule(Collection<MyCourse> courses) {
		_courses = courses;
	}
	
	public void print() {
		StringBuilder sb = new StringBuilder("[");
		for (MyCourse course : _courses) {
			sb.append("{");
			sb.append(String.format("\"class\": \"%s\",", course.getClasss()));
			sb.append(String.format("\"room\": \"%s\",", course.getRoom()));
			sb.append(String.format("\"instructor\": \"%s\",", course.getInstructor()));
			sb.append(String.format("\"time\": \"%s\"", course.getTimeSlot()));
			sb.append("},");
		}
		sb.replace(sb.length() - 1, sb.length(), "");
		sb.append("]");
		System.out.println(sb.toString());
		System.out.println("Energy: " + this.getEnergy());
	}

	/**
	 * <h1>Fitness function:</h1>
	 * Assign instructors, times, rooms, and courses. For your initial population, this will be random. Assess the fitness function as follows:
	 * <pre>
	 * For each course that is taught by an instructor who can teach it, other than Staff: +3
	 * For each course taught by Staff: +1
	 * For each course that is the only course scheduled in that room at that time: +5
	 * For each course that is in a room large enough to accommodate it: +5
	 * 		Room capacity is no more than twice the expected enrollment: +2
	 * For each course that does not have the same instructor teaching another course at the same time: +5
	 * For each schedule that has the same instructor teaching more than 4 courses: -5 per course over 4
	 * For each schedule that has Rao or Mitchell (graduate faculty) teaching more courses than Hare or Bingham (same number of courses is OK): -10
	 * CS 101 and CS 191 are usually taken the same semester; the same applies to CS 201 and CS 291. Therefore apply these rules to those pairs of courses:
	 * 		Courses are scheduled for same time: -15
	 * 		Courses are scheduled for adjacent times: +5
	 * 		if these courses are scheduled for adjacent times, and
	 * 			Are in the same building: +5 points
	 * 			Are both on the quad (Haag, Royall, Flarsheim): no modification
	 * 			1 is in Katz and the other isn’t: -3
	 * 			1 is in Bloch and the other isn’t: -3
	 * 			(Yes, if one’s in Katz and the other’s in Bloch, that’s -6)
	 * CS101A and CS101B are scheduled 3 hours apart or more: +5
	 * CS191A and CS191B are scheduled 3 hours apart or more: +5
	 * </pre> 
	 * @return result given the rules above
	 */
	public int getEnergy() {
		
		List<MyClass> CS101 = Arrays.asList(new MyClass[]
				{ MyClass.CS101A, MyClass.CS101B });
		List<MyClass> CS201 = Arrays.asList(new MyClass[]
				{ MyClass.CS201A, MyClass.CS201B });
		List<MyClass> CS191 = Arrays.asList(new MyClass[] 
				{ MyClass.CS191A, MyClass.CS191B });
		List<MyClass> CS291 = Arrays.asList(new MyClass[]
				{ MyClass.CS291A, MyClass.CS291B });

		List<List<MyClass>> classesToCheck = new ArrayList<List<MyClass>>(); 
		classesToCheck.add(CS101);
		classesToCheck.add(CS191);
		
		int result = 0;
		
		HashMap<MyInstructor, Integer> instructorClassCounts = new HashMap<MyInstructor, Integer>();
		instructorClassCounts.put(MyInstructor.HARE, 0);
		instructorClassCounts.put(MyInstructor.BINGHAM, 0);
		instructorClassCounts.put(MyInstructor.KUHAIL, 0);
		instructorClassCounts.put(MyInstructor.MITCHELL, 0);
		instructorClassCounts.put(MyInstructor.RAO, 0);
		instructorClassCounts.put(MyInstructor.STAFF, 0);
		
		for (MyCourse course : _courses) {
			// For each course taught by Staff: +1
			if (course.getInstructor() == MyInstructor.STAFF) result += 1;
			
			// For each course that is the only course scheduled in that room at that time: +5
			boolean onlyOneClassScheduledForThisTimeThisRoom = true;
			// For each course that does not have the same instructor teaching another course at the same time: +5
			boolean onlyOneClassScheduledForThisTimeThisInstructor = true;
			for (MyCourse c : _courses) {
				if (c.getRoom() == course.getRoom() && c.getTimeSlot() == course.getTimeSlot())
					onlyOneClassScheduledForThisTimeThisRoom = false;
				if (c.getInstructor() == course.getInstructor() && c.getTimeSlot() == course.getTimeSlot())
					onlyOneClassScheduledForThisTimeThisInstructor = false;

				// CS 101 and CS 191 are usually taken the same semester; the same applies to CS 201 and CS 291. Therefore apply these rules to those pairs of courses:
				if (CS101.contains(course.getClasss()) || CS201.contains(course.getClasss())) {
					if ((CS101.contains(course.getClasss()) && CS191.contains(c.getClasss()))
						|| (CS201.contains(course.getClasss()) && CS291.contains(c.getClasss()))) {
						// Courses are scheduled for same time: -15
						if (course.getTimeSlot() == c.getTimeSlot()) result -= 15;
						// Courses are scheduled for adjacent times: +5
						if (MyUtility.areTimeSlotsAdjacent(course.getTimeSlot(), c.getTimeSlot())) {
							result += 5;
							// Are in the same building: +5 points
							if (MyUtility.areInSameBuilding(course.getRoom(), c.getRoom())) result += 5;
							// Are both on the quad (Haag, Royall, Flarsheim): no modification
							
							// 1 is in Katz and the other isn’t: -3
							if ((course.getRoom() == MyRoom.KATZ209 && c.getRoom() != MyRoom.KATZ209)
									|| (c.getRoom() == MyRoom.KATZ209 && course.getRoom() != MyRoom.KATZ209)) result -= 3;
							// 1 is in Bloch and the other isn’t: -3
							if ((course.getRoom() == MyRoom.BLOCH0009 && c.getRoom() != MyRoom.BLOCH0009)
									|| (c.getRoom() == MyRoom.BLOCH0009 && course.getRoom() != MyRoom.BLOCH0009)) result -= 3;
						}
					}
					// CS101A and CS101B are scheduled 3 hours apart or more: +5
					// CS191A and CS191B are scheduled 3 hours apart or more: +5
					for (List<MyClass> classSet : classesToCheck ) {
						if (course.getClasss() == classSet.get(0)
							&& c.getClasss() == classSet.get(1)) {
							int t1 = course.getTimeSlot().ordinal();
							int t2 = c.getTimeSlot().ordinal();
							int diff = Math.abs(t1 - t2);
							if (diff >= 3) result += 5;
						}	
					}
				}
			}
			if (onlyOneClassScheduledForThisTimeThisRoom) result += 5;
			if (onlyOneClassScheduledForThisTimeThisInstructor) result += 5;
			
			// For each course that is in a room large enough to accommodate it: +5
			if (MyUtility.getCapacity(course.getRoom()) > MyUtility.getCapacity(course.getClasss())) result += 5;
			
			// Room capacity is no more than twice the expected enrollment: +2
			if (MyUtility.getCapacity(course.getRoom()) >  2 * MyUtility.getCapacity(course.getClasss())) result += 2;
			
			// general counts
			instructorClassCounts.put(course.getInstructor(), instructorClassCounts.get(course.getInstructor()) + 1);
		}
		
		// For each schedule that has the same instructor teaching more than 4 courses: -5 per course over 4
		for (MyInstructor instructor : instructorClassCounts.keySet()) {
			int classCount = instructorClassCounts.get(instructor);
			result -= 5 * Math.max(0, classCount - 4);
		}
		
		// For each schedule that has Rao or Mitchell (graduate faculty) teaching more courses than Hare or Bingham (same number of courses is OK): -10
		int raoClassCount = instructorClassCounts.get(MyInstructor.RAO);
		int mitchellClassCount = instructorClassCounts.get(MyInstructor.MITCHELL);
		int hareClassCount = instructorClassCounts.get(MyInstructor.HARE);
		int binghamClassCount = instructorClassCounts.get(MyInstructor.BINGHAM);
		if (raoClassCount + mitchellClassCount > hareClassCount + binghamClassCount) result -= 10;
		
		return result;
	}
}
