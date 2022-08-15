package com.yice.edu.cn.common.pojo.dm.classCard;
/**
 * 星期枚举<br>
 * 与Cron中的星期String值对应
 *
 * @see #SUN
 * @see #MON
 * @see #TUE
 * @see #WED
 * @see #THU
 * @see #FRI
 * @see #SAT
 * @author zzx
 *
 */
public enum WeekEnum {

	MON("MON"),
	TUE("TUE"),
	WED("WED"),
	THU("THU"),
	FRI("FRI"),
	SAT("SAT"),
	SUN("SUN");

	WeekEnum(String des) {
		this.des = des;
	}


	private String des;


	public String getDes() {
		return des;
	}


	public void setDes(String des) {
		this.des = des;
	}


}
