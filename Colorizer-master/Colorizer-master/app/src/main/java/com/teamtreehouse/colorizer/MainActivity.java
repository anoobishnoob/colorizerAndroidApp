package com.teamtreehouse.colorizer;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    int[] imageResIds = {R.drawable.cuba1, R.drawable.cuba2, R.drawable.cuba3};
    int imageIndex = 0;
    boolean color = true;
    boolean red = true;
    boolean green = true;
    boolean blue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        loadImage();
    }

    private void loadImage() {
        Glide.with(this).load(imageResIds[imageIndex]).into(imageView);
    }
    //glide library is what is used to load images without getting memery errors


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.options_menu, menu);

       Drawable nextImageDrawable = menu.findItem(R.id.nextImage).getIcon();
       nextImageDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


       menu.findItem(R.id.green).setChecked(green);
       menu.findItem(R.id.red).setChecked(red);
       menu.findItem(R.id.blue).setChecked(blue);

       menu.setGroupVisible(R.id.colorGroup, color);

        return true;
        // code above does the same but better as the code below
        //super.onCreateOptionsMenu(options_menu) is what was removed
    }
    //CTR+O is the shortcut

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nextImage: // this loads the next images
                imageIndex++; //adavnces the images through the stored images
                if (imageIndex >= imageResIds.length) //if we go above the length
                    imageIndex = 0; // resets length to 0
                loadImage(); // loads image
                break; // gets out of switch statement
            case R.id.color:
                color = !color;
                updateSaturation();
                invalidateOptionsMenu(); // android specific code that our options menu is no longer valid
                break;

            case R.id.red:
                red = !red;
                updateColors();
                item.setChecked(red);
                break;
            case R.id.green:
                green = !green;
                updateColors();
                item.setChecked(green);
                break;
            case R.id.blue:
                blue = !blue;
                updateColors();
                item.setChecked(blue);
                break;

            case R.id.reset:
                imageView.clearColorFilter();
                red = green = blue = color = true;
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *  MenuItem menuItem = options_menu.add("Next Image");
     menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); // creates actionbar
     menuItem.setIcon(R.drawable.ic_add_a_photo_black_24dp);
     menuItem.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP); // this changes the icon to white, every non transparent pixel gets changed to white
     *
     */



    private void updateSaturation() {
        ColorMatrix colorMatrix = new ColorMatrix();
        if (color) {
            red = green = blue = true;
            colorMatrix.setSaturation(1);
        } else {
            colorMatrix.setSaturation(0);
        }
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }

    private void updateColors() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] matrix = {
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        };
        if (!red) matrix[0] = 0;
        if (!green) matrix[6] = 0;
        if (!blue) matrix[12] = 0;
        colorMatrix.set(matrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }
}
