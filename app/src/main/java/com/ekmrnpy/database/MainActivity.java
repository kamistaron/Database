package com.ekmrnpy.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtFamily, edtID;
    Button btnInsert, btnView, btnUpdate, btnDelete;
    DatabaseManager dbm;
    TextView txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtFamily = (EditText) findViewById(R.id.edt_family);
        edtID = (EditText) findViewById(R.id.edt_id);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnView = (Button) findViewById(R.id.btn_view);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        txtCount = (TextView) findViewById(R.id.txt_count);
        dbm = new DatabaseManager(this);

        setCount();

//        btnInsert.setOnClickListener(new View.onClickListener()){
//            @Override
//            public void onClick(View view) {
//                String mID = edtID.getText().toString();
//                String mName = edtName.getText().toString();
//                String mFamily = edtFamily.getText().toString();
//                Person iPerson = new Person();
//                iPerson.pID = mID;
//                iPerson.pName = mName;
//                iPerson.pFamily = mFamily;
//                dbm.insertPerson(iPerson);
//            }
//        });
    }
    public void onButtonInsertClick(View view){
        String mID = edtID.getText().toString();
        String mName = edtName.getText().toString();
        String mFamily = edtFamily.getText().toString();
        if (TextUtils.isEmpty(mID) || TextUtils.isEmpty(mName) || TextUtils.isEmpty(mFamily)) {

            Toast.makeText(MainActivity.this, "تمامی فیلد‌ها باید تکمیل شود", Toast.LENGTH_LONG).show();

        }else{
            Person iPerson = new Person();
            iPerson.pID = mID;
            iPerson.pName = mName;
            iPerson.pFamily = mFamily;
            dbm.insertPerson(iPerson);
            Toast.makeText(MainActivity.this, "اطلاعات با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
        }

        setCount();
    }

    public void onButtnViewClick(View view){
        Log.i("kamran", "view btn Clicked");
        String vID = edtID.getText().toString();
        Person vPer = dbm.getPerson(vID);
        edtName.setText(vPer.pName);
        edtFamily.setText(vPer.pFamily);
        Log.i("kamran", "Data Viewed!");
    }


    public void onButtnUpdateClick(View view){
        String uID = edtID.getText().toString();
        String uName = edtName.getText().toString();
        String uFamily = edtFamily.getText().toString();

        Person uperson = new Person();
        uperson.pID = uID;
        uperson.pName = uName;
        uperson.pFamily = uFamily;
        dbm.updatePerson(uperson);
    }

    public void onButtnDelete(View view){
        String delID = edtID.getText().toString();

        Person dperson = new Person();
        dperson.pID = delID;
        boolean del = dbm.deletePerson(dperson);
        if (del) {
            Toast.makeText(MainActivity.this, "اطلاعات حذف شد", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "در حذف اطلاعات مشکلی وجود دار", Toast.LENGTH_LONG).show();
        }
        setCount();
    }

    public void setCount(){
        int sCount = dbm.personCount();
        txtCount.setText(Integer.toString(sCount) + " نفر پرسنل");
    }

}
