package com.ac.feign.member.api;

import com.ac.feign.member.dto.MemberDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-member")
public interface MemberFeignApi {

    @ApiOperation(value = "获取用户")
    @GetMapping("member/{id}")
    MemberDTO findMember(@PathVariable("id") Long id);

}
