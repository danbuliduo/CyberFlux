package io.cyberflux.node.engine.huaxu;

import io.cyberflux.node.engine.huaxu.annotation.CyberBean;
import io.cyberflux.node.engine.huaxu.annotation.CyberConfiguration;
import io.cyberflux.node.engine.huaxu.annotation.CyberInject;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObject;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObjectScan;
import io.cyberflux.node.engine.huaxu.exception.CyberFluxBeanException;
import io.cyberflux.node.engine.huaxu.utils.CyberPackageUtils;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public final class CyberFluxBeanFactory {
    private final CyberFluxBeanContainer context;

    public CyberFluxBeanFactory() {
        this(CyberPackageUtils.getStartupClassName());
    }
    public CyberFluxBeanFactory(Class<?> clasz) {
        this(clasz.getPackageName());
    }
    public CyberFluxBeanFactory(String packageName) {
        this(new CyberFluxBeanContainer(), packageName);
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
        List<Object> objects = new ArrayList<>();
        List<Object> configs = new ArrayList<>();
        Flux.fromIterable(CyberPackageUtils.scanClassName(packageName, true)).subscribe(name -> {
            try {
                Class<?> clasz = Class.forName(name);
                if (clasz.isAnnotationPresent(CyberMetaObjectScan.class)) {
                    Flux.fromArray(clasz.getAnnotation(CyberMetaObjectScan.class).value())
                        .filter(sname -> !name.startsWith(sname))
                        .subscribe(sname -> {
                            objects.addAll(loadBeans(sname));
                        });
                }
                if (clasz.isAnnotationPresent(CyberConfiguration.class)) {
                    CyberConfiguration configuration = clasz.getAnnotation(CyberConfiguration.class);
                    String injectName = configuration.value().isBlank() ? name : configuration.value();
                    if (!context.contains(injectName)) {
                        Object object = clasz.getDeclaredConstructor().newInstance();
                        context.saveBean(injectName, object);
                        configs.add(object);
                    } else throw new CyberFluxBeanException("Configuration already exists.");
                }
                if (clasz.isAnnotationPresent(CyberMetaObject.class)) {
                    CyberMetaObject metaObject = clasz.getAnnotation(CyberMetaObject.class);
                    String injectName = metaObject.value().isBlank() ? name : metaObject.value();
                    if (!context.contains(injectName)) {
                        Object object = clasz.getDeclaredConstructor().newInstance();
                        context.saveBean(injectName, object);
                        objects.add(object);
                    } else throw new CyberFluxBeanException("MetaObject already exists.");
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
        Flux.fromIterable(configs).subscribe(object -> {
            Flux.fromArray(object.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CyberBean.class))
                .subscribe(method -> {
                    CyberBean bean = method.getAnnotation(CyberBean.class);
                    String injectName =  bean.value().isBlank() ? method.getName() : bean.value();
                    if(!context.contains(injectName)) {
                        objects.add(object);
                        method.setAccessible(true);
                        try {
                            context.saveBean(injectName, method.invoke(object));
                        } catch (ReflectiveOperationException e) {
                            e.printStackTrace();
                        }
                    } else throw new CyberFluxBeanException("BeanObject already exists.");
                }
            );
        });
        return objects;
    }

    private void injectBeans(List<Object> objects) {
        Flux.fromIterable(objects).subscribe(object -> {
            Flux.fromArray(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CyberInject.class))
                .subscribe(field -> {
                    CyberInject inject = field.getAnnotation(CyberInject.class);
                    String injectName = inject.value().isBlank() ? field.getType().getName() : inject.value();
                    field.setAccessible(true);
                    if (!context.contains(injectName)) {
                        try {
                            field.set(object, context.findBean(injectName));
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else throw new CyberFluxBeanException("Injection object not found.");
                }
            );
        });
    }
}
