package ph.edu.mapua.braille3d.Others;

import java.io.Serializable;

public class ExampleWords implements Serializable {

    public String example1;
    public String example2;
    public String example3;
    public String example4;
    public String example5;

    public ExampleWords() {

    }


    public ExampleWords(String example1, String example2, String example3, String example4, String example5) {
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
        this.example4 = example4;
        this.example5 = example5;
    }
}
