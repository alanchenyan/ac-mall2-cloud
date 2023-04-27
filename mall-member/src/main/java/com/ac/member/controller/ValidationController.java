package com.ac.member.controller;

import com.ac.core.validation.action.AddAction;
import com.ac.core.validation.action.EditAction;
import com.ac.member.vo.PersonEditVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "Validation校验测试")
@RestController
@RequestMapping("validation")
public class ValidationController {

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody @Validated(AddAction.class) PersonEditVO vo) {
        log.info("add,vo={}", vo);
        return true;
    }

    @ApiOperation(value = "修改")
    @PutMapping
    public boolean update(@RequestBody @Validated(EditAction.class) PersonEditVO vo) {
        log.info("update,vo={}", vo);
        return true;
    }
}
