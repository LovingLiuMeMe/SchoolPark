package cn.lovingliu.pojo.bo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserBO implements Serializable {
    private static final long serialVersionUID = 1347832968647534291L;

    private Long id;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    private String username;
    @NotBlank(message = "登录密码不能为空")
    private String password;

    private String question;

    private String answer;

    private Integer role;

    private String portrait;

    private Integer ststus;

    private Integer infractionsCount;

    private Integer inBlacklist;

    private Date createdTime;

    private Date updatedTime;

}
