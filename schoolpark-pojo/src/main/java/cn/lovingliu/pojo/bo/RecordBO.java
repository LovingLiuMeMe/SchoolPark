package cn.lovingliu.pojo.bo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class RecordBO {
    private Integer id;

    @NotNull(message = "违停者信息不能空")
    private Integer userId;

    private Integer createrId;

    private String recordDescribe;

    private Integer recordStatus;

    private Integer amt;

    private Integer remarkType;

    private String remark;

    private Date createdTime;

    private Date updatedTime;

    private List<String> picturesName;
}
