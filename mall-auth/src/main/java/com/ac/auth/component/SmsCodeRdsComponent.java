package com.ac.auth.component;

import com.ac.oauth2.enums.SmsBuzTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeRdsComponent {

    public boolean codeVerify(SmsBuzTypeEnum type, String mobile, String msgCode) {
        //TODO  校验短信码 逻辑实现
        return true;
    }
}
