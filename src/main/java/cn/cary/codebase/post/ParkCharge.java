package cn.cary.codebase.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ParkCharge.java
 * @Description TODO
 * @createTime 2021年10月15日 11:46:00
 */
@Data
public class ParkCharge {
    @ApiModelProperty(value = "记录id")
    private String itemId;
    @ApiModelProperty(value = "停车场名称")
    private String parkName;
    @ApiModelProperty(value = "停车场编号")
    private String parkCode;
    @ApiModelProperty(value = "车牌")
    private String carNumber;
    @ApiModelProperty(value = "收费时间")
    private String feesTime;
    @ApiModelProperty(value = "应收金额")
    private String ysMoney;
    @ApiModelProperty(value = "优惠金额")
    private String yhMoney;
    @ApiModelProperty(value = "实收金额")
    private String ssMoney;
    @ApiModelProperty(value = "全网优惠券金额")
    private String netDiscountMoney;
    @ApiModelProperty(value = "回滚减免金额")
    private String hgMoney;
    @ApiModelProperty(value = "入场时间")
    private String inTime;
    @ApiModelProperty(value = "收费操作员ID")
    private String operatorId;
    @ApiModelProperty(value = "收费操作员名称")
    private String operatorName;
    @ApiModelProperty(value = "收费设备id")
    private String equipmentId;
    @ApiModelProperty(value = "车辆入场纪录ID")
    private String parkInId;
    @ApiModelProperty(value = "付款方式")
    private String payType;
    @ApiModelProperty(value = "支付来源")
    private String payFrom;
    @ApiModelProperty(value = "代扣标识")
    private String replaceDeduct;
    @ApiModelProperty(value = "APP用户ID")
    private String appUserId;
    @ApiModelProperty(value = "套餐ID")
    private String userTypeId;
    @ApiModelProperty(value = "套餐名称")
    private String userTypeName;
    @ApiModelProperty(value = "卡ID")
    private String cardId;
    @ApiModelProperty(value = "备用字段")
    private String attach;

    @Override
    public String toString() {
        return "ParkCharge{" +
                "itemId='" + itemId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", feesTime='" + feesTime + '\'' +
                ", ysMoney='" + ysMoney + '\'' +
                ", yhMoney='" + yhMoney + '\'' +
                ", ssMoney='" + ssMoney + '\'' +
                ", netDiscountMoney='" + netDiscountMoney + '\'' +
                ", hgMoney='" + hgMoney + '\'' +
                ", inTime='" + inTime + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", equipmentId='" + equipmentId + '\'' +
                ", parkInId='" + parkInId + '\'' +
                ", payType='" + payType + '\'' +
                ", payFrom='" + payFrom + '\'' +
                ", replaceDeduct='" + replaceDeduct + '\'' +
                ", appUserId='" + appUserId + '\'' +
                ", userTypeId='" + userTypeId + '\'' +
                ", userTypeName='" + userTypeName + '\'' +
                ", cardId='" + cardId + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }
}
