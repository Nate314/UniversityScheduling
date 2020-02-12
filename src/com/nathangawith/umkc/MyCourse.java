package com.nathangawith.umkc;

import com.nathangawith.umkc.MyEnums.MyClass;
import com.nathangawith.umkc.MyEnums.MyRoom;
import com.nathangawith.umkc.MyEnums.MyInstructor;
import com.nathangawith.umkc.MyEnums.MyTimeSlot;

/**
 * MyCourse object is used to bundle a class, room, instructor and time slot
 */
public class MyCourse {
	
	// private fields
	private MyClass _class;
	private MyRoom _room;
	private MyInstructor _instructor;
	private MyTimeSlot _timeSlot;

	// public getters
	public MyClass getClasss() { return _class; }
	public MyRoom getRoom() { return _room; }
	public MyInstructor getInstructor() { return _instructor; }
	public MyTimeSlot getTimeSlot() { return _timeSlot; }
	// public setters
	public void setClasss(MyClass val) { this._class = val; }
	public void setRoom(MyRoom val) { this._room = val; }
	public void setInstructor(MyInstructor val) { this._instructor = val; }
	public void setTimeSlot(MyTimeSlot val) { this._timeSlot = val; }
	
	// constructor
	public MyCourse(MyClass c, MyRoom r, MyInstructor i, MyTimeSlot t) {
		_class = c;
		_room = r;
		_instructor = i;
		_timeSlot = t;
	}
}
