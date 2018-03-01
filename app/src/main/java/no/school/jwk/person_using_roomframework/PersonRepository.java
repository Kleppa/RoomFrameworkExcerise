package no.school.jwk.person_using_roomframework;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;


/**
 * Created by Kleppa on 23/02/2018.
 */

public class PersonRepository {
    private final MainActivity mainActivity;
    private AppDatabase db;
    private PersonDao dao;
    private List<Person> personList;
    private Person userToGet;
    private boolean userIsFound;

    public PersonRepository(Application application, MainActivity mainActivity) {

        db = Room.databaseBuilder(application,
                AppDatabase.class, "database-name").build();
        dao = db.personDao();

        this.mainActivity = mainActivity;

    }

    public void addPerson(Person person) {

        new insertAsyncTask(dao).execute(person);
    }

    public List<Person> getUsersFromDb() {
        new getAllPersons(personList, dao, this).execute();
        return personList;
    }

    public void dropFromDatabase(Person... person) {
        new DropContent(dao).execute(person);
    }
    public List<Person> getPersons() {
        return personList;
    }

    void setPersonList(List<Person> personList) {

        mainActivity.setPersonList(personList);
        this.personList = personList;
    }

    public Person findPersonByName(String firstname, String secondname) {
        new FindByName(dao, this).execute(firstname, secondname);

        return this.userToGet;
    }

    public void updatePerson(Person p) {
        new UpdateContent(dao).execute(p);
    }

    public void setUserToGet(Person userToGet) {
        userIsFound=true;
        this.userToGet = userToGet;

    }
    public Person getPerson(){

        if (userIsFound) {

            userIsFound = false;
            Person tmp = userToGet;
            userToGet = null;
            return tmp;
        }
        Log.d("No Person Was Loaded by findPersonByNumber", "getPerson() returned nuull ");
        return null;
    }



    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao mAsyncTaskDao;

        insertAsyncTask(PersonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            for (Person param : params) {
                mAsyncTaskDao.insertAll(param);
            }
            return null;
        }
    }

    private static class getAllPersons extends AsyncTask<Void, Void, Void> {

        private PersonDao mAsyncTasksDao;
        private List<Person> personList;
        private PersonRepository personRepository;

        getAllPersons(List<Person> personList, PersonDao personDao, PersonRepository personRepository) {

            this.personList = personList;
            this.mAsyncTasksDao = personDao;
            this.personRepository = personRepository;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            personList = mAsyncTasksDao.getAll();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("Done", "Async operasjon ferdig");
            //når prosessen har kjørt ferdig så skal følgende skje
            resultFromAsyncGetOperation(personList);
        }

        private void resultFromAsyncGetOperation(List<Person> personList) {

            Log.d("result", personList.toString());

            personRepository.setPersonList(personList);

        }

    }

    private static class DropContent extends AsyncTask<Person, Void, Void> {
        private final PersonDao personDao;

        public DropContent(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(final Person... person) {

            for (Person personObj : person) {
                personDao.delete(personObj);
            }

            Log.d("Async", "doInBackground: task is done!");
            return null;
        }
    }

    private static class UpdateContent extends AsyncTask<Person, Void, Void> {

        private PersonDao personDao;

        public UpdateContent(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            for (Person personObject : people) {
                personDao.update(personObject);
            }
            return null;
        }
    }

    private static class FindByName extends AsyncTask<String, Void, Void> {

        private PersonDao dao;
        private PersonRepository personRepository;

        public FindByName(PersonDao dao, PersonRepository personRepository) {
            this.dao = dao;
            this.personRepository = personRepository;
        }

        @Override
        protected Void doInBackground(String... name) {
            personRepository.setUserToGet(dao.findByName(name[0], name[1]));

            return null;
        }
    }
}
