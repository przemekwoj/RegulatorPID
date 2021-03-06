package com.przemo.RegulatorPID_1;

public class PID_Thread implements Runnable
{
	private boolean isFinished = false;
	//pid parameters
		private double kp;
		private double ti;
		private double td;
		//request value
		private int ud;
	@Override
	public void run() 
	{
		System.out.println(kp);
		 PID pid = new PID(kp, ti, td, ud);
		while(!isFinished)
		{
			 pid.returnCaltulateValue();
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	public void setValue(double kp,double ti,double td, int ud)
	{
		this.kp = kp;
		this.ti= ti;
		this.td = td;
		this.ud = ud;
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
	public float getUd() {
		return ud;
	}
	public void setUd(int ud) {
		this.ud = ud;
	}
	
}
