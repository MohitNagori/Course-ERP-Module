package com.mohit.college.extra;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.mohit.college.activity.Login;

/**
 * Created by Mohit on 6/4/2016.
 */
public class Mythread  extends Thread {
   Context context;

    Mythread(Context context, ImageView image)
   {
       this.context = context;
   }
   public  void run() {
       try {
           sleep(845);
           Intent i = new Intent(context, Login.class);
           i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           context.startActivity(i);


       } catch (InterruptedException e) {
           e.printStackTrace();
       }

   }


}
