package no.school.jwk.person_using_roomframework;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.IdRes;

/**
 * Created by Kleppa on 23/02/2018.
 */
@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    private Long uid;
    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;
    public Person(){

    }
    public Person(String firstName, String secondname) {
        this.firstName = firstName;
        this.lastName = secondname;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
