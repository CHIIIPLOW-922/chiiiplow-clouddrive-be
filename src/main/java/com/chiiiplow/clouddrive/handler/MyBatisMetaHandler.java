package com.chiiiplow.clouddrive.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis 元处理器
 *
 * @author yangzhixiong
 * @date 2024/12/06
 */
@Component
public class MyBatisMetaHandler implements MetaObjectHandler {

    private static final String CREATED_TIME = "createdTime";
    private static final String REGISTER_TIME = "registerTime";
    private static final String MODIFY_TIME = "modifyTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATED_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, REGISTER_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, MODIFY_TIME, LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, MODIFY_TIME, LocalDateTime.class, LocalDateTime.now());
    }
}
