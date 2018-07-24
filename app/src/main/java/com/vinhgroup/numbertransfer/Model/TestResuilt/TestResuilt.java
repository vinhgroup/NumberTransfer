package com.vinhgroup.numbertransfer.Model.TestResuilt;

/**
 * Created by Vinh on 6/5/2018.
 */

public class TestResuilt {
    boolean Select;
    String Id;
    String Name;
    String NumberPhone;
    String NumberPhoneAfterChange;
    String Email;

    public TestResuilt(String id, String name, String numberPhone, String numberPhoneAfterChange, String email, boolean select) {
        Id = id;
        Name = name;
        NumberPhone = numberPhone;
        NumberPhoneAfterChange = numberPhoneAfterChange;
        Email = email;
        Select = select;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumberPhone() {
        return NumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        NumberPhone = numberPhone;
    }

    public String getNumberPhoneAfterChange() {
        return NumberPhoneAfterChange;
    }

    public void setNumberPhoneAfterChange(String numberPhoneAfterChange) {
        NumberPhoneAfterChange = numberPhoneAfterChange;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isSelect() {
        return Select;
    }

    public void setSelect(boolean select) {
        Select = select;
    }
}
