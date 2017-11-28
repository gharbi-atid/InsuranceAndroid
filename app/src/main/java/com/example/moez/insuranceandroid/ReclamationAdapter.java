package com.example.moez.insuranceandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ReclamationAdapter extends ArrayAdapter<Reclamation> {

    public ReclamationAdapter(Context context, int resource, List<Reclamation> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Reclamation c = getItem(position);
        if (v == null) {
            final LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_reclamation, null);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences reportingPref = getContext().getSharedPreferences("reporting_app", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = reportingPref.edit();

                    prefEditor.putString("description", getItem(position).getDescription());
                    prefEditor.putString("subject", getItem(position).getSubject());

                    //prefEditor.putString("date", getItem(position).getEndDate());
                    prefEditor.commit();

                   // Intent i = new Intent(getContext(), AfficheActivity.class);
                   // getContext().startActivity(i);
                }
            });

        }


        if (c != null) {

            TextView description = v.findViewById(R.id.description);
            description.setText(c.getDescription());

            TextView subject = v.findViewById(R.id.subject);
            subject.setText(c.getSubject());
        }


        return v;
    }

}
