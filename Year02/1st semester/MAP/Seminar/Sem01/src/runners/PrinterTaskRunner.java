package runners;

import utils.Constants;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner runner) {
        super(runner);
    }

    public void executeOneTask() {
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    public void decorateExecuteOneTask() {
        System.out.println("Task-ul s-a executat la: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }
}
