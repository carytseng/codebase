package cn.cary.codebase.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ParkInDTO.java
 * @Description 入场信息DTO
 * @createTime 2021年10月15日 11:20:00
 */
@Data
public class ParkInDTO {
    @ApiModelProperty(value = "记录id")
    private String itemId;
    @ApiModelProperty(value = "停车场名称")
    private String parkName;
    @ApiModelProperty(value = "停车场编号")
    private String parkCode;
    @ApiModelProperty(value = "入场时间")
    private String inTime;
    @ApiModelProperty(value = "操作员")
    private String inOperator;
    @ApiModelProperty(value = "车牌")
    private String carNumber;
    @ApiModelProperty(value = "未发行的卡号，如纸票号等")
    private String idno;
    @ApiModelProperty(value = "设备名称")
    private String equipName;
    @ApiModelProperty(value = "设备编号")
    private String equipCode;
    @ApiModelProperty(value = "图片路径")
    private String inCarPhoto;
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
        return "ParkInDTO{" +
                "itemId='" + itemId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", inTime='" + inTime + '\'' +
                ", inOperator='" + inOperator + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", idno='" + idno + '\'' +
                ", equipName='" + equipName + '\'' +
                ", equipCode='" + equipCode + '\'' +
                ", inCarPhoto='" + inCarPhoto + '\'' +
                ", vehicleInfo='" + vehicleInfo + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", attach='" + attach + '\'' +
                ", isRoad=" + isRoad +
                ", carportNo='" + carportNo + '\'' +
                ", citySystemNo='" + citySystemNo + '\'' +
                '}';
    }
}
