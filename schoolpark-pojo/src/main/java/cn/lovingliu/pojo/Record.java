package cn.lovingliu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer createrId;

    private String recordDescribe;

    private Integer recordStatus;

    private Integer amt;

    private Integer remarkType;

    private String remark;

    private Date createdTime;

    private Date updatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table school_park_record
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.id
     *
     * @return the value of school_park_record.id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.id
     *
     * @param id the value for school_park_record.id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.user_id
     *
     * @return the value of school_park_record.user_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.user_id
     *
     * @param userId the value for school_park_record.user_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.creater_id
     *
     * @return the value of school_park_record.creater_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getCreaterId() {
        return createrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.creater_id
     *
     * @param createrId the value for school_park_record.creater_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.record_describe
     *
     * @return the value of school_park_record.record_describe
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public String getRecordDescribe() {
        return recordDescribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.record_describe
     *
     * @param recordDescribe the value for school_park_record.record_describe
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setRecordDescribe(String recordDescribe) {
        this.recordDescribe = recordDescribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.record_status
     *
     * @return the value of school_park_record.record_status
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getRecordStatus() {
        return recordStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.record_status
     *
     * @param recordStatus the value for school_park_record.record_status
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.amt
     *
     * @return the value of school_park_record.amt
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getAmt() {
        return amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.amt
     *
     * @param amt the value for school_park_record.amt
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.remark_type
     *
     * @return the value of school_park_record.remark_type
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getRemarkType() {
        return remarkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.remark_type
     *
     * @param remarkType the value for school_park_record.remark_type
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.remark
     *
     * @return the value of school_park_record.remark
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.remark
     *
     * @param remark the value for school_park_record.remark
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.created_time
     *
     * @return the value of school_park_record.created_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.created_time
     *
     * @param createdTime the value for school_park_record.created_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_record.updated_time
     *
     * @return the value of school_park_record.updated_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_record.updated_time
     *
     * @param updatedTime the value for school_park_record.updated_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_record
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", createrId=").append(createrId);
        sb.append(", recordDescribe=").append(recordDescribe);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", amt=").append(amt);
        sb.append(", remarkType=").append(remarkType);
        sb.append(", remark=").append(remark);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}