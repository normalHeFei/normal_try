package com.normal.trysth.objs;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hefei on 2017/7/25.
 */
public class Student {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private String name;
    private Integer score;
    private Double height;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public Student(String name, Integer score, Double height) {
        this.name = name;
        this.score = score;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student someObj = (Student) o;

        if (name != null ? !name.equals(someObj.name) : someObj.name != null) return false;
        return score != null ? score.equals(someObj.score) : someObj.score == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public static List<Student> tenRandomStuds() {
        List<Student> result = new ArrayList<>();
        return result;
    }
}
