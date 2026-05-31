package com.example.trabajopracticoinmobiliaria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Llamada extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:2664553747"));
        context.startActivity(i);
        Toast.makeText(context, "Llamando Home Seller", Toast.LENGTH_LONG).show();
    }
}
