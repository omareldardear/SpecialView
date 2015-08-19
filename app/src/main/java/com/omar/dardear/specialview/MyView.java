package com.omar.dardear.specialview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Omar on 8/19/2015.
 */
public class MyView extends View {

    private Paint p;
    private int startX;
    private int startY;
    private int radius;
    private ArrayList<Integer> colors;
    private ArrayList<Integer> colors2;
    private ArrayList<Float> values;
    Bitmap bitmap;
    Context mContext;
    public int isTouched=0;
    public int ItemTouched=0;
    public int OldTouched=0;





    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        p = new Paint();
        p.setAntiAlias(true);

        colors = new ArrayList<Integer>();
        colors2 = new ArrayList<Integer>();
        values = new ArrayList<Float>();

        startX = 0;
        startY = 0;
        radius = 600;

        colors.add(Color.parseColor("#3C9EFF"));
        colors.add(Color.parseColor("#F7B6BA"));
        colors.add(Color.parseColor("#3CCE3D"));

        colors2.add(Color.parseColor("#1F7A70"));
        colors2.add(Color.parseColor("#FE6D6E"));
        colors2.add(Color.parseColor("#92E299"));


        values.add(1f);
        values.add(1f);
        values.add(1f);


    }


    boolean isTouchStateChanged()
    {
        if (isTouched==OldTouched)
            return false;
        return true;
    }

    void SetPressed(Canvas c,Canvas canvas,int position)
    {
        float offset = 0;
        float sum = 0;
        for (int a = 0; a < values.size(); a++) {
            sum += values.get(a);
        }

        float angle = (float) (360 / sum);

        Log.e("angle", "" + angle);

        RectF rectF = new RectF();

        rectF.set(getStartX(), getStartY(), getStartX() + getRadius(),
                getStartY() + getRadius());


        for (int i = 0; i < values.size(); i++) {

            if (i==position)
            {
                p.setColor(colors2.get(i));
            }
            else
            {
                p.setColor(colors.get(i));
            }


            if (i == 0) {
                canvas.drawArc(rectF, 0, values.get(i) * angle, true, p);
                c.drawArc(rectF, 0, values.get(i) * angle, true, p);
            } else {
                canvas.drawArc(rectF, offset, values.get(i) * angle, true, p);
                c.drawArc(rectF, offset, values.get(i) * angle, true, p);

            }

            offset += (values.get(i) * angle);
        }

        canvas.save();
        canvas.restore();


    }


    void ResetPressed(Canvas c,Canvas canvas)
    {
        float offset = 0;
        float sum = 0;
        for (int a = 0; a < values.size(); a++) {
            sum += values.get(a);
        }

        float angle = (float) (360 / sum);

        Log.e("angle", "" + angle);

        RectF rectF = new RectF();

        rectF.set(getStartX(), getStartY(), getStartX() + getRadius(),
                getStartY() + getRadius());


        for (int i = 0; i < values.size(); i++) {

            p.setColor(colors.get(i));


            if (i == 0) {
                canvas.drawArc(rectF, 0, values.get(i) * angle, true, p);
                c.drawArc(rectF, 0, values.get(i) * angle, true, p);
            } else {
                canvas.drawArc(rectF, offset, values.get(i) * angle, true, p);
                c.drawArc(rectF, offset, values.get(i) * angle, true, p);

            }

            offset += (values.get(i) * angle);
        }

        canvas.save();
        canvas.restore();


    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmap = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);

        Log.e("", "onDraw() is called...");

        float offset = 0;
        float sum = 0;
        for (int a = 0; a < values.size(); a++) {
            sum += values.get(a);
        }

        float angle = (float) (360 / sum);

        Log.e("angle", "" + angle);

        RectF rectF = new RectF();
        rectF.set(getStartX(), getStartY(), getStartX() + getRadius(),
                getStartY() + getRadius());

        for (int i = 0; i < values.size(); i++) {

            p.setColor(colors.get(i));

            if (i == 0) {
                canvas.drawArc(rectF, 0, values.get(i) * angle, true, p);
               c.drawArc(rectF, 0, values.get(i) * angle, true, p);
            } else {
                canvas.drawArc(rectF, offset, values.get(i) * angle, true, p);
                c.drawArc(rectF, offset, values.get(i) * angle, true, p);

            }

            offset += (values.get(i) * angle);
        }
        canvas.save();

        if ( isTouchStateChanged())
        {

            OldTouched = isTouched;
            if (isTouched == 1) {

                SetPressed(c, canvas, ItemTouched);
            }
            else
            {

                ResetPressed(c, canvas);
            }

        }

    }



    int getTouchItemPosition(int x,int y)
    {
        int color = bitmap.getPixel(x, y);

        Log.e("", "" + color);

        if (colors.contains(color)||colors2.contains(color) )
        {
            Log.e("", "is matching");
            for (int i = 0; i < colors.size(); i++) {
                if (color==colors.get(i)||color==colors2.get(i) )
                    return i;
            }
        }
        return -1;

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ItemTouched=getTouchItemPosition((int)event.getX(), (int)event.getY());

        if (ItemTouched>=0)
        {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isTouched = 1;
                    break;
               default:
                   isTouched = 0;
                   break;


            }
            invalidate();

        }


        return true;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public ArrayList<Float> getValues() {
        return values;
    }

    public void setValues(ArrayList<Float> values) {
        this.values = values;
    }


}
