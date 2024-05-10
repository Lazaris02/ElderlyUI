package com.example.elderlyui;

import android.widget.ImageButton;
import android.widget.TextView;

public class AppsContainer {

    /*An app consists of the icon and the text-name
    * of the app , we need to hold them both*/
    private ImageButton appImg;
    private TextView appText;

    public AppsContainer(ImageButton appImg,TextView appText){
        this.appImg = appImg;
        this.appText = appText;
    }

    public ImageButton getAppImg(){return this.appImg;}
    public TextView getAppText(){return this.appText;}

}
