package cn.lovingliu.service.impl;

import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.UserBO;
import cn.lovingliu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserByPhone(String phone){
        User user = userMapper.selectByPhone(phone);
        return user;
    }

    public User getUserById(Long userId){
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    public Integer createUser(UserBO userBO){
        User user = new User();
        BeanUtils.copyProperties(userBO,user);
        int count = userMapper.insertSelective(user);
        return count;
    }

    public List<User> getUserListByRole(Integer role){
        return userMapper.selectByRole(role);
    }

    public Integer deleteUser(Long userId){
        return userMapper.deleteByPrimaryKey(userId);
    }

    public Integer updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
