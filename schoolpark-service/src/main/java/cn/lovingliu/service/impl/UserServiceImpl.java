package cn.lovingliu.service.impl;

import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.UserBO;
import cn.lovingliu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByPhone(String phone){
        User user = userMapper.selectByPhone(phone);
        return user;
    }

    @Override
    public User getUserById(Long userId){
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public Integer createUser(UserBO userBO){
        User user = new User();
        BeanUtils.copyProperties(userBO,user);
        int count = userMapper.insertSelective(user);
        return count;
    }

    @Override
    public List<User> getUserListByRole(Integer role){
        return userMapper.selectByRole(role);
    }

    @Override
    public Integer deleteUser(Long userId){
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Integer updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void noticeUser(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        Integer oldInfractionsCount = user.getInfractionsCount();
        if(oldInfractionsCount < 1){
            log.info("【短信通知: 用户:{},你已经违章停车1次,请注意核销.】",user.getUsername());
        }else if(oldInfractionsCount >= 1 && oldInfractionsCount < 2){
            log.info("【严重警告: 用户:{},你已经违章停车2次,请注意核销.】",user.getUsername());
        }else if(oldInfractionsCount >= 2){
            user.setInBlacklist(1);
            log.info("【取消资格: 用户:{},你已经违章停车:{} 次,现已进入黑名单,将无法再进入校园停车.】",user.getUsername(),oldInfractionsCount + 1);
        }
        user.setInfractionsCount(oldInfractionsCount + 1);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
