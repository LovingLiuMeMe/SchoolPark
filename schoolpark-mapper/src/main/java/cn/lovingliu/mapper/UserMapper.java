package cn.lovingliu.mapper;

import cn.lovingliu.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table school_park_user
     *
     * @mbg.generated Sun Mar 15 17:38:42 CST 2020
     */
    int updateByPrimaryKey(User record);

    List<User> selectByRole(Integer role);

    User selectByPhone(String phone);
}