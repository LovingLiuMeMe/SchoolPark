package cn.lovingliu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Pictures implements Serializable {
    private Integer id;

    private Integer recordId;

    private String pictureName;

    private Integer pictureOrder;

    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table school_park_pictures
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_pictures.id
     *
     * @return the value of school_park_pictures.id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_pictures.id
     *
     * @param id the value for school_park_pictures.id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_pictures.record_id
     *
     * @return the value of school_park_pictures.record_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_pictures.record_id
     *
     * @param recordId the value for school_park_pictures.record_id
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_pictures.picture_name
     *
     * @return the value of school_park_pictures.picture_name
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public String getPictureName() {
        return pictureName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_pictures.picture_name
     *
     * @param pictureName the value for school_park_pictures.picture_name
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_pictures.picture_order
     *
     * @return the value of school_park_pictures.picture_order
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Integer getPictureOrder() {
        return pictureOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_pictures.picture_order
     *
     * @param pictureOrder the value for school_park_pictures.picture_order
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setPictureOrder(Integer pictureOrder) {
        this.pictureOrder = pictureOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column school_park_pictures.created_time
     *
     * @return the value of school_park_pictures.created_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column school_park_pictures.created_time
     *
     * @param createdTime the value for school_park_pictures.created_time
     *
     * @mbg.generated Fri Feb 07 18:15:37 CST 2020
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_pictures
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
        sb.append(", recordId=").append(recordId);
        sb.append(", pictureName=").append(pictureName);
        sb.append(", pictureOrder=").append(pictureOrder);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}