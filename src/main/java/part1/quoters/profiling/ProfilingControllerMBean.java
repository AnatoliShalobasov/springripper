package part1.quoters.profiling;

/*
ProfilingControllerMBean - интерфейс, который позволит
менять параметры класса ProfilingController на лету (в любое время)
с помощью JMX console.
 */
public interface ProfilingControllerMBean {

    void setEnabled(boolean enabled);
}