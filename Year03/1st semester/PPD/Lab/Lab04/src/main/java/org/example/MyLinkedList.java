package org.example;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedList {
    private final Lock lock = new ReentrantLock();
    private final LinkedList<Node> list = new LinkedList<>();

    public void add(Node node) {
        lock.lock();
        boolean finished = true;
        for (int i = 0; i < list.size(); ++i) {
            Node actualNode = list.get(i);
            if (actualNode.getId().equals(node.getId())) {
                if (actualNode.getPoints() == -1) {
                    finished = false;
                    break;
                }

                if (node.getPoints() == -1) {
                    list.remove(i);
                    list.add(0, node);
                    finished = false;
                    break;
                }

                node.setPoints(node.getPoints() + actualNode.getPoints());

                while (i > 1 && node.getPoints() > list.get(i - 1).getPoints()) {
                    list.set(i, list.get(i - 1));
                    --i;
                }

                list.set(i, node);
                finished = false;
                break;
            }
        }

        if (finished) {
            int i = 0;
            while (i < list.size()) {
                if (list.get(i).getPoints() <= node.getPoints()) {
                    break;
                }
                ++i;
            }
            list.add(i, node);
        }
        lock.unlock();
    }

    public LinkedList<Node> getLinkedList() {
        return list;
    }
}
