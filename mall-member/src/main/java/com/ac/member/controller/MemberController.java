package com.ac.member.controller;

import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.ac.member.service.MemberService;
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

    @ApiOperation(value = "通过ID查询")
    @GetMapping("{id}")
    public Member findById(@PathVariable Long id) {
        return memberServiceImpl.findById(id);
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
}
