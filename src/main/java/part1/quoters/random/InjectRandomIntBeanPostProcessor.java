package part1.quoters.random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/*
Зачем нужно 2 метода в BeanPostProcessor?

Когда обьъекту нужно добавить логику на лету, то используеться механизм Dynamic Proxy.
Но после тогда как мы создадим прокси объекта, все метаданные сотруться.
По этой причине в методе postProcessBeforeInitialization мы работыем с оригинальными объектами.
А в postProcessAfterInitialization мы можем работать уже с прокси-объектами.
 */
public class InjectRandomIntBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            InjectRandomInt injectRandomInt = declaredField.getAnnotation(InjectRandomInt.class);
            if (injectRandomInt != null) {
                int min = injectRandomInt.min();
                int max = injectRandomInt.max();

                Random random = new Random();
                int i = min + random.nextInt(max - min);
                declaredField.setAccessible(true);
                ReflectionUtils.setField(declaredField, bean, i);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}