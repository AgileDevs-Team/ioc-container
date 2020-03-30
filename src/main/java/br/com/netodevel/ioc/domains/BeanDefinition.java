package br.com.netodevel.ioc.domains;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanDefinition {

    private final Object bean;
    private final Class<?> clazz;
    private final Method[] methods;
    private final Field[] fields;

    public BeanDefinition(Object bean, Class<?> clazz, Method[] methods, Field[] fields) {
        this.bean = bean;
        this.clazz = clazz;
        this.methods = methods;
        this.fields = fields;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method[] getMethods() {
        return methods;
    }

    public Object getBean() {
        return bean;
    }

    public Field[] getFields() {
        return fields;
    }
}
