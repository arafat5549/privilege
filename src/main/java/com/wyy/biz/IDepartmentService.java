package com.wyy.biz;


import com.wyy.model.Department;
import com.wyy.model.User;

import java.util.List;


/**
 * 部门表相关操作
 */
public interface IDepartmentService {

	/**
	 * 查询所有Department对象
	 * @return
	 */
	List<Department> getDepartmentList();
	
	/**
	 * 根据id获取Department对象
	 * @param id
	 * @return
	 */
	Department getDepartmentById(Integer id);
	
	/**
	 * 新增记录
	 * @param department
	 * @param user
	 * @return
	 */
	Integer createDepartment(Department department, User user);
	
	/**
	 * 根据id修改一条记录
	 * @param department
	 * @param user
	 * @return
	 */
	Integer updateDepartmentById(Department department, User user);
	
	/**
	 * 根据id删除一条记录
	 * @param id
	 * @return
	 */
	Integer deleteDepartmentById(Integer id);
	
	/**
	 * 部门是否被用户使用
	 * @param departmentId
	 */
	Boolean isUsedByUser(Integer departmentId);
	
	/**
	 * 获取部门树
	 * @return
	 */
	String getDepartmentTree();
	
	/**
	 * 获取Select控件中Options需要的List
	 * @return
	 */
	List<Department> getDepartmentListForOption();
}
