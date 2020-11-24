package net.tedata.trans.register;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Key implements Serializable
{
	private int uId;
	private String time;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
