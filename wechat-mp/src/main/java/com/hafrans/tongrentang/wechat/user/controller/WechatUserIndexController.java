package com.hafrans.tongrentang.wechat.user.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.status.PlainStatus;
import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.common.vo.ResponseData;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;
import com.hafrans.tongrentang.wechat.user.domain.vo.BasicUserInfo;
import com.hafrans.tongrentang.wechat.user.domain.vo.BasicUserInfoWithToken;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.domain.vo.Token;
import com.hafrans.tongrentang.wechat.user.domain.vo.WechatMPRegister;
import com.hafrans.tongrentang.wechat.user.exception.UserNotFoundException;
import com.hafrans.tongrentang.wechat.user.service.UserService;
import com.hafrans.tongrentang.wechat.user.service.WechatUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/wx/v1/user")
@RestController
@Api(tags = "微信小程序登录相关", value = "WechatUserIndexController")
@Slf4j
public class WechatUserIndexController {

	@Autowired
	WechatUserService wechatUserService;

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@GetMapping("/")
	@ApiOperation(value = "小程序登录系统ping", notes = "系统运行时可以通过该接口测试系统是否能够正常使用", produces = "json")
	public ResponseEntity<Map<String, String>> index() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ping", "pong");
		map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	@ApiOperation(httpMethod = "POST", produces = "application/json", value = "小程序登录", notes = "小程序尝试登录并返回令牌")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "js_code", value = "wx.login生产的jsCode串", dataTypeClass = String.class, allowEmptyValue = false, required = true, example = "xasxsadasdsafregtfdas") })
	@ApiResponses({ @ApiResponse(code = 200, message = "响应消息") })
	public ResponseData<Token> login(@RequestParam(name = "js_code", required = true) String jsCode)
			throws UserNotFoundException, StatusException {
		Code2SessionResponse resp = wechatUserService.login(jsCode);
		if (resp.getOpenId() == null || resp.getErrcode() != 0) { // fetch openId from wechat server.
			return ResponseData.Builder(PlainStatus.STATUS_LOGIN_FAILED_INVALID_OPENID, "js_code is invalid", null);
		} // if

		String token = userService.loginByCode2Session(resp);
		return ResponseData.Builder(PlainStatus.STATUS_LOGIN_SUCCESS, "login:success", new Token(token));

	}

	@PostMapping("/register") 
	@ApiOperation(value="用户注册",notes="新申请一个login的jscode 以及将userinfo的加密内容上传到服务器，完成注册后返回token与userinfo")
	@ApiImplicitParams({
		@ApiImplicitParam(name="regform",dataTypeClass=WechatMPRegister.class,required=true,value="userinfo与login jscode串的json对象",allowEmptyValue=false)
	})
	@ApiResponse(code=200,message="userinfo与token串")
	public ResponseData<BasicUserInfoWithToken> login(@RequestBody @Valid WechatMPRegister regform) throws StatusException, UserNotFoundException {
		String jsCode = regform.getCode();
		Code2SessionResponse resp = wechatUserService.login(jsCode);
		if (resp.getOpenId() == null || resp.getErrcode() != 0) { // fetch openId from wechat server.
			return ResponseData.Builder(PlainStatus.STATUS_LOGIN_FAILED_INVALID_OPENID, "js_code is invalid", null);
		} // if
		BasicUserInfoWithToken buiwt = userService.registerViaWechatMP(resp, regform);
		return ResponseData.Builder(PlainStatus.STATUS_REGISTER_SUCCESS, "user register success", buiwt);
	}

	@GetMapping("/refresh")
	@ApiOperation(value = "更新令牌", notes = "直接带token访问本接口刷新现有令牌")
	@ApiResponses({ @ApiResponse(code = 200, message = "响应消息") })
	public ResponseData<Token> refresh() throws StatusException, UserNotFoundException {

		SystemUserPrincipal principal = (SystemUserPrincipal) SecurityUtils.getSubject().getPrincipal();
		if (principal == null) {
			log.error("principal is null in refresh!");
			throw new StatusException(PlainStatus.STATUS_FAILED, "system maintainence");
		}

		String token = userService.refreshTokenByPrincipal(principal);
		return ResponseData.Builder(PlainStatus.STATUS_JWT_SUCCESS, "jwt update success", new Token(token));
	}
	
	

	@ApiOperation(value="获取用户基本信息", notes="只含有基本信息")
	@ApiResponse(code=200,message="基本信息")
	@GetMapping("/userinfo")
	public ResponseData<BasicUserInfo> userinfo() throws StatusException{
		SystemUserPrincipal pp = (SystemUserPrincipal) SecurityUtils.getSubject().getPrincipal();
		if (pp == null) {
			log.error("principal is null in userinfo!");
			throw new StatusException(PlainStatus.STATUS_FAILED, "system maintainence");
		}
		UserProfile profile = pp.getUser().getProfile();
	
		BasicUserInfo userinfo = BasicUserInfo.populate(profile);
		
		return ResponseData.Builder(PlainStatus.STATUS_OK, "userinfo:ok", Timestamp.from(Instant.now()),userinfo);
	}
	
	

}
