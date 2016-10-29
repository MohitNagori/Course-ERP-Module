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
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.Course.EditCourse;
import com.mohit.college.activity.Course.ViewCourse;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.CourseTable;
import com.mohit.college.pojoclass.PojoCourse;

import java.util.Vector;

/**
 * Created by Mohit on 6/6/2016.
 */

public class CourseMyArrayAdapter extends BaseAdapter implements Filterable{
    private final Context context;
    Vector<PojoCourse> values;

    Vector<PojoCourse> mStringFilterList;
    ValueFilter valueFilter;

    public CourseMyArrayAdapter(Context context, Vector<PojoCourse> values) {
     //   super(context, R.layout.coursemylist, values);
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
        View rowView = inflater.inflate(R.layout.coursemylist, null, false);

        final PojoCourse contact = values.get(position);

        TextView imgView = (TextView) rowView.findViewById(R.id.img);
        imgView.setText(contact.getId());

        GradientDrawable d = (GradientDrawable) imgView.getBackground();
        int red = (int)(Math.random()*255);
        int blue = (int)(Math.random()*255);
        int green = (int)(Math.random() * 255);

        d.setColor(Color.rgb(red, green, blue));

        TextView nameView = (TextView) rowView.findViewById(R.id.namecourse);
        nameView.setText(contact.getName());



        ImageView img = (ImageView) rowView.findViewById(R.id.editcourse);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditCourse.class);
                i.putExtra("course", contact.getName());
                i.putExtra("id", contact.getId());
                context.startActivity(i);
            }
        });

        img = (ImageView) rowView.findViewById(R.id.deletecourse);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteHelper helper = new MySQLiteHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                long id = CourseTable.delete(db,contact.getId());
                if (id != -1) {
                    Intent intent= new Intent(context, ViewCourse.class);
                    context.startActivity(intent);
                    ((ViewCourse)context).finish();
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

                Vector<PojoCourse> filterList = new Vector<PojoCourse>();

                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        PojoCourse country = new PojoCourse(mStringFilterList.get(i)
                                .getName(), mStringFilterList.get(i)
                                .getId());

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
            values = (Vector<PojoCourse>) results.values;
            notifyDataSetChanged();
        }

    }


}
