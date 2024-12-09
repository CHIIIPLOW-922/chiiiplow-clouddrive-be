package com.chiiiplow.clouddrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chiiiplow.clouddrive.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射器
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
