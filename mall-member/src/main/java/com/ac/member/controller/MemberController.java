package com.ac.member.controller;

import com.ac.member.component.MemberIntegralComponent;
import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.ac.member.service.MemberService;
import com.ac.member.vo.IntegralLogEditVO;
import com.ac.member.vo.MemberEditVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户")
@RestController
@RequestMapping("member")
public class MemberController {

    @Resource
    private MemberService memberServiceImpl;

    @Resource
    private MemberIntegralComponent memberIntegralComponent;

    @ApiOperation(value = "获取用户")
    @GetMapping("{id}")
    public MemberDTO findMember(@PathVariable Long id) {
        return memberServiceImpl.findMember(id);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public Boolean addMember(@RequestBody @Valid MemberEditVO editVO) {
        return memberServiceImpl.addMember(editVO);
    }

    @ApiOperation(value = "查询用户列表")
    @GetMapping("list")
    public List<MemberDTO> listMember(@Valid MemberQry qry) {
        return memberServiceImpl.listMember(qry);
    }

    @ApiOperation(value = "记录积分")
    @PostMapping
    public Boolean recordIntegral(@RequestBody @Valid IntegralLogEditVO logEditVO) {
        memberIntegralComponent.recordIntegral(logEditVO);
        return true;
    }
}
