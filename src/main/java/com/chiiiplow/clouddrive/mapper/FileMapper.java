package com.chiiiplow.clouddrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chiiiplow.clouddrive.entity.File;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文件映射器
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {
}
