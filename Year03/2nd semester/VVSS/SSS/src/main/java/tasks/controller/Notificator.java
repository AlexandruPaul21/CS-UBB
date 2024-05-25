package tasks.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import tasks.model.Task;

import java.util.Date;

public class Notificator extends Thread {

    public static boolean isActive = true;
    private static final int millisecondsInSec = 1000;
    private static final int secondsInMin = 60;

    private static final Logger log = Logger.getLogger(Notificator.class.getName());

    private ObservableList<Task> tasksList;

    public Notificator(ObservableList<Task> tasksList){
        this.tasksList=tasksList;
    }

    @Override
    public void run() {
        Date currentDate = new Date();
        // fix
        while (isActive) {
        // fix
            for (Task t : tasksList) {
                if (t.isActive()) {
                    if (t.isRepeated() && t.getEndTime().after(currentDate)){

                        Date next = t.nextTimeAfter(currentDate);
                        long currentMinute = getTimeInMinutes(currentDate);
                        long taskMinute = getTimeInMinutes(next);
                        if (currentMinute == taskMinute){
                            showNotification(t);
                        }
                    }
                    else {
                        if (!t.isRepeated()){
                            if (getTimeInMinutes(currentDate) == getTimeInMinutes(t.getTime())){
                                showNotification(t);
                            }
                        }

                    }
                }

            }

            try {
                Thread.sleep(millisecondsInSec*secondsInMin);

            } catch (InterruptedException e) {
                log.error("thread interrupted exception");
            }
            currentDate = new Date();
        }
    }
    public static void showNotification(Task task){
        log.info("push notification showing");
        Platform.runLater(() -> {
            Notifications.create().title("Task reminder").text("It's time for " + task.getTitle()).showInformation();
        });
    }
    private static long getTimeInMinutes(Date date){
        return date.getTime()/millisecondsInSec/secondsInMin;
    }
}
