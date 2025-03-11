package com.xzc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.model.entity.SystemPost;
import com.xzc.lease.model.entity.SystemUser;
import com.xzc.lease.web.admin.mapper.SystemPostMapper;
import com.xzc.lease.web.admin.mapper.SystemUserMapper;
import com.xzc.lease.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzc.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.xzc.lease.web.admin.vo.system.user.SystemUserQueryVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private SystemUserMapper mapper;
    @Autowired
    private SystemPostMapper systemPostMapper;

    @Override
    public IPage<SystemUserItemVo> pageSUSer(Page<SystemUserItemVo> systemUserItemVoPage, SystemUserQueryVo queryVo) {
        return mapper.pageSUSer(systemUserItemVoPage,queryVo);
    }

    @Override
    public SystemUserItemVo getSystemUserItemVoById(Long id) {
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        SystemUser systemUser = mapper.selectById(id);
        BeanUtils.copyProperties(systemUser,systemUserItemVo);

        SystemPost systemPost = systemPostMapper.selectById(systemUser.getPostId());
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }


}




