package com.example.piyumithanirman.creative;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class SignUp extends Activity {
    static final Pattern PASSWORD_PATTERN=
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    //"(?=.*[a-z])" +
                    //"(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onSignUpClick(View v) {

        EditText name = (EditText) findViewById(R.id.TFname);
        EditText email = (EditText) findViewById(R.id.TFemail);
        EditText uname = (EditText) findViewById(R.id.TFuname);
        EditText pass1 = (EditText) findViewById(R.id.TFpass1);
        EditText pass2 = (EditText) findViewById(R.id.TFpass2);

        String namestr = name.getText().toString();
        String emailstr = email.getText().toString();
        String unamestr = uname.getText().toString();
        String pass1str = pass1.getText().toString();
        String pass2str = pass2.getText().toString();
        if (v.getId() == R.id.Bsignupbutton) {

            if(namestr.isEmpty()){

                Toast pass = Toast.makeText(SignUp.this, "name is empty", Toast.LENGTH_SHORT);
                pass.show();
            }

            else if(emailstr.isEmpty()){

                Toast pass = Toast.makeText(SignUp.this, "email is empty", Toast.LENGTH_SHORT);
                pass.show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()){

                Toast pass = Toast.makeText(SignUp.this, "please enter valied email", Toast.LENGTH_SHORT);
                pass.show();
            }
            else if(unamestr.isEmpty()){

                Toast pass = Toast.makeText(SignUp.this, "username is empty", Toast.LENGTH_SHORT);
                pass.show();
            }
            else if(pass1str.isEmpty()){

                Toast pass = Toast.makeText(SignUp.this, "password is empty", Toast.LENGTH_SHORT);
                pass.show();
            }
            else if(!PASSWORD_PATTERN.matcher(pass1str).matches()){

                Toast pass = Toast.makeText(SignUp.this, "password too weak", Toast.LENGTH_SHORT);
                pass.show();
            }
            else if (!pass1str.equals(pass2str)) {
                Toast pass = Toast.makeText(SignUp.this, "password don't match", Toast.LENGTH_SHORT);
                pass.show();
            }


            else if (v.getId() == R.id.Bsignupbutton) {
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);
                {
                    Toast pass = Toast.makeText(SignUp.this, "you are successfully registered now", Toast.LENGTH_SHORT);
                    pass.show();
                }
            }
        }

        DatabaseHelper dbHelper=new DatabaseHelper(this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        insertData(db,namestr,emailstr,unamestr,pass1str);
    }
    private void insertData(SQLiteDatabase db, String name, String emil, String user_name, String pw){
        ContentValues details=new ContentValues();
        details.put("name",name);
        details.put("email",emil);
        details.put("user_name",user_name);
        details.put("pw",pw);
        db.insert("USER",null,details);


    }


}
