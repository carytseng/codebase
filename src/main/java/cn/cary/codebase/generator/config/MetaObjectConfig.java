package cn.cary.codebase.generator.config;

import cn.cary.codebase.generator.base.CommonEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段统一更新类
 * @date 2021/3/25 15:59
 */
@Component
public class MetaObjectConfig implements MetaObjectHandler {

    private static final String CREATE_TIME = "createDate";

    private static final String CREATE_USER = "createBy";

    private static final String UPDATE_TIME = "updateDate";

    private static final String UPDATE_USER = "updateBy";

    private static final String IS_DEL = "delFlag";

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldVal(metaObject, CREATE_USER, CREATE_TIME);
        setFieldVal(metaObject, UPDATE_USER, UPDATE_TIME);

        if (metaObject.hasGetter(IS_DEL)) {
            setFieldValByName(IS_DEL, Integer.parseInt(CommonEnum.ZERO.getCode()), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldVal(metaObject, UPDATE_USER, UPDATE_TIME);
    }


    private void setFieldVal(MetaObject metaObject, String userField, String datetimeField) {
        if (metaObject.hasGetter(userField)) {
            setFieldValByName(userField, "admin", metaObject);
        }
        if (metaObject.hasGetter(datetimeField)) {
            setFieldValByName(datetimeField, new Date(), metaObject);
        }
    }

}
