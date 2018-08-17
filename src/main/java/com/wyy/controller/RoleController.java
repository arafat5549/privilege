package com.wyy.controller;

import com.wyy.api.IPrivilegeBaseApiService;
import com.wyy.biz.IRoleService;
import com.wyy.model.Role;
import com.wyy.model.RoleSearchModel;
import com.wyy.model.User;
import com.wyy.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;



/**
 *角色管理相关的控制器 
 */

@Controller
@RequestMapping("/controller/role")
public class RoleController {

	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/list")
    public String main(String visitedModule,String visitedResource,HttpServletRequest request,ModelMap map) {

		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		String menus = privilegeBaseApiService.getModuleTree(user.getId(),visitedModule,visitedResource);
        map.put("user", user);
        map.put("menus", menus);
        
		return "role/list.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/getRoleDataTables")
    public String getRoleDataTables(RoleSearchModel searchModel,ModelMap map) {
		return roleService.getRoleDataTables(searchModel);
	}
	
	@ResponseBody
	@RequestMapping("/getRoleDataRow")
	public String  getRoleDataRow(@RequestParam("id") Integer id) throws Exception{		
		return roleService.getRoleDataRow(id);
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String  get(@RequestParam("id") Integer id) throws Exception{		
		Role role = roleService.getRoleById(id);		
		return JsonUtil.convertObj2json(role).toString();	
	}
	
	@RequestMapping("/addform")
    public String addform() {		
		return "role/addform.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/add")
    public String add(@ModelAttribute("role")Role role,HttpServletRequest request) {
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);
		//生成节点角色
		if(role.getRemark() == null)
		{
			role.setRemark("");
		}
		roleService.createRole(role,user);
		return ConstantUtil.Success;
    }
	
	@RequestMapping("/updateform")
    public String updateform(Integer id,HttpServletRequest request,ModelMap map) {
		Role role = roleService.getRoleById(id);
		map.put("role", role);
		return "role/updateform.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/update")
    public String update(@ModelAttribute("role")  Role role,HttpServletRequest request) {
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);
		//生成节点角色
		roleService.updateRoleById(role,user);
		
		return ConstantUtil.Success;
    }
	
	@ResponseBody
	@RequestMapping("/delete")
	public String  delete(@RequestParam("id") Integer id) {		
	
		//判断节点是否被用户关联
		if(roleService.isUsedByUser(id))
		{
			return ConstantUtil.Fail;
		}
		
		roleService.deleteRoleById(id);
		
		return ConstantUtil.Success;
	}
	
	@RequestMapping("/assignform")
	public String assignform(String id,ModelMap map)
	{
		map.put("id", id);
		return "role/assignform.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/assign")
	public String assign(Integer id,String checkedStr)
	{
		if(id == null || StringUtil.isStrEmpty(id.toString()) || StringUtil.isStrEmpty(checkedStr))
		{
			return ConstantUtil.Fail;
		}
		roleService.assignModuleAndResource(id, checkedStr);
		
		return ConstantUtil.Success;
	}
}
