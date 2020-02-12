package cn.lovingliu.component;

import cn.lovingliu.constant.UserRole;
import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.pojo.User;
import cn.lovingliu.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author：LovingLiu
 * @Description: 系统启动时自动创建超级管理员用户
 * @Date：Created in 2020-02-07
 */
@Component
@Slf4j
public class InitSupperAdmin implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            log.debug("============== 初始化超级用户 ==============");
            List<User> userList = userMapper.selectByRole(UserRole.SUPPER_ADMIN);
            if(userList.size() < 1){
                User superAdmin = new User();
                superAdmin.setUsername("SuperAdmin");
                superAdmin.setPassword(MD5Util.MD5EncodeUtf8("123456"));
                superAdmin.setPhone("13000000000");
                superAdmin.setQuestion("超级管理员初始密码是多少?");
                superAdmin.setAnswer("123456");
                superAdmin.setRole(UserRole.SUPPER_ADMIN);
                superAdmin.setCreatedTime(new Date());
                superAdmin.setUpdatedTime(new Date());
                userMapper.insertSelective(superAdmin);
            }
        }).start();
    }
}
