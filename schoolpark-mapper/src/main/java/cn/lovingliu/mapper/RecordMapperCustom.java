package cn.lovingliu.mapper;

import cn.lovingliu.pojo.vo.RecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author：LovingLiu
 * @Description: 自定义违停信息
 * @Date：Created in 2020-02-09
 */
public interface RecordMapperCustom {
    RecordVO selectByPrimaryKey(Integer recordId);
    List<RecordVO> selectListByParamsMap(@Param("paramsMap") Map<String,Object> paramsMap);
    Integer updateRecordStatusByPrimaryKey(@Param("paramsMap") Map<String,Object> paramsMap);
}
