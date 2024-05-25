package tasks.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestTask {

    @Test
    public void Test1_TestConstructor() {
        String defaultTitle = "Title";
        Date defaultStartDate = new Date(1);
        Date defaultEndDate = new Date(2);
        int defaultInterval = 2;

        Assertions.assertDoesNotThrow(() -> {
            Task task;

            task = new Task(defaultTitle, defaultStartDate);
            Assertions.assertNotNull(task);
            Assertions.assertEquals(defaultTitle, task.getTitle());
            Assertions.assertEquals(defaultStartDate, task.getStartTime());
            Assertions.assertEquals(defaultStartDate, task.getEndTime());
            Assertions.assertEquals(0, task.getRepeatInterval());

            task = new Task(defaultTitle, defaultStartDate, defaultEndDate, defaultInterval);
            Assertions.assertNotNull(task);

            Assertions.assertEquals(defaultTitle, task.getTitle());
            Assertions.assertEquals(defaultStartDate, task.getStartTime());
            Assertions.assertEquals(defaultEndDate, task.getEndTime());
            Assertions.assertEquals(defaultInterval, task.getRepeatInterval());
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(defaultTitle, new Date(-1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Task("", defaultStartDate, defaultEndDate, defaultInterval));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(defaultTitle, new Date(-1), defaultEndDate, defaultInterval));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(defaultTitle, defaultStartDate, new Date(-1), defaultInterval));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(defaultTitle, defaultStartDate, defaultEndDate, 0));
    }

    @Test
    public void Test2_TestNextTimeAfter() throws InterruptedException {
        Task task = new Task("Title", new Date(), new Date(), 3);
        Thread.sleep(3);
        Assertions.assertNotNull(task);

        Assertions.assertNull(task.nextTimeAfter(new Date()));

        Date today = new Date();
        task = new Task("Title", new Date(), new Date(today.getTime() + 10000L), 10);
        Assertions.assertNotNull(task);

        Assertions.assertEquals(new Date(today.getTime() + 10).toString(), task.nextTimeAfter(new Date(today.getTime() + 1)).toString());
    }
}
