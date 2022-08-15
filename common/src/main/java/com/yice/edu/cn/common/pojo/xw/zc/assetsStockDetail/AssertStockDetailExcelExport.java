package com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class AssertStockDetailExcelExport {


	@Excel(name = "资产编号")
	private String assetsNo;

	@Excel(name ="资产名称")
	private String assetsName;

	@Excel(name ="资产属性，1：固定资产  2：易耗品")
	private String assetsProperty;
	
	@Excel(name = "资产状态，1：空闲  2：占用  3：维修中  4：故障 5：报废")
	private String status;

	@Excel(name = "责任人")
	private String dutyPersonName;

	@Excel(name = "楼栋名称")
	private String buildingName;

	@Excel(name = "使用场地")
	private String usePlaceName;

	@Excel(name ="是否超出维保期限")
	private String isOverMaintenanceTime;

	@Excel(name ="是否超出有效期限")
	private String  isOverValidTime;

	@Excel(name = "入库时间")
	private String putInTime;

}
