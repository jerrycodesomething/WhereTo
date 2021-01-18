package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.GridView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Profile extends AppCompatActivity {

//    GridView gridView;
//
//    //a list of images in array
//    int [] images = {R.drawable.ic_launcher_background};
//
//    String [] names = {"test name"};
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gridView =(GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ProfileImageAdapter(this));

//        gridView = findViewById(R.id.gridView);
//
//        CustomAdapter customAdapter = new CustomAdapter(names,images,this);
//
//        gridView.setAdapter(customAdapter);
    }



//    public class CustomAdapter extends BaseAdapter{
//
//        private String[] imageNames;
//        private int[] imagesPhoto;
//        private Context context;
//        private LayoutInflater layoutInflater;
//
//        public CustomAdapter(String[] imageNames,int[] imagesPhoto, Context context) {
//            this.imageNames = imageNames;
//            this.imagesPhoto = imagesPhoto;
//            this.context = context;
//            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//        }
//
//        @Override
//        public int getCount() {
//            return imagesPhoto.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//            if(view == null){
//                view = layoutInflater.inflate(R.layout.row_items, viewGroup,false);
//
//            }
//            TextView tvName = view.findViewById(R.id.tvName);
//            ImageView imageView = view.findViewById(R.id.imageView);
//
//            tvName.setText(imageNames[i]);
//            imageView.setImageResource(imagesPhoto[i]);
//
//
//
//
//            return null;
//        }
    }
