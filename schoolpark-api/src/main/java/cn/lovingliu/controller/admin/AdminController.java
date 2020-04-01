package cn.lovingliu.controller.admin;

import cn.lovingliu.constant.RecordStatus;
import cn.lovingliu.constant.UserRole;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.exception.SchoolParkException;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.RecordBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.FileService;
import cn.lovingliu.service.PictureService;
import cn.lovingliu.service.RecordService;
import cn.lovingliu.service.UserService;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(value = "管理员用户",tags = "管理员用户接口")
@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController implements BaseController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "创建违停信息",notes = "创建违停信息",httpMethod = "POST")
    @PostMapping("/record/create")
    public ServerResponse create(@ApiParam(name = "recordBO",value = "违停详细信息",required = true)
                                 @RequestBody @Valid RecordBO recordBO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SchoolParkException(bindingResult.getFieldError().getDefaultMessage());
        }
        Integer userId = recordBO.getUserId();
        User user = userService.getUserById(Long.valueOf(userId.toString())); // int => long
        if(user.getInBlacklist() == 1){
            return ServerResponse.createByErrorMessage("该用户已经再黑名单中,共有"+user.getInfractionsCount()+"次违停记录,请立即使用拖车拖走！");
        }
        if(user.getRole() == UserRole.USER_IN){
            recordBO.setAmt(0);
        }
        Integer recordId = recordService.createRecord(recordBO);

        log.info("recordId",recordId);
        pictureService.createPictures(recordId,recordBO.getPicturesName());

        userService.noticeUser(Long.valueOf(userId.toString())); // int => long

        if(user.getRole() == UserRole.USER_IN){
            return ServerResponse.createBySuccessMessage("创建成功违停记录成功,违停人是校内人员,不进行扣款!");
        }else {
            return ServerResponse.createBySuccessMessage("创建成功");
        }
    }

    @ApiOperation(value = "获取违停信息列表",notes = "获取违停信息列表",httpMethod = "GET")
    @GetMapping("/record/list")
    public ServerResponse list(@ApiParam(name = "recordStatus",value = "违停记录状态",required = false)
                               @RequestParam(value = "recordStatus",required = false)Integer recordStatus,
                               @ApiParam(name = "page",value = "当前页码",required = false)
                               @RequestParam(value = "page",defaultValue = "1") Integer page,

                               @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                               HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        PagedGridResult pagedGridResult = recordService.getRecordVOListByUserId(userInCookie.getId(),recordStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "审核违停信息",notes = "审核违停信息",httpMethod = "POST")
    @GetMapping("/record/pass")
    public ServerResponse list(@ApiParam(name = "recordId",value = "记录ID",required = true)
                               @RequestParam(value = "recordId",required = true)Integer recordId){
        int count = recordService.changeRecordStatus(recordId, RecordStatus.WAIT_COMMENT);
        if (count > 0){
            return ServerResponse.createBySuccess("审核成功");
        }
        return ServerResponse.createByErrorMessage("审核异常,详情请咨询超级管理员");
    }

    @ApiOperation(value = "获取管理员信息(已测)",notes = "获取管理员信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改管理员信息(已测)",notes = "修改管理员信息",httpMethod = "POST")
    @PostMapping("/info/update")
    public ServerResponse update(@ApiParam(name = "user",value = "要修改的管理员信息",required = true)
                                 @RequestParam(value = "user",required = true) User user,
                                 HttpServletRequest request){

        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        if (user.getPortrait() != null && selectUser.getPortrait() != null && !user.getPortrait().equals(selectUser.getPortrait())){
            boolean isDelete = fileService.deleteImage(selectUser.getPortrait());
            if(!isDelete){
                log.error("图片删除失败");
            }
        }
        user.setId(userInCookie.getId());
        int count = userService.updateUser(user);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("更新成功");
        }else {
            return ServerResponse.createByErrorMessage("更新失败");
        }
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
}
