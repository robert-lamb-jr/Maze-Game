package com.example.amazebyrobertlamb.ui.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MazePanel extends View implements P7PanelS23 {

    private int myWidth;
    private int myHeight;
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private int counter;
    private float percentToExit;

    public MazePanel(Context context) {
        super(context);
        init();
    }

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MazePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        myWidth = 2500;
        myHeight = 2500;
        bitmap = Bitmap.createBitmap(myWidth, myHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int size = Math.min(width, height);

        setMeasuredDimension(size, size);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        addBackground(percentToExit);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //myTestImage();
    }

    @Override
    public void commit() {
        invalidate();
    }

    @Override
    public boolean isOperational() {
        if (canvas == null) {
            return false;
        }
        return true;
    }

    @Override
    public void setColor(int argb) {
        paint.setColor(argb);
    }

    @Override
    public int getColor() {
        return paint.getColor();
    }


    @Override
    public void addBackground(float percentToExit) {
        // Set colors based on percentToExit
        float red, green, blue;
        if (percentToExit < 50) {
            red = 0;//(50 - percentToExit) / 50f; // Transition from black to gold
            green = 115;//red * 0.8f;
            blue = 230;//0f;
        } else {
            green = (percentToExit - 50) / 50f; // Transition from grey to green
            red = 0.5f + green * 0.5f;
            blue = 0.5f;
        }
        // Draw two solid rectangles
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(0, 0, myWidth, myHeight / 2, new Paint());
        canvas.drawColor(Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255)));
        canvas.drawRect(0, myHeight / 2, myWidth, myHeight, new Paint());
    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        canvas.drawRect(x, y, width, height, paint);
    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path wallPath = new Path();
        wallPath.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < nPoints; i++) {
            wallPath.lineTo(xPoints[i], yPoints[i]);
        }
        wallPath.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(wallPath, paint);
    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < nPoints; i++) {
            path.lineTo(xPoints[i], yPoints[i]);
        }
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(path, paint);
    }

    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        canvas.drawOval(x, y, width, height, paint);
    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Path wallPath = new Path();
        wallPath.addArc(x, y, width, height, startAngle, arcAngle);
        canvas.drawPath(wallPath, paint);
    }

    @Override
    public void addMarker(float x, float y, String str) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText(str, x, y, paint);
    }

    @Override
    public void setRenderingHint(P7RenderingHints hintKey, P7RenderingHints hintValue) {

    }

    /*private void myTestImage() {
        setColor(Color.RED);
        addFilledOval(50, 50, 300, 300);
        setColor(Color.GREEN);
        addFilledOval(50, 50, 400, 400);
        setColor(Color.YELLOW);
        addFilledRectangle(50, 50, 100, 200);
        setColor(Color.BLUE);
        int[] poly1 = new int[4];
        int[] poly2 = new int[4];
        poly1[0] = 25;
        poly1[1] = 50;
        poly1[2] = 75;
        poly1[3] = 100;
        poly2[0] = 75;
        poly2[1] = 50;
        poly2[2] = 50;
        poly2[3] = 75;
        addFilledPolygon(poly1, poly2, 4);
    }*/

    public void update() {
    }


}
