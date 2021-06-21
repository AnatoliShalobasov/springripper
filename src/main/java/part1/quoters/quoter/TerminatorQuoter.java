package part1.quoters.quoter;

import part1.quoters.random.InjectRandomInt;
import part1.quoters.postproxy.PostProxy;
import part1.quoters.profiling.Profiling;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    @PostConstruct
    public void init() {
        System.out.println("Фаза 2");
        System.out.println(repeat);
    }

    public TerminatorQuoter() {
        System.out.println("Фаза 1");
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Фаза 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("Сообщение : " + message);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}