package tasks.services;

import org.junit.jupiter.api.*;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskServiceTest {
    private static TasksService service;

    @BeforeAll
    static void generalSetUp() {
        ArrayTaskList tasks = new ArrayTaskList();
        service = new TasksService(tasks);
    }

    @AfterAll
    static void bigCleaning() {
        service = null;
    }

    @Nested
    @Tag("ECP")
    @DisplayName("ECP Tests")
    class EcpTesting {
        @BeforeEach
        void testSetup() {
            service.clear();
            Task task = new Task("Test data", new Date(), new Date(), 1);
            service.saveTask(task);
        }

        @AfterEach
        void testCleaning() {
            service.clear();
        }


        @Test
        @DisplayName("Save task with a valid description")
        public void saveTaskWithValidTitle() {
            Task task = new Task("Sample", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> service.saveTask(task));
            Assertions.assertEquals(2, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a invalid description")
        public void saveTaskWithInvalid() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Task task = new Task("bzhwbkwuzbzgntpfcdkrcnniztzzvjhitfwxrtphqjrgemnxuauyjuanvuieeeadgacfvxjamgypqxtkvaeidiyphmpabbrhhqaehhjmphecaemfmnvnqyryuxtpzzuifpnjmqwepbprcvuyhzfywrctikgyrhtuuawxdzkxjbcbqczcbeyvegkffmrafpxzruxfxjpmxrdftpuxihxrnkthwfjytyrgugxufqhmkezttkdkznzubdiygtbwvdxrghkzeetayzjduayfmqrgihmknknvhvvdmixmhzftjctw", new Date(), new Date(), 1);
                service.saveTask(task);
            });
            Assertions.assertEquals(1, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a valid date")
        public void saveTaskWithValidDate() {
            Task task = new Task("Title", new Date(2024, 04, 21), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> {
                service.saveTask(task);
            });

            Assertions.assertEquals(2, service.getTasks().size());
            Assertions.assertEquals(service.getTasks().getTask(1), task);
        }
    }


    @Nested
    @Tag("BVA")
    @DisplayName("BVA Tests")
    class BvaTesting {
        @BeforeEach
        void testSetup() {
            service.clear();
            Task task = new Task("Test data", new Date(), new Date(), 1);
            service.saveTask(task);
        }

        @AfterEach
        void testCleaning() {
            service.clear();
        }

        @Test
        @DisplayName("Save task with a valid description (lower bound)")
        public void saveTaskWithValidTitle() {
            Task task = new Task("T", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> {
                service.saveTask(task);
            });

            Assertions.assertEquals(2, service.getTasks().size());
            Assertions.assertEquals(service.getTasks().getTask(1), task);
        }

        @Test
        @DisplayName("Save task with a invalid description (empty)")
        public void saveTaskWithInvalidTitleLowerBound() {
            Assertions.assertThrows(Exception.class, () -> {
                Task task = new Task("", new Date(), new Date(), 1);
                service.saveTask(task);
            });
            Assertions.assertEquals(1, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a invalid description (too long)")
        public void saveTaskWithInvalidTitleUpperBound() {
            Assertions.assertThrows(Exception.class, () -> {
                Task task = new Task("wLhIUEnLeKNcsrv7oFQqpr2gznG41jQdaWE5MYp1x2Z88PEuaCegF3dSr3ScuuwzzFfT5Fj6Zah8etTUXrRiUau9qfLJZxqwqMoaDv6TT6mG8V2V20BrOlYlV1w2A50sREW5YFRA5N4cY1UMF7NMZN5KTeSCWskqlz5gKbdFbQaMj6P2ZZ3xqHlBH4eDfbrpKD6RJB3i4rASxpJ3RSNNMX0rG4uYJrnuCWlkFiEtjzd3yhVrkIlIwIpw4U9oH6Yt", new Date(), new Date(), 1);
                service.saveTask(task);
            });
            Assertions.assertEquals(1, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a valid description (upper bound)")
        public void saveTaskWithValidTitleUpperBound() {
            Task task = new Task("wLhIUEnLeKNcsrv7oFQqpr2gznG41jQdaWE5MYp1x2Z88PEuaCegF3dSr3ScuuwzzFfT5Fj6Zah8etTUXrRiUau9qfLJZxqwqMoaDv6TT6mG8V2V20BrOlYlV1w2A50sREW5YFRA5N4cY1UMF7NMZN5KTeSCWskqlz5gKbdFbQaMj6P2ZZ3xqHlBH4eDfbrpKD6RJB3i4rASxpJ3RSNNMX0rG4uYJrnuCWlkFiEtjzd3yhVrkIlIwIpw4U9oH6Y", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> service.saveTask(task));
            Assertions.assertEquals(2, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a valid date (lower bound)")
        public void saveTaskWithValidDateLowerBound() {
            Task task = new Task("Title", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> {
                service.saveTask(task);
            });

            Assertions.assertEquals(2, service.getTasks().size());
            Assertions.assertEquals(service.getTasks().getTask(1), task);
        }

        @Test
        @DisplayName("Save task with a valid date (upper bound)")
        public void saveTaskWithValidDateUpperBound() {
            Task task = new Task("Title", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> {
                service.saveTask(task);
            });

            Assertions.assertEquals(2, service.getTasks().size());
            Assertions.assertEquals(service.getTasks().getTask(1), task);
        }

        @Test
        @DisplayName("Save task with invalid date (before 1970)")
        public void saveTaskWithInvalidDateLowerBound() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new Task("Title", new Date(-1), new Date(69, 12, 31), 1));
            Assertions.assertEquals(1, service.getTasks().size());
        }

        @Test
        @Disabled("For testing purposes")
        @DisplayName("Disabled test")
        public void testRepeatInvalidValue() {

        }
    }
}
