package com.yice.edu.cn.common.pojo.dm.classCard;
/**
 * 星期枚举<br>
 * 与Cron中的星期String值对应
 * @author zzx
 *
 */
public enum EquipmentEnum {

	MON_START("周一开机"),
	TUE_START("周二开机"),
	WED_START("周三开机"),
	THU_START("周四开机"),
	FRI_START("周五开机"),
	SAT_START("周六开机"),
	SUN_START("周日开机"),
	MON_END("周一关机"),
	TUE_END("周二关机"),
	WED_END("周三关机"),
	THU_END("周四关机"),
	FRI_END("周五关机"),
	SAT_END("周六关机"),
	SUN_END("周日关机");

	EquipmentEnum(String des) {
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
