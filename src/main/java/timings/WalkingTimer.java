package timings;

import net.dv8tion.jda.core.JDA;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WalkingTimer {

    private JDA api;

    public WalkingTimer(JDA api){
        this.api = api;
    }

    public void start(){
        Task task = new Task(api);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
        executorService.scheduleAtFixedRate(task::taskRecruit, 0, 1, TimeUnit.DAYS);
        executorService.scheduleAtFixedRate(task::addDaysRecruit, 0, 1, TimeUnit.DAYS);
        executorService.scheduleAtFixedRate(task::taskHoliday, 0, 1, TimeUnit.DAYS);
        executorService.scheduleAtFixedRate(task::eraseLanceBot, 0, 1, TimeUnit.MINUTES);
        executorService.scheduleAtFixedRate(task::serverStatus, 0, 1, TimeUnit.MINUTES);
        executorService.scheduleAtFixedRate(task::notificationTactics, 0, 1, TimeUnit.MINUTES);
    }

}
