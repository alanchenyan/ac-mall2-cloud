package com.ac.core.exception;

import com.ac.core.i18n.I18nResource;
import com.ac.core.response.RepResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alan Chen
 * @description 全局异常处理
 * @date 2023/4/27
 */
@Slf4j
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private I18nResource validationI18nSource;

    private I18nResource responseMessageI18nSource;

    /**
     * 是否开启Validator国际化功能
     * @return
     */
    protected boolean enableValidationI18n(){
        return false;
    }

    /**
     * 国际化文件地址
     * @return
     */
    protected String validationI18nSourcePath(){
        return "i18n/validation";
    }

    /**
     * 是否开启消息国际化
     * @return
     */
    protected boolean enableResponseMessageI18n(){
        return false;
    }

    /**
     * 消息国际化文件地址
     * @return
     */
    protected String responseMessageI18nSourcePath(){
        return "i18n/messages";
    }


    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RepResult errorHandler(Exception ex) {
        ex.printStackTrace();
        log.error("Exception:"+ex.getMessage());
        return RepResult.fail(ex.getMessage());
    }

    /**
     * validator校验失败信息处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public RepResult bindExceptionHandler(BindException exception) {
        exception.printStackTrace();
        return doValidationException(exception.getBindingResult());
    }

    /**
     * validator校验失败信息处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RepResult validationHandler(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        log.error("MethodArgumentNotValidException:"+exception.getMessage());
        return doValidationException(exception.getBindingResult());
    }

    /**
     * 拦截捕捉业务异常 ServiceException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServerException.class)
    public RepResult commonExceptionHandler(ServerException ex) {

        ex.printStackTrace();
        log.error("ServiceException:"+ex.getMessage());

        if(enableResponseMessageI18n()){
            if(responseMessageI18nSource == null){
                responseMessageI18nSource = new I18nResource(responseMessageI18nSourcePath());
            }
            String messageKey = ex.getMessage();
            try{
                String message = responseMessageI18nSource.getValue(messageKey);
                String[] placeholder = ex.getPlaceholder();
                if(placeholder!=null && placeholder.length>0){
                   for(int i =0;i<placeholder.length;i++){
                       message = message.replace("#{"+(i+1)+"}",placeholder[i]);
                   }
                }
                return RepResult.info(message);
            }catch (Exception e){
                return RepResult.info(ex.getMessage());
            }
        }

        return RepResult.info(ex.getMessage());
    }


    private RepResult doValidationException(BindingResult bindingResult){
        StringBuffer stringBuffer = new StringBuffer();

        if(enableValidationI18n()){
            if(validationI18nSource == null){
                validationI18nSource = new I18nResource(validationI18nSourcePath());
            }

            for (FieldError error : bindingResult.getFieldErrors()) {
                String messageKey = error.getDefaultMessage();
                try{
                    String message = validationI18nSource.getValue(messageKey);
                    stringBuffer.append(message).append(";");
                }catch (Exception e){
                    stringBuffer.append(messageKey).append(";");
                }
            }
        }else{
            for (FieldError error : bindingResult.getFieldErrors()) {
                stringBuffer.append(error.getDefaultMessage()).append(";");
            }
        }

        log.error("BindException:"+stringBuffer.toString());
        return RepResult.info(stringBuffer.toString());
    }

}
