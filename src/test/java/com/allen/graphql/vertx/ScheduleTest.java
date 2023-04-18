package com.allen.graphql.vertx;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleTest {

    public class Task {
        private String name;
        private Promise<Map<String, Object>> promise;

        private List<Task> prevTasks;
        private List<Task> nextTasks;

        private Integer seconds;
        private Boolean success;

        Task(String name, Integer seconds, Boolean success) {
            this.name = name;
            promise = Promise.promise();
            prevTasks = new ArrayList<>();
            nextTasks = new ArrayList<>();
            this.seconds = seconds;
            this.success = success;
        }

        void addPrev(Task... tasks) {
            for (Task task: tasks) {
                if (!prevTasks.contains(task)) {
                    prevTasks.add(task);
                }
            }
        }

        private void setDependency() {
//            System.out.println(name + " set dependency");

            if (prevTasks.isEmpty()) {
                return;
            }

            List<Future> futures = new ArrayList<>();
            for (Task task: prevTasks) {
                futures.add(task.getResult());
            }
            CompositeFuture.all(futures)
                    .onSuccess(handler -> this.execute(List.of()))
                    .onFailure(err -> this.failedOnPrev());
        }

        Future<Map<String, Object>> getResult() {
            return promise.future();
        }

        void execute(List<Map<String, Object>> input) {
//            System.out.println(name + " executing");

            if(!success) {
                System.out.println(Thread.currentThread().getName()+"=="+name + " failed");
                promise.fail(name);
                return;
            }

            if(0 < seconds) {
//                System.out.println(name + " sleep");
                try {
                    Thread.sleep(seconds * 1000);
                } catch (InterruptedException ignored) {

                }
            }

            System.out.println(Thread.currentThread().getName()+"=="+name + " finished");
            promise.complete(Map.of("key", name));
        }

        void failedOnPrev() {
            System.out.println(name + " failed on prev");
            promise.fail(name);
        }
    }

    @Test
    void runSchedule() {
        Task task1 = new Task("task01", 0, true);
        Task task2 = new Task("task02", 0, true);
        Task task3 = new Task("task03", 0, true);
        Task task4 = new Task("task04", 0, true);
        Task task5 = new Task("task05", 0, true);
        Task task6 = new Task("task06", 0, true);
        Task task7 = new Task("task07", 0, true);
        task4.addPrev(task2);
        task3.addPrev(task1, task2);
        task5.addPrev(task3);
        task6.addPrev(task3);
        task7.addPrev(task4, task5, task6);
        task4.setDependency();
        task3.setDependency();

        task5.setDependency();
        task6.setDependency();
        task7.setDependency();

        task1.execute(List.of());
        task2.execute(List.of());
    }

}
