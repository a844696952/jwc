package com.yice.edu.cn.common.pojo.jw.timetable.GA;


/**
 * 
* @ClassName: Room  
* @Description: 预留对象 
* @author xuchang  
* @date 2018年12月26日
 */
public class Room {
	
	private int id;
	
	private int capacity;
	
	private String buildingNumb;
	
	private String roomNumb;
	
	private int type;
	
	private int[] times;
	
	
	public Room(int id, int capacity, String buildingNumb, String roomNumb, int type,int[] times) {
		this.id = id;
		this.capacity = capacity;
		this.buildingNumb = buildingNumb;
		this.roomNumb = roomNumb;
		this.type = type;
		this.times=times;
	}


	public Room() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public String getBuildingNumb() {
		return buildingNumb;
	}


	public void setBuildingNumb(String buildingNumb) {
		this.buildingNumb = buildingNumb;
	}


	public String getRoomNumb() {
		return roomNumb;
	}


	public void setRoomNumb(String roomNumb) {
		this.roomNumb = roomNumb;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int[] getTimes() {
		return times;
	}


	public void setTimes(int[] times) {
		this.times = times;
	}
	
	
	
	
}
