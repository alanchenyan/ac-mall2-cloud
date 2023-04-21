package com.ac.common.apilog;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    private static final String UNKNOWN = "unknown";

    /**
     * 日志切入点 - 正常执行<br>
     * 表达式1：拦截所有controller
     * 表达式2：排除拦截RedisTestController中的方法
     */
    @Pointcut(
            "execution(public * com.ac.*.controller.*Controller.*(..))" +
                    "&& !execution(public * com.ac.*.controller.RedisTestController.*(..))"
    )
    public void logPointCut() {

    }

    /**
     * 日志切入点 - 异常
     */
    @Pointcut(
            "execution(public * com.ac.*.controller.*Controller.*(..))"
    )
    public void logExceptionPointCut() {

    }

    /**
     * 正常执行
     *
     * @param point 切入点
     * @throws Throwable 异常信息
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            long costTime = System.currentTimeMillis() - startTime;
            String ua = request.getHeader("User-Agent");
            String appVersion = request.getHeader("App-Version");
            String memberId = request.getHeader("memberId");
            UserAgent userAgent = UserAgentUtil.parse(ua);

            ApiLog apiLog = new ApiLog();

            apiLog.setCreateTime(LocalDateTime.now());
            apiLog.setTimeCost(costTime);
            apiLog.setUrl(request.getRequestURL().toString());
            apiLog.setHttpMethod(request.getMethod());
            apiLog.setClassMethod(point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
            apiLog.setRequestParams(extractParams(point));

            apiLog.setAppVersion(appVersion);
            apiLog.setMemberId(memberId);

            apiLog.setThreadId(Long.toString(Thread.currentThread().getId()));
            apiLog.setThreadName(Thread.currentThread().getName());
            apiLog.setIp(getIp(request));

            apiLog.setMobile(userAgent.isMobile());
            apiLog.setBrowser(userAgent.getBrowser().getName());
            apiLog.setPlatform(userAgent.getPlatform().getName());
            apiLog.setOs(userAgent.getOs().getName());
            apiLog.setEngine(userAgent.getEngine().getName());
            apiLog.setBrowserVersion(userAgent.getVersion());
            apiLog.setEngineVersion(userAgent.getEngineVersion());

            log.info("API请求时长:{}毫秒,请求信息:{}", costTime, JSONUtil.toJsonStr(apiLog));
            log.info("API请求结果,API:{},结果:{}", apiLog.getUrl(), JSONUtil.toJsonStr(result));
            if (costTime >= 100) {
                log.info("需要优化的API,{}毫秒,请求信息:{}", costTime, JSONUtil.toJsonStr(apiLog));
            }
        }
        return result;
    }

    /**
     * 异常
     *
     * @param point 切入点
     * @param e     异常
     */
    @AfterThrowing(pointcut = "logExceptionPointCut()", throwing = "e")
    public void logExceptionAround(JoinPoint point, Throwable e) {
        String classMethod = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            url = request.getRequestURL().toString();
        }
        log.error("API执行出异常了,classMethod={},url={},errorMsg={}", classMethod, url, e.getMessage());
    }

    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    /**
     * 提取请求参数
     *
     * @param joinPoint
     * @return
     */
    private Map<String, Object> extractParams(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }
}
