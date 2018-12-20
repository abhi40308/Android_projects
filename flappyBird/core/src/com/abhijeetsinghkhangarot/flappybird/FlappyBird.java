package com.abhijeetsinghkhangarot.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture gameOver;
	//ShapeRenderer shapeRenderer;
	Texture[] birds = new Texture[2];
	int flapstate = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
    float gap = 400;
    int score = 0;
    BitmapFont font;
    float maxTubeOffset;

    int scoringTube = 0;
    Circle birdCircle;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottonTubeRectangles;

	Texture topTube;
	Texture bottomTube;
	Random randomGenerator;
	float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeOffset = new float[numberOfTubes];
    float[] tubeX = new float[numberOfTubes];
    float distanceBetweenTubes;

	@Override
	public void create () {
		batch = new SpriteBatch();
        birdCircle = new Circle();
        gameOver = new Texture("game_over.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);
       // shapeRenderer = new ShapeRenderer();
        background = new Texture("bg.png");
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");

        topTubeRectangles = new Rectangle[numberOfTubes];
        bottonTubeRectangles = new Rectangle[numberOfTubes];

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

        startGame();

	}

	public void startGame(){

        birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
        for (int i=0; i<numberOfTubes; i++){
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + i * distanceBetweenTubes + Gdx.graphics.getWidth();

            topTubeRectangles[i] = new Rectangle();
            bottonTubeRectangles[i] = new Rectangle();
        }

    }

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    if(gameState == 1){

            if(tubeX[scoringTube] < Gdx.graphics.getWidth()/2 - birds[0].getWidth() / 2){

                score ++;

                Gdx.app.log("score is : ", String.valueOf(score));

                if( scoringTube<3){
                    scoringTube++;
                }
                else {
                    scoringTube = 0;
                }

            }

			if(Gdx.input.justTouched()){
				velocity = -20;

			}

            for (int i=0; i<numberOfTubes; i++) {

			    if(tubeX[i] < -topTube.getWidth()){

			        tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

                }
                else {
			        tubeX[i] = tubeX[i] - tubeVelocity;

                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - bottomTube.getHeight() - gap / 2 + tubeOffset[i]);

                topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottonTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - bottomTube.getHeight() - gap / 2 + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            }

            if(birdY > 0) {
                velocity++;
                birdY -= velocity;
            }
            else {
                gameState = 2;
            }
        }
        else if (gameState == 0){
	        if(Gdx.input.justTouched())
	            gameState=1;
        } else if (gameState == 2){
	        batch.draw(gameOver, Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2, Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2);

	        if(Gdx.input.justTouched()){

	            gameState = 1;
	            startGame();
	            scoringTube = 0;
	            score = 0;
	            velocity = 0;
            }
        }

	    if(flapstate == 0)
            flapstate=1;
        else flapstate=0;


		batch.draw(birds[flapstate], Gdx.graphics.getWidth()/2 - birds[flapstate].getWidth()/2, birdY);
        font.draw(batch, String.valueOf(score), 100, 200);

		batch.end();

        birdCircle.set(Gdx.graphics.getWidth() /2, birdY + birds[flapstate].getHeight() / 2, birds[flapstate].getWidth() / 2 );
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.RED);

		for(int i=0; i<4; i++){

//            shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - bottomTube.getHeight() - gap / 2 + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
//		    shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());

		    if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottonTubeRectangles[i])){

		        gameState = 2;

		    }

        }

//		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
//		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		birds[0].dispose();
		birds[1].dispose();
	}
}
