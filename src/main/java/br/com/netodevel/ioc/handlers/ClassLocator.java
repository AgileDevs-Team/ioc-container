package br.com.netodevel.ioc.handlers;

import java.util.Set;

public interface ClassLocator {

    Set<Class<?>> locateClass(String directory);
}
