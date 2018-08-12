package com.przemo.RegulatorPID_1;

public class PID 
{
	//pid parameters
	private double kp;
	private double ti;
	private double td;
	//request value
	private double ud;
	// error
	private double e = 0;
	//previous error
	private double e_prev = 0;
	//calculate value
	public static int u;
	
	public static int u_aktualne;
	
	private double sumError = 0;
	
	private double deltaError = 0;




	public PID(double kp,double ti,double td,double ud)
	{
		this.kp = kp;
		this.ti = ti;
		this.td = td;
		this.ud = ud;
	}
	
	double calculateError()
	{
		e = ud - u_aktualne;
		return e;
	}
	public void returnCaltulateValue()
	{
		e = calculateError();
		sumError = sumError + e;
		deltaError = e - e_prev;
		u = (int)(kp*(e + (1/ti)*sumError +td*deltaError));
		e_prev = e;
	}
	public double getUd() {
		return ud;
	}


	public void setUd(float ud) {
		this.ud = ud;
	}


	public double getE() {
		return e;
	}


	public void setE(float e) {
		this.e = e;
	}


	public double getE_prev() {
		return e_prev;
	}


	public void setE_prev(float e_prev) {
		this.e_prev = e_prev;
	}
	public double getKp() {
		return kp;
	}

	public void setKp(float kp) {
		this.kp = kp;
	}

	public double getTi() {
		return ti;
	}

	public void setTi(float ti) {
		this.ti = ti;
	}

	public double getTd() {
		return td;
	}

	public void setTd(float td) {
		this.td = td;
	}
	
	
}
