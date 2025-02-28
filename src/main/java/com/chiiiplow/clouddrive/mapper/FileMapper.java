package com.chiiiplow.clouddrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chiiiplow.clouddrive.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 文件映射器
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {


    /**
     * 使用 Tree 查询文件 ID
     *
     * @param parentId 上级ID
     * @return {@link List}<{@link File}>
     */
    List<File> queryFileIdWithTree(@Param("parentId") String parentId);
}
