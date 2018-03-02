package no.school.jwk.person_using_roomframework;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import no.school.jwk.person_using_roomframework.Components.PersonArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private PersonRepository personRepository;
    private List<Person> personList;
    private PersonArrayAdapter personArrayAdapter;
    private ListView listview;
    private boolean listIsLoaded;
    private final ArrayList<Button> btnList;
    private EditText inputField;

    public MainActivity() {

        btnList = new ArrayList<Button>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personRepository = new PersonRepository(getApplication(), this);
        inputField = findViewById(R.id.inputField);

        personRepository.loadUsersFromDatabase();

        String[] btnnames = {"add1", "delete2", "find3", "all4"};

        for (int i = 0; i < btnnames.length; i++) {
            btnList.add(findViewById(getResources().getIdentifier(btnnames[i], "id", getPackageName())));
        }

    }


    public void addPerson(View view) {
        String[] fullname = inputField.getText().toString().trim().split(" ");

        if ( fullname.length==0) {

            Toast.makeText(this, "Wrong format", Toast.LENGTH_SHORT).show();

        } else {

            Person tmp = new Person();

            StringBuilder stringBuilder = new StringBuilder();
            tmp.setFirstName(fullname[0]);
            for (int i = 1; i < fullname.length; i++) {
                stringBuilder.append(fullname[i]+" ");
            }
            tmp.setLastName(stringBuilder.toString());
            personRepository.addPerson(tmp);
            inputField.setText("");
            personRepository.loadUsersFromDatabase();
        }

    }

    public void setPersonList(@NonNull List<Person> personList) {

        Log.d("Whaddup", "setPersonList: " + personList.size());

        this.personList = personList;
        listIsLoaded = true;

        //  deleteDatabaseContent(this.personList);
    }

    private boolean deleteDatabaseContent(@NonNull List<Person> personList) {

        Person[] personArray = new Person[personList.size()];
        personArray = personList.toArray(personArray);

        Log.d("Before drop", "deleteDatabaseContent: Is done");

        personRepository.dropFromDatabase((Person[]) personArray);
        Log.d("Done", "deleteDatabaseContent: Is done");

        return true;
    }

    public boolean updatePerson(@NonNull Person p) {

        if (listIsLoaded) {
            personRepository.updatePerson(p);
            return true;
        }
        return false;
    }

    protected void fillArrayAdapter(List<Person> people) {

        this.personList.forEach(p -> Log.d("Hello", "person: " + p.getFirstName()));

        personArrayAdapter = new PersonArrayAdapter(MainActivity.this, R.layout.list_item, people);
        listview = (ListView) findViewById(R.id.listViewPerson);

        personArrayAdapter.notifyDataSetChanged();
        listview.setAdapter(personArrayAdapter);


    }
}
