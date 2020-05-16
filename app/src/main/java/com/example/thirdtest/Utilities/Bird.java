package com.example.thirdtest.Utilities;

public class Bird {

    private float birdX;
    private float birdY;

    public float getBirdX() {
        return birdX;
    }
    public float getBirdY() {
        return birdY;
    }

    public void setBirdX(float birdX) {
        this.birdX = birdX;
    }
    public void setBirdY(float birdY) {
        this.birdY = birdY;
    }

/*
        imgBird.setX(screenWidth + 80.0f);
        imgBird.setY(-80.0f);

        time.schedule(new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    changePos();
                }
            });
        }
    },0,20);



    //Posicion pajarito
    public void changePos(){
        birdX += 10;
        if (imgBird.getX() > screenWidth){
            birdX = -100.0f;
            birdY = (float)Math.floor(Math.random()*(screenHeight - imgBird.getHeight()));
        }
        imgBird.setX(birdX);
        imgBird.setY(birdY);
    } */
}



