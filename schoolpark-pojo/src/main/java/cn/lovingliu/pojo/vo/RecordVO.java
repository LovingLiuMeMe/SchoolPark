package cn.lovingliu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordVO implements Serializable {
    private Integer id;

    private String userName;

    private String createrName;

    private String recordDescribe;

    private Integer recordStatus;

    private Integer amt;

    private Integer remarkType;

    private String remark;

    private Date createdTime;

    private Date updatedTime;

    List<PictureVO> pictureVOList;
}
