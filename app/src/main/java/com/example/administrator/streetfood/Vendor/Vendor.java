package com.example.administrator.streetfood.Vendor;

public class Vendor {

    private final String TAG = "vendor";
    private int id;
    private String email, password, gender, birthdate, firstname, lastname;

    public Vendor() {
    }

    public Vendor(String email, String password, String gender, String birthdate, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getTAG() {
        return TAG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
