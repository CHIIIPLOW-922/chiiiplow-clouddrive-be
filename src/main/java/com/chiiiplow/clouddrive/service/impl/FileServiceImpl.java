package com.chiiiplow.clouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.mapper.FileMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件业务实现
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public String test() {
        System.out.println(fileMapper.exists(new LambdaQueryWrapper<File>().eq(File::getId, 123)));
        return null;
    }
}
