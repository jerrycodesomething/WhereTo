package com.example.whereto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CheckinActivity extends AppCompatActivity {

    private Button btn_camera;
    private ImageView iv_camera;
    private final int CAMERA_REQUEST = 10;
    private Button btn_privacy;
    private PopupWindow pw_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        btn_camera = findViewById(R.id.bt_turnon);
        iv_camera = findViewById(R.id.iv_photo);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(camera,CAMERA_REQUEST);
            }


            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                switch (requestCode) {
                    case CAMERA_REQUEST:
                        if (resultCode == RESULT_OK) {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            iv_camera.setImageBitmap(bitmap);
                        }
                        break;
                }
            }
        });

        btn_privacy = findViewById(R.id.bt_share);
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view =  getLayoutInflater().inflate(R.layout.activity_sharing_privacy_setting,null);
                pw_privacy = new PopupWindow(view,btn_privacy.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                pw_privacy.setOutsideTouchable(true);
                pw_privacy.showAsDropDown(btn_privacy);

                //set public onclick
                TextView textView = view.findViewById(R.id.tv_public);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw_privacy.dismiss();
                        btn_privacy.setText(textView.getText());
                    }
                });
                //set friends onclick
                TextView textView_fiend = view.findViewById(R.id.tv_friend);
                textView_fiend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw_privacy.dismiss();
                        btn_privacy.setText(textView_fiend.getText());
                    }
                });

            }
        });
    }
}