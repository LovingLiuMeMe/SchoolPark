package cn.lovingliu.controller.user;

import cn.lovingliu.constant.RecordStatus;
import cn.lovingliu.constant.UserRole;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.vo.RecordVO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.FileService;
import cn.lovingliu.service.RecordService;
import cn.lovingliu.service.UserService;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "普通用户",tags = "普通用户接口")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController implements BaseController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "获取违停详细信息",notes = "获取违停详细信息",httpMethod = "GET")
    @GetMapping("/record/{recordId}")
    public ServerResponse record(@PathVariable Integer recordId){
        if (recordId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        RecordVO recordVO = recordService.getRecordVOByRecordId(recordId);
        return ServerResponse.createBySuccess(recordVO);
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

    @ApiOperation(value = "缴纳罚款",notes = "缴纳罚款",httpMethod = "POST")
    @GetMapping("/record/pay")
    public ServerResponse pay(@ApiParam(name = "recordId",value = "记录ID",required = true)
                               @RequestParam(value = "recordId",required = true)Integer recordId,HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        int count = 0;
        if(userInCookie.getRole() == UserRole.USER_IN){
            count = recordService.changeRecordStatus(recordId, RecordStatus.WAIT_PASS);
        }else{
            count = recordService.changeRecordStatus(recordId, RecordStatus.WAIT_COMMENT);
        }

        if (count > 0){
            if(userInCookie.getRole() == UserRole.USER_IN){
                return ServerResponse.createBySuccess("处理成功,因您是校内车主,不进行罚款,只对你进行口头警告!");
            }else {
                return ServerResponse.createBySuccess("处理成功,等待管理员审核");
            }

        }
        return ServerResponse.createByErrorMessage("处理失败,详情请咨询管理员");
    }

    @ApiOperation(value = "评论",notes = "评论",httpMethod = "GET")
    @GetMapping("/record/comment")
    public ServerResponse comment(@ApiParam(name = "recordId",value = "记录ID",required = true)
                                  @RequestParam(value = "recordId",required = true)
                                  Integer recordId,
                                  @ApiParam(name = "remarkType",value = "评论等级",required = true)
                                  @RequestParam(value = "remarkType",required = true)
                                  Integer remarkType,
                                  @ApiParam(name = "remarkType",value = "评论详情",required = true)
                                  @RequestParam(value = "remarkType",required = true)
                                  String remark
                               ){
        int count = recordService.commentRecord(recordId,remarkType,remark);
        if (count > 0){
            return ServerResponse.createBySuccess("评论成功");
        }
        return ServerResponse.createByErrorMessage("评论失败");
    }

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/info/update")
    public ServerResponse update(@ApiParam(name = "user",value = "要修改的管理员信息",required = true)
                                 @RequestParam(value = "user",required = true)
                                  User user,HttpServletRequest request){

        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        if (user.getPortrait() != null && StringUtils.isNotBlank(selectUser.getPortrait()) && !user.getPortrait().equals(selectUser.getPortrait())){
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
}
