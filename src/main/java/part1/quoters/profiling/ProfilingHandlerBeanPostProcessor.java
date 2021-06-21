package part1.quoters.profiling;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/*
Зачем нужно 2 метода в BeanPostProcessor?
Когда обьъекту нужно добавить логику на лету, то используеться механизм Dynamic Proxy.
Но после тогда как мы создадим прокси объекта, все метаданные сотруться.
По этой причине в методе postProcessBeforeInitialization мы работыем с оригинальными объектами.
А в postProcessAfterInitialization мы можем работать уже с прокси-объектами.
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();
    private ProfilingController profilingController = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if (profilingController.isEnabled()) {
                        System.out.println("Профилирую...");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, objects);
                        long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("Всё");
                        return retVal;
                    }
                    return method.invoke(bean, objects);
                }
            });
        }
        return bean;
    }
}