package com.bit.fuxingwuye.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 23 on 2018/2/25.
 */

public class Community {

    /**
     * currentPage : 1
     * records : [{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":null,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a8cfc54518089ae7afccc0d","imgUrl":null,"miliCId":null,"name":"虚拟小区","province":null,"rank":null,"updateAt":null,"yun_community_id":null},{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":null,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a8cfa62518089ae7afccc0c","imgUrl":null,"miliCId":null,"name":"天津展会","province":null,"rank":null,"updateAt":null,"yun_community_id":null},{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":null,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a82adf3b06c97e0cd6c0f3d","imgUrl":null,"miliCId":64,"name":"和谐景苑","province":null,"rank":null,"updateAt":null,"yun_community_id":81},{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":1519473296338,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a9152909ce95a94f49e6ea2","imgUrl":null,"miliCId":64,"name":"和谐景苑","province":null,"rank":null,"updateAt":1519473296338,"yun_community_id":81},{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":1519473311778,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a91529f9ce95a94f49e6ea3","imgUrl":null,"miliCId":64,"name":"和谐景苑","province":null,"rank":null,"updateAt":1519473311778,"yun_community_id":81},{"address":null,"area":null,"checkInRoomCnt":null,"city":null,"code":null,"coordinate":null,"country":null,"createAt":1519474563408,"createId":null,"dataStatus":1,"district":null,"householdCnt":null,"id":"5a9157839ce95a94f49e6ea4","imgUrl":null,"miliCId":64,"name":"和谐景苑","province":null,"rank":null,"updateAt":1519474563408,"yun_community_id":81}]
     * total : 6
     * totalPage : 1
     */

    private int currentPage;
    private int total;
    private int totalPage;
    private List<RecordsBean> records;

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

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean implements Serializable{
        /**
         * address : null
         * area : null
         * checkInRoomCnt : null
         * city : null
         * code : null
         * coordinate : null
         * country : null
         * createAt : null
         * createId : null
         * dataStatus : 1
         * district : null
         * householdCnt : null
         * id : 5a8cfc54518089ae7afccc0d
         * imgUrl : null
         * miliCId : null
         * name : 虚拟小区
         * province : null
         * rank : null
         * updateAt : null
         * yun_community_id : null
         */

        private String address;
        private String area;
        private String checkInRoomCnt;
        private String city;
        private String code;
        private String coordinate;
        private String country;
        private String createAt;
        private String createId;
        private int dataStatus;
        private String district;
        private String householdCnt;
        private String id;
        private String imgUrl;
        private String miliCId;
        private String name;
        private String province;
        private String rank;
        private String updateAt;
        private String yun_community_id;
        private int roomsAmount;

        public int getRoomsAmount() {
            return roomsAmount;
        }

        public void setRoomsAmount(int roomsAmount) {
            this.roomsAmount = roomsAmount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCheckInRoomCnt() {
            return checkInRoomCnt;
        }

        public void setCheckInRoomCnt(String checkInRoomCnt) {
            this.checkInRoomCnt = checkInRoomCnt;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public int getDataStatus() {
            return dataStatus;
        }

        public void setDataStatus(int dataStatus) {
            this.dataStatus = dataStatus;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getHouseholdCnt() {
            return householdCnt;
        }

        public void setHouseholdCnt(String householdCnt) {
            this.householdCnt = householdCnt;
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

        public String getMiliCId() {
            return miliCId;
        }

        public void setMiliCId(String miliCId) {
            this.miliCId = miliCId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }

        public String getYun_community_id() {
            return yun_community_id;
        }

        public void setYun_community_id(String yun_community_id) {
            this.yun_community_id = yun_community_id;
        }
    }
}
