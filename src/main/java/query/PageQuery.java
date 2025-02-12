package query;

import lombok.Data;

/**
 * 页面查询
 *
 * @author yangzhixiong
 * @date 2025/02/11
 */
@Data
public class PageQuery {
    private Long pageNo = 1L;

    private Long pageSize = 10L;

}
