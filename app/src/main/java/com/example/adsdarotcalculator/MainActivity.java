package com.example.adsdarotcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView tvFirstItem,tvMult,tvIndex,tvSum;
    ListView lv;
    Button btnBack;

    boolean type;
    double firstNum,d,num;
    String [] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvFirstItem = findViewById(R.id.tvFirstItem);
        tvMult = findViewById(R.id.tvMult);
        tvIndex = findViewById(R.id.tvIndex);
        tvSum = findViewById(R.id.tvSum);
        lv = findViewById(R.id.lv);
        btnBack = findViewById(R.id.btnRet);

        Intent get = getIntent();
        firstNum = get.getDoubleExtra("first", 1);
        d = get.getDoubleExtra("d", 1);
        type = get.getBooleanExtra("seriesChoice",false);
        arr = new String[20];

        if(type){
            arr[0] = beautifulNum(firstNum) + "";
            for(int i = 1;i<20 ;i++) {
                num = firstNum * (Math.pow(d, i));
                arr[i]= beautifulNum(num) + "";
            }
        }
        else{
            arr[0] = beautifulNum(firstNum) + "";
            for(int i = 1;i<20 ;i++) {
                num = firstNum + d*i;
                arr[i]= beautifulNum(num) + "";
            }
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,arr);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(this);
        tvFirstItem.setText("first num: "+firstNum);
        tvMult.setText("d: "+ d);
    }
    public String beautifulNum(double value){
        if(value>1000000||value<-1000000){
            String scientificNotation = String.format("%.4e", value);
            String[] parts = scientificNotation.split("e");
            double base = Double.parseDouble(parts[0]) / 10.0;
            int exponent = Integer.parseInt(parts[1]) + 1;
            return String.format("%.4f * 10^%d", base, exponent);
        }
        else {
            return value+"";
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        double sum = 0;
        if (type) {
            for (int i = 0; i <= position; i++) {
                sum += firstNum * Math.pow(d, i);
            }
        }
        else {
            for (int i = 0; i <= position; i++) {
                sum += firstNum + d * i;
            }
        }

        tvIndex.setText("Item: " + (position+1));
        if(sum>1000000){
            tvSum.setText("Sum: " + beautifulNum(sum));
        }
        else {
            tvSum.setText("Sum: " + sum);
        }
    }

}