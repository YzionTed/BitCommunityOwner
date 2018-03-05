package com.BIT.fuxingwuye.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dell on 2017/7/3.
 */

public class UserBean implements Serializable{

        private String id;
        private String userName;
        private String password;
        private int code;            //验证码（默认为0）
        private String mobilePhone;
        private String email;
        private int verityType;      //验证方式（0：短信验证；1：密码验证）：包括注册和登录
        private int sex;             //性别（0：未知；1：男；2：女）
        private int identityStatus;  //户主身份验证状态（1：未通过；2：未验证；3：通过），默认为未验证
        private String headImg;
        private String imgId;  //头像id（如果上传头像，则将上传接口返回的ID放在url后面传过来。如：http://xxx.xx.xx?imgId=xxxxx）
        private String nikeName;
        private String source;       //来源（0：本站注册账号； 1：微信； 2：新浪微博；3：QQ）
        private String identity;
        private String bdaddr;      //蓝牙地址
        private String address;
        private String marriage;     //婚姻状态（0：未婚 1：已婚 2：离异 3：其他）
        private String plotId;       //小区ID
        private String flootId;      //楼盘ID
        private Long createDate;
        private String creator;
        private String modifier;     //修改人
        private Long updateTime;
        private String owner;
        private String remark;
        private int dataStatus;      //数据状态（0：无效；1：有效），默认有效
        private String[] plotIds;
        private List<FloorMap> floorInfo;
        private int emchatStatus;
        private int validStatus;

    public UserBean() {
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getVerityType() {
            return verityType;
        }

        public void setVerityType(int verityType) {
            this.verityType = verityType;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getIdentityStatus() {
            return identityStatus;
        }

        public void setIdentityStatus(int identityStatus) {
            this.identityStatus = identityStatus;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public String getPlotId() {
            return plotId;
        }

        public void setPlotId(String plotId) {
            this.plotId = plotId;
        }

        public String getFlootId() {
            return flootId;
        }

        public void setFlootId(String flootId) {
            this.flootId = flootId;
        }

        public Long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Long createDate) {
            this.createDate = createDate;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDataStatus() {
            return dataStatus;
        }

        public void setDataStatus(int dataStatus) {
            this.dataStatus = dataStatus;
        }

    public String getBdaddr() {
        return bdaddr;
    }

    public void setBdaddr(String bdaddr) {
        this.bdaddr = bdaddr;
    }

    public String getOwner() {
        if(owner==null){
            return null;
        }
        if(owner.equals("1")){
            return "户主";
        }else if(owner.equals("2")){
            return "家属";
        }else if(owner.equals("3")){
            return "租客";
        }else{
            return owner;
        }
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getPlotIds() {
        return plotIds;
    }

    public void setPlotIds(String[] plotIds) {
        this.plotIds = plotIds;
    }

    public List<FloorMap> getFloorInfo() {
        return floorInfo;
    }

    public void setFloorInfo(List<FloorMap> floorInfo) {
        this.floorInfo = floorInfo;
    }

    public class FloorMap implements Serializable{
        private String floorId;
        private String address;

        public String getFloorId() {
            return floorId;
        }

        public void setFloorId(String floorId) {
            this.floorId = floorId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public int getEmchatStatus() {
        return emchatStatus;
    }

    public void setEmchatStatus(int emchatStatus) {
        this.emchatStatus = emchatStatus;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }
}
