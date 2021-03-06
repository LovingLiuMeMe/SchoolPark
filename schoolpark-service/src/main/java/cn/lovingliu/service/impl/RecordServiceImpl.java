package cn.lovingliu.service.impl;

import cn.lovingliu.constant.RecordStatus;
import cn.lovingliu.mapper.RecordMapper;
import cn.lovingliu.mapper.RecordMapperCustom;
import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Record;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.vo.RecordVO;
import cn.lovingliu.service.BaseService;
import cn.lovingliu.service.RecordService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl extends BaseService implements RecordService {
    @Autowired
    private RecordMapperCustom recordMapperCustom;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;

    public RecordVO getRecordVOByRecordId(Integer recordId){
        RecordVO recordVO = recordMapperCustom.selectByPrimaryKey(recordId);
        return recordVO;
    }

    public PagedGridResult getRecordVOListByUserId(Long userId, Integer recordStatus, Integer page, Integer pageSize){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId);
        if(recordStatus != null){
            paramsMap.put("recordStatus",recordStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<RecordVO> recordVOList = recordMapperCustom.selectListByParamsMap(paramsMap);
        return setterPagedGrid(recordVOList,page);
    }

    public PagedGridResult getRecordVOListByCreaterId(Long createrId, Integer recordStatus, Integer page, Integer pageSize){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("createrId", createrId);
        if(recordStatus != null){
            paramsMap.put("recordStatus",recordStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<RecordVO> recordVOList = recordMapperCustom.selectListByParamsMap(paramsMap);
        return setterPagedGrid(recordVOList,page);
    }

    public PagedGridResult getRecordVOList(Integer recordStatus, Integer page, Integer pageSize){
        Map<String,Object> paramsMap = new HashMap<>();
        if(recordStatus != null){
            paramsMap.put("recordStatus",recordStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<RecordVO> recordVOList = recordMapperCustom.selectListByParamsMap(paramsMap);
        return setterPagedGrid(recordVOList,page);
    }

    public Integer changeRecordStatus(Integer recordId,Integer willChangeRecordStatus){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("recordId",recordId);
        paramsMap.put("willChangeRecordStatus",willChangeRecordStatus);
        int count = recordMapperCustom.updateRecordStatusByPrimaryKey(paramsMap);
        if(count > 0){
            // 减少次数
            Record record = recordMapper.selectByPrimaryKey(recordId);
            User user = userMapper.selectByPrimaryKey(Long.valueOf(record.getUserId().toString()));
            Integer infractionsCount = user.getInfractionsCount();
            Integer inBlacklist = user.getInBlacklist();
            if(infractionsCount == 3 && inBlacklist == 1){
                user.setInBlacklist(0);
            }
            user.setInfractionsCount(infractionsCount - 1);
            userMapper.updateByPrimaryKeySelective(user);
        }
        return count;
    }

    public Integer createRecord(Record record){
        Integer count = recordMapper.insertSelective(record);
        return count;
    }

    public Integer commentRecord(Integer recordId,Integer remarkType,String remark){
        Record record = recordMapper.selectByPrimaryKey(recordId);
        record.setRecordStatus(RecordStatus.FINISH_COMMENT);
        record.setRemarkType(remarkType);
        record.setRemark(remark);
        record.setUpdatedTime(new Date());
        return recordMapper.updateByPrimaryKeySelective(record);
    }
}
