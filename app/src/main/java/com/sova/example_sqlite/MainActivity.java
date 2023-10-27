package com.sova.example_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etContact, etBDay;
    Button btnInsert, btnUpdate, btnDelete, btnViewAll, btnClear, btnView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etBDay = findViewById(R.id.etBirthDate);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnView = findViewById(R.id.btnView);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = etName.getText().toString();
                if (nameTXT.isEmpty()) { Toast.makeText(MainActivity.this,
                        "Fill data to add",
                        Toast.LENGTH_SHORT).show();
                    return;}
                String contactTXT = etContact.getText().toString();
                String bDayTXT = etBDay.getText().toString();
                try {
                    Boolean checkInsert = dbHelper.insertUserDetails(
                            nameTXT,
                            contactTXT,
                            bDayTXT
                    );
                    if (checkInsert == true)
                        Toast.makeText(MainActivity.this,
                                "New data inserted",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "New data not inserted",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT = etId.getText().toString();
                String nameTXT = etName.getText().toString();
                String contactTXT = etContact.getText().toString();
                String bDayTXT = etBDay.getText().toString();
                try {
                    Boolean checkUpdate = dbHelper.updateUserDetails(
                            idTXT,
                            nameTXT,
                            contactTXT,
                            bDayTXT
                    );
                    if (checkUpdate == true)
                        Toast.makeText(MainActivity.this,
                                "Data updated",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "Data not updated",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT = etId.getText().toString();
                try {
                    Boolean checkDelete = dbHelper.deleteUserDetails(
                            idTXT
                    );
                    if (checkDelete == true)
                        Toast.makeText(MainActivity.this,
                                "Data deleted",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "Data not deleted",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etId.setText("");
                etName.setText("");
                etContact.setText("");
                etBDay.setText("");
            }
        });
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = dbHelper.getData();
                if(result.getCount()==0){
                    Toast.makeText(MainActivity.this,
                            "No data",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {

                    buffer.append("ID :" + result.getString(0) + "\n");
                    buffer.append("Name :" + result.getString(1) + "\n");
                    buffer.append("Contact :" + result.getString(2) + "\n");
                    buffer.append("BirthDate :" + result.getString(3) + "\n");

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Users");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String id = etId.getText().toString();
                    Cursor result = dbHelper.getData(id);
                    if (result.getCount()==0){
                    Toast.makeText(MainActivity.this,
                            "No data",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                while (result.moveToNext()) {

                   etName.setText(result.getString(1) + "\n");
                   etContact.setText(result.getString(2) + "\n");
                   etBDay.setText(result.getString(3) + "\n");

                }
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }}

        });
    }
}
