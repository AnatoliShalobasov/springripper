package part1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TODO переделать на тесты
  /*
        BeanDefinitionDocumentReader считывает описание (определение) бинов из xml,
        кладет описание в BeanDefinitions,
        BeanFactory создает BeanPostProcessor-ы и кладет в сторонку, так как это обсобые объекты в которых на данном этапе
        просто прописаны описания бинов
        BeanFactory создает singleton бины согласно конфигурации,
        отдает созданный бин BeanPostProcessor-ам,
        отрабатывает метод postProcessBeforeInitialization у BeanPostProcessor-а
        BeanPostProcessor возвращает, отданный ему бин, BeanFactory,
        после вызываеться init-method бина,
        BeanFactory отдает бины BeanPostProcessor-ам,
        отрабатывает метод postProcessAfterInitialization у BeanPostProcessor-а
        BeanPostProcessor возвращает бины BeanFactory,
        BeanFactory кладет их в context
         */
public class SpringRipperPartOneApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    }
}