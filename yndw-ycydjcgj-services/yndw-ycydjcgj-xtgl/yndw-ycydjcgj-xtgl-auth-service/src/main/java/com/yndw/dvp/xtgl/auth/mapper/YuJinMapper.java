package com.yndw.dvp.xtgl.auth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.xtgl.auth.model.YuJin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface YuJinMapper extends SuperMapper<YuJin> {
    void updateByEntity(@Param(value = "yuJin") YuJin yuJin);
    YuJin getYuJinEntity();

}
