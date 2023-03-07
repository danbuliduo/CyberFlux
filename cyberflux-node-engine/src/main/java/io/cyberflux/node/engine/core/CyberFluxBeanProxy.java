package io.cyberflux.node.engine.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CyberFluxBeanProxy implements InvocationHandler {
    private Object bean;
    public CyberFluxBeanProxy(Object bean) {
        this.bean = bean;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       return method.invoke(bean, args);
    }
}
