package com.example.administrator.googlenavigationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2018-04-06.
 */

public class GetFloatingIconClick extends BroadcastReceiver {

    Context context;

    public GetFloatingIconClick(){

    }

    public GetFloatingIconClick(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//            Intent selfIntent = new Intent(this, MainActivity.class);
//            selfIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(selfIntent);
        Intent newIntent = intent;
        String msg = newIntent.getStringExtra("msg");
        Log.d("Broadcast",msg);
        Intent selfIntent = new Intent(context, MainActivity.class);
        selfIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(selfIntent);
    }
}
