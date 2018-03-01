package no.school.jwk.person_using_roomframework;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Kleppa on 23/02/2018.
 */

@Dao
public interface PersonDao {

    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("select * from person where uid Like :uid")
    List<Person> loadAllByIds(int[] uid);

    @Query("SELECT * FROM person WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Person findByName(String first, String last);

    @Insert
    void insertAll(Person... person);

    @Delete
    void delete(Person... person);

    @Update(onConflict = REPLACE)
    void update(Person person);

}
