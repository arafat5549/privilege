package com.wyy.mapper;


import com.wyy.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}