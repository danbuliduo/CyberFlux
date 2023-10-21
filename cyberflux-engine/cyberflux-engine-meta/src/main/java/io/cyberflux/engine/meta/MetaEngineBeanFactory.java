package io.cyberflux.engine.meta;

import io.cyberflux.utils.jar.PackageUtils;
import io.cyberflux.engine.meta.annotation.MetaBean;
import io.cyberflux.engine.meta.annotation.MetaConfiguration;
import io.cyberflux.engine.meta.annotation.MetaInject;
import io.cyberflux.engine.meta.annotation.MetaObject;
import io.cyberflux.engine.meta.annotation.MetaObjectScan;
import io.cyberflux.engine.meta.annotation.MetaParam;
import io.cyberflux.engine.meta.exception.MetaEngineBeanException;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MetaEngineBeanFactory {
    private final static Logger log = LoggerFactory.getLogger(MetaEngineBeanFactory.class);
    private final MetaEngineBeanContainer context;

    public MetaEngineBeanFactory() {
        this(PackageUtils.getStartupClassName());
    }
    public MetaEngineBeanFactory(Class<?> clasz) {
        this(clasz.getPackageName());
    }
    public MetaEngineBeanFactory(String packageName) {
        this(new MetaEngineBeanContainer(), packageName);
    }
    public MetaEngineBeanFactory(MetaEngineBeanContainer context, String packageName) {
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
        Flux.fromIterable(PackageUtils.scanClassName(packageName, true)).subscribe(name -> {
            try {
                Class<?> clasz = Class.forName(name);
                if (clasz.isAnnotationPresent(MetaObjectScan.class)) {
                    Flux.fromArray(clasz.getAnnotation(MetaObjectScan.class).value())
                        .filter(sname -> !name.startsWith(sname))
                        .subscribe(sname -> {
                            objects.addAll(loadBeans(sname));
                        });
                }
                if (clasz.isAnnotationPresent(MetaConfiguration.class)) {
                    MetaConfiguration configuration = clasz.getAnnotation(MetaConfiguration.class);
                    String injectName = configuration.value().isBlank() ? name : configuration.value();
                    if (!context.contains(injectName)) {
                        Object object = clasz.getDeclaredConstructor().newInstance();
                        context.saveBean(injectName, object);
                        configs.add(object);
                    } else throw new MetaEngineBeanException("Configuration already exists.");
                }
                if (clasz.isAnnotationPresent(MetaObject.class)) {
                    MetaObject metaObject = clasz.getAnnotation(MetaObject.class);
                    String injectName = metaObject.value().isBlank() ? name : metaObject.value();
                    if (!context.contains(injectName)) {
                        Object object = clasz.getDeclaredConstructor().newInstance();
                        context.saveBean(injectName, object);
                        objects.add(object);
                    } else throw new MetaEngineBeanException("MetaObject already exists.");
                }
            } catch (ReflectiveOperationException e) {
                log.error("{}", e);
            }
        });
        loadingCyberBean(objects, configs);
        return objects;
    }

    private void loadingCyberBean(List<Object> objects, List<Object> configs) {
        Flux.fromIterable(configs).subscribe(object -> {
            Flux.fromArray(object.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(MetaBean.class))
                .subscribe(method -> {
                    MetaBean bean = method.getAnnotation(MetaBean.class);
                    String injectName =  bean.value().isBlank() ? method.getName() : bean.value();
                    if(!context.contains(injectName)) {
                        Class<?>[] classes =  method.getParameterTypes();
                        Object[] injectArgs = new Object[classes.length];
                        method.setAccessible(true);
                        for(int i = 0; i < classes.length; ++i) {
                            String paramName = null;
                            if(classes[i].isAnnotationPresent(MetaParam.class)) {
                                paramName = classes[i].getAnnotation(MetaParam.class).value();
                            }
                            if(paramName == null || paramName.isBlank()) {
                                paramName = classes[i].getName();
                            }
                            if(!context.contains(paramName)) {
                                throw new MetaEngineBeanException("ParamObject already exists.");
                            }
                            injectArgs[i] = context.findBean(paramName);
                        }
                        try {
                            context.saveBean(injectName, method.invoke(object, injectArgs));
                        } catch (ReflectiveOperationException e) {
                            log.error("{}", e);
                        }
                        objects.add(object);
                    } else throw new MetaEngineBeanException("BeanObject already exists.");
                }
            );
        });
    }

    private void injectBeans(List<Object> objects) {
        Flux.fromIterable(objects).subscribe(object -> {
            Flux.fromArray(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MetaInject.class))
                .subscribe(field -> {
                    MetaInject inject = field.getAnnotation(MetaInject.class);
                    String injectName = inject.value().isBlank() ? field.getType().getName() : inject.value();
                    field.setAccessible(true);
                    if (context.contains(injectName)) {
                        try {
                            field.set(object, context.findBean(injectName));
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            log.error("{}", e);
                        }
                    } else throw new MetaEngineBeanException("Injection object not found.");
                }
            );
        });
    }
}
