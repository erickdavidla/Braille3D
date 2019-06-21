package ph.edu.mapua.braille3d.Others;

import java.io.Serializable;

public class Assessment implements Serializable{

    public String student;
    public String teacher;
    public String exercise;
    public String duration;
    public int item1;
    public int item2;
    public int item3;
    public int item4;
    public int item5;
    public int item6;
    public int item7;
    public int item8;
    public int item9;
    public int item10;

    public Assessment() {

    }

    public Assessment(String student, String teacher, String exercise, String duration, int item1, int item2,
                           int item3, int item4, int item5) {
        this.student = student;
        this.teacher = teacher;
        this.exercise = exercise;
        this.duration = duration;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
    }

    public Assessment(String student, String teacher, String exercise, String duration, int item1, int item2,
                int item3, int item4, int item5, int item6, int item7, int item8, int item9, int item10) {
        this.student = student;
        this.teacher = teacher;
        this.exercise = exercise;
        this.duration = duration;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.item7 = item7;
        this.item8 = item8;
        this.item9 = item9;
        this.item10 = item10;
    }
}
