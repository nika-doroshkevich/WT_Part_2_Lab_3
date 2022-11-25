package by.nika_doroshkevich.model;

import java.util.HashMap;
import java.util.Objects;

public class File {

    private int id;
    private Student student;
    private HashMap<Subject, Integer> progress;
    private static int defaultId = 1;

    public File() {
    }

    public File(Student student) {
        this.student = student;
        this.progress = new HashMap<>();
        this.id = defaultId++;
    }

    public File(int id, Student student, HashMap<Subject, Integer> progress) {
        this.id = id;
        this.student = student;
        this.progress = progress;
    }

    public File(Student student, HashMap<Subject, Integer> progress) {
        this.id = defaultId++;
        this.student = student;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public HashMap<Subject, Integer> getProgress() {
        return progress;
    }

    public void setProgress(HashMap<Subject, Integer> progress) {
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id
                && Objects.equals(student, file.student)
                && Objects.equals(progress, file.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, progress);
    }

    @Override
    public String toString() {
        return "File id: " + id + "\n" +
                student + "\n" +
                (progress.isEmpty() ? "" : "Progress: \n") +
                (progress.containsKey(Subject.MATH) ? "- " + Subject.MATH + ": " + progress.get(Subject.MATH) + "\n" : "") +
                (progress.containsKey(Subject.PHYSICS) ? "- " + Subject.PHYSICS + ": " + progress.get(Subject.PHYSICS) + "\n" : "") +
                (progress.containsKey(Subject.LITERATURE) ? "- " + Subject.LITERATURE + ": " + progress.get(Subject.LITERATURE) + "\n" : "") +
                (progress.containsKey(Subject.GEOGRAPHY) ? "- " + Subject.GEOGRAPHY + ": " + progress.get(Subject.GEOGRAPHY) + "\n" : "") +
                (progress.containsKey(Subject.ENGLISH) ? "- " + Subject.ENGLISH + ": " + progress.get(Subject.ENGLISH) + "\n" : "");
    }
}
