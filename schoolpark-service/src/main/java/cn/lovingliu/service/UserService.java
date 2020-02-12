package cn.lovingliu.service;

import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.UserBO;

import java.util.List;

public interface UserService {
    User getUserByPhone(String phone);
    User getUserById(Long userId);
    Integer createUser(UserBO userBO);
    List<User> getUserListByRole(Integer role);
    Integer deleteUser(Long userId);
    Integer updateUser(User user);
}
