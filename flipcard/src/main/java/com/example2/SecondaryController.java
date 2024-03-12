package com.example2;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.image.Image;

import java.security.SecureRandom;
import java.util.*;

public class SecondaryController {

    //所有imageView的變數宣告
    @FXML
    private ImageView pic;
    @FXML
    private ImageView btp00;
    @FXML
    private ImageView btp01;
    @FXML
    private ImageView btp10;
    @FXML
    private ImageView btp11;
    @FXML
    private ImageView btp20;
    @FXML
    private ImageView btp21;
    @FXML
    private ImageView btp30;
    @FXML
    private ImageView btp31;
    @FXML
    private ImageView btp40;
    @FXML
    private ImageView btp41;
    @FXML
    private ImageView btp50;
    @FXML
    private ImageView btp51;

    //ImageView的陣列，方便快速存取所有ImageView
    @FXML
    private ImageView [] btpic=new ImageView[12];
    
    //所有Button的變數宣告
    @FXML
    private Button bt00;
    @FXML
    private Button bt01;
    @FXML
    private Button bt10;
    @FXML
    private Button bt11;
    @FXML
    private Button bt20;
    @FXML
    private Button bt21;
    @FXML
    private Button bt30;
    @FXML
    private Button bt31;
    @FXML
    private Button bt40;
    @FXML
    private Button bt41;
    @FXML
    private Button bt50;
    @FXML
    private Button bt51;
    
    //Button的陣列，方便快速存取所有Button
    @FXML
    private Button [] bts=new Button[12];
    
    //time，用來控制動畫的速度(包括翻牌與反應)
    @FXML
    private float time;
    
    //anispeed，radioButton的群組
    @FXML
    private ToggleGroup anispeed = new ToggleGroup();
    //所有RadioButton的變數宣告
    @FXML
    private RadioButton rd1;
    @FXML
    private RadioButton rd2;
    @FXML
    private RadioButton rd3;

    //nametag，玩家名稱Label的變數宣告
    //score，分數Label的變數宣告
    //plus，連擊獎勵(額外加分)Label的變數宣告
    //nogood，錯誤數Label的變數宣告
    //restart，重來Button的變數宣告
    @FXML
    private Label nametag;
    @FXML
    private Label score;
    @FXML
    private Label plus;
    @FXML
    private Label nogood;
    @FXML
    private Button restart;

    //point:實際儲存分數的變數
    //add:實際儲存額外加分的變數
    //bad:實際儲存錯題數的變數
    //count:儲存答對的組數，用以改變動畫
    int point;
    int add;
    int bad;
    int count;


    //答題即時反應的圖片，共兩位玩家
    Image img1=new Image("file:src/main/resources/com/pic/think.png");
    Image img2=new Image("file:src/main/resources/com/pic/pick.png");
    Image img3=new Image("file:src/main/resources/com/pic/correct.png");
    Image img4=new Image("file:src/main/resources/com/pic/wrong.png");
    Image img5=new Image("file:src/main/resources/com/pic/next.png");
    Image img6=new Image("file:src/main/resources/com/pic/think2.png");
    Image img7=new Image("file:src/main/resources/com/pic/pick2.png");
    Image img8=new Image("file:src/main/resources/com/pic/correct2.png");
    Image img9=new Image("file:src/main/resources/com/pic/wrong2.png");
    Image img10=new Image("file:src/main/resources/com/pic/next2.png");
    
    //LRimgs，將答題即時反應圖片用陣列儲存，用以快速切換兩位玩家
    //LRindex，陣列的相對位置，會是5的倍數，代表不同玩家的參照起始位置
    Image [] LRimgs={img1,img2,img3,img4,img5,img6,img7,img8,img9,img10};
    int LRindex;
    String [] names={"亮亮","曉慧"};

    //按鈕裡，題目所使用的圖片
    Image imgQ=new Image("file:src/main/resources/com/pic/what.png");
    Image imgCT=new Image("file:src/main/resources/com/pic/CAT.png");
    Image imgDG=new Image("file:src/main/resources/com/pic/DOG.png");
    Image imgCW=new Image("file:src/main/resources/com/pic/COW.png");
    Image imgSP=new Image("file:src/main/resources/com/pic/SHEEP.png");
    Image imgPG=new Image("file:src/main/resources/com/pic/PIG.png");
    Image imgCH=new Image("file:src/main/resources/com/pic/CHICKEN.png");
    Image imgCT2=new Image("file:src/main/resources/com/pic/CAT2.png");
    Image imgDG2=new Image("file:src/main/resources/com/pic/DOG2.png");
    Image imgCW2=new Image("file:src/main/resources/com/pic/COW2.png");
    Image imgSP2=new Image("file:src/main/resources/com/pic/SHEEP2.png");
    Image imgPG2=new Image("file:src/main/resources/com/pic/PIG2.png");
    Image imgCH2=new Image("file:src/main/resources/com/pic/CHICKEN2.png");
    
    //imglist: 儲存亂數打亂後的圖片的順序
    //listp: 儲存亂數打亂後的圖片，其對應的圖與答案index
    //Aimg: 所有題目使用的圖片組成的陣列，方便快速存取
    //pair: 所有題目使用的圖片的對應關係，與Aimg是平行陣列，好一同存取
    Image [] imglist=new Image[12];
    int [] listp=new int[12];
    Image [] Aimg={imgCT,imgDG,imgCW,imgSP,imgPG,imgCH,imgCT2,imgDG2,imgCW2,imgSP2,imgPG2,imgCH2};
    int [] pair={0,1,2,3,4,5,0,1,2,3,4,5};
    
    //flipindex: 翻開的牌屬於哪個pair，最多儲存兩個數值，用來對答案
    //questGEN: 用來儲存亂數生成的index(0~11)，用來生成題目
    private static ArrayList<Integer> flipindex=new ArrayList<Integer>();
    private static ArrayList<Integer> questGEN=new ArrayList<Integer>();
    
    //初始化，同時也是重新開始按鈕會觸發的函數
    //依序執行以下動作
    //01 初始化答題即時反應的圖片，以及名稱
    //02 初始化所有按鈕及其中的圖片，同時初始化動畫速度的事件
    //03 初始化所有記分的數值(point,add,bad)以及標籤，動畫速度的參照時間(time)，答對題數(count)
    //04 生成題目
    public void initialize() {
        //01
        LRindex=0;
        pic.setImage(LRimgs[LRindex]);
        nametag.setText(names[0]);
        
        //02
        initIMGBT();initTIME();
        for(int i=0;i<12;i++){
            //System.out.println(btpic[i]);
            imglist[i]=imgQ;listp[i]=-1;
            btpic[i].setImage(imglist[i]);
        }

        //03
        time=1;point=0;add=0;bad=0;count=0;
        score.setText(Integer.toString(point));
        plus.setText(Integer.toString(add));
        nogood.setText(Integer.toString(bad));

        //04
        genq();
    }

    //為RadioButton新增監聽事件
    public void initTIME(){
        anispeed.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n){
                RadioButton rb =(RadioButton)anispeed.getSelectedToggle();
                if(rb==rd1){
                    time=2;
                }
                else if(rb==rd2){
                    time=1;
                }
                else{
                    time=(float)0.5;
                }
            } 
            
        });
    }

    //將Button與ImageView的陣列初始化，並新增題目按鈕與重來按鈕的事件
    public void initIMGBT() {
        btpic[0]=btp00;
        btpic[1]=btp01;
        btpic[2]=btp10;
        btpic[3]=btp11;
        btpic[4]=btp20;
        btpic[5]=btp21;
        btpic[6]=btp30;
        btpic[7]=btp31;
        btpic[8]=btp40;
        btpic[9]=btp41;
        btpic[10]=btp50;
        btpic[11]=btp51;
        bts[0]=bt00;
        bts[1]=bt01;
        bts[2]=bt10;
        bts[3]=bt11;
        bts[4]=bt20;
        bts[5]=bt21;
        bts[6]=bt30;
        bts[7]=bt31;
        bts[8]=bt40;
        bts[9]=bt41;
        bts[10]=bt50;
        bts[11]=bt51;
        for(int i=0;i<12;i++){
            final int ii=i;
            bts[i].setOnAction(event->{
                check(ii);
            });
            bts[i].setDisable(false);
        }
        restart.setOnAction(event->{
            initialize();
        });
    }

    //切換即時反應玩家用的函數
    public void nextpp(){
        if(LRindex<2){
            LRindex+=5;
            if(flipindex.size()==1){
                pic.setImage(LRimgs[LRindex+1]);
            }
            else{
                pic.setImage(LRimgs[LRindex]);
            }
            nametag.setText(names[1]);
            
        }
        else{
            LRindex=0;
            if(flipindex.size()==1){
                pic.setImage(LRimgs[LRindex+1]);
            }
            else{
                pic.setImage(LRimgs[LRindex]);
            }
            nametag.setText(names[0]);
        }
    }

    //四個timeline動畫的宣告，為了在播放一個動畫時，能停止其他的動畫
    Timeline t1;
    Timeline t2;
    Timeline t3;
    Timeline t4;

    //停止所有動畫，沒跑完的動畫就會直接停止，而不會跟新開始的動畫衝突
    public void stopp(){
        if(t1 !=null){
            t1.stop();
        }
        if(t2 !=null){
            t2.stop();
        }
        if(t3 !=null){
            t3.stop();
        }
        if(t4 !=null){
            t4.stop();
        }
    }

    //將即時反應玩家改成思考的圖片
    public void thinkp(){
        stopp();
        t1=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(pic.imageProperty(),LRimgs[LRindex] )),
            new KeyFrame(Duration.seconds(0.1), new KeyValue(pic.imageProperty(),LRimgs[LRindex+1]))
        );
        t1.play();
    }

    //將即時反應玩家改成答對的圖片
    public void correctp(){
        stopp();
        t2=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(pic.imageProperty(),LRimgs[LRindex+2])),
            new KeyFrame(Duration.seconds(1.0+0.2*time), new KeyValue(pic.imageProperty(),LRimgs[LRindex]))
        );
        t2.play();
    }

    //將即時反應玩家改成答錯的圖片
    public void wrongp(){
        stopp();
        t3=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(pic.imageProperty(),LRimgs[LRindex+3] )),
            new KeyFrame(Duration.seconds(0.5+0.2*time), new KeyValue(pic.imageProperty(),LRimgs[LRindex]))
        );
        t3.play();
    }

    //將即時反應玩家改成結束的圖片
    public void endp(){
        stopp();
        t4=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(pic.imageProperty(),LRimgs[LRindex+2] )),
            new KeyFrame(Duration.seconds(time), new KeyValue(pic.imageProperty(),LRimgs[LRindex+4]))
        );
        t4.play();
    }

    //翻開一張牌的動畫
    public void flipone(int index){
        Timeline tl=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(btpic[index].imageProperty(),imglist[index]))
            ,new KeyFrame(Duration.seconds(time), new KeyValue(btpic[index].imageProperty(),imglist[index]))
        );
        tl.play();
    }

    //關起一張牌的動畫
    public void flipback(int index){
        Timeline tl=new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(btpic[index].imageProperty(),imglist[index]))
            ,new KeyFrame(Duration.seconds(time), new KeyValue(btpic[index].imageProperty(),imgQ))
        );
        tl.play();
    }

    //檢查翻牌是否正確
    //如果還沒翻兩張牌，就執行翻牌動畫
    //如果已經翻開兩張牌，則執行判定
    //如果兩張的pair相同，而且使用者不是點擊同一張牌，則正確，否則為錯誤
    //判定完會改變計分區的所有標籤
    public void check(int index){
        if(flipindex.size()!=2){
            flipone(index);
            flipindex.add(index);
            if(flipindex.size()==1){
                thinkp();
            }
        }
        if(flipindex.size()==2){
            int id1=flipindex.get(0);
            int id2=flipindex.get(1);
            if(listp[id1]==listp[id2] && btpic[id1]!=btpic[id2]){
                bts[id1].setDisable(true);
                bts[id2].setDisable(true);
                point=point+10+add;
                add+=5;count++;
                //System.out.println(count);
                if(count==6){
                    endp();
                }
                else{
                    correctp();
                }
                
            }
            else{
                flipback(id1);flipback(id2);
                bad+=1;add=0;
                wrongp();
            }
            flipindex.clear();
            score.setText(Integer.toString(point));
            plus.setText(Integer.toString(add));
            nogood.setText(Integer.toString(bad));
        }
    }

    //生成問題，首先會先生成不重複的0~11數字
    //之後，根據生成數字的順序將Aimg放入imglist
    //並將pair放入listp
    public void genq(){
        questGEN.clear();
        SecureRandom sr=new SecureRandom();
        for(int i=0;i<12;i++){
            int rd=sr.nextInt(12);
            if(!questGEN.contains(rd)){
                questGEN.add(rd);
            }
            else{
                i--;
            }
        }
        for(int i=0;i<12;i++){
            imglist[i]=Aimg[questGEN.get(i)];
            listp[i]=pair[questGEN.get(i)];
            System.out.println(imglist[i]+" | "+listp[i]);
        }

    }

    //返回標題
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}