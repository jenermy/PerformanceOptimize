package com.example.performanceoptimize;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/7 11:51
 */

public class SingleCard {
    public Bitmap bitmap;
    public RectF area;
    public Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public SingleCard(RectF area){
        this.area = area;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,null,area,paint);
    }

}
