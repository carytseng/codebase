package cn.cary.codebase.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ParkOutDTO.java
 * @Description 出场信息DTO
 * @createTime 2021年10月15日 11:20:00
 */
@Data
public class ParkOutDTO {
    @ApiModelProperty(value = "记录id")
    private String itemId;
    @ApiModelProperty(value = "停车场名称")
    private String parkName;
    @ApiModelProperty(value = "停车场编号")
    private String parkCode;
    @ApiModelProperty(value = "车牌")
    private String carNumber;
    @ApiModelProperty(value = "出场时间")
    private String outTime;
    @ApiModelProperty(value = "应收金额")
    private Double ysMoney;
    @ApiModelProperty(value = "优惠金额")
    private Double yhMoney;
    @ApiModelProperty(value = "实收金额")
    private Double ssMoney;
    @ApiModelProperty(value = "免费金额")
    private Double freeMoney;
    @ApiModelProperty(value = "回滚减免金额")
    private Double hgMoney;
    @ApiModelProperty(value = "入场时间")
    private String inTime;
    @ApiModelProperty(value = "入场设备编号")
    private String inEquipCode;
    @ApiModelProperty(value = "入场设备名称")
    private String inEquipName;
    @ApiModelProperty(value = "入场记录id")
    private String inRecordId;
    @ApiModelProperty(value = "车辆出场方式")
    private String outMode;
    @ApiModelProperty(value = "操作员")
    private String outOperator;
    @ApiModelProperty(value = "出场设备编号")
    private String outEquipCode;
    @ApiModelProperty(value = "车场设备名称")
    private String outEquipName;
    @ApiModelProperty(value = "付款方式")
    private String payTypeName;
    @ApiModelProperty(value = "未发行的卡号，如纸票号等")
    private String idno;
    @ApiModelProperty(value = "是否是收费记录")
    private Integer isReal;
    @ApiModelProperty(value = "图片路径")
    private String outCarPhoto;
    @ApiModelProperty(value = "车辆信息")
    private String vehicleInfo;
    @ApiModelProperty(value = "联系地址")
    private String address;
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "备用字段")
    private String attach;
    @ApiModelProperty(value = "路内车场标记")
    private Integer isRoad;
    @ApiModelProperty(value = "泊位号")
    private String carportNo;
    @ApiModelProperty(value = "停车系统编号")
    private String citySystemNo;

    @Override
    public String toString() {
        return "ParkOutDTO{" +
                "itemId='" + itemId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", outTime='" + outTime + '\'' +
                ", ysMoney='" + ysMoney + '\'' +
                ", yhMoney='" + yhMoney + '\'' +
                ", ssMoney='" + ssMoney + '\'' +
                ", freeMoney='" + freeMoney + '\'' +
                ", hgMoney='" + hgMoney + '\'' +
                ", inTime='" + inTime + '\'' +
                ", inEquipCode='" + inEquipCode + '\'' +
                ", inEquipName='" + inEquipName + '\'' +
                ", inRecordId='" + inRecordId + '\'' +
                ", outMode='" + outMode + '\'' +
                ", outOperator='" + outOperator + '\'' +
                ", outEquipCode='" + outEquipCode + '\'' +
                ", outEquipName='" + outEquipName + '\'' +
                ", payTypeName='" + payTypeName + '\'' +
                ", idno='" + idno + '\'' +
                ", isReal='" + isReal + '\'' +
                ", outCarPhoto='" + outCarPhoto + '\'' +
                ", vehicleInfo='" + vehicleInfo + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", attach='" + attach + '\'' +
                ", isRoad='" + isRoad + '\'' +
                ", carportNo='" + carportNo + '\'' +
                ", citySystemNo='" + citySystemNo + '\'' +
                '}';
    }
}
