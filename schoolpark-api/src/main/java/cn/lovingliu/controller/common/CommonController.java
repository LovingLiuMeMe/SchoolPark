package cn.lovingliu.controller.common;

import cn.lovingliu.component.RandomValidateCode;
import cn.lovingliu.constant.UserStatus;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.exception.SchoolParkException;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.UserBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.RecordService;
import cn.lovingliu.service.UserService;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import cn.lovingliu.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@Api(value = "公共接口",tags = "公共接口")
@RestController
@RequestMapping("/")
@Slf4j
public class CommonController implements BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RandomValidateCode randomValidateCode;

    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("login")
    public ServerResponse login(@RequestParam(value = "verifyCode",required = true) String verifyCode,
                                @RequestParam(value = "phone",required = true) String phone,
                                @RequestParam(value = "password",required = true) String password,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        if(StringUtils.isBlank(verifyCode)){
            return ServerResponse.createByErrorMessage("验证码不能为空");
        }
//        HttpSession session = request.getSession();
//        String verifyCodeInSession = (String) session.getAttribute(SessionNames.VERIFY_CODE_KEY);
//        if (verifyCodeInSession == null || !verifyCode.equalsIgnoreCase(verifyCodeInSession)){
//            return ServerResponse.createByErrorMessage("验证码错误");
//        }
        if(StringUtils.isBlank(phone)){
            return ServerResponse.createByErrorMessage("用户名为空");
        }
        if(StringUtils.isBlank(password)){
            return ServerResponse.createByErrorMessage("密码为空");
        }
        User selectUser = userService.getUserByPhone(phone);
        if(selectUser == null){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        if(!MD5Util.MD5EncodeUtf8(password).equals(selectUser.getPassword())){
            return ServerResponse.createByErrorMessage("用户密码错误");
        }
        if(selectUser.getStstus() == UserStatus.DISABLE){
            return ServerResponse.createByErrorMessage("用户被禁用无法登录");
        }
        selectUser = setNullProperty(selectUser);
        CookieUtil.set(response,USER_COOKIE_KEY, JsonUtils.objectToJson(selectUser),MAX_AGE);
        return ServerResponse.createBySuccessMessage("登录成功");
    }

    @ApiOperation(value = "用户退出登录",notes = "用户退出登录",httpMethod = "POST")
    @PostMapping("logout")
    public ServerResponse logout(HttpServletResponse response) {
        CookieUtil.set(response,USER_COOKIE_KEY,null,0);
        return ServerResponse.createBySuccessMessage("退出登录");
    }

    @ApiOperation(value = "获取图片验证码",notes = "获取图片验证码",httpMethod = "GET")
    @GetMapping(value = "/getVerifyCode")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            //输出验证码图片方法
            randomValidateCode.getRandCode(request, response);
        } catch (Exception e) {
            log.error("========= 获取验证码失败 =========", e);
        }
    }
    @ApiOperation(value = "普通用户注册",notes = "普通用户注册",httpMethod = "POST")
    @PostMapping(value = "/register")
    public ServerResponse register(@ApiParam(name = "userBO",value = "管理员信息",required = true)
                             @RequestBody @Valid UserBO userBO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new SchoolParkException(bindingResult.getFieldError().getDefaultMessage());
        }
        User userInDb = userService.getUserByPhone(userBO.getPhone());
        if(userInDb != null){
            return ServerResponse.createByErrorMessage("号码已被人注册,无法注册!");
        }
        userBO.setPassword(MD5Util.MD5EncodeUtf8(userBO.getPassword()));
        userBO.setCreatedTime(new Date());
        userBO.setUpdatedTime(new Date());
        int count = userService.createUser(userBO);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("创建成功");
        }else {
            return ServerResponse.createByErrorMessage("创建异常");
        }
    }
    private User setNullProperty(User userResult){
        userResult.setPassword(null);
        userResult.setAnswer(null);
        userResult.setPhone(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        return userResult;
    }
}
