package com.example.xcliang.singin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;


import org.json.JSONObject;




public class Sign_up extends AppCompatActivity {

    private EditText username_text;
    private EditText firstname_text;
    private EditText lastname_text;
    private EditText password_text;
    private EditText licencenum_text;
    private EditText licencestate_text;
    private EditText make_text;
    private EditText model_text;
    private EditText year_text;
    private EditText color_text;
    private EditText email;



    private User signUpDriver;

    private String url = "https://smartparker.cf/add_customer.php";

    //create an instance of the helper so we can access the functions
    private ServerHelper helper = new ServerHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signUpDriver = new User();

        username_text = (EditText)findViewById(R.id.username_signup_field);
        firstname_text = (EditText)findViewById(R.id.firstname_signup_field);
        lastname_text = (EditText)findViewById(R.id.lastname_signup_field);
        password_text = (EditText)findViewById(R.id.password_signup_field);



        Button btn1=(Button)findViewById(R.id.btn_button1);

        //goes to the second sign up page
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUpDriver.setUsername(username_text.getText().toString());
                signUpDriver.setFirstName(firstname_text.getText().toString());
                signUpDriver.setLastName(lastname_text.getText().toString());
                signUpDriver.setPassword_hash(password_text.getText().toString());

                gotoLayout2();
            }
        });



    }


    //Each time we want to communicate with the server it need to be with an asynchronous task.
    //Create a class for each request type to the server
    private class signUpTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params){

            String username = params[0];
            String password = params[1];
            String fname = params[2];
            String lname = params[3];
            String license_num = params[4];
            String state = params[5];
            String make = params[6];
            String model = params[7];
            String year = params[8];
            String color = params[9];
            String email = params[10];



            try{

                getinfo_signup(username, password, fname, lname, license_num, state, make,
                        model, year, color, email, url);


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }

    //TODO: Add a comment describing what this is and why it is used and what it does
    private void gotoLayout2(){
        setContentView(R.layout.activity_signin2);
        Button btn2=(Button)findViewById(R.id.btn_button2_1);
        Button btn3=(Button)findViewById(R.id.btn_button2_2);

        //find the objects on the page
        licencenum_text = (EditText)findViewById(R.id.licencenum_signup_field);
        licencestate_text = (EditText)findViewById(R.id.licencestate_signup_field);
        make_text = (EditText)findViewById(R.id.make_signup_field);
        model_text = (EditText)findViewById(R.id.model_signup_field);
        year_text = (EditText)findViewById(R.id.year_signup_field);
        color_text = (EditText)findViewById(R.id.color_signup_field);

        //set up the default values or plug in values if the back button was hit
        licencenum_text.setText(signUpDriver.license_plate);
        licencestate_text.setText(signUpDriver.license_state);
        make_text.setText(signUpDriver.make);
        model_text.setText(signUpDriver.model);
        year_text.setText(signUpDriver.year);
        color_text.setText(signUpDriver.color);


        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //save the driver info
                signUpDriver.setLicense_plate(licencenum_text.getText().toString());
                signUpDriver.setLicense_state(licencestate_text.getText().toString());
                signUpDriver.setMake(make_text.getText().toString());
                signUpDriver.setModel(model_text.getText().toString());
                signUpDriver.setYear(year_text.getText().toString());
                signUpDriver.setColor(color_text.getText().toString());

                gotoLayout1();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //save the driver info
                signUpDriver.setLicense_plate(licencenum_text.getText().toString());
                signUpDriver.setLicense_state(licencestate_text.getText().toString());
                signUpDriver.setMake(make_text.getText().toString());
                signUpDriver.setModel(model_text.getText().toString());
                signUpDriver.setYear(year_text.getText().toString());
                signUpDriver.setColor(color_text.getText().toString());


                gotoLayout3();
            }
        });

    }

    //TODO: Add a comment describing what this is and why it is used and what it does
    private void gotoLayout1(){
        //when the user hits the very first button on the very first sign up page
        setContentView(R.layout.activity_signin);
        Button btn1=(Button)findViewById(R.id.btn_button1);

        username_text = (EditText)findViewById(R.id.username_signup_field);
        firstname_text = (EditText)findViewById(R.id.firstname_signup_field);
        lastname_text = (EditText)findViewById(R.id.lastname_signup_field);
        password_text = (EditText)findViewById(R.id.password_signup_field);

        username_text.setText(signUpDriver.username);
        firstname_text.setText(signUpDriver.firstName);
        lastname_text.setText(signUpDriver.lastName);
        password_text.setText(signUpDriver.password_hash);

        //goes to the second signup page
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //get the info from this page
                signUpDriver.setUsername(username_text.getText().toString());
                signUpDriver.setFirstName(firstname_text.getText().toString());
                signUpDriver.setLastName(lastname_text.getText().toString());
                signUpDriver.setPassword_hash(password_text.getText().toString());
                gotoLayout2();
            }
        });
    }

    //TODO: Add a comment describing what this is and why it is used and what it does
    private void gotoLayout3(){
        //when the user hits the back button on the last sign up page
        setContentView(R.layout.activity_signin3);

        //find the 2 buttons on the page
        Button btn4=(Button)findViewById(R.id.btn_button3_1);
        Button btnSubmit=(Button)findViewById(R.id.btn_button3_2);

        //get the editText field
        email = (EditText)findViewById(R.id.venmoname_signup_field);

        email.setText(signUpDriver.email);

        //goes back to the second page
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //save info on this page
                signUpDriver.setEmail(email.getText().toString());
                gotoLayout2();
            }
        });


        //when the submit button is pressed this is called
        btnSubmit.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               signUpDriver.setEmail(email.getText().toString());

                //create an instance of the async task that communicates with server and
               // signs up a user
               signUpTask task = new signUpTask();
               try {
                   //call the task and hold all execution until it finishes
                   task.execute(signUpDriver.username, signUpDriver.password_hash,
                           signUpDriver.firstName, signUpDriver.lastName,
                           signUpDriver.license_plate, signUpDriver.license_state,
                           signUpDriver.make, signUpDriver.model,
                           signUpDriver.year, signUpDriver.color, signUpDriver.email).get();

                   //navigate to the display page
                   Intent displayIntent = new Intent(Sign_up.this, DisplayUser.class);
                   displayIntent.putExtra("user", signUpDriver);
                   startActivity(displayIntent);

               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }





           }
        });
    }






    public void getinfo_signup(String username, String password, String firstname, String lastname,
                               String licencenum, String licencestate, String make, String model,
                               String year, String color, String venmo_uname, String url_name)
            throws UnsupportedEncodingException {

        String salt = helper.genSalt();

        //create parameter sended to the server
        String data = URLEncoder.encode("username", "UTF-8") + "=" +
                URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                URLEncoder.encode(password, "UTF-8");
        data += "&" + URLEncoder.encode("salt", "UTF-8") + "=" +
                URLEncoder.encode(salt, "UTF-8");
        data += "&" + URLEncoder.encode("first_name", "UTF-8") + "=" +
                URLEncoder.encode(firstname, "UTF-8");
        data += "&" + URLEncoder.encode("last_name", "UTF-8") + "=" +
                URLEncoder.encode(lastname, "UTF-8");
        data += "&" + URLEncoder.encode("license_plate", "UTF-8") + "=" +
                URLEncoder.encode(licencenum, "UTF-8");
        data += "&" + URLEncoder.encode("state", "UTF-8") + "=" +
                URLEncoder.encode(licencestate, "UTF-8");
        data += "&" + URLEncoder.encode("make", "UTF-8") + "=" +
                URLEncoder.encode(make, "UTF-8");
        data += "&" + URLEncoder.encode("model", "UTF-8") + "=" +
                URLEncoder.encode(model, "UTF-8");
        data += "&" + URLEncoder.encode("year", "UTF-8") + "=" +
                URLEncoder.encode(year, "UTF-8");
        data += "&" + URLEncoder.encode("color", "UTF-8") + "=" +
                URLEncoder.encode(color, "UTF-8");
        data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                URLEncoder.encode(venmo_uname, "UTF-8");

        String json_str = "";
        json_str = helper.communicateWithServer(url_name, data);

        if (json_str != "") {
            try {
                //Create the JSON object from the returned text from the server.
                JSONObject obj = new JSONObject(json_str);

                //get the Status from the returned JSON object
                String status_str = obj.getString("status");
                System.out.println(obj.toString());

                //convert the Status from a string to an int
                Integer status = Integer.parseInt(status_str);

                if (status != 1) { //the salt was returned
                    String message = obj.getString("message");
                    System.out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }




}

