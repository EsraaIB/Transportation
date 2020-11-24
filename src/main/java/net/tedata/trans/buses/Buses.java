package net.tedata.trans.buses;

import javax.persistence.*;

@Entity
public class Buses {

	private int busNo, noOfEmployee, capacity;
	@Transient
	private boolean checked;

	private String busName, busFrom, busTo, busTime ;

	@Id
	public int getBusNo() {
		return busNo;
	}

	public void setBusNo(int busNo) {
		this.busNo = busNo;
	}
	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getNoOfEmployee() {
		return noOfEmployee;
	}

	public void setNoOfEmployee(int noOfEmployee) {
		this.noOfEmployee = noOfEmployee;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusFrom() {
		return busFrom;
	}

	public void setBusFrom(String busFrom) {
		this.busFrom = busFrom;
	}

	public String getBusTo() {
		return busTo;
	}

	public void setBusTo(String busTo) {
		this.busTo = busTo;
	}

	public String getBusTime() {
		return busTime;
	}

	public void setBusTime(String busTime) {
		this.busTime = busTime;
	}

}
