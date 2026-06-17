package com.micronet.aop;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@InterceptorBean(LogRequest.class)
public class LogRequestInterceptor implements MethodInterceptor<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(LogRequestInterceptor.class);

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        String methodName = context.getDeclaringType().getSimpleName() + "." + context.getMethodName();
        LOG.info("[REQUEST] Entering method: {} with arguments: {}", methodName, context.getParameterValueMap());

        long start = System.currentTimeMillis();
        try {
            Object result = context.proceed();
            LOG.info("[REQUEST] Completed method: {} in {}ms", methodName, System.currentTimeMillis() - start);
            return result;
        } catch (Exception e) {
            LOG.error("[REQUEST] Exception in method: {} after {}ms - {}", methodName, System.currentTimeMillis() - start, e.getMessage());
            throw e;
        }
    }
}
