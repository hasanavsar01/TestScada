package com.example.testscada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testscada.views.CustomView;

public class MainActivity extends AppCompatActivity {

    private CustomView customScale;

    boolean P1_ON = false;
    boolean P2_ON = false;
    boolean V1_OPEN = false;
    boolean V2_OPEN = false;
    boolean hasWater = false;
    boolean isFilling = false;
    boolean isDischarging = false;
    int waterAmount = 0;
    int count = 0;

    //Buttons
    Button buttonP1;
    Button buttonP2;
    Button buttonV1;
    Button buttonV2;

    //Pumps
    ImageView imageP1;
    ImageView imageP2;

    //Valves
    ImageView imageV1;
    ImageView imageV2;

    //Pipes
    ImageView imagePipeP1;
    ImageView imagePipeP2;
    ImageView imagePipeV1Entry;
    ImageView imagePipeV1Exit;
    ImageView imagePipeTank;
    ImageView imagePipeV2;

    //WaterAmount
    TextView textViewWaterAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        buttonP1 = (Button) findViewById(R.id.P1);
        buttonP2 = (Button) findViewById(R.id.P2);
        buttonV1 = (Button) findViewById(R.id.V1);
        buttonV2 = (Button) findViewById(R.id.V2);

        //Pumps
        imageP1 = (ImageView) findViewById(R.id.imagePump1);
        imageP2 = (ImageView) findViewById(R.id.imagePump2);

        //Valves
        imageV1 = (ImageView) findViewById(R.id.imageValve1);
        imageV2 = (ImageView) findViewById(R.id.imageValve2);

        //Pipes
        imagePipeP1 = (ImageView) findViewById(R.id.imagePipeP1);
        imagePipeP2 = (ImageView) findViewById(R.id.imagePipeP2);
        imagePipeV1Entry = (ImageView) findViewById(R.id.imagePipeV1Entry);
        imagePipeV1Exit = (ImageView) findViewById(R.id.imagePipeV1Exit);
        imagePipeTank = (ImageView) findViewById(R.id.imagePipeTank);
        imagePipeV2 = (ImageView) findViewById(R.id.imagePipeV2);

        //WaterAmount
        textViewWaterAmount = findViewById(R.id.textViewWaterAmount);

        //Scale
        customScale = (CustomView) findViewById(R.id.customScale);

        buttonP1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (P1_ON){
                    P1_ON = false;
                    imageP1.setImageResource(R.drawable.pump_red);
                    imagePipeP1.setImageResource(R.drawable.pipe1_gray);

                    if(!P2_ON){
                        imagePipeV1Entry.setImageResource(R.drawable.pipehrz_gray);
                        imagePipeV1Exit.setImageResource(R.drawable.pipehrz_gray);

                        //Stop Filling
                        isFilling = false;
                    }
                }
                else{
                    P1_ON = true;
                    imageP1.setImageResource(R.drawable.pump_green);
                    imagePipeP1.setImageResource(R.drawable.pipe1_blue);
                    imagePipeV1Entry.setImageResource(R.drawable.pipehrz_blue);

                    if(V1_OPEN){
                        imagePipeV1Exit.setImageResource(R.drawable.pipehrz_blue);

                        //Enable filling if not enabled
                        if(!isFilling) {
                            isFilling = true;
                            fill();
                        }
                    }
                }
            }
        });

        buttonP2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (P2_ON){
                    P2_ON = false;
                    imageP2.setImageResource(R.drawable.pump_red);
                    imagePipeP2.setImageResource(R.drawable.pipe2_gray);

                    if(!P1_ON){
                        imagePipeV1Entry.setImageResource(R.drawable.pipehrz_gray);
                        imagePipeV1Exit.setImageResource(R.drawable.pipehrz_gray);

                        //Stop Filling
                        isFilling = false;
                    }
                }
                else{
                    P2_ON = true;
                    imageP2.setImageResource(R.drawable.pump_green);
                    imagePipeP2.setImageResource(R.drawable.pipe2_blue);
                    imagePipeV1Entry.setImageResource(R.drawable.pipehrz_blue);

                    if(V1_OPEN){
                        imagePipeV1Exit.setImageResource(R.drawable.pipehrz_blue);

                        //Enable filling if not enabled
                        if(!isFilling) {
                            isFilling = true;
                            fill();
                        }
                    }
                }
            }
        });

        buttonV1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (V1_OPEN){
                    V1_OPEN = false;
                    imageV1.setImageResource(R.drawable.valve_red);
                    imagePipeV1Exit.setImageResource(R.drawable.pipehrz_gray);

                    //Stop Filling
                    isFilling = false;
                }
                else{
                    V1_OPEN = true;
                    imageV1.setImageResource(R.drawable.valve_green);

                    if(P1_ON || P2_ON){
                        imagePipeV1Exit.setImageResource(R.drawable.pipehrz_blue);

                        //Enable filling if not enabled
                        if(!isFilling) {
                            isFilling = true;
                            fill();
                        }
                    }
                }
            }
        });

        buttonV2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (V2_OPEN){
                    V2_OPEN = false;
                    imageV2.setImageResource(R.drawable.valve_red);
                    imagePipeTank.setImageResource(R.drawable.pipe3_gray);
                    imagePipeV2.setImageResource(R.drawable.pipehrz_gray);

                    //Stop discharging
                    isDischarging = false;
                }
                else{
                    V2_OPEN = true;
                    imageV2.setImageResource(R.drawable.valve_green);

                    if(hasWater){
                        imagePipeTank.setImageResource(R.drawable.pipe3_blue);
                        imagePipeV2.setImageResource(R.drawable.pipehrz_blue);

                        //Enable discharging if not enabled
                        if(!isDischarging) {
                            isDischarging = true;
                            discharge();
                        }
                    }
                }
            }
        });
    }

    public void fill(){
        waterAmount++;

        if(waterAmount > 0){
            if(waterAmount > 100){
                waterAmount = 100;
            }
            hasWater = true;
        }

        customScale.UpdateSize(waterAmount);

        textViewWaterAmount.setText(("% " + waterAmount));

        if(isFilling){
            refreshFill(100);
        }
    }

    private void refreshFill(int miliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fill();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }

    public void discharge(){
        waterAmount--;

        if(waterAmount <= 0){
            hasWater = false;
            waterAmount = 0;
        }

        customScale.UpdateSize(waterAmount);

        textViewWaterAmount.setText(("% " + waterAmount));

        if(isDischarging){
            refreshDischarge(200);
        }
    }

    private void refreshDischarge(int miliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                discharge();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }
}
