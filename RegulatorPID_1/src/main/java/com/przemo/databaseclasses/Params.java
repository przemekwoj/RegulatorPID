package com.przemo.databaseclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parameters")
public class Params 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id = 1;
	
	@Column(name = "kp")
	private double kp;
	
	@Column(name = "ti")
	private double ti;
	
	@Column(name = "td")
	private double td;
	
	@Column(name = "text")
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public double getKp() {
		return kp;
	}
	public void setKp(double kp) {
		this.kp = kp;
	}
	public double getTi() {
		return ti;
	}
	public void setTi(double ti) {
		this.ti = ti;
	}
	public double getTd() {
		return td;
	}
	public void setTd(double td) {
		this.td = td;
	}

}
