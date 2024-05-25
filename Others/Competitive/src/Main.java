import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static void getCoordinates(int rows, int cols, int position, ArrayList<Integer> arr) {
        int x = position/cols + 1;
        int y = position % cols;
        if (y == 0) {
            y = cols;
            --x;
        }
        arr.add(x);
        arr.add(y);
    }

    private static int getManhattan(int rows, int cols, int act, int old) {
        ArrayList<Integer> arrAct = new ArrayList<>();
        ArrayList<Integer> arrOld = new ArrayList<>();
        getCoordinates(rows, cols, act, arrAct);
        getCoordinates(rows, cols, old, arrOld);
        return Math.abs(arrAct.get(0) - arrOld.get(0)) + Math.abs(arrAct.get(1) - arrOld.get(1));
    }

    private static void update(String direction, ArrayList<Integer> arr) {
        int x = arr.get(0);
        int y = arr.get(1);
        arr.clear();
        if (direction.equals("W")) {
            y -= 1;
        }
        if (direction.equals("S")) {
            x += 1;
        }
        if (direction.equals("E")) {
            y += 1;
        }
        if (direction.equals("N")) {
            x -= 1;
        }
        arr.add(x);
        arr.add(y);
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("level3/level3-0.in");
        List<String> lines = Files.readAllLines(path);
        String all = lines.get(0);
        String[] sep = all.split(" ");
        int rows = Integer.parseInt(sep[0]);
        int cols = Integer.parseInt(sep[1]);
        int nrOfPoints1 = Integer.parseInt(sep[2]);
        int nrOfPoints = 0;
        //System.out.println(nrOfPoints1);
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        Map<Integer, Integer> res = new HashMap<>();
        for (int i = 3, j = 0; j < nrOfPoints1; ++j, i += 2) {
            int act = Integer.parseInt(sep[i]);
            int col = Integer.parseInt(sep[1 + i]);
            //System.out.println(act + " " + col);
            if (map.get(col) == null) {
                ArrayList<Integer> tmp = new ArrayList<>();
                tmp.add(act);
                map.put(col, tmp);
            } else {
                ArrayList<Integer> tmp = map.get(col);
                tmp.add(act);
                map.replace(col, tmp);
            }
            nrOfPoints = i;
        }
        nrOfPoints += 2;
        int nrOfPaths = Integer.parseInt(sep[nrOfPoints]);
        int color = Integer.parseInt(sep[nrOfPoints + 1]);
        int starting = Integer.parseInt(sep[nrOfPoints + 2]);
        //System.out.println(nrOfPaths + " " + color + " " + starting);
        ArrayList<Integer> act = map.get(color);
        if (starting != act.get(0) && starting != act.get(1)) {
            System.out.println("-1 1");
            return;
        }

        int ending = -1;
        if (starting == act.get(0)) {
            ending = act.get(1);
        } else {
            ending = act.get(0);
        }

        ArrayList<Integer> start = new ArrayList<>();
        getCoordinates(rows, cols, starting, start);

        ArrayList<Integer> finish = new ArrayList<>();
        getCoordinates(rows, cols, ending, finish);

        int pathLen = Integer.parseInt(sep[nrOfPoints + 3]);

        for (int i = nrOfPoints + 4; i < sep.length; ++i) {
            update(sep[i], start);
        }

        if (start.equals(finish)) {
            System.out.println("1 " + pathLen);
        } else {
            System.out.println("-1 " + pathLen);
        }


    }
}