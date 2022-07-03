package com.widget.draggablesquareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private DraggableSquareView dragSquare;
    private int imageStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragSquare = (DraggableSquareView) findViewById(R.id.drag_square);
        dragSquare.setListener(new DraggableSquareView.Listener() {
            @Override
            public void pickImage(int imageStatus, boolean isModify) {

            }

            @Override
            public void takePhoto(int imageStatus, boolean isModify) {

            }
        });
        dragSquare.post(new Runnable() {
            @Override
            public void run() {
                dragSquare.requestLayout();
            }
        });

        setImages("https://i.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U",
                "https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI",
                "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg",
                "http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg",
                "http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");

        Glide.with(this).load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI").into((ImageView) findViewById(R.id.cover_iv));
    }

    public void setImages(String... imageUrls) {
        if (imageUrls == null) return;
        for (int i = 0; i < (Math.min(imageUrls.length, dragSquare.getImageSetSize())); i++) {
            dragSquare.fillItemImage(imageStatus, imageUrls[i], false);
        }
    }

}