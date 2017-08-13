package com.zero.pattern.creator.beans;

public class AbstractSender implements Sender, Cloneable {

	private long id;

	protected String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}
	
	public void send(String message) {
		System.out.println(this.getClass().getSimpleName() + "#" + Integer.toHexString(this.hashCode()).toUpperCase()
				+ " send message :: " + message);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[ id:" + this.getId() + ", type : " + this.getType() + "]";
	}

	@Override
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}
