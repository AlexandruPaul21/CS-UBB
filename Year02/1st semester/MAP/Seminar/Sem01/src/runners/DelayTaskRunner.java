package runners;

import utils.Constants;

public class DelayTaskRunner extends AbstractTaskRunner{
    public DelayTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask() {
        runner.executeOneTask();
        delay();
    }

    private void delay() {
        try {
            Thread.sleep(Constants.DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
