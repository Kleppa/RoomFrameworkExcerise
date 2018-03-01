package no.school.jwk.person_using_roomframework.Components;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import no.school.jwk.person_using_roomframework.Person;
import no.school.jwk.person_using_roomframework.R;

/**
 * Created by Kleppa on 01/03/2018.
 */

public class PersonArrayAdapter extends ArrayAdapter<Person> {
    @NonNull
    private  Context mContext;
    private  int resource;
    @NonNull
    private  Person[] objects;
    private  int textViewResourceId;
    private  List<Person> objlist;

    public PersonArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.resource = resource;
    }

    public PersonArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.mContext = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
    }

    public PersonArrayAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, (Person[]) objects);
        this.mContext = context;
        this.resource = resource;
        this.objects = (Person[]) objects;
    }

    public PersonArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, (Person[]) objects);
    }

    public PersonArrayAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.objlist = objects;
    }

    public PersonArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Person> objlist) {
        super(context, resource, textViewResourceId, objlist);
        this.mContext = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objlist = objlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)

            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Person currentPerson = objlist.get(position);



        TextView textView_firstname = (TextView) listItem.findViewById(R.id.textView_firstname);
        textView_firstname.setText(currentPerson.getFirstName());


        TextView textView_lastname = (TextView) listItem.findViewById(R.id.textView_lastname);

        textView_lastname.setText(currentPerson.getLastName());



        return listItem;
    }

}

