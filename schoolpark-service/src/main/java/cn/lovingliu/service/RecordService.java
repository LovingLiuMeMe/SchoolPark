package cn.lovingliu.service;

import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.bo.RecordBO;
import cn.lovingliu.pojo.vo.RecordVO;

public interface RecordService {
    RecordVO getRecordVOByRecordId(Integer recordId);
    PagedGridResult getRecordVOListByUserId(Long userId, Integer recordStatus, Integer page, Integer pageSize);
    PagedGridResult getRecordVOListByCreaterId(Long createrId, Integer recordStatus, Integer page, Integer pageSize);
    PagedGridResult getRecordVOList(Integer recordStatus, Integer page, Integer pageSize);
    Integer changeRecordStatus(Integer recordId,Integer willChangeRecordStatus);
    Integer createRecord(RecordBO recordBO);
    Integer commentRecord(Integer recordId,Integer remarkType,String remark);
}
