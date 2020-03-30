package br.com.netodevel.ioc;

import br.com.netodevel.ioc.annotations.Component;
import br.com.netodevel.ioc.annotations.Inject;
import br.com.netodevel.ioc.domains.BeanDefinition;
import br.com.netodevel.ioc.handlers.ClassFromDirectory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleIoc {

    public static void main(String[] args) {
        IocContainer iocContainer = run(SimpleIoc.class);

        SampleComponent sampleComponent = iocContainer.getBean(SampleComponent.class);
        System.out.println(sampleComponent.sampleFieldBean.hello());
    }

    private static IocContainer run(Class<SimpleIoc> rootClazz) {
        ClassFromDirectory classFromDirectory = new ClassFromDirectory();

        String directory = rootClazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        Set<Class<?>> locatedClasses = classFromDirectory.locateClass(directory);

        final Map<String, BeanDefinition> pool = new ConcurrentHashMap<>(32);
        locatedClasses.forEach(e -> {

            if (e.isAnnotationPresent(Component.class)) {
                try {
                    Class<?> object = Class.forName(e.getName());
                    Object objectWithDependencies = resolverDependecies(object);

                    BeanDefinition beanDefinition = new BeanDefinition(objectWithDependencies, object, object.getMethods(), object.getDeclaredFields());
                    pool.put(e.getName(), beanDefinition);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        return new IocContainer(pool);
    }

    private static Object resolverDependecies(Class<?> rootClass) throws InstantiationException, ClassNotFoundException, IllegalAccessException {

        Class<?> beanClass = Class.forName(rootClass.getName());
        Object objectRoot = beanClass.newInstance();

        for (int i = 0; i < rootClass.getFields().length; i++) {
            Field field = rootClass.getFields()[i];
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> childClass = Class.forName(field.getType().getName());
                Object objectChild = childClass.newInstance();

                field.setAccessible(true);
                field.set(objectRoot, objectChild);
            }
        }

        return objectRoot;
    }

}
