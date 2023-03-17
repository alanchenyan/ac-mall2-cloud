package com.ac.member.rds;

import com.ac.common.util.redis.RdsComponent;
import com.ac.member.entity.Member;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MemberRds {

    @Resource
    private RdsComponent rdsComponent;

    /**
     * 用前缀，组成目录的结构
     */
    private static final String KEY_PREFIX = "member:";

    public Boolean save(Member obj) {
        return rdsComponent.hmSetObj(populateKey(obj.getId()), obj);
    }

    public Member get(Long id) {
        return rdsComponent.hmGetObj(populateKey(id), Member.class);
    }

    private String populateKey(Long id) {
        return KEY_PREFIX + id;
    }
}
