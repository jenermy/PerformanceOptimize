package com.example.performanceoptimize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/7 11:54
 */

public class MultiCardsView extends View {
    private ArrayList<SingleCard> cardsList = new ArrayList<>(5);
    private boolean enableOverdrawOpt = true;
    public MultiCardsView(Context context){
        this(context,null);
    }
    public MultiCardsView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public MultiCardsView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }
    public void addCards(SingleCard card){
        cardsList.add(card);
    }
    public void setEnableOverdrawOpt(boolean enableOverdrawOpt){
        this.enableOverdrawOpt = enableOverdrawOpt;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(cardsList == null || canvas == null)return;
        if(enableOverdrawOpt){
            drawCardsWithOptimizeOverdraw(canvas,cardsList.size()-1);
        }else{
            drawCardsNormal(canvas,cardsList.size()-1);
        }
    }
    private void drawCardsWithOptimizeOverdraw(Canvas canvas,int index){
        if(canvas == null || index < 0 || index >= cardsList.size())return;
        SingleCard card = cardsList.get(index);
        if(card != null && !canvas.quickReject(card.area, Canvas.EdgeType.BW)){
            int saveCount = canvas.save();
            if(canvas.clipRect(card.area, Region.Op.DIFFERENCE)){
                drawCardsWithOptimizeOverdraw(canvas,index -1);
            }
            canvas.restoreToCount(saveCount);
            saveCount = canvas.save();
            if(canvas.clipRect(card.area)){
                Rect clip = canvas.getClipBounds();
                card.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        }else{
            drawCardsWithOptimizeOverdraw(canvas,index-1);
        }
    }
    private void drawCardsNormal(Canvas canvas,int index){
        if(canvas == null || index < 0 || index >= cardsList.size())return;
        SingleCard card = cardsList.get(index);
        if(card != null){
            drawCardsNormal(canvas,index-1);
            card.draw(canvas);
        }
    }
}
