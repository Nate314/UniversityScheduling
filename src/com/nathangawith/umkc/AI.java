package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.Random;

import com.nathangawith.umkc.MyEnums.MyClass;
import com.nathangawith.umkc.MyEnums.MyInstructor;
import com.nathangawith.umkc.MyEnums.MyRoom;
import com.nathangawith.umkc.MyEnums.MyTimeSlot;

public class AI {

	private MyClass[] allClasses = MyClass.values();
	private MyRoom[] allRooms = MyRoom.values();
	private MyInstructor[] allInstructors = MyInstructor.values();
	private MyTimeSlot[] allTimeSlots = MyTimeSlot.values();

	private Random random = new Random();
	private double t_max = 0.5;
//	private float t_min = 1f;
	private float t_delta = 0.95f;

	public AI() {
		long start = System.currentTimeMillis();
		// Simulated Annealing
		int resets = 0;
		int changes = 0;
		int attempts = 0;
		int successfullAttempts = 0;
		MySchedule bestSchedule = null;
		int bestScore = 0;
		MySchedule schedule = this.getRandomInitialSchedule();
		String str = "\rTemperature: %f, Energy: %d, Prob: %f > %f";
		double t = t_max;
		for (;;) {
			int e_s = schedule.getEnergy();
			MySchedule next = this.radomlyMutate(schedule);
			int e_n = next.getEnergy();
			int e_delta = e_n - e_s;
			double prob = Math.exp(((float) e_delta) / ((float) t));
			float rand = random.nextFloat();
			attempts++;
			if (e_delta >= 0) {
				schedule = next;
				successfullAttempts++;
				changes++;
			}
			else if (prob > rand) {
				schedule = next;
				changes++;
			}
			schedule.print();
			
			int nrg = schedule.getEnergy();
			if (nrg > bestScore) {
				bestSchedule = new MySchedule(schedule.getCourses());
				bestScore = nrg;
			}

			System.out.printf("\n%d, %d, %d", e_s, e_n, e_delta);
			System.out.printf(str, t, e_s, prob, rand);
			if (attempts % 4000 == 0 || successfullAttempts % 400 == 0) {
				if (changes == 0 && resets != 0) break;
				t *= t_delta;
				changes = 0;
				attempts = 0;
				successfullAttempts = 0;
				resets++;
			}
		}
//		if (bestSchedule.getEnergy() > 100) {
			this.printResult("Best:", bestSchedule);
			this.printResult("Final Schedule:", schedule);
			System.out.println();
			long now = System.currentTimeMillis();
			System.out.printf("Ran for %f seconds\n", (now - start) / 1000f);	
//		} else {
//			new AI();
//		}
	}
	
	private void printResult(String label, MySchedule s) {
		System.out.println();
		System.out.println();
		System.out.println("--------------------------------");
		System.out.println(label);
		s.print();
		System.out.println("--------------------------------");
	}

	/**
	 * randomly chooses to change the room, instructor, or time slot of one random
	 * course in the schedule.
	 * 
	 * @param schedule schedule to mutate
	 * @return slightly altered schedule
	 */
	private MySchedule radomlyMutate(MySchedule schedule) {
		ArrayList<MyCourse> courses = (ArrayList<MyCourse>) schedule.getCourses();
		int courseIndex = this.random.nextInt(courses.size());
		MyCourse course = courses.get(courseIndex);
		switch (random.nextInt(3)) {
			case 0:
				MyRoom aRoom = this.getRandomRoom();
				while (course.getRoom() == aRoom)
					aRoom = this.getRandomRoom();
				course.setRoom(aRoom);
				break;
			case 1:
				MyInstructor aInstructor = this.getRandomInstructor();
				while (course.getInstructor() == aInstructor)
					aInstructor = this.getRandomInstructor();
				course.setInstructor(aInstructor);
				break;
			case 2:
				MyTimeSlot aTimeSlot = this.getRandomTimeSlot();
				while (course.getTimeSlot() == aTimeSlot)
					aTimeSlot = this.getRandomTimeSlot();
				course.setTimeSlot(aTimeSlot);
				break;
		}
		courses.set(courseIndex, course);
		return new MySchedule(courses);
	}

	/**
	 * the random schedule returned has one of each class but the rooms,
	 * instructors, and time slots are assigned randomly.
	 * 
	 * @return random schedule
	 */
	private MySchedule getRandomInitialSchedule() {
		ArrayList<MyCourse> courses = new ArrayList<MyCourse>();
		for (MyClass aClass : this.allClasses)
			courses.add(new MyCourse(aClass,
				this.getRandomRoom(),
				this.getRandomInstructor(),
				this.getRandomTimeSlot()));
		MySchedule result = new MySchedule(courses);
		return result.getEnergy() > 0 ? result : this.getRandomInitialSchedule();
	}

	/**
	 * @return a randomly selected MyRoom
	 */
	private MyRoom getRandomRoom() {
		return this.allRooms[this.random.nextInt(this.allRooms.length)];
	}

	/**
	 * @return a randomly selected MyInstructor
	 */
	private MyInstructor getRandomInstructor() {
		return this.allInstructors[this.random.nextInt(this.allInstructors.length)];
	}

	/**
	 * @return a randomly selected MyTimeSlot
	 */
	private MyTimeSlot getRandomTimeSlot() {
		return this.allTimeSlots[this.random.nextInt(this.allTimeSlots.length)];
	}

}
