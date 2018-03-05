package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 17:15
 */

public class RepairBean extends BaseObservable implements Serializable {

    private String id;
    private String repairNo;
    private String repairType;
    private String faultType;
    private String repairDescribe;
    private String repairAddress;
    private String repairAddressId;
    private String reportMan;
    private String reportPhone;
    private long repairTime;
    private String faultDegree;
    private String userId;
    private String userName;
    private String repairMan;
    private String repairPhone;
    private String repairId;
    private long acceptTime;
    private String repairStatus;
    private String rejectReason;
    private long finishTime;
    private String needPay;
    private String payStatus;
    private long payTime;
    private String plotId;
    private String evaluationStatus;
    private String employeeId;
    private String employeeName;
    private long beginTime;
    private long endTime;
    private List<ImageBean> imgUrls;
    private List<String> imgIds;

    public RepairBean() {
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getRepairNo() {
        return repairNo;
    }

    public void setRepairNo(String repairNo) {
        this.repairNo = repairNo;
    }

    @Bindable
    public String getRepairType() {
        if(repairType.equals("1")){
            return "住户";
        }else if(repairType.equals("2")){
            return "公共物业";
        }else{
            return repairType;
        }
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    @Bindable
    public String getFaultType() {
            switch (Integer.parseInt(faultType)){
                case 1:
                    return "水电燃气";
                case 2:
                    return "房屋结构";
                case 3:
                    return "安防消防";
                case 4:
                    return "电梯";
                case 5:
                    return "门禁";
                case 99:
                    return "其他";
                default:
                    return faultType;
            }
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    @Bindable
    public String getRepairDescribe() {
        return TextUtils.isEmpty(repairDescribe)?"":repairDescribe;
    }

    public void setRepairDescribe(String repairDescribe) {
        this.repairDescribe = repairDescribe;
    }

    @Bindable
    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    @Bindable
    public String getRepairAddressId() {
        return repairAddressId;
    }

    public void setRepairAddressId(String repairAddressId) {
        this.repairAddressId = repairAddressId;
    }

    @Bindable
    public String getReportMan() {
        return reportMan;
    }

    public void setReportMan(String reportMan) {
        this.reportMan = reportMan;
    }

    @Bindable
    public String getReportPhone() {
        return reportPhone;
    }

    public void setReportPhone(String reportPhone) {
        this.reportPhone = reportPhone;
    }

    @Bindable
    public String getRepairTime() {
        Date date = new Date(repairTime);
        return new SimpleDateFormat("MM-dd HH:mm").format(date);
    }

    public void setRepairTime(long repairTime) {
        this.repairTime = repairTime;
    }

    @Bindable
    public String getFaultDegree() {
        return faultDegree;
    }

    public void setFaultDegree(String faultDegree) {
        this.faultDegree = faultDegree;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Bindable
    public String getRepairMan() {
        return repairMan;
    }

    public void setRepairMan(String repairMan) {
        this.repairMan = repairMan;
    }

    @Bindable
    public String getRepairPhone() {
        return repairPhone;
    }

    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }

    @Bindable
    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    @Bindable
    public String getAcceptTime() {
        Date date = new Date(acceptTime);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    @Bindable
    public String getRepairStatus() {
        switch (Integer.parseInt(repairStatus)){
            case 1:
                return "待受理";
            case 2:
                return "已受理";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
            case 5:
                return "被驳回";
            default:
                return repairStatus;
        }
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    @Bindable
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Bindable
    public String getFinishTime() {
        Date date = new Date(finishTime);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    @Bindable
    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    @Bindable
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Bindable
    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    @Bindable
    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    @Bindable
    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    @Bindable
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Bindable
    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    @Bindable
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Bindable
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<ImageBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImageBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<String> getImgIds() {
        return imgIds;
    }

    public void setImgIds(List<String> imgIds) {
        this.imgIds = imgIds;
    }

    public static class ImageBean {
        private String id;
        private String imgUrl;
        private String imgName;
        private String contentType;

        public ImageBean() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }
}
