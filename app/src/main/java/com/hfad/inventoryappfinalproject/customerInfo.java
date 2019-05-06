package com.hfad.inventoryappfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class customerInfo extends AppCompatActivity {

    private ListView listView;
    private SQLiteOpenHelper openHelper;
    public SQLiteDatabase db;
    public ArrayList<String> listItem;
    public ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerinfo);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView = (ListView) findViewById(R.id.firstLineup);
        listView.setAdapter(adapter);
        listItem = new ArrayList<>();

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
    }

    final DatabaseHelper dbhelper = new DatabaseHelper(this);

    public void onTestClickEmployees(View view){
        SQLiteDatabase db = dbhelper.getReadableDatabase();

    }

    public void moveToCustomer(View view){
        Intent intent = new Intent(customerInfo.this, HomePageActivity.class); //can later change this to the homescreen activity
        //intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void moveToCart(View view) {
        Intent intent = new Intent(customerInfo.this, CartActivity.class); //can later change this to the homescreen activity
        //intent.putExtras(mBundle);
        startActivity(intent);
    }


    public void onClickLogout (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showEmployees(View view){
        adapter.clear();
        int i;
        String query = "SELECT * FROM EMPLOYEES";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];
        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = cursor.getString(1)+"\n "+ cursor.getString(2);
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void showCustomers(View view){
        adapter.clear();
        int i;
        String query = "SELECT * FROM CUSTOMERS";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];
        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = cursor.getString(1)+"\n "+ cursor.getString(2);
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void showOrders(View view){
        adapter.clear();
        int i;
        String query = "SELECT * FROM ORDERS";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];
        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Order number: "+ cursor.getString(0)+"\n Part Number: "+ cursor.getString(1)+" \nDate Arrived: "+ cursor.getString(2);
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void showOrderDetails(View view){
        adapter.clear();
        int i;
        String query = "SELECT * FROM ODETAILS";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];
        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Order number: "+ cursor.getString(0)+"\n Part Number: "+ cursor.getString(1)+" \nQTY: "+ cursor.getString(2)+ " \nDate Ordered: "+ cursor.getString(3);
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }


    public void showParts(View view){
        adapter.clear();
        int i;
        String query = "SELECT * FROM PARTS";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Part number: "+ cursor.getString(0)+"\n Name: "+ cursor.getString(1)+"\n QOH: "+ cursor.getString(2)+" \nPrice: "+ cursor.getString(3);
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void averageCostParts(View view) {
        adapter.clear();
        int i;
        String query = "SELECT e_id,name,phonenum,email FROM Customers JOIN ORDERS on CUSTOMERS.id = ORDERS.customer_no ;";
        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Employee ID:: "+ cursor.getString(0)
                    +"\n Name: "+ cursor.getString(1)
                    +"\n Phonenum: "+ cursor.getString(2)
                    +" \nEmail: "+ cursor.getString(3)
            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void selfJoin(View view) {
        adapter.clear();
        int i;
        String query = "SELECT c1.name, c2.name, c1.zipcode FROM customers c1 INNER JOIN customers c2 ON c2.zipcode = c1.zipcode WHERE  c1.id <> c2.id;";
        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Name 1:: "+ cursor.getString(0)
                    +"\n Name 2: "+ cursor.getString(1)
                    +"\n Zipcode: "+ cursor.getString(2)

            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void likeOP(View view) {
        adapter.clear();
        int i;
        String query = "SELECT * FROM parts WHERE parts_name LIKE \"%NA%\"";
        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "PartNum: "+ cursor.getString(0)
                    +"\n PartName: "+ cursor.getString(1)
                    +"\n Quantity: "+ cursor.getString(2)
                    +"\n Price: "+ cursor.getString(3)

            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void aggregate2(View view) {
        adapter.clear();
        int i;
        String query =""
                + "SELECT *, MIN(parts_price) AS \"Lowest price part\" "
                + "FROM parts;";
        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Part number: "+ cursor.getString(0)
                    +"\n Part name: "+ cursor.getString(1)
                    +"\n Part price: "+ cursor.getString(3)

            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }
    public void aggregate1(View view) {
        adapter.clear();
        int i;
        String query =""
                + "SELECT *, SUM(parts_price * parts_quantity) AS \"Parts above $10\" "
                + "FROM parts "
                + "GROUP BY parts_price "
                + "HAVING SUM(parts_price) > 10;";
        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "Part number: "+ cursor.getString(0)
                    +"\n Part name: "+ cursor.getString(1)
                    +"\n Part price: "+ cursor.getString(3)

            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }

    public void threeTableJoin(View view) {
        adapter.clear();
        int i;
        String query = "SELECT CUSTOMERS.id, NAME, phonenum, zipcode,email, order_no,part_no,e_id,customer_no,username,password FROM customers\n" +
                "JOIN (SELECT order_no, part_no,e_id,customer_no,id,username,password FROM orders JOIN employees ON e_id = e_id WHERE 1) as\n" +
                "NEWTABLE ON customer_no = customers.id;";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "id: "+ cursor.getString(0)
                    +"\n Name: "+ cursor.getString(1)
                    +"\n phoneNum: "+ cursor.getString(2)
                    +"\n zipcode: "+ cursor.getString(3)
                    +"\n email: "+ cursor.getString(4)
                    +"\n orderNum: "+ cursor.getString(5)
                    +"\n part_no: "+ cursor.getString(6)
                    +"\n e_id: "+ cursor.getString(7)
                    +"\n customer_no: "+ cursor.getString(8)
                    +"\n username: "+ cursor.getString(9)
                    +"\n password: "+ cursor.getString(10)

            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }
    public void subQuery(View view) {
        adapter.clear();
        int i;
        String query = "SELECT parts.parts_name,parts.parts_price,parts_quantity\n" +
                "FROM parts \n" +
                "WHERE parts.parts_number IN (SELECT orders.part_no\n" +
                "\t\t\tFROM orders\n" +
                "\t\t   WHERE parts.parts_price >= 500);";

        Cursor cursor = db.rawQuery(query, null);

        String array[] = new String[cursor.getCount()];

        i = 0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array[i] = "PartNum: "+ cursor.getString(0)
                    +"\n PartsPrice: "+ cursor.getString(1)
                    +"\n Quantity: "+ cursor.getString(2)



            ;
            i++;
            cursor.moveToNext();
        }

        adapter.addAll(array);
    }


}
