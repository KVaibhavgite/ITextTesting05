package com.example.itexttesting05;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

public class OldPrintActivity extends AppCompatActivity {

    Button printPdf;
    EditText editText;
    DataTable dataTable;
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_print);

        printPdf = findViewById(R.id.oldPrintBtn);
        editText = findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table);
        myHelper = new MyHelper(this);
        sqLiteDatabase = myHelper.getWritableDatabase();


        DataTableHeader header = new DataTableHeader.Builder()
                .item("Invoice No", 5)
                .item("Customer Name", 5)
                .item("Date", 5)
                .item("Date", 5)
                .build();

        String[] columns = {"invoiceNo","CustomerName","date"};
        Cursor cursor = sqLiteDatabase.query("myTable",columns,null,null,null,null,null);

        ArrayList<DataTableRow> rows = new ArrayList<DataTableRow>();
        // define 200 fake rows for table
        for(int i=0;i<cursor.getCount();i++) {
            cursor.moveToNext();

            DataTableRow row = new DataTableRow.Builder()
                    .value(String.valueOf(cursor.getInt(0)))
                    .value(cursor.getString(1))
                    .value(dataTable.format(cursor.getLong(2)))
            .build();
            rows.add(row);
        }

        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);

        printSelectedInvoice();


    }

    private void printSelectedInvoice() {

        printPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int invoiceNoForPrint = Integer.parseInt(editText.getText().toString());
                Cursor cursor = sqLiteDatabase.rawQuery("Select * from myTable where invoiceNo ="+invoiceNoForPrint,null);
                cursor.moveToNext();
                try{
                    new PrintPDF(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)).getPDF();
                    Toast.makeText(OldPrintActivity.this, "pdf Created", Toast.LENGTH_SHORT).show();

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }
}