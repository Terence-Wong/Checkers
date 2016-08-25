package com.company;

import java.awt.*;

/**
 * Created by Terence on 2016-05-22
 * ICS -----
 * Date: 20xx
 * Teacher:-----
 */
public class clickButton {
    int x,y,width,height;
    int WIDTH,HEIGHT;
    Color buttCol,textCol;
    String display;
    int fontSize = 27;
    int center;
    boolean active;
    Main project;
    public clickButton(Main project,Color buttCol,Color textCol,int x, int y, int width, int height,String display,boolean activity){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height=height;
        WIDTH = width;
        HEIGHT = height;
        this.buttCol=buttCol;
        this.textCol=textCol;
        this.display=display;
        this.project=project;
        active=activity;
        center = display.length()*fontSize/6;
    }
    public void paint(Graphics2D g){
        if (active) {
            g.setColor(buttCol);
            g.fillRect(x,y,width,height);
            g.setColor(textCol);

            g.setFont(new Font("CalibriLight", Font.PLAIN, fontSize));
            g.drawString(display,x-center+(width-fontSize)/2,y-5+(height+fontSize)/2);
        }
    }
    public void click(int xx, int yy){
        if(xx>=x&&xx<=x+width && yy>=y&&yy<=y+height && active){
            if(project.menu){
                project.clicked(display);
            }
            if(project.options){
                project.OPclicked(x,y);
            }
            if(display.equals("Back")){
                project.back();
            }
        }
    }

    public void toggle() {
        active=!active;
        if(active){
            width=WIDTH;
            height=HEIGHT;
        }else{
            width = 0;
            height = 0;
        }
    }
}
