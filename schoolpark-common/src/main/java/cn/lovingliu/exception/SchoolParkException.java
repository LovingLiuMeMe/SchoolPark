package cn.lovingliu.exception;

import cn.lovingliu.enums.ExceptionCodeEnum;
import lombok.Data;

/**
 * @Author：LovingLiu
 * @Description: LovingMallException 统一异常处理
 * @Date：Created in 2019-10-29
 */
@Data
public class SchoolParkException extends RuntimeException {
    private Integer code;

    public SchoolParkException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public SchoolParkException(String message) {
        super(message);
        this.code = ExceptionCodeEnum.PARAM_ERROR.getCode();
    }
    public SchoolParkException(ExceptionCodeEnum exceptionCodeEnum) {
        super(exceptionCodeEnum.getMsg());
        this.code = exceptionCodeEnum.getCode();
    }
}
