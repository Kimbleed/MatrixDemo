package com.example.a49479.matrixdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MatrixView matrixView;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrixView = (MatrixView) findViewById(R.id.matrixView);
        btn = (Button) findViewById(R.id.btn);
        btn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(matrixView.state == MatrixView.STATE_NORMAL) {
                    matrixView.change(MatrixView.STATE_90DEGREE_ROTATE);
                    btn.setText("90°");
                }
                else if(matrixView.state == MatrixView.STATE_90DEGREE_ROTATE){
                    matrixView.change(MatrixView.STATE_NORMAL);
                    btn.setText("NORMAL");
                }
                else{
                    matrixView.change(MatrixView.STATE_NORMAL);
                    btn.setText("NORMAL");
                }

            }
        });
    }

}
