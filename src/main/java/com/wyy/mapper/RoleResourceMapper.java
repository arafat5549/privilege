package com.wyy.mapper;


import com.wyy.model.RoleResource;

public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);
}