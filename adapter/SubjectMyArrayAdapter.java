package com.mohit.college.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohit.college.R;
import com.mohit.college.activity.Branch.EditBranch;
import com.mohit.college.activity.Branch.ViewBranch;
import com.mohit.college.activity.Subject.EditSubject;
import com.mohit.college.activity.Subject.ViewSubject;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.SubjectTable;
import com.mohit.college.pojoclass.PojoSubject;

import java.util.Vector;

/**
 * Created by Mohit on 6/6/2016.
 */

public class SubjectMyArrayAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    Vector<PojoSubject> values;


    Vector<PojoSubject> mStringFilterList;
    ValueFilter valueFilter;


    public SubjectMyArrayAdapter(Context context, Vector<PojoSubject> values) {
      //  super(context, R.layout.subjectmylist, values);
        this.context = context;
        this.values = values;
        this.mStringFilterList=values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return values.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.subjectmylist, null, false);

        final PojoSubject contact = values.get(position);

        TextView imgView = (TextView) rowView.findViewById(R.id.img);
        imgView.setText(contact.getId());

        GradientDrawable d = (GradientDrawable) imgView.getBackground();
        int red = (int)(Math.random()*255);
        int blue = (int)(Math.random()*255);
        int green = (int)(Math.random() * 255);

        d.setColor(Color.rgb(red, green, blue));

        TextView nameView = (TextView) rowView.findViewById(R.id.subjectname);
        nameView.setText(contact.getName());

        TextView nameView1 = (TextView) rowView.findViewById(R.id.subjectshortname);
        nameView1.setText(contact.getSname());

        TextView nameView2 = (TextView) rowView.findViewById(R.id.subjectcode);
        nameView2.setText(contact.getCode());


        ImageView img = (ImageView) rowView.findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditSubject.class);
                i.putExtra("sname", contact.getName());
                i.putExtra("ssname", contact.getSname());
                i.putExtra("scode", contact.getCode());
                i.putExtra("sid", contact.getId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });


        img = (ImageView) rowView.findViewById(R.id.delete);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteHelper helper = new MySQLiteHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                long id = SubjectTable.delete(db, contact.getId());
                if (id != -1) {
                    Intent i= new Intent(context, ViewSubject.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    context.startActivity(i);

                }



            }
        });

        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                Vector<PojoSubject> filterList = new Vector<PojoSubject>();

                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        PojoSubject country = new PojoSubject(mStringFilterList.get(i)
                                .getName(), mStringFilterList.get(i)
                                .getId(), mStringFilterList.get(i)
                                .getSname(), mStringFilterList.get(i)
                                .getCode());

                        filterList.add(country);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            values = (Vector<PojoSubject>) results.values;
            notifyDataSetChanged();
        }

    }

}
