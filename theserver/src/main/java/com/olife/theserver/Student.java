package com.olife.theserver;

import java.io.Serializable;

public class Student implements Serializable{
	   private String sno;	   
	   private String sname;	      
	   private String sclass;	      
	   private String scollege;	      
	   private String sgrade;	      
	   private String ssex;	      
	   private int sstatus;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSclass() {
		return sclass;
	}
	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
	public String getScollege() {
		return scollege;
	}
	public void setScollege(String scollege) {
		this.scollege = scollege;
	}
	public String getSgrade() {
		return sgrade;
	}
	public void setSgrade(String sgrade) {
		this.sgrade = sgrade;
	}
	public String getSsex() {
		return ssex;
	}
	public void setSsex(String ssex) {
		this.ssex = ssex;
	}
	public int getSstatus() {
		return sstatus;
	}
	public void setSstatus(int sstatus) {
		this.sstatus = sstatus;
	}
	public Student(String sno, String sname, String sclass, String scollege, String sgrade, String ssex, int sstatus) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.sclass = sclass;
		this.scollege = scollege;
		this.sgrade = sgrade;
		this.ssex = ssex;
		this.sstatus = sstatus;
	}
	
	public Student(){
		super();
	}

}
