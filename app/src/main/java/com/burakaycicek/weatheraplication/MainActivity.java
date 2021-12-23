package com.burakaycicek.weatheraplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editCity;
    TextView textCity;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey = "251e4b3387636e8209caf22171b42ee0";
    DigitalClock digitalClock;
    ImageView night;
    ImageView morning;
    ImageView iconn;
    ImageView sulenaz;
    ImageView wallpaper;
    ImageView mornm;
    TextView feelslike;
    TextView pressurer;
    TextView tempMinn;
    ImageView wallAnkar;
    Drawable resim;
    ImageView backroundd;
    Double getval;
    ImageView backAnk;
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editCity = findViewById(R.id.editCity);
        textCity = findViewById(R.id.textCity);
        digitalClock = findViewById(R.id.digitalClock);
        wallpaper = findViewById(R.id.wallpaper);
        feelslike = findViewById(R.id.feelslike);
        pressurer = findViewById(R.id.pressureText);
        tempMinn = findViewById(R.id.tempMin);
        iconn = findViewById(R.id.icon);
       // wallAnkar = findViewById(R.id.wallAnk);
       // mornm = findViewById(R.id.morningM);


        time();
        changeImage();


    }

    public void changeImage(){
        if(editCity.getText().toString()=="Ankara"){
            wallpaper.setImageResource(R.drawable.sule);
        }else if (editCity.getText().equals("London")){
            wallpaper.setImageResource(R.drawable.ankaraa);
        }
    }

    public void time() {

        if (timeOfDay >= 0 && timeOfDay < 12) {
            Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            wallpaper.setImageResource(R.drawable.morning);


        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            wallpaper.setImageResource(R.drawable.sule);

        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            wallpaper.setImageResource(R.drawable.kevin);




        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            ///    if(ankara)




        }
    }


    public void getWeather(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Example> exampleCall = weatherAPI.getWeather(editCity.getText().toString(), apikey);
        exampleCall.enqueue(new Callback<Example>() {
            ;

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Please Enter a valid City", Toast.LENGTH_LONG).show();

                } else if (!(response.isSuccessful())) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                }
                Example myData = response.body();
                Main main = myData.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);
                textCity.setText(String.valueOf(temperature) + "C");









                Double feelsliken = main.getFeelsLike();
                Integer feelslike1 = (int) (feelsliken - 273.15);
                feelslike.setText(String.valueOf(feelslike1));

                Integer pressure = main.getPressure();
                pressurer.setText(String.valueOf(pressure));

                Double tempM = main.getTempMin();
                Integer tempMi = (int)(tempM - 273.15);
                tempMinn.setText(String.valueOf(tempMi));








            }



            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
    // public Integer calculateTemp(Double getval){


    //     this.getval = getval;
    //     Integer temperature = (int) (getval - 273.15);
    //     return temperature;

    // }

}