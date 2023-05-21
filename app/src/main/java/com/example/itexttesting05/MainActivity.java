package com.example.itexttesting05;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editTextName,editTextNumber,qty1,qty2;
    Spinner spinner1,spinner2;
    Button buttonSavePrint,buttonOldPrint;
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    String [] itemList;
    int [] itemPrice;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectAssignment();
        callOnClickListner();
    }

    private void callOnClickListner() {
        buttonSavePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String number = editTextNumber.getText().toString();
                String item1 = spinner1.getSelectedItem().toString();
                int itemQty1 = Integer.parseInt(String.valueOf(qty1.getText()));
                int itemAmount1 = itemQty1*itemPrice[spinner1.getSelectedItemPosition()];

                String item2 = spinner2.getSelectedItem().toString();
                int itemQty2 = Integer.parseInt(String.valueOf(qty2.getText()));
                int itemAmount2 = itemQty2*itemPrice[spinner2.getSelectedItemPosition()];

                Date date = new Date();

                myHelper.insert(name,number, date.getTime(), item1,itemQty1,itemAmount1,item2,itemQty2,itemAmount2);

                Cursor cursor = sqLiteDatabase.rawQuery("select * from myTable",null);
                cursor.move(cursor.getCount());
                try {
                    new PrintPDF(cursor.getInt(0),name,number,date.getTime(),item1,itemQty1,itemAmount1,item2,itemQty2,itemAmount2).getPDF();
                    Toast.makeText(MainActivity.this, "pdf create", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                   e.printStackTrace();
                }
            }
        });
    }

    private void ObjectAssignment() {


        editTextName = findViewById(R.id.editTextName);
        editTextNumber = findViewById(R.id.editTextNumber);
        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 =findViewById(R.id.spinner2);
        buttonSavePrint = findViewById(R.id.buttonSavePrint);
        buttonOldPrint =findViewById(R.id.buttonPrintOld);

        itemList = new String[]{"Danishes","Macaroons","Cannoli","Tarts","Croissants"};
        itemPrice = new int[]{100,220,120,150,150};

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemList);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        myHelper = new MyHelper(MainActivity.this);
        sqLiteDatabase = myHelper.getWritableDatabase();

    }
}