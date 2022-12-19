package model;

import container.Sorting;
import utils.Functions;

public class SortingTask extends Task{
    private int[] list;
    private Sorting sorting;

    public int[] getList() {
        return list;
    }

    public SortingTask(String taskId, String description, int[] list, Sorting sorting) {
        super(taskId, description);
        this.list = list;
        this.sorting = sorting;
    }

    public void execute() {
        AbstractSorter(sorting);
    }

    private void AbstractSorter(Sorting sorting) {
        if (sorting == Sorting.BUBBLE_SORT) {
            boolean ok;
            do {
                ok = false;
                for (int i = 0; i < list.length; ++i) {
                    if (list[i] > list[i+1]) {
                        Functions.swap(list[i], list[i+1]);
                        ok = true;
                    }
                }
            } while (ok);
        } else {
            Functions.quickSort(list, 0, list.length);
        }
    }
}
