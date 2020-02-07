package com.nathangawith.umkc;

import java.util.Random;

public class AI {

	private Random random = new Random();
	private int t_max = 100;
	private int t_min = 0;
	private float t_delta = 0.9f;
	
	public AI() {
		MySchedule schedule = getRandomInitialSchedule();
		for (int t = t_max; t > t_min; t *= t_delta) {
			// schedule.print();
			System.out.println(String.format("\rTemperature: %d", t));
			int e_s = schedule.getEnergy();
			MySchedule next = radomlyMutate(schedule);
			int e_n = next.getEnergy();
			int e_delta = e_n - e_s;
			if (e_delta > 0) schedule = next;
			else if (randomProbability(e_delta, t)) schedule = next;
		}
	}
	
	private boolean randomProbability(int e_delta, int temperature) {
		return Math.exp(((double) e_delta) / ((double) temperature)) > random.nextFloat();
	}
	
	private MySchedule radomlyMutate(MySchedule schedule) {
		return null;
	}
	
	private MySchedule getRandomInitialSchedule() {
		return null;
	}

}
