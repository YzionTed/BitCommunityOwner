package com.push.message;

import java.io.Serializable;

/**
 * Created by DELL60 on 2018/2/28.
 */

public class JPushBean implements Serializable {

    /**
     * action : 100301
     * data : {"title":"安防警报：2号楼1单元202","police_state":1,"police_id":"5a96a6918d6ac17c91ed08c7","address":"2号楼1单元202","police_type":"","communityId":"5a82adf3b06c97e0cd6c0f3d","police_time":""}
     */

    private String action;
    private DataBean data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * title : 安防警报：2号楼1单元202
         * police_state : 1
         * police_id : 5a96a6918d6ac17c91ed08c7
         * address : 2号楼1单元202
         * police_type :
         * communityId : 5a82adf3b06c97e0cd6c0f3d
         * police_time :
         */

        private String title;
        private int auditStatus;
        private String userId;
        private String communityId;
        private String apply_id;
        private int relationship;
        private String roomId;
        private String notice_id;

        public String getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(String notice_id) {
            this.notice_id = notice_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

        public int getRelationship() {
            return relationship;
        }

        public void setRelationship(int relationship) {
            this.relationship = relationship;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }
}
