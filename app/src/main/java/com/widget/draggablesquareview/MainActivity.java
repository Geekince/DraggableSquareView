package com.widget.draggablesquareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DraggableSquareView dragSquare;
    private int imageStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragSquare = (DraggableSquareView) findViewById(R.id.drag_square);
        dragSquare.post(new Runnable() {
            @Override
            public void run() {
                dragSquare.requestLayout();
            }
        });
        dragSquare.setListener(new DraggableSquareView.Listener() {
            @Override
            public void pickImage(int imageStatus, boolean isModify) {

            }

            @Override
            public void takePhoto(int imageStatus, boolean isModify) {

            }
        });
        setImages("http://lorempixel.com/400/400?flag=0",
                "http://lorempixel.com/400/400?flag=1",
                "http://lorempixel.com/400/400?flag=2",
                "http://lorempixel.com/400/400?flag=3",
                "http://lorempixel.com/400/400?flag=4",
                "http://lorempixel.com/400/400?flag=5");
    }

    public void setImages(String... imageUrls) {
        if (imageUrls == null) return;
        for (int i = 0; i < (Math.min(imageUrls.length, dragSquare.getImageSetSize())); i++) {
            dragSquare.fillItemImage(imageStatus, imageUrls[i], false);
        }
    }

}