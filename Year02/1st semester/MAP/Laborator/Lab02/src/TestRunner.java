import container.Strategy;
import model.MessageTask;
import runners.DelayTaskRunner;
import runners.PrinterTaskRunner;
import runners.StrategyTaskRunner;

import java.time.LocalDateTime;

public class TestRunner {
    private static MessageTask[] getMessages() {
        MessageTask temaLab = new MessageTask("1",
                "doua probleme",
                "pentru seminar","Mihai","Florentin", LocalDateTime.now());

        MessageTask temaSeminar = new MessageTask("2",
                "trei probleme","pentru laborator","Andrei","Andreea",LocalDateTime.now());

        MessageTask temaCurs = new MessageTask("3",
                "patru probleme","pentru curs", "Diana", "Paula", LocalDateTime.now());

        return new MessageTask[]{temaLab, temaSeminar, temaCurs};
    }

    public static void run(Strategy strategy) {
        MessageTask[] messages = getMessages();
//        for (MessageTask message : messages) {
//            message.execute();
//        }

//        TaskRunner taskRunner = new StrategyTaskRunner(strategy);
//        taskRunner.addTask(messages[0]);
//        taskRunner.addTask(messages[1]);
//        taskRunner.addTask(messages[2]);
//        taskRunner.executeAll();

//        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new StrategyTaskRunner(strategy));
//        printerTaskRunner.addTask(messages[0]);
//        printerTaskRunner.addTask(messages[1]);
//        printerTaskRunner.addTask(messages[2]);
//        printerTaskRunner.executeAll();

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new DelayTaskRunner(new StrategyTaskRunner(strategy)));
        printerTaskRunner.addTask(messages[0]);
        printerTaskRunner.addTask(messages[1]);
        printerTaskRunner.addTask(messages[2]);
        printerTaskRunner.executeAll();
    }
}
