package cn.lovingliu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PictureVO implements Serializable {
    private Integer pictureId;
    private String pictureName;
    private Integer pictureOrder;
}
