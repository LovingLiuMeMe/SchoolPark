package cn.lovingliu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordVO {
    private Integer id;

    private Integer userName;

    private Integer createrName;

    private String recordDescribe;

    private Integer recordStatus;

    private Integer amt;

    private Integer remarkType;

    private String remark;

    private Date createdTime;

    private Date updatedTime;

    List<PictureVO> pictureVOList;
}
