import domain.Student;
import map.MyMap;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Student> hashStud = new HashSet<>();
        Student  s1= new    Student("Dan", 4.5f);
        Student  s2= new    Student("Ana", 8.5f);
        Student  s3= new    Student("Daniel", 4.5f);
        Student  s4= new Student("Maria", 4.8f);
        Student  s5= new Student("Bogdan", 5.6f);
        hashStud.addAll(Arrays.asList(s1, s2, s3));
        System.out.println("Hash set");
        for (Student student : hashStud) {
            System.out.println(student);
        }

        System.out.println("\nTree set");
        TreeSet<Student> treeStud = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getNume().compareTo(o2.getNume());
            }
        });

        treeStud.addAll(Arrays.asList(s1, s2, s3));

        for (Student student : treeStud) {
            System.out.println(student);
        }

        System.out.println("\nHash map");
        HashMap<String, Student> hashmap = new HashMap<>();
        hashmap.put(s1.getNume(),s1);
        hashmap.put(s2.getNume(),s2);
        hashmap.put(s3.getNume(),s3);

        for (Map.Entry<String, Student> pair : hashmap.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }

        System.out.println("\nTree map");
        TreeMap<String, Student> treemap = new TreeMap<>();
        treemap.put(s1.getNume(),s1);
        treemap.put(s2.getNume(),s2);
        treemap.put(s3.getNume(),s3);

        for (Map.Entry<String, Student> pair : treemap.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }

        System.out.println("\nMy Map");

        MyMap map = new MyMap();
        map.add(s1);
        map.add(s2);
        map.add(s3);
        map.add(s4);
        map.add(s5);
        map.printAll();

        System.out.println("\nSorted students");
        Set<Map.Entry<Integer, List<Student>>> entries = map.getEntries();
        for (Map.Entry<Integer, List<Student>> entry : entries) {
            List<Student> studentList = entry.getValue();
            studentList.sort(new NameComparator());
            System.out.println(entry.getKey() + " " + studentList);
        }
    }

    static class NameComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getNume().compareTo(o2.getNume());
        }
    }
}