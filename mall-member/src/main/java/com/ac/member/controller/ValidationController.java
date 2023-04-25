package com.ac.member.controller;

import com.ac.member.vo.PersonAddVO;
import com.ac.member.vo.PersonUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "Validation校验测试")
@RestController
@RequestMapping("validation")
public class ValidationController {

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody @Valid PersonAddVO vo) {
        log.info("add,vo={}", vo);
        return true;
    }

    @ApiOperation(value = "修改")
    @PutMapping
    public boolean update(@RequestBody @Valid PersonUpdateVO vo) {
        log.info("update,vo={}", vo);
        return true;
    }
}
