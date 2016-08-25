package com.company;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Main extends JPanel {
    public static JFrame frame = new JFrame("Checkers");
    public static int HEIGHT = 700;
    public static int WIDTH = 850;
    int wc = (WIDTH-300)/2;

    //BUGS
    //none


    //custom colors
    public Color red = new Color(232,28,28);
    public Color green = new Color(87,212,87);
    public Color blue = new Color (87,150,212);
    public Color yellow = new Color (255,238,0);
    public Color orange = new Color (250,188,87);

    public Color grey = new Color(225,225,225);
    public Color black = new Color(41,41,41);
    public Color white = new Color(255,255,255);

    public Color HIGHLIGHTCOL = new Color(212,190,0);

    Color[] COLS = {red,green,blue,yellow,orange,grey,black,white};

    public int b1C = 5;
    public int b2C = 1;
    public int b3C = 5;
    public int pC  = 0;
    public int sC  = 6;

    public Color backdropCol = COLS[b3C];
    public Color backgroundCol = COLS[b2C];
    public Color primaryCol = COLS[pC];
    public Color secondaryCol = COLS[sC];
    public Color boardCol = COLS[b1C];

    //public Color playerCol1=;
    //public Color playerCol2=;
    public ArrayList<clickButton> butts = new ArrayList<clickButton>();

    boolean menu = true;
    boolean game = false;
    boolean instructions = false;
    boolean options = false;



    int turn;
    int cellsize = 65;
    public cell[][] board = new cell[8][8];
    int selectedx, selectedy, selectPiece;
    boolean jumped = false;
    boolean willJump = false;

    public Main(){
        //on create


        try {
            BufferedReader br = new BufferedReader(new FileReader("Colors.txt"));
            try {
                String x=br.readLine();
                pC=Integer.parseInt(x);
                x=br.readLine();
                sC=Integer.parseInt(x);
                x=br.readLine();
                b1C=Integer.parseInt(x);
                x=br.readLine();
                b2C=Integer.parseInt(x);
                x=br.readLine();
                b3C=Integer.parseInt(x);
                br.close();

            } catch (IOException e) { }
        } catch (FileNotFoundException e) {
            b1C = 5;
            b2C = 1;
            b3C = 5;
            pC  = 0;
            sC  = 6;
        }

        primaryCol=COLS[pC];
        secondaryCol=COLS[sC];
        boardCol=COLS[b1C];
        backgroundCol=COLS[b2C];
        backdropCol=COLS[b3C];



        butts.add(new clickButton(this,primaryCol,secondaryCol,wc,290,300,50,"Play",true));
        butts.add(new clickButton(this,primaryCol,secondaryCol,wc,350,300,50,"How to Play",true));
        butts.add(new clickButton(this,primaryCol,secondaryCol,wc,410,300,50,"Options",true));
        butts.add(new clickButton(this,primaryCol,secondaryCol,wc,470,300,50,"Quit",true));

        butts.add(new clickButton(this,primaryCol,secondaryCol,10,10,75,35,"Back",false));
        for(int y = 0; y < 5; y++){
            butts.add(new clickButton(this,primaryCol,secondaryCol,520,y*80+140,35,35,"   <",false));
        }
        for(int y = 0; y < 5; y++){
            butts.add(new clickButton(this,primaryCol,secondaryCol,600,y*80+140,35,35,"   >",false));
        }



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                for(int x = 0; x < butts.size(); x++) {
                     butts.get(x).click(e.getX(), e.getY());
                }
                if(game){
                    gameclick(e.getX(), e.getY());
                }

            }
        });
        setFocusable(true);
    }

    public void OPclicked(int x,int y) {
        boolean increase;
        if(x==520){
            increase=false;
        }else{
            increase=true;
        }
        switch(y){
            case 140:
                if(increase){
                    if(pC==COLS.length-1){
                        pC=0;
                    }else{
                        pC++;
                    }
                }else{
                    if(pC==0){
                        pC=COLS.length-1;
                    }else{
                        pC--;
                    }
                }
                primaryCol=COLS[pC];
                break;
            case 220:
                if(increase){
                    if(sC==COLS.length-1){
                        sC=0;
                    }else{
                        sC++;
                    }
                }else{
                    if(sC==0){
                        sC=COLS.length-1;
                    }else{
                        sC--;
                    }
                }
                secondaryCol=COLS[sC];
                break;
            case 300:
                if(increase){
                    if(b1C==COLS.length-1){
                        b1C=0;
                    }else{
                        b1C++;
                    }
                }else{
                    if(b1C==0){
                        b1C=COLS.length-1;
                    }else{
                        b1C--;
                    }
                }
                boardCol=COLS[b1C];
                break;
            case 380:
                if(increase){
                    if(b2C==COLS.length-1){
                        b2C=0;
                    }else{
                        b2C++;
                    }
                }else{
                    if(b2C==0){
                        b2C=COLS.length-1;
                    }else{
                        b2C--;
                    }
                }
                backgroundCol=COLS[b2C];
                break;
            case 460:
                if(increase){
                    if(b3C==COLS.length-1){
                        b3C=0;
                    }else{
                        b3C++;
                    }
                }else{
                    if(b3C==0){
                        b3C=COLS.length-1;
                    }else{
                        b3C--;
                    }
                }
                backdropCol=COLS[b3C];
                break;
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("Colors.txt"));
            out.write(String.valueOf(pC));
            out.newLine();
            out.write(String.valueOf(sC));
            out.newLine();
            out.write(String.valueOf(b1C));
            out.newLine();
            out.write(String.valueOf(b2C));
            out.newLine();
            out.write(String.valueOf(b3C));
            out.close();
        } catch (IOException e) {}
        for(int i = 0; i < butts.size(); i++){
            butts.get(i).buttCol = primaryCol;
            butts.get(i).textCol = secondaryCol;
        }
        repaint();
    }

    private void  gameclick(int x, int y) {

        int boardx = (int) Math.floor((x-80)/65);
        int boardy = (int) Math.floor((y-80)/65);

        if(board[boardy][boardx].selected&& boardy!=selectedy && boardx!=selectedx){
            board[boardy][boardx].contained=selectPiece;

            board[selectedy][selectedx].contained=0;
            if(turn==1 && boardy==0){
                board[boardy][boardx].contained=turn+2;
            }else if(turn==2 && boardy==7){
                board[boardy][boardx].contained=turn+2;
            }

                if(boardx-selectedx>1){//right
                    if(boardy-selectedy>1){//UP
                        board[selectedy+1][selectedx+1].contained=0;
                    }else{//down
                        board[selectedy-1][selectedx+1].contained=0;
                    }
                    jumped = true;
                    selectedx=boardx;
                    selectedy=boardy;
                    selectPiece=board[boardy][boardx].contained;
                }
                if(boardx-selectedx<-1){//left
                    if(boardy-selectedy>1){//UP
                        board[selectedy+1][selectedx-1].contained=0;
                    }else{//down
                        board[selectedy-1][selectedx-1].contained=0;
                    }
                    jumped = true;
                    selectedx=boardx;
                    selectedy=boardy;
                    selectPiece=board[boardy][boardx].contained;
                }


            if(true){//stalemate

            }
            if(lost()){
                repaint();
                JOptionPane.showMessageDialog(frame,"Good Game!");
                toMenu();
            }

            for(int yy = 0; yy < 8; yy++){
                for(int xx = 0; xx < 8; xx++){
                    board[yy][xx].selected = false;
                }
            }
            if (jumped) {
                willJump = jumpAval(boardx,boardy);
                repaint();
            }


            if(willJump){

                return;
            } else {
                jumped = false;
                willJump = false;
                if(turn==1){
                    turn=2;
                }else{
                    turn=1;
                }
            }


            
        } else if((board[boardy][boardx].contained==turn || board[boardy][boardx].contained==turn+2) && !willJump){
            for(int yy = 0; yy < 8; yy++){
                for(int xx = 0; xx < 8; xx++){
                    board[yy][xx].selected = false;
                }
            }
            selectedx=boardx;
            selectedy=boardy;
            selectPiece=board[boardy][boardx].contained;
            board[boardy][boardx].selected = true;
            if(board[boardy][boardx].contained%2!=0){
                try {
                    if(board[boardy-1][boardx-1].contained==0){
                        board[boardy-1][boardx-1].selected=true;
                    }else
                    if(board[boardy-1][boardx-1].contained==2||board[boardy-1][boardx-1].contained==4){
                        if(board[boardy-2][boardx-2].contained==0){
                            board[boardy-2][boardx-2].selected=true;
                        }
                    }
                } catch (Exception e) {

                }
                try {
                    if(board[boardy-1][boardx+1].contained==0){
                        board[boardy-1][boardx+1].selected=true;
                    }else
                    if(board[boardy-1][boardx+1].contained==2||board[boardy-1][boardx+1].contained==4){
                        if(board[boardy-2][boardx+2].contained==0){
                            board[boardy-2][boardx+2].selected=true;
                        }
                    }
                } catch (Exception e) {

                }
                if(board[boardy][boardx].contained==3){
                    try {
                        if(board[boardy+1][boardx-1].contained==0){
                            board[boardy+1][boardx-1].selected=true;
                        }else
                        if(board[boardy+1][boardx-1].contained==2||board[boardy+1][boardx-1].contained==4){
                            if(board[boardy+2][boardx-2].contained==0){
                                board[boardy+2][boardx-2].selected=true;
                            }
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if(board[boardy+1][boardx+1].contained==0){
                            board[boardy+1][boardx+1].selected=true;
                        }else
                        if(board[boardy+1][boardx+1].contained==2||board[boardy+1][boardx+1].contained==4){
                            if(board[boardy+2][boardx+2].contained==0){
                                board[boardy+2][boardx+2].selected=true;
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            } else{
                try {
                    if(board[boardy+1][boardx-1].contained==0){
                        board[boardy+1][boardx-1].selected=true;
                    }else
                    if(board[boardy+1][boardx-1].contained==1||board[boardy+1][boardx-1].contained==3){
                        if(board[boardy+2][boardx-2].contained==0){
                            board[boardy+2][boardx-2].selected=true;
                        }
                    }
                } catch (Exception e) {

                }
                try {
                    if(board[boardy+1][boardx+1].contained==0){
                        board[boardy+1][boardx+1].selected=true;
                    }else
                    if(board[boardy+1][boardx+1].contained==1||board[boardy+1][boardx+1].contained==3){
                        if(board[boardy+2][boardx+2].contained==0){
                            board[boardy+2][boardx+2].selected=true;
                        }
                    }
                } catch (Exception e) {

                }
                if(board[boardy][boardx].contained==4){
                    try {
                        if(board[boardy-1][boardx-1].contained==0){
                            board[boardy-1][boardx-1].selected=true;
                        }else
                        if(board[boardy-1][boardx-1].contained==1||board[boardy-1][boardx-1].contained==3){
                            if(board[boardy-2][boardx-2].contained==0){
                                board[boardy-2][boardx-2].selected=true;
                            }
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if(board[boardy-1][boardx+1].contained==0){
                            board[boardy-1][boardx+1].selected=true;
                        }else
                        if(board[boardy-1][boardx+1].contained==1||board[boardy-1][boardx+1].contained==3){
                            if(board[boardy-2][boardx+2].contained==0){
                                board[boardy-2][boardx+2].selected=true;
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }

        }
        repaint();
    }
    private boolean jumpAval(int x , int y){
        boolean available = false;
        for(int yy = 0; yy < 8; yy++){
            for(int xx = 0; xx < 8; xx++){
                board[yy][xx].selected = false;
            }
        }
        if(board[y][x].contained%2!=0){
            try {
                if(board[y-1][x-1].contained==2||board[y-1][x-1].contained==4){
                    if(board[y-2][x-2].contained==0){
                        board[y-2][x-2].selected=true;
                        available = true;
                    }
                }
            } catch (Exception e) {}
            try {
                if(board[y-1][x+1].contained==2||board[y-1][x+1].contained==4){
                    if(board[y-2][x+2].contained==0){
                        board[y-2][x+2].selected=true;
                        available = true;
                    }
                }
            } catch (Exception e) {}
            if(board[y][x].contained==3){
                try {
                    if(board[y+1][x-1].contained==2||board[y+1][x-1].contained==4){
                        if(board[y+2][x-2].contained==0){
                            board[y+2][x-2].selected=true;
                            available = true;
                        }
                    }
                } catch (Exception e) {}
                try {
                    if(board[y+1][x+1].contained==2||board[y+1][x+1].contained==4){
                        if(board[y+2][x+2].contained==0){
                            board[y+2][x+2].selected=true;
                            available = true;
                        }
                    }
                } catch (Exception e) {}
            }
        } else{
            try {
                if(board[y+1][x-1].contained==1||board[y+1][x-1].contained==3){
                    if(board[y+2][x-2].contained==0){
                        board[y+2][x-2].selected=true;
                        available = true;
                    }
                }
            } catch (Exception e) {}
            try {
                if(board[y+1][x+1].contained==1||board[y+1][x+1].contained==3){
                    if(board[y+2][x+2].contained==0){
                        board[y+2][x+2].selected=true;
                        available = true;
                    }
                }
            } catch (Exception e) {}
            if(board[y][x].contained==4){
                try {
                    if(board[y-1][x-1].contained==1||board[y-1][x-1].contained==3){
                        if(board[y-2][x-2].contained==0){
                            board[y-2][x-2].selected=true;
                            available = true;
                        }
                    }
                } catch (Exception e) {}
                try {
                    if(board[y-1][x+1].contained==1||board[y-1][x+1].contained==3){
                        if(board[y-2][x+2].contained==0){
                            board[y-2][x+2].selected=true;
                            available = true;
                        }
                    }
                } catch (Exception e) {}
            }
        }

        return available;

    }
    private void toMenu() {

        menu = true;
        instructions = false;
        game = false;
        options = false;
        for(int i = 0; i < 4; i++){
            butts.get(i).active=false;
            butts.get(i).toggle();
        }
        for(int i = 4; i < butts.size(); i++){
            butts.get(i).active=true;
            butts.get(i).toggle();
        }

        frame.validate();
        frame.repaint();
    }
    public void back() {
        if(game){

        }
        toMenu();
    }
    private boolean lost() {
        int one = 0;
        int two = 0;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                if(board[y][x].contained==1 || board[y][x].contained==3){
                    one++;
                }
                if(board[y][x].contained==2 || board[y][x].contained==4){
                    two++;
                }
                if(one>0 && two>0){
                    return false;
                }

            }
        }
        return true;
    }

    public void clicked(String value){
        switch(value){
            case "Play":
                menu = false;
                instructions = false;
                game = true;
                options = false;
                drawgame();
                break;
            case "How to Play":
                menu = false;
                instructions = true;
                game = false;
                options = false;
                break;
            case "Options":
                menu = false;
                instructions = false;
                game = false;
                options = true;
                drawOptions();
                break;
            case "Quit":
                System.exit(0);
                break;
        }

        frame.repaint();
        frame.validate();
    }
    private void drawOptions(){

        for(int x = 0; x < butts.size(); x++){
            butts.get(x).toggle();
        }
            repaint();
    }
    private void drawgame() {
        for(int x = 0; x < 5; x++){
            butts.get(x).toggle();
        }
        turn = 1;
        for(int y = 0; y < 8;y++){
            for(int x = 0; x < 8; x++){
                board[y][x]=new cell(this, 0,cellsize);
            }
        }
        for(int y = 0; y < 3; y++){
            int x;
            if(y%2==0){
                x = 0;
            }else{
                x = 1;
            }
            while(x < 8){

                board[y][x]=new cell(this, 2,cellsize);
                x+=2;
            }
        }

        for(int y = 5; y < 8; y++){
            int x;
            if(y%2==0){
                x = 0;
            }else{
                x = 1;
            }
            while(x < 8){
                board[y][x]=new cell(this, 1,cellsize);
                x+=2;
            }
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(backdropCol);
        g.fillRect(0,0,WIDTH,HEIGHT);
        if(menu){
            for(int x = 0; x < butts.size(); x++){
                butts.get(x).paint(g2d);
            }
            g.setColor(primaryCol);
            g.setFont(new Font("CalibriLight", Font.PLAIN, 60));
            g.drawString("Checkers.",wc+20,210);
        }
        if(game){
            butts.get(4).paint(g2d);
            g.setColor(boardCol);
            for(int y = 0; y < 8; y++){
                int x;
                if(y%2==0){
                    x = 0;
                }else{
                    x = 1;
                }
                while(x < 8){
                    g.fillRect(70+(x*cellsize+10),60+(y*cellsize+10),cellsize,cellsize);
                    x+=2;
                }
            }
            g.setColor(backgroundCol);
            for(int y = 0; y < 8; y++){
                int x;
                if(y%2==0){
                    x = 1;
                }else{
                    x = 0;
                }
                while(x < 8){
                    g.fillRect(70+(x*cellsize+10),60+(y*cellsize+10),cellsize,cellsize);
                    x+=2;
                }
            }
            for(int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    board[y][x].paint(g2d,x,y);
                }
            }

        }
        if(options){
            for(int x = 4; x < butts.size(); x++){
                butts.get(x).paint(g2d);
            }
            g.setColor(primaryCol);
            g.setFont(new Font("CalibriLight", Font.PLAIN, 28));
            g.drawString("Primary Color",180,170);
            g.drawString("Secondary Color",180,250);
            g.drawString("Board Color",180,330);
            g.drawString("Background Color",180,410);
            g.drawString("Backdrop Color",180,490);
            g.fillRect(562,142,30,30);
            g.setColor(Color.black);
            g.drawRect(562,142,30,30);

            g.setColor(secondaryCol);
            g.fillRect(562,222,30,30);
            g.setColor(Color.black);
            g.drawRect(562,222,30,30);

            g.setColor(boardCol);
            g.fillRect(562,302,30,30);
            g.setColor(Color.black);
            g.drawRect(562,302,30,30);

            g.setColor(backgroundCol);
            g.fillRect(562,382,30,30);
            g.setColor(Color.black);
            g.drawRect(562,382,30,30);

            g.setColor(backdropCol);
            g.fillRect(562,462,30,30);
            g.setColor(Color.black);
            g.drawRect(562,462,30,30);
        }

    }
    public static void main(String[] args) {
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        frame.setIconImage(icon);
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main project = new Main();
        frame.add(project);
        frame.repaint();
        frame.validate();
    }


}
