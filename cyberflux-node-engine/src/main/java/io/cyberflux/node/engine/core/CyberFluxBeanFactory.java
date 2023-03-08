package io.cyberflux.node.engine.core;

//import io.cyberflux.node.engine.annotation.CyberBean;
import io.cyberflux.node.engine.annotation.CyberInject;
import io.cyberflux.node.engine.annotation.CyberReactor;
import io.cyberflux.node.engine.container.CyberFluxBeanContainer;
import io.cyberflux.node.engine.utils.CyberPackageUtils;

import java.util.ArrayList;
import java.util.List;
import reactor.core.publisher.Flux;

public class CyberFluxBeanFactory {
    private final CyberFluxBeanContainer context;

    public CyberFluxBeanFactory() {
        this(CyberPackageUtils.getRootPackageName());
    }
    public CyberFluxBeanFactory(String packageName) {
        this(CyberFluxBeanContainer.INSTANCE, packageName);
    }
    public CyberFluxBeanFactory(CyberFluxBeanContainer context, String packageName) {
        this.context = context;
        injectBeans(loadBeans(packageName));
    }

    public Object findBean(String injectName) {
        return context.findBean(injectName);
    }

    public <T> Flux<T> beanFlux(Class<T> clasz) {
        return context.beanFlux(clasz);
    }

    private List<Object> loadBeans(String packageName) {
        List<Object> beans = new ArrayList<>();
        Flux.fromIterable(CyberPackageUtils.scanClassName(packageName, true)).subscribe(name -> {
            try {
                Class<?> clasz = Class.forName(name);
                if (clasz.isAnnotationPresent(CyberReactor.class)) {
                    CyberReactor reactor = clasz.getAnnotation(CyberReactor.class);
                    String injectName = reactor.value().isBlank() ? name : reactor.value();
                    if (!context.contains(injectName)) {
                        Object bean = clasz.getDeclaredConstructor().newInstance();
                        context.saveBean(injectName, bean);
                        beans.add(bean);
                        /*Flux.fromArray(clasz.getMethods())
                            .filter(method -> method.isAnnotationPresent(CyberBean.class))
                            .subscribe(method -> {
                                System.out.println(method.getName());
                            }
                        );*/
                    }
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
        return beans;
    }

    private void injectBeans(List<Object> beans) {
        Flux.fromIterable(beans).subscribe(bean -> {
            Flux.fromArray(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CyberInject.class))
                .subscribe(field -> {
                    CyberInject inject = field.getAnnotation(CyberInject.class);
                    String injectName = inject.value().isBlank() ? field.getType().getName() : inject.value();
                    field.setAccessible(true);
                    try {
                        field.set(bean, context.findBean(injectName));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            );
        });
    }
}
