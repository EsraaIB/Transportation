package net.tedata.trans.register;

import javax.persistence.*;

@Entity
public class Register {
	@EmbeddedId
	private Key mykey;
	private int busNo;
	private String branch;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	

	public int getBusNo() {
		return busNo;
	}
	public void setBusNo(int busNo) {
		this.busNo = busNo;
	}
	public Key getMykey() {
		return mykey;
	}
	public void setMykey(Key mykey) {
		this.mykey = mykey;
	}
	
	

}
