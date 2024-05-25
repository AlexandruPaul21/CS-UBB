package tasks.model;


import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;
public class LinkedTaskList  extends TaskList {
    private static final Logger log = Logger.getLogger(LinkedTaskList.class.getName());
    private class LinkedTaskListIterator implements Iterator<Task>{
        private int cursor;
        private int lastCalled = -1;

        @Override
        public boolean hasNext() {
            return cursor < numberOfTasks;
        }

        @Override
        public Task next() {
            if (!hasNext()){
                log.error("next iterator element doesn't exist");
                throw new NoSuchElementException("No next element");
            }
            lastCalled = cursor;
            return getTask(cursor++);
        }

        @Override
        public void remove() {
            if (lastCalled == -1){
                throw new IllegalStateException();
            }
            LinkedTaskList.this.remove(getTask(lastCalled));
            cursor = lastCalled;
            lastCalled = -1;
        }
    }
    private int numberOfTasks;
    private Node last;

    @Override
    public void add(Task task) {
        numberOfTasks++;
        Node lastNode = last;
        Node newNode = new Node(task, lastNode);
        if (last!= null) last.setNext(newNode);
        last = newNode;
    }
    @Override
    public boolean remove(Task task) {
        if (isNull(task)) {
            log.error("removing task that doesn't exist");
            throw new NullPointerException("Task is null");
        }

        Node cursor = last;
        if (last.getTask().equals(task)) this.last = last.getLast();
        int tasksToCheck = size();
        while (tasksToCheck > 0 && !task.equals(cursor.getTask())){
            cursor = cursor.getLast();
            tasksToCheck--;
        }
        if (isNull(cursor)) return false;

        if (cursor.last!= null) cursor.getLast().setNext(cursor.getNext());
        if (cursor.next!= null) cursor.getNext().setLast(cursor.getLast());

        numberOfTasks--;
        return true;
    }

    @Override
    public int size() {
        return numberOfTasks;
    }
    @Override
    public Task getTask(int index) {
        if (index < 0 || index > size()-1) {
            log.error("index doesn't exist");
            throw new IndexOutOfBoundsException("Index not found");
        }
        int stepsBack = size()-index-1;
        Node current = last;
        while (stepsBack > 0){
            current = current.getLast();
            stepsBack--;
        }
        return current.getTask();
    }

    @Override
    public List<Task> getAll() {
        LinkedList<Task> tks=new LinkedList<>();
        for (Task t: this)
            tks.add(t);
        return tks;
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskListIterator();
    }

    private static class Node {
        private Task task;
        private Node last;
        private Node next;

        private Node getNext() {
            return next;
        }

        private void setNext(Node next) {
            this.next = next;
        }

        private Node(Task task, Node last) {
            this.task = task;
            this.last = last;
        }

        private Task getTask() {
            return task;
        }

        private Node getLast() {
            return last;
        }

        private void setLast(Node last) {
            this.last = last;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedTaskList that = (LinkedTaskList) o;

        if (numberOfTasks != that.numberOfTasks) return false;
        int i = 0;
        for (Task a : this){
            if (!a.equals(((LinkedTaskList) o).getTask(i))){
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfTasks;
        result = 31 * result + getTask(0).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "numberOfTasks=" + numberOfTasks +
                ", last=" + last +
                '}';
    }
    @Override
    protected LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList tasks = new LinkedTaskList();
        for (Task t : this){
            tasks.add(t);
        }
        return tasks;
    }
}
