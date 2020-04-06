package cn.lovingliu.controller.superadmin;

import cn.lovingliu.constant.UserRole;
import cn.lovingliu.constant.UserStatus;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.exception.SchoolParkException;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.UserBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.FileService;
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
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(value = "超级管理员用户",tags = "超级管理员用户")
@RestController
@RequestMapping("superAdmin")
@Slf4j
public class SuperAdminController implements BaseController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据管理员获取违停信息列表",notes = "根据管理员获取违停信息列表",httpMethod = "GET")
    @GetMapping("/record/listByCreaterId")
    public ServerResponse listByCreaterId(@ApiParam(name = "recordStatus",value = "违停记录状态",required = false)
                               @RequestParam(value = "recordStatus",required = false)Integer recordStatus,

                               @ApiParam(name = "page",value = "当前页码",required = false)
                               @RequestParam(value = "page",defaultValue = "1") Integer page,

                               @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,

                               @ApiParam(name = "createrId",value = "管理员Id",required = false)
                               @RequestParam(value = "createrId",required = false) Long createrId){
        PagedGridResult pagedGridResult = recordService.getRecordVOListByCreaterId(createrId,recordStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "根据普通用户获取违停信息列表",notes = "根据管理员获取违停信息列表",httpMethod = "GET")
    @GetMapping("/record/listByUserId")
    public ServerResponse listByUserId(@ApiParam(name = "recordStatus",value = "违停记录状态",required = false)
                               @RequestParam(value = "recordStatus",required = false)Integer recordStatus,

                               @ApiParam(name = "page",value = "当前页码",required = false)
                               @RequestParam(value = "page",defaultValue = "1") Integer page,

                               @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,

                               @ApiParam(name = "userId",value = "普通用户ID",required = false)
                               @RequestParam(value = "userId",required = false) Long userId){
        PagedGridResult pagedGridResult = recordService.getRecordVOListByUserId(userId,recordStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "获取所有违停信息列表",notes = "获取所有违停信息列表",httpMethod = "GET")
    @GetMapping("/record/list")
    public ServerResponse recordList(@ApiParam(name = "recordStatus",value = "违停记录状态",required = false)
                                       @RequestParam(value = "recordStatus",required = false)Integer recordStatus,

                                       @ApiParam(name = "page",value = "当前页码",required = false)
                                       @RequestParam(value = "page",defaultValue = "1") Integer page,

                                       @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        PagedGridResult pagedGridResult = recordService.getRecordVOList(recordStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "创建管理员(已测)",notes = "创建管理员",httpMethod = "POST")
    @PostMapping("/admin/create")
    public ServerResponse createAdmin(@ApiParam(name = "userBO",value = "管理员信息",required = true)
                                 @RequestBody @Valid UserBO userBO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SchoolParkException(bindingResult.getFieldError().getDefaultMessage());
        }
        User userInDb = userService.getUserByPhone(userBO.getPhone());
        if(userInDb != null){
            return ServerResponse.createByErrorMessage("号码已被人使用,无法创建!");
        }
        userBO.setPassword(MD5Util.MD5EncodeUtf8(userBO.getPassword()));
        userBO.setRole(UserRole.ADMIN);
        userBO.setCreatedTime(new Date());
        userBO.setUpdatedTime(new Date());
        int count = userService.createUser(userBO);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("创建成功");
        }else {
            return ServerResponse.createByErrorMessage("创建异常");
        }
    }

    @ApiOperation(value = "获得管理员列表(已测)",notes = "获得管理员列表",httpMethod = "POST")
    @PostMapping("/admin/list")
    public ServerResponse adminList(){
        List<User> userList = userService.getUserListByRole(UserRole.ADMIN);
        userList.forEach(e -> {
            e.setPassword(null);
            e.setAnswer(null);
        });
        return ServerResponse.createBySuccess(userList);
    }

    @ApiOperation(value = "禁/启用管理员(已测)",notes = "禁/启用管理员",httpMethod = "POST")
    @PostMapping("/admin/able")
    public ServerResponse able(@ApiParam(name = "adminId",value = "管理员ID",required = true)
                                 @RequestParam(value = "adminId",required = true)
                                         Long adminId){
        User user = userService.getUserById(adminId);
        if(user.getRole() != UserRole.ADMIN){
            return ServerResponse.createByErrorMessage("该用户角色不是管理员");
        }
        user.setStstus(user.getStstus() == UserStatus.ENABLE ? UserStatus.DISABLE : UserStatus.ENABLE);
        int count = userService.updateUser(user);
        if(count > 0){
            if(user.getStstus() == UserStatus.DISABLE){
                return ServerResponse.createBySuccessMessage("禁用成功");
            }else {
                return ServerResponse.createBySuccessMessage("启用成功");
            }

        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @ApiOperation(value = "删除管理员(已测)",notes = "删除管理员",httpMethod = "POST")
    @PostMapping("/admin/delete")
    public ServerResponse adminDelete(@ApiParam(name = "adminId",value = "管理员ID",required = true)
                                 @RequestParam(value = "adminId",required = true)
                                 Long adminId){
        PagedGridResult pagedGridResult = recordService.getRecordVOListByCreaterId(adminId,null,0,10);
        if(pagedGridResult.getTotal() > 0){
            return ServerResponse.createByErrorMessage("该管理员已有关联违章记录,无法删除");
        }

        int count = userService.deleteUser(adminId);
        if(count > 0){
            // 删除图片
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("创建失败");
    }

    @ApiOperation(value = "获得普通用户列表",notes = "获得普通用户列表",httpMethod = "POST")
    @PostMapping("/user/list")
    public ServerResponse userList(){
        List<User> userList = userService.getUserListByRole(null);
        userList.forEach(e -> {
            e.setPassword(null);
            e.setAnswer(null);
        });
        return ServerResponse.createBySuccess(userList);
    }

    @ApiOperation(value = "获取超级管理员信息(已测)",notes = "获取超级管理员信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改超级管理员信息(已测)",notes = "修改超级管理员信息",httpMethod = "POST")
    @PostMapping("/info/update")
    public ServerResponse update(@ApiParam(name = "user",value = "要修改的管理员信息",required = true)
                                 @RequestBody User user,HttpServletRequest request){

        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        if (user.getPortrait() != null && selectUser.getPortrait() != null && !user.getPortrait().equals(selectUser.getPortrait())){
            boolean isDelete = fileService.deleteImage(selectUser.getPortrait());
            if(!isDelete){
                log.error("图片删除失败");
            }
        }
        user.setUpdatedTime(new Date());
        user.setId(userInCookie.getId());
        if(StringUtils.isNotBlank(user.getPassword())){
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        }
        int count = userService.updateUser(user);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("更新成功");
        }else {
            return ServerResponse.createByErrorMessage("更新失败");
        }
    }

}
