package com.xzc.lease.web.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.model.entity.SystemUser;
import com.xzc.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.xzc.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xzc.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    IPage<SystemUserItemVo> pageSUSer(Page<SystemUserItemVo> systemUserItemVoPage, SystemUserQueryVo queryVo);
}




