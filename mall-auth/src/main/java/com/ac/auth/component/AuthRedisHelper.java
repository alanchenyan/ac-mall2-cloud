package com.ac.auth.component;

import com.ac.core.redis.util.RdsComponent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthRedisHelper {
    @Resource
    private RdsComponent rdsComponent;

    public static final String KEY = "auth:error:";
    public static final String KEY_DEL_RECORD = "mem:del:mobile:";

    public static final Long TIME = 43200L;

    public void inc(String mobile) {
        if (!rdsComponent.hasKey(KEY + mobile)) {
            rdsComponent.set(KEY + mobile, 1l, TIME);
        } else {
            rdsComponent.incr(KEY + mobile);
        }
    }

    public Boolean hasLock(String mobile) {
        Object ob = rdsComponent.get(KEY + mobile);
        if (null == ob) {
            return false;
        } else {
            return Integer.parseInt(ob.toString()) > 4;
        }
    }

    public Boolean getDelRecord(String mobile) {
        return rdsComponent.get(KEY_DEL_RECORD + mobile) != null;
    }
}
