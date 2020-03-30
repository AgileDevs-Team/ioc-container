package br.com.netodevel.ioc;

import br.com.netodevel.ioc.domains.BeanDefinition;

import java.util.Map;

public class IocContainer {

    private final Map<String, BeanDefinition> pool;

    public IocContainer(Map<String, BeanDefinition> pool) {
        this.pool = pool;
    }

    public <T> T getBean(Class<T> clazz) {
        Object beam = getBean(clazz.getName());
        return clazz.cast(beam);
    }

    private Object getBean(String bean) {
        BeanDefinition beanDefinition = pool.get(bean);
        if (beanDefinition == null) {
            return null;
        }
        return beanDefinition.getBean();
    }

}
