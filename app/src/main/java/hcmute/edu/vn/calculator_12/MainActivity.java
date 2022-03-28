package hcmute.edu.vn.calculator_12;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    List<Button> listBtn = new ArrayList<Button>();
    List<Button> listOperatorBtn= new ArrayList<>();
    String firstNumb;
    String secondNumb=null;
    String operator=null;
    Float result=0f;
    Button dotBtn;
    Button equalBtn;
    Button resetBtn;
    TextView screen;
    Integer maxNumb=9;
    Boolean isNew=true;
    Boolean isError=false;
    public void resetOperatorColor(){
        for (Button operatorBtn :listOperatorBtn){
                    operatorBtn.setTextColor(Color.parseColor("black"));
            };
    }
    public void resetCalculator(){
        resetOperatorColor();
        secondNumb=null;
        operator=null;
        result=0f;
        isNew=true;

    }
    public void startNewWithOldResult(){
        firstNumb=result.toString();
        resetCalculator();
    }

    public void startNewWithoutOldResult(){
        firstNumb="0";
        isError=false;
        resetCalculator();
        screen.setText(firstNumb);
    }
    public Boolean isContainDot(){
        return screen.getText().toString().contains(".");
    }
    public Boolean isMaxLength(){
        return screen.getText().length()>maxNumb;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        //CAL SCREEN
        screen=findViewById(R.id.textView);
        //DOT
        dotBtn=findViewById(R.id.dot);
        dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(operator==null && !isContainDot()) // FIRST NUMB CASE
                {
                    screen.setText(screen.getText().toString() +".");
                    isNew=false;
                }
                else if (operator!=null){ // SECOND NUMB CASE
                    if(secondNumb==null  ){
                        if(isContainDot() && !isNew)
                            return;
                        secondNumb="0.";
                        screen.setText(secondNumb);
                    }
                    else if(secondNumb!=null && !isContainDot())
                        screen.setText(screen.getText().toString() +".");
                }

            }
        });
        //EQUAL
        equalBtn=findViewById(R.id.equal);
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HAVENT CHOSEN OPERATOR
                if(operator==null)
                    return;
                if(isError){
                    screen.setText("ERROR");
                    resetCalculator();
                    return;
                }

                //HAVENT CHOSEN SECOND NUMBER
                if(secondNumb==null ){
                  if(operator.equals("+")){
                      result=Float.parseFloat(firstNumb) + Float.parseFloat(firstNumb);
                  }
                  else if(operator.equals("-")){
                      result=Float.parseFloat(firstNumb) - Float.parseFloat(firstNumb);
                  }
                  else if(operator.equals("X")){
                      result=Float.parseFloat(firstNumb) * Float.parseFloat(firstNumb);
                  }
                  else if(operator.equals("/")){
                      result=Float.parseFloat(firstNumb) / Float.parseFloat(firstNumb);
                  }
                }

                //ALREADY CHOSEN SECOND NUMBER
                else{
                    if(operator.equals("+")){
                        result=Float.parseFloat(firstNumb) + Float.parseFloat(secondNumb);
                    }
                    else if(operator.equals("-")){
                        result=Float.parseFloat(firstNumb) - Float.parseFloat(secondNumb);
                    }
                    else if(operator.equals("X")){
                        result=Float.parseFloat(firstNumb) * Float.parseFloat(secondNumb);
                    }
                    else if(operator.equals("/")){
                        if(secondNumb.equals("0")){
                            screen.setText("ERROR");
                            isError=true;
                            resetCalculator();
                            return;

                        }
                        else result=Float.parseFloat(firstNumb) / Float.parseFloat(secondNumb);
                    }
                }
                    if(result!=Math.ceil(result)){
                        screen.setText(result.toString());
                    }
                    else{
                        Integer castedValue=Math.round(result);
                        screen.setText(castedValue.toString());
                    }
                startNewWithOldResult();
            }
        });
        //RESET
        resetBtn=findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewWithoutOldResult();

            }
        });

        //OPERATOR
        listOperatorBtn.add(findViewById(R.id.plus));
        listOperatorBtn.add(findViewById(R.id.minus));
        listOperatorBtn.add(findViewById(R.id.divided));
        listOperatorBtn.add(findViewById(R.id.multiply));
        listOperatorBtn.add(findViewById(R.id.plus));
        for (Button operatorBtn :listOperatorBtn){
            operatorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetOperatorColor();
                    String chosenOperator=operatorBtn.getText().toString();
                    operatorBtn.setTextColor(Color.parseColor("white"));
                    operator=chosenOperator;

                }
            });
        }

        //NUMBER
        listBtn.add(findViewById(R.id.number0));
        listBtn.add(findViewById(R.id.number1));
        listBtn.add(findViewById(R.id.number2));
        listBtn.add(findViewById(R.id.number3));
        listBtn.add(findViewById(R.id.number4));
        listBtn.add(findViewById(R.id.number5));
        listBtn.add(findViewById(R.id.number6));
        listBtn.add(findViewById(R.id.number7));
        listBtn.add(findViewById(R.id.number8));
        listBtn.add(findViewById(R.id.number9));
        for (Button btn : listBtn)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(isNew){
                        screen.setText("0");
                        isNew=false;
                    }
                    if(screen.getText().toString().equals("0")){ //INITIALIZE CASE
                        if(operator==null){
                            firstNumb=btn.getText().toString();
                            screen.setText(firstNumb);
                        }
                        else{
                            secondNumb=btn.getText().toString();
                            screen.setText(secondNumb);
                        }
                        return;
                    }
                    String prevValue=screen.getText().toString();
                    if(operator==null){
                        if(!isMaxLength()){
                            firstNumb= prevValue + btn.getText();
                            screen.setText(firstNumb);
                        }

                    }
                    else
                    {
                        if(secondNumb==null ){
                            secondNumb=btn.getText().toString();
                        }
                        else if (!isMaxLength()){
                            secondNumb= prevValue + btn.getText();
                        }
                        screen.setText(secondNumb);

                    }

                }
            });
        }





    }
}