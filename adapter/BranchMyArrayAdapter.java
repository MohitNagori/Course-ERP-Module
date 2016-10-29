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
import com.mohit.college.activity.Course.ViewCourse;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.pojoclass.PojoBranch;

import java.util.Vector;

/**
 * Created by Mohit on 6/6/2016.
 */
public class BranchMyArrayAdapter extends BaseAdapter implements Filterable{
    private final Context context;
    Vector<PojoBranch> values;

    Vector<PojoBranch> mStringFilterList;
    ValueFilter valueFilter;


    public BranchMyArrayAdapter(Context context, Vector<PojoBranch> values) {
    //    super(context, R.layout.branchmylist, values);
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
        View rowView = inflater.inflate(R.layout.branchmylist, null, false);

        final PojoBranch contact = values.get(position);

        TextView imgView = (TextView) rowView.findViewById(R.id.img);
        imgView.setText(contact.getId());

        GradientDrawable d = (GradientDrawable) imgView.getBackground();
        int red = (int)(Math.random()*255);
        int blue = (int)(Math.random()*255);
        int green = (int)(Math.random() * 255);

        d.setColor(Color.rgb(red, green, blue));

        TextView nameView = (TextView) rowView.findViewById(R.id.namecourse);
        nameView.setText(contact.getName());


        TextView nameView1 = (TextView) rowView.findViewById(R.id.branchseats);
        nameView1.setText(contact.getSeats());



        ImageView img = (ImageView) rowView.findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditBranch.class);
                i.putExtra("branch", contact.getName());
                i.putExtra("seats",contact.getSeats());
                i.putExtra("id",contact.getId());
                i.putExtra("course",ViewBranch.s.getSelectedItem().toString());
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

                long id = BranchTable.delete(db, contact.getId());
                if (id != -1) {
                    Intent i= new Intent(context, ViewBranch.class);
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

                Vector<PojoBranch> filterList = new Vector<PojoBranch>();

                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        PojoBranch country = new PojoBranch(mStringFilterList.get(i)
                                .getName(), mStringFilterList.get(i)
                                .getId(), mStringFilterList.get(i)
                                .getSeats());

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
            values = (Vector<PojoBranch>) results.values;
            notifyDataSetChanged();
        }

    }


}
