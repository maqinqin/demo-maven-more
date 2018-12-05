package com.git.cloud.sys.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.sys.model.po.MenuPo;
import com.git.cloud.sys.service.MenuService;

/**
 * 登录action
 * @author wangdy
 *
 */
@SuppressWarnings("unchecked")
public class LoginAction extends BaseAction{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;


	public LoginAction(){
		super();
	}

	private MenuService menuService;
	
	/**
	 * 显示系统菜单
	 * @Title: showMenu
	 * @Description: TODO
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String showMenu(){
		try {
			List<MenuPo>  menuList = menuService.selectMenu();
		    this.getRequest().setAttribute("menuList", menuList);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}finally{
		}
		return "menu";
	}
	/**
	 * 显示系统菜单 by parentId
	 * @Title: showRootMenus
	 * @Description: TODO
	 * @field: 
	 * @return void
	 * @throws
	 */
	public void showMenusByParentId(){
		try {
			String parentId = this.getRequest().getParameter("parentId");
			List<MenuPo>  menuList = menuService.selectMenusByParentId(parentId);
			this.arrayOut(menuList);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}finally{
		}
	}
	
	/**
	 * 获取系统导航菜单（取得带层级的菜单）-wangdy
	 */
	public void showSystemMenu() throws Exception {
		this.arrayOut(this.menuService.selectSystemMenu());
	}
	
    public void setSession(){
    	String firstMenuId = this.getRequest().getParameter("firstMenuId");
    	this.getRequest().getSession().setAttribute("firstMenuId", firstMenuId);
    	String secondMenuId = this.getRequest().getParameter("secondMenuId");
    	this.getRequest().getSession().setAttribute("secondMenuId", secondMenuId);
    }
	public String login(){
		
		HttpServletRequest request = null;
		
		String result = "login";
		
		request = ServletActionContext.getRequest();
		// 此处默认有值
				String username = request.getParameter("username");
		//MD5加密
		String password = com.git.cloud.sys.tools.CipherUtil.generatePassword(request.getParameter("password"));
		//String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();
		try {
			if (!currentUser.isAuthenticated()){
//				token.setRememberMe(true);
				currentUser.login(token);
			}
			
			result = "index";
		} catch (Exception e) {
//			logger.error(e.getMessage());
			logger.error("异常exception",e);
			result = "login";
		}
		return result;
		
	}
	
    /**
     * 登出
     * @return
     */
    public String logout() {  
  
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "logout";  
        currentUser.logout();  
        return result;  
    } 


	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
}
