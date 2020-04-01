package cn.lovingliu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PictureVO {
    private Integer pictureId;
    private String pictureName;
    private Integer pictureOrder;
}
