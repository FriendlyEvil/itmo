package ru.ifmo.rain.krivopaltsev.student;


import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentGroupQuery {
    private static final Comparator<Student> NAME_COMPARE = Comparator.comparing(Student::getLastName).
            thenComparing(Student::getFirstName).
            thenComparing(Student::compareTo);

    private <R extends Collection<String>> R mapStudentsToCollection(List<Student> students,
                                                                     Function<Student, String> mapFunction,
                                                                     Collector<String, ?, R> coll) {
        return students.stream().map(mapFunction).collect(coll);
    }


    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapStudentsToCollection(students, Student::getFirstName, Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapStudentsToCollection(students, Student::getLastName, Collectors.toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return mapStudentsToCollection(students, Student::getGroup, Collectors.toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapStudentsToCollection(students, student -> student.getFirstName() + " " + student.getLastName(), Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mapStudentsToCollection(students, Student::getFirstName, Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    private List<Student> sortStudents(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudents(students, Student::compareTo);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudents(students, NAME_COMPARE);
    }

    private <R> R filterAndSortByName(Collection<Student> students, Predicate<Student> predicate, Collector<Student, ?, R> coll) {
        return students.stream().filter(predicate).sorted(NAME_COMPARE).collect(coll);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return filterAndSortByName(students, student -> name.equals(student.getFirstName()), Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return filterAndSortByName(students, student -> name.equals(student.getLastName()), Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return filterAndSortByName(students, student -> group.equals(student.getGroup()), Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return filterAndSortByName(students, student -> group.equals(student.getGroup()),
                Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }

    private Stream<Map.Entry<String, List<Student>>> getGroupMapStream(Collection<Student> students) {
        return students.stream().collect(Collectors.groupingBy(Student::getGroup, Collectors.toList())).entrySet().stream();
    }

    private List<Group> getGroup(Collection<Student> students, Function<Collection<Student>, List<Student>> function) {
        return getGroupMapStream(students).map(e -> new Group(e.getKey(), function.apply(e.getValue()))).
                sorted(Comparator.comparing(Group::getName)).
                collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroup(students, this::sortStudentsByName);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroup(students, this::sortStudentsById);
    }

    private String getLargest(Collection<Student> students, ToIntFunction<List<Student>> function) {
        return getGroupMapStream(students).max(Comparator.comparingInt(
                (Map.Entry<String, List<Student>> g) -> function.applyAsInt(g.getValue())).
                thenComparing(Map.Entry::getKey, Collections.reverseOrder(String::compareTo))).
                map(Map.Entry::getKey).orElse("");
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getLargest(students, List::size);
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getLargest(students, s -> getDistinctFirstNames(s).size());
    }
}
