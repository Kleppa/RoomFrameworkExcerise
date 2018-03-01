package no.school.jwk.person_using_roomframework;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import no.school.jwk.person_using_roomframework.Components.PersonArrayAdapter;

public class MainActivity extends AppCompatActivity {
    PersonRepository personRepository;
    private List<Person> personList;
    private PersonArrayAdapter personArrayAdapter;
    private ListView listview;
    private boolean listIsLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        personRepository = new PersonRepository(getApplication(), this);

        personRepository.getUsersFromDb();
        personRepository.addPerson(new Person("Jarand", "Waage Kleppa"));

    }


    public void setPersonList(@NonNull List<Person> personList) {

        Log.d("Whaddup", "setPersonList: " + personList.size());
        this.personList = personList;
        listIsLoaded=true;
        fillArrayAdapter();
      //  deleteDatabaseContent(this.personList);
    }

    private boolean deleteDatabaseContent(@NonNull List<Person> personList) {

        Person[] personArray = new Person[personList.size()];
        personArray= personList.toArray(personArray);
        Log.d("Before drop", "deleteDatabaseContent: Is done");
       personRepository.dropFromDatabase((Person[]) personArray);
        Log.d("Done", "deleteDatabaseContent: Is done");
        return true;
    }
    public boolean updatePerson(@NonNull Person p){
        if(listIsLoaded){
            personRepository.updatePerson(p);
            return true;
        }
        return false;
    }

    private void fillArrayAdapter() {
        this.personList.forEach(p -> Log.d("Hello", "person: " + p.getFirstName()));

        personArrayAdapter = new PersonArrayAdapter(MainActivity.this, R.layout.list_item, personList);
        listview = (ListView) findViewById(R.id.listViewPerson);

        personArrayAdapter.notifyDataSetChanged();
        listview.setAdapter(personArrayAdapter);


    }
}
