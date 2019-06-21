package ph.edu.mapua.braille3d.Others;

import java.io.Serializable;

public class User implements Serializable {

    public String email;
    public String full_name;
    public String home_add;
    public String phone_num;
    public String role;
    public String teacher;
    public String status;

    public User() {

    }

    public User(String email, String full_name, String home_add, String phone_num, String role, String teacher, String status) {
        this.email = email;
        this.full_name = full_name;
        this.home_add = home_add;
        this.phone_num = phone_num;
        this.role = role;
        this.teacher = teacher;
        this.status = status;
    }

}
