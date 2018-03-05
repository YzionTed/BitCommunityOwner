package com.bit.communityOwner.model;

import java.util.List;

/**
 * Created by zhangjiajie on 18/3/1.
 */

public class UserInfo {
    /**
     * age : 27
     * attach : {"loginCommunity":"5a82adf3b06c97e0cd6c0f3d","loginCommunityName":"和谐景苑"}
     * bdaddr : 7C:50:49:52:94:D8
     * birthday : 1990-09-21
     * createAt : 1518510973846
     * createId : 5a82a22c9ce93e30677c3f9a
     * currentAddress : 当前地址
     * dataStatus : 1
     * email : 123456789@qq.com
     * headImg : https://www.cnblogs.com/images/logo_small.gif
     * householdAddress : 户口所在地
     * id : 5a82a37d9ce93e30677c3f9c
     * identityCard : 440***********2278
     * loginName : 15900010001
     * name : 业主1号555
     * nickName : 昵称
     * password : e10adc3949ba59abbe56e057f20f883e
     * permissions : ["permissionsB","permissionsA"]
     * phone : 159******01
     * politicsStatus : 0
     * profession : 职位
     * remark : 备注信息
     * roles : ["roleA","roleB"]
     * sex : 1
     * telPhone : 010-1234567
     * updateAt : 1519884064229
     * userDevice : null
     * validity : 0
     * verified : 0
     * workUnit : 工作单位
     */

    private int age;
    private String attach;
    private String bdaddr;
    private String birthday;
    private long createAt;
    private String createId;
    private String currentAddress;
    private int dataStatus;
    private String email;
    private String headImg;
    private String householdAddress;
    private String id;
    private String identityCard;
    private String loginName;
    private String name;
    private String nickName;
    private String password;
    private String phone;
    private int politicsStatus;
    private String profession;
    private String remark;
    private int sex;
    private String telPhone;
    private long updateAt;
    private Object userDevice;
    private int validity;
    private int verified;
    private String workUnit;
    private List<String> permissions;
    private List<String> roles;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBdaddr() {
        return bdaddr;
    }

    public void setBdaddr(String bdaddr) {
        this.bdaddr = bdaddr;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Object getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(Object userDevice) {
        this.userDevice = userDevice;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
