package com.wyy.biz.impl;

import com.wyy.mapper.ModuleMapper;
import com.wyy.biz.IModuleService;
import com.wyy.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements IModuleService {
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Override
	public List<Module> getModuleList()
	{
		return moduleMapper.getModuleList();
	}
	
	@Override
	public List<Module> getModuleListByFlag(String flag)
	{
		return moduleMapper.getModuleListByFlag(flag);
	}
	
	@Override
	public List<Module> getModuleListByRoleId(Integer roleId)
	{
		return moduleMapper.getModuleListByRoleId(roleId);
	}
	
	@Override
	public List<Module> getModuleListByUserId(Integer userId)
	{
		return moduleMapper.getModuleListByUserId(userId);
	}
}
