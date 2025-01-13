package com.chiiiplow.clouddrive.validation;

import javax.validation.GroupSequence;

/**
 * 校验顺序
 *
 * @author yangzhixiong
 * @date 2025/01/06
 */
@GroupSequence({Group.G1.class, Group.G2.class, Group.G3.class, Group.G4.class, Group.G5.class})
public interface Group {
    interface G1{}
    interface G2{}
    interface G3{}
    interface G4{}
    interface G5{}

}
