package com.chiiiplow.clouddrive.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chiiiplow.clouddrive.util.BeanCopyUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 页 DTO
 *
 * @author yangzhixiong
 * @date 2025/02/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private Long total;

    private Long pages;

    private Long current;

//    private FileDTO currentFile;

    private List<T> list;


    public static <T, P> PageDTO<T> empty(Page<P> p) {
        return new PageDTO<>(p.getTotal(), p.getPages(), p.getCurrent(), Collections.emptyList());
    }

    public static <T, P> PageDTO<T> of(Page<P> p, Class<T> clazz) {
        List<P> records = p.getRecords();
        if (records == null || records.isEmpty()) {
            return empty(p);
        }
        List<T> list = BeanCopyUtils.copyBeanList(records, clazz);
        return new PageDTO<>(p.getTotal(), p.getPages(), p.getCurrent(), list);
    }
}
