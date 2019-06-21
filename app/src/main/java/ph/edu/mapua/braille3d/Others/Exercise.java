package ph.edu.mapua.braille3d.Others;

import java.io.Serializable;

public class Exercise implements Serializable {

    public String name;
    public String difficulty;
    public String assigned;
    public String owner;
    public String status;
    public Boolean module1, module2, module3, num1, num2;
    public String item1, item2, item3, item4, item5, item6, item7,
                item8, item9, item10;

    public Exercise() {

    }

    public Exercise(String name, String difficulty, String assigned, String owner, String status, String item1,
                    String item2, String item3, String item4, String item5) {
        this.name = name;
        this.difficulty = difficulty;
        this.assigned = assigned;
        this.owner = owner;
        this.status = status;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.module1 = false;
        this.module2 = false;
        this.module3 = false;
        this.num1 = false;
        this.num2 = false;
    }

    public Exercise(String name, String difficulty, String assigned, String owner, String status, String item1,
                    String item2, String item3, String item4, String item5, String item6,
                    String item7, String item8, String item9, String item10) {
        this.name = name;
        this.difficulty = difficulty;
        this.assigned = assigned;
        this.owner = owner;
        this.status = status;
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
        this.module1 = false;
        this.module2 = false;
        this.module3 = false;
        this.num1 = false;
        this.num2 = false;
    }
}
