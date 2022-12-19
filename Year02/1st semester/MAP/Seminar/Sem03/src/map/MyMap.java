package map;

import domain.Student;

import java.util.*;

public class MyMap {
    private Map<Integer, List<Student>> map;

    public MyMap() {
        map = new TreeMap<>();
    }
    
    public void add(Student student) {
        Integer media = student.getMediaRotunjita();
        List<Student> lista = map.get(media);
        if (lista == null) {
            lista = new ArrayList<Student>();
            map.put(media, lista);
        }
        lista.add(student);
    }

    public void printAll() {
        for (Map.Entry<Integer, List<Student>> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries() {
        return map.entrySet();
    }
}
