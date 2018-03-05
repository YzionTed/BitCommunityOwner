package com.bit.fuxingwuye.bean;

import com.bit.fuxingwuye.base.ProprietorBean;

import java.util.List;

/**
 * Created by 23 on 2018/3/1.
 */

public class RecordData {
    /**
     * currentPage : 1
     * records : [{"area":1100,"auditStatus":1,"birthday":"1990-09-21","buildingId":"5a82ae1db06c97e0cd6c0f41","canApply":true,"checkInTime":1519747200000,"closed":false,"communityId":"5a82adf3b06c97e0cd6c0f3d","contract":"12345678","contractPhone":"15900010001","createAt":1519782979788,"createId":"5a82a37d9ce93e30677c3f9c","currentAddress":"测试地址","dataStatus":1,"householdAddress":"广东省广州市芳村区","id":"5a960c430cf2edc1ca6cd645","identityCard":"440883199009212278","miliUId":null,"name":"测试","nickName":"昵称","phone":"159******01","politicsStatus":1,"proprietorId":"5a82a37d9ce93e30677c3f9c","relationship":1,"remark":null,"roomId":"5a82b06db06c97e0cd6c1047","roomLocation":"和谐景苑2号楼1单元202","roomName":"1单元202","sex":1,"telPhone":"0766-5805462","updateAt":1519782979788,"updateBy":null,"userId":"5a82a37d9ce93e30677c3f9c","workUnit":"测试地址"}]
     * total : 1
     * totalPage : 1
     */

    private int currentPage;
    private int total;
    private int totalPage;
    private List<RecordData.RecordsBean> records;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<RecordData.RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordData.RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * area : 1100
         * auditStatus : 1
         * birthday : 1990-09-21
         * buildingId : 5a82ae1db06c97e0cd6c0f41
         * canApply : true
         * checkInTime : 1519747200000
         * closed : false
         * communityId : 5a82adf3b06c97e0cd6c0f3d
         * contract : 12345678
         * contractPhone : 15900010001
         * createAt : 1519782979788
         * createId : 5a82a37d9ce93e30677c3f9c
         * currentAddress : 测试地址
         * dataStatus : 1
         * householdAddress : 广东省广州市芳村区
         * id : 5a960c430cf2edc1ca6cd645
         * identityCard : 440883199009212278
         * miliUId : null
         * name : 测试
         * nickName : 昵称
         * phone : 159******01
         * politicsStatus : 1
         * proprietorId : 5a82a37d9ce93e30677c3f9c
         * relationship : 1
         * remark : null
         * roomId : 5a82b06db06c97e0cd6c1047
         * roomLocation : 和谐景苑2号楼1单元202
         * roomName : 1单元202
         * sex : 1
         * telPhone : 0766-5805462
         * updateAt : 1519782979788
         * updateBy : null
         * userId : 5a82a37d9ce93e30677c3f9c
         * workUnit : 测试地址
         */

        private String communityId;
        private String contract;
        private String contractPhone;
        private long createAt;
        private String createId;
        private String currentAddress;
        private int dataStatus;
        private String householdAddress;
        private String id;
        private String identityCard;
        private int auditStatus;
        private Object miliUId;
        private String name;
        private String nickName;
        private String phone;
        private int politicsStatus;
        private String proprietorId;
        private int relationship;
        private Object remark;
        private String roomId;
        private String roomLocation;
        private String roomName;
        private int sex;
        private String telPhone;
        private long updateAt;
        private Object updateBy;
        private String userId;
        private String workUnit;

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        public String getContractPhone() {
            return contractPhone;
        }

        public void setContractPhone(String contractPhone) {
            this.contractPhone = contractPhone;
        }

        public long getCreateAt() {
            return createAt;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public int getDataStatus() {
            return dataStatus;
        }

        public void setDataStatus(int dataStatus) {
            this.dataStatus = dataStatus;
        }

        public String getHouseholdAddress() {
            return householdAddress;
        }

        public void setHouseholdAddress(String householdAddress) {
            this.householdAddress = householdAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public Object getMiliUId() {
            return miliUId;
        }

        public void setMiliUId(Object miliUId) {
            this.miliUId = miliUId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPoliticsStatus() {
            return politicsStatus;
        }

        public void setPoliticsStatus(int politicsStatus) {
            this.politicsStatus = politicsStatus;
        }

        public String getProprietorId() {
            return proprietorId;
        }

        public void setProprietorId(String proprietorId) {
            this.proprietorId = proprietorId;
        }

        public int getRelationship() {
            return relationship;
        }

        public void setRelationship(int relationship) {
            this.relationship = relationship;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomLocation() {
            return roomLocation;
        }

        public void setRoomLocation(String roomLocation) {
            this.roomLocation = roomLocation;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public long getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(long updateAt) {
            this.updateAt = updateAt;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getWorkUnit() {
            return workUnit;
        }

        public void setWorkUnit(String workUnit) {
            this.workUnit = workUnit;
        }
    }
}
