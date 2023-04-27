package com.ac.core.response;

import com.ac.core.i18n.I18nResource;
import com.ac.core.i18n.I18nResourceFactory;
import lombok.Data;


/**
 * @author Alan Chen
 * @description 响应前端统一格式
 * @date 2023/04/15
 */
@Data
public class RepResult {
    private int code;
    private String msg;
    private Object data;

    private static I18nResource i18nResource = I18nResourceFactory.getI18nResource();

    public RepResult(){}

    public RepResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RepResult successMsg(String msg){
        return new RepResult(200,msg,"");
    }

    public static RepResult success(Object data){
        return new RepResult(200,"",data);
    }

    public static RepResult success(){
        return new RepResult(200,i18nResource.getValue("success"),"");
    }

    public static RepResult info(String msg){
        return new RepResult(600,msg,"600");
    }

    public static RepResult fail(Object data){
        return new RepResult(500,i18nResource.getValue("server_error"),data);
    }

    public static RepResult fail(){
        return new RepResult(500,i18nResource.getValue("server_error"),"");
    }

    public static RepResult fail(int code,String msg,Object data){
        return new RepResult(code,msg,data);
    }

}
