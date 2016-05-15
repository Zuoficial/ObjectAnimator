package smoowy.kiminouso;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ImageView image,titulo, contenedor;
    MediaPlayer mp;
    Bitmap bmp;
    View texto;
    public static boolean apiMuyBajo = (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this,R.raw.song);
        image = (ImageView) findViewById(R.id.originalImageHolder);
        titulo = (ImageView) findViewById(R.id.titulo);
        contenedor = (ImageView) findViewById(R.id.container);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        texto = (View) findViewById(R.id.textView);


        // Para convertirlo en IMMERSIVE MODE
        if(!apiMuyBajo)
            Immersive();


        // SI se quiere disminuir la cantidad de memoria usada

        //BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        //int sampleSize = 4;
       // bitmapOptions.inSampleSize = sampleSize;
       // Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.shigatsu01,bitmapOptions);
        //image.setImageBitmap(bmp);




    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus) {


            ObjectAnimator aparecer = ObjectAnimator.ofFloat(image,View.ALPHA,0f,1f);
            aparecer.setRepeatCount(0);
            aparecer.setDuration(5000);

            ValueAnimator moverse = ObjectAnimator.ofFloat(image,View.TRANSLATION_X,200f,-450f);
            moverse.setRepeatCount(0);
            moverse.setDuration(10000);

            ValueAnimator desaparecer = ObjectAnimator.ofFloat(image,View.ALPHA,1f,0f);
            desaparecer.setRepeatCount(0);
            desaparecer.setDuration(10000);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(moverse).with(aparecer);
            animatorSet.play(aparecer).before(desaparecer);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.shigatsu2);
                    image.setImageBitmap(bmp);

                }
            });


            ObjectAnimator aparecer1 = ObjectAnimator.ofFloat(image,View.ALPHA,0f,1f);
            aparecer1.setRepeatCount(0);
            aparecer1.setDuration(5000);


            ValueAnimator moverse1 = ObjectAnimator.ofFloat(image,View.TRANSLATION_X,300f,-150f);
            moverse1.setRepeatCount(0);
            moverse1.setDuration(10000);

            ValueAnimator desaparecer1 = ObjectAnimator.ofFloat(image,View.ALPHA,1f,0f);
            desaparecer1.setRepeatCount(0);
            desaparecer1.setDuration(10000);

            AnimatorSet animatorSet1 = new AnimatorSet();
            animatorSet1.play(moverse1).with(aparecer1);
            animatorSet1.play(aparecer1).before(desaparecer1);

            ObjectAnimator aparecerTexto = ObjectAnimator.ofFloat(titulo, View.ALPHA,0f,1f);
            aparecerTexto.setDuration(5000);

            ObjectAnimator Convertirseblanco = ObjectAnimator.ofFloat(contenedor,View.ALPHA,0f,1f);
            aparecerTexto.setDuration(2000);

            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.play(Convertirseblanco).before(aparecerTexto);
            animatorSet2.setDuration(7000);



            AnimatorSet animatorSetMaster = new AnimatorSet();
            animatorSetMaster.play(animatorSet).before(animatorSet1);
            animatorSetMaster.play(animatorSet2).after(animatorSet1);

            animatorSetMaster.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    //Cuando se quiere animar varios elementos de golpe se puede utilizar esta
                    //forma
                    ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(texto,
                            PropertyValuesHolder.ofFloat("scaleX", .8f, 1.0f),
                            PropertyValuesHolder.ofFloat("scaleY", .8f, 1.0f),
                            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
                    scaleDown.setDuration(2000);
                    scaleDown.start();
                }
            });

            animatorSetMaster.start();








            /*

            image.animate().alpha(1f).setDuration(5000);
            image.animate().translationX(-450f).setDuration(10000).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    image.animate().alpha(0f).setDuration(5000);
                    super.onAnimationEnd(animation);
                }
            }); */



        }


        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onStop() {
        mp.stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        mp.start();
        super.onResume();
    }
    private void Immersive() {
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
