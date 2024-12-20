package com.chiiiplow.clouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddrive.entity.File;


/**
 * 文件业务接口
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public interface IFileService extends IService<File> {

    String test();
}
