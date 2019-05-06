package com.oreon.burgerman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BurgerManView extends View
{
    private Bitmap guruh[] = new Bitmap[2];
    private int guruhX = 10;
    private int guruhY;
    private int kecepatanGuruh;



    private int canvasWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed = 15;
    private Paint yellowPaint = new Paint();

    private int greenX,greenY,greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed = 10;
    private Paint redPaint = new Paint();


    private int score,lifeCounterOfGuruh;

    private boolean sentuh = false;


    private Bitmap backgroundutama;
    private Paint skor = new Paint();
    private Bitmap nyawa[] = new Bitmap[2];


    public BurgerManView(Context context) {
        super(context);
        guruh[0] = BitmapFactory.decodeResource(getResources(),R.drawable.guruhicon);
        guruh[1] = BitmapFactory.decodeResource(getResources(),R.drawable.guruh2icon);

        backgroundutama = BitmapFactory.decodeResource(getResources(),R.drawable.backgroundmain);

        yellowPaint.setColor(Color.BLUE);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        skor.setColor(Color.BLACK);
        skor.setTextSize(80);
        skor.setTypeface(Typeface.DEFAULT_BOLD);
        skor.setAntiAlias(true);

        nyawa[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        nyawa[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

//        posisi awal ketika start
        guruhY = 550;
        score = 0;
        lifeCounterOfGuruh = 3;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        //display nyawa di dalam array, backgorund,guruh ke main activity
        canvas.drawBitmap(backgroundutama,0,0,null);
        int minguruhY = guruh[0].getHeight();
        int maxguruhY = canvasHeight - guruh[0].getHeight() * 3;
        guruhY = guruhY + kecepatanGuruh;
        if(guruhY < minguruhY)
        {
            guruhY = minguruhY;
        }
        if(guruhY > maxguruhY)
        {
            guruhY = maxguruhY;
        }
        kecepatanGuruh = kecepatanGuruh + 2;

        if(sentuh)
        {
            canvas.drawBitmap(guruh[1],guruhX,guruhY,null);
            sentuh=false;
        }
        else {
            canvas.drawBitmap(guruh[0],guruhX,guruhY,null);
        }
//        checker bola warna biru

        yellowX = yellowX  - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY))
        {
            score = score + 10;
            yellowX =  -100;
        }
        if(yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxguruhY - minguruhY)) + minguruhY;
        }
        canvas.drawCircle(yellowX,yellowY,30,yellowPaint);

//        checker bola warna hijau
        greenX = greenX  - greenSpeed;

        if(hitBallChecker(greenX,greenY))
        {
            score = score + 25;
            greenX =  -100;
        }
        if(greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxguruhY - minguruhY)) + minguruhY;
        }
        canvas.drawCircle(greenX,greenY,20,greenPaint);

//    cek    bola merah
        redX = redX  - redSpeed;

        if(hitBallChecker(redX,redY))
        {
            redX =  -100;
            lifeCounterOfGuruh--;

            if(lifeCounterOfGuruh == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);

                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                untuk kirim nilai total score ke gameover activity
                gameOverIntent.putExtra("score",score);
                getContext().startActivity(gameOverIntent);
            }
        }
        if(redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxguruhY - minguruhY)) + minguruhY;
        }
        canvas.drawCircle(redX,redY,30,redPaint);

        //bagian lain
        canvas.drawText("skor : " + score,20,60,skor);
        for(int i = 0;i<3;i++)
        {
            int x = (int) (380+nyawa[0].getWidth()* 1.5 * i);
            int y = 30;

//    jika nyawa masih > dari 0 maka pakai nyawa dengan indeks 0 (warna)
            if(i < lifeCounterOfGuruh)
            {
                canvas.drawBitmap(nyawa[0],x,y,null);
            }
            else
            {
                //    jika nyawa < dari 0 maka pakai nyawa dengan indeks 1 (grey)
                canvas.drawBitmap(nyawa[1],x,y,null);
            }
        }
    }

    public boolean hitBallChecker(int x,int y)
    {
        if (guruhX < x && x < (guruhX + guruh[0].getWidth()) && guruhY < y && y < (guruhY + guruh[0].getHeight()))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            sentuh = true;
            kecepatanGuruh = -22;
        }
        return true;
    }
}
