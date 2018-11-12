package com.example.piyumithanirman.creative;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName;
    DatabaseHelper dbHelper;
    Button login;
    Button signUp;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.Blogin);
        signUp = (Button) findViewById(R.id.Bsignup);
        userName = (EditText) findViewById(R.id.TFusername);

        pw = (EditText) findViewById(R.id.TFpassword);
        dbHelper = new DatabaseHelper(this);


        login.setOnClickListener(new login());
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }


    class login implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            String username = String.valueOf(userName.getText());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor usernameTableValues = db.query("USER", new String[]{"user_name"}, null, null, null, null, null);

            usernameTableValues.moveToFirst();

            boolean usernameMatched = false;

            int nameChecked = 0;

            while (!usernameMatched) {

                String savedUserName = usernameTableValues.getString(0);

                if (username.equals(savedUserName)) {
                    usernameMatched = true;

                    Cursor pwList = db.query("USER", new String[]{"pw"}, "user_name=?", new String[]{username}, null, null, null);
                    pwList.moveToFirst();

                    boolean pwmached = false;
                    int passwordChecked = 0;

                    while (!pwmached) {

                        String svedPW = pwList.getString(0);

                        if (pw.getText().toString().equals(svedPW)) {
                            startActivity(new Intent(MainActivity.this,Display.class));
                            break;
                        } else {
                            passwordChecked++;
                            if (passwordChecked < pwList.getCount()) {
                                pwList.moveToNext();
                            } else {
                                Toast.makeText(MainActivity.this, "pw incorrect", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                    }
                } else {
                    nameChecked++;
                    if (nameChecked < usernameTableValues.getCount()) {
                        usernameTableValues.moveToNext();

                    } else {
                        Toast.makeText(MainActivity.this, "you need to sign up first", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        }
    }
}


