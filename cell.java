package com.company;
import java.awt.*;
/**
 * Created by Terence on 2016-06-02
 * ICS -----
 * Date: 20xx
 * Teacher:-----
 */

///bugges too lazy to fix, click on a peice weiredly(selection) and u can destroy your own pice
public class cell {
    Main game;
    int contained,cellsize;
    boolean selected=false;

    public cell(Main project, int player, int size){
        game = project;
        contained=player;


        cellsize = size;
    }
    public void paint(Graphics g,int x, int y){
        g.setColor(Color.blue);
        g.drawString(String.valueOf(contained),70 + (x * cellsize + 10), 60 + (y * cellsize + 10));
        if(selected){
            g.setColor(game.HIGHLIGHTCOL);
            g.fillRect(70 + (x * cellsize + 10), 60 + (y * cellsize + 10), cellsize, cellsize);
        }
        if(contained!=0){
            switch (contained){
                case 1:
                    g.setColor(game.primaryCol);
                    g.fillOval(70 + (x * cellsize + 10), 60 + (y * cellsize + 10), cellsize, cellsize);
                    break;
                case 2:
                    g.setColor(game.secondaryCol);
                    g.fillOval(70 + (x * cellsize + 10), 60 + (y * cellsize + 10), cellsize, cellsize);
                    break;
                case 3:
                    g.setColor(game.primaryCol);
                    g.fillOval(70 + (x * cellsize + 10), 60 + (y * cellsize + 10), cellsize, cellsize);
                    g.setColor(game.HIGHLIGHTCOL);
                    g.fillOval(70 + (x * cellsize + 10)+cellsize/4, 60 + (y * cellsize + 10)+cellsize/4, cellsize/2, cellsize/2);
                    break;
                case 4:
                    g.setColor(game.secondaryCol);
                    g.fillOval(70 + (x * cellsize + 10), 60 + (y * cellsize + 10), cellsize, cellsize);
                    g.setColor(game.HIGHLIGHTCOL);
                    g.fillOval(70 + (x * cellsize + 10)+cellsize/4, 60 + (y * cellsize + 10)+cellsize/4, cellsize/2, cellsize/2);
                    break;
            }


        }

    }


}
