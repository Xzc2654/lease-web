package com.xzc.lease.web.app.mapper;

import com.xzc.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.xzc.lease.model.entity.LeaseTerm
*/
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    List<LeaseTerm> selectByRoomId(long id);

    List<LeaseTerm> selectListByRoomId(Long id);
}




