import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TextObjects extends PApplet {



ControlP5 cp5;

int StartX = 0;  //遍历矩形区域的像素点 左上角坐标
int StartY = 0;
int rectWidth = 960;
int rectHeight = 540;//遍历矩形区域的像素点  矩形区域高宽

int UIGroupX = 0;
int UIGroupY = 140;
Group _group;
Module[] mods;  //存放粒子数组
//float unit;   //粒子半径最大值
//float chaUnit;
int gridX = 3;  //遍历矩形区域时的 取样差
int gridY = 3;
int index = 0;  //粒子数组长度
RadioButton r_shapes, r_effects, r_music;
Toggle t1_shape, t2_shape, t1_effect, t2_effect, t1_music, t2_music;
int isshape = 0;
int iseffects = 0;
int ismusic = 0;
int[] colors = {
  0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7, 0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4, 
  0xff009688, 0xff4CAF50, 0xff8BC34A, 0xffCDDC39, 0xffFFEB3B, 0xffFFC107, 0xffFF9800, 0xffFF5722
};
int myTextColor = color(255, 255, 250);  //浅灰色文字
boolean isFocus = false;

PVector mouse = new PVector(rectWidth/2, rectHeight/2);
PVector xiangxin = new PVector(rectWidth/2, rectHeight/2);
//PVector GG = new PVector(0, 0);
float a1 = 0;
float b1 = 0;
float a2 = 0;
float b2 = 0;
float GG = 0;

//sound//AudioSample kick;

Minim minim;
AudioPlayer kick;

public void setup() {
  // size(640, 360);
  
  startUI();
  createText();
  startModule();
  //sound
  minim = new Minim(this);
  kick = minim.loadFile("BD.mp3");
  if ( kick == null ) println("Didn't get kick!");
  // kick.trigger();
}

public void draw() {

  background(255);
  //fill(100);
  //noStroke();
  //rect(0, 0, rectWidth/4, rectHeight);

  //UI
  if (cp5.get(Textfield.class, "_input").isMouseOver()) {   //输入框获得焦点 
    isFocus = true;
  } else {                                       //输入框第一次失去焦点
    if (isFocus == true) {                
      createText();
      startModule();
    }
    isFocus = false;
  } 

  //sound
  for (int i = 0; i < kick.bufferSize() - 1; i++)
  {
    float x1 = map(i, 0, kick.bufferSize(), 0, width);
    float x2 = map(i+1, 0, kick.bufferSize(), 0, width);
    stroke(0);
    line(x1, 450 - kick.mix.get(i)*50, x2, 450 - kick.mix.get(i+1)*50);
  }


  float zhouqi = cp5.getController("_zhouqi").getValue();
  float xiangwei = cp5.getController("_xiangwei").getValue();
  float banjing = cp5.getController("_banjing").getValue();
  //int isshape = 0;


  //判断radiobutton1
  if (!(t1_shape.getState()||t2_shape.getState())) {
    if (isshape==0) t1_shape.setState(true);
    else t2_shape.setState(true);
  }

  if (t1_shape.getState()) {
    isshape = 0;
  }
  if (t2_shape.getState()) {
    isshape = 1;
  }


  //判断radiobutton2 设置单选
  if (!(t1_effect.getState()||t2_effect.getState())) {
    if (iseffects==0) t1_effect.setState(true);
    else t2_effect.setState(true);
  }

  if (t1_effect.getState()) {
    iseffects = 0;
  }
  if (t2_effect.getState()) {
    iseffects = 1;
  }

  //判断radiobutton3 设置单选
  if (!(t1_music.getState()||t2_music.getState())) {
    if (ismusic==0) t1_music.setState(true);
    else t2_music.setState(true);
  }

  if (t1_music.getState()) {
    ismusic = 0;
    kick.play();
  }
  if (t2_music.getState()) {
    ismusic = 1;
    kick.pause();
  }



  if (mousePressed == true && mouseX>rectWidth/4 ) { 
    mouse = new PVector(mouseX, mouseY);
  }
  fill(200);
  stroke(0);
  strokeWeight(2);
  ellipse(mouse.x, mouse.y, 24, 24);
  // GG = map(GG, a1,a2,(a2-a1)/4+a1,a2-(a2-a1)/4);

  for (int i = 0; i<index; i++) {
    //mods[index/2]._position
    mods[i].seek(mouse, iseffects);

    int j = (int)map(i, 0, index-1, 0, kick.bufferSize()-1);
    float kickmix = 20*kick.mix.get(j/2);

    if (ismusic==1) kickmix = 0;

    mods[i].update(zhouqi, xiangwei, banjing);
    mods[i].display(isshape, map(mods[i]._xOffset, a1, a2, (a2-a1)/random(2, 5)+a1, a2-(a2-a1)/random(2, 5)), kickmix, iseffects);
    //mods[i].display(isshape, 0,kickmix);
  }
}

public void createText() {
  rectMode(CORNER);
  textFont(createFont("FZSTK.TTF", 36));
  textAlign(CENTER);
  textSize(100);
  fill(myTextColor);
  text(cp5.get(Textfield.class, "_input").getText(), rectWidth/2, rectHeight/2);
}

public void startModule() {

  int count = 0;      //粒子个数
  index = 0;  //遍历粒子
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == color(255, 255, 250)) { 
        count++;
      }
    }
  }
  //print(count);

  //count = rectWidth * rectHeight;
  mods = new Module[count];
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        mods[index] = new Module(StartX+i, StartY+j, random(-3, 3), index, colors[PApplet.parseInt(random(0, 15))]); 
        mods[index].update(50, 5, 5);
        mods[index].display(0, 0, 0, iseffects);
        index++;
      }
    }
  }
  //float xiangxin_X = mods[index/2]._xOffset;
  //float xiangxin_Y = mods[index/2]._yOffset;
  //xiangxin = new PVector( mods[index-1]._xOffset, mods[index-1]._yOffset);

  a1 =  mods[0]._xOffset;
  b1 =  mods[0]._yOffset;
  a2 =  mods[index-1]._xOffset;
  b2 =  mods[index-1]._yOffset;
}




public void startUI() {
  //文本输入框
  cp5 = new ControlP5(this);
  //创捷UI组
  _group = cp5.addGroup("")
    .setPosition(0, 20)  //左下角
    .setWidth(rectWidth/4)
    .setHeight(20)
    .setBackgroundHeight(height)
    .setBackgroundColor(color(0, 50))
    ;
  // change the original colors
  cp5.setColorForeground(color(0, 0, 0));
  cp5.setColorBackground(color(0, 0, 0, 200)); 
  cp5.setFont(createFont("SIMYOU.TTF", 15));
  cp5.setColorCaptionLabel(color(0, 1));//更改标签颜色 透明

  RadioButton r_shapes;
  r_shapes = cp5.addRadioButton("radioButton")
    .setPosition(50, 25)
    .setSize(70, 35)
    .setColorForeground(color(120))
    .setColorActive(color(255))
    .setColorLabel(color(255))
    .setItemsPerRow(5)
    .setSpacingColumn(0)
    .addItem("cir", 0)
    .addItem("rec", 1)
    .setGroup(_group)
    ;
  t1_shape = r_shapes.getItem("cir");
  t1_shape.setImages(loadImage("button_cir_F.png"), loadImage("button_cir_T.png"), loadImage("button_cir_T.png"));
  t2_shape = r_shapes.getItem("rec");
  t2_shape.setImages(loadImage("button_rec_F.png"), loadImage("button_rec_T.png"), loadImage("button_rec_T.png"));
  r_shapes.activate(0);

  RadioButton r_effects;
  r_effects = cp5.addRadioButton("radioButton2")
    .setPosition(40, 65)
    .setSize(70, 35)
    .setColorForeground(color(120))
    .setColorActive(color(255))
    .setColorLabel(color(255))
    .setItemsPerRow(5)
    .setSpacingColumn(0)
    .addItem("effect1", 0)
    .addItem("effect2", 1)
    .setGroup(_group)
    ;
  t1_effect = r_effects.getItem("effect1");
  t1_effect.setImages(loadImage("button_yi_F.png"), loadImage("button_yi_T.png"), loadImage("button_yi_T.png"));
  t2_effect = r_effects.getItem("effect2");
  t2_effect.setImages(loadImage("button_er_F.png"), loadImage("button_er_T.png"), loadImage("button_er_T.png"));
  r_effects.activate(0);

  RadioButton r_music;
  r_music = cp5.addRadioButton("radioButton3")
    .setPosition(52, 105)
    .setSize(70, 35)
    .setColorForeground(color(120))
    .setColorActive(color(255))
    .setColorLabel(color(255))
    .setItemsPerRow(5)
    .setSpacingColumn(0)
    .addItem("music", 0)
    .addItem("nomusic", 1)
    .setGroup(_group)
    ;
  t1_music = r_music.getItem("music");
  t1_music.setImages(loadImage("button_open_F.png"), loadImage("button_open_T.png"), loadImage("button_open_T.png"));
  t2_music = r_music.getItem("nomusic");
  t2_music.setImages(loadImage("button_close_F.png"), loadImage("button_close_T.png"), loadImage("button_close_T.png"));
  r_music.activate(1);

  cp5.addTextfield("_input")
    .setPosition(UIGroupX, UIGroupY+50)   //针对父对象的相对坐标
    .setSize(200, 30)
    //.setFocus(true)
    // .setColor(color(255, 255, 0))
    .setText("please input")
    .setGroup(_group)
    ;
  cp5.addSlider("_zhouqi")
    .setPosition(UIGroupX, UIGroupY+130)
    .setSize(200, 20)
    .setRange(1, 100)
    .setValue(50)
    .setGroup(_group)
    ;
  cp5.addSlider("_xiangwei")
    .setPosition(UIGroupX, UIGroupY+210)
    .setSize(200, 20)
    .setRange(0, 2*PI)
    .setValue(5)
    .setGroup(_group)
    ;
  cp5.addSlider("_banjing")
    .setPosition(UIGroupX, UIGroupY+290)
    .setSize(200, 20)
    .setRange(0, 20)
    .setValue(5)
    .setGroup(_group)
    ;



  Textlabel myTextlabelA;
  Textlabel myTextlabelB;
  Textlabel myTextlabelC;
  Textlabel myTextlabelD;
  Textlabel myTextlabelE;
  Textlabel myTextlabelF;
  Textlabel myTextlabelG;
  myTextlabelA = cp5.addTextlabel("_labelA")
    .setText("文字")
    .setPosition(UIGroupX, UIGroupY+25)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;
  myTextlabelB = cp5.addTextlabel("_labelB")
    .setText("周期")
    .setPosition(UIGroupX, UIGroupY+105)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;
  myTextlabelC = cp5.addTextlabel("_labelC")
    .setText("相位")
    .setPosition(UIGroupX, UIGroupY+185)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;
  myTextlabelD = cp5.addTextlabel("_labelD")
    .setText("半径")
    .setPosition(UIGroupX, UIGroupY+265)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;

  myTextlabelE = cp5.addTextlabel("_labelE")
    .setText("音乐")
    .setPosition(UIGroupX, UIGroupY-30)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;

  myTextlabelF = cp5.addTextlabel("_labelF")
    .setText("粒子")
    .setPosition(UIGroupX, UIGroupY-70)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;

  myTextlabelG = cp5.addTextlabel("_labelG")
    .setText("形状")
    .setPosition(UIGroupX, UIGroupY-110)
    .setColorValue(color(0, 0, 0))
    //.setFont(createFont("Georgia", 20))
    .setGroup(_group)
    ;
}
class Module {
  float _xOffset;   //粒子位置
  float _yOffset;
  float _unit;   //粒子半径最大值
  float chaUnit;
  int _index;  //粒子相位
  float tt = 0;
  int _color;
  PVector velocity;
  PVector acceleration;
  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed
  PVector _position ;
  float distance;
  PVector retarget = new PVector(rectWidth/2, rectHeight/2);
  boolean GGG = true;
  // float xiangxin = 0;
  // Contructor
  Module(int xOffsetTemp, int yOffsetTemp, float tempUnit, int tempIndex, int tempColor) {
    _xOffset = xOffsetTemp;
    _yOffset = yOffsetTemp; 
    _unit = tempUnit;
    // chaUnit = tempUnit;
    _index = tempIndex;
    _color = tempColor;
    acceleration = new PVector(0, 0);
    velocity = new PVector(0, -2);
    _position = new PVector(_xOffset, _yOffset);
    maxspeed = 4;
    maxforce = 2;
  }

  // Custom method for updating the variables
  public void update(float _zhouqi, float xiangwei, float _banjing) {
    chaUnit = (_banjing+_unit)* sin(PI/_zhouqi * tt + _index*(xiangwei/(2*PI)));
    tt++;

    velocity.add(acceleration);
    velocity.limit(maxspeed);
    _position.add(velocity);
    acceleration.mult(0);  //与0相乘  重置
  }

  // Custom method for drawing the object
  public void display(int isshape, float GG, float wave, int iseffect) {
    fill(_color);
    noStroke();
    pushMatrix();
    //translate(-rectWidth/2,-rectHeight/2);
    //translate(_position.x+_xOffset, _position.y+_yOffset);


    if (iseffect==1) {
      //effect1 
      translate(_position.x, _position.y);
    } else {
      ///222 
      if (distance<10) {
        translate(_position.x-rectWidth/2+_xOffset, _position.y-rectHeight/2+_yOffset);
      } else {
        translate(_position.x-rectWidth/2+GG, _position.y-rectHeight/2+_yOffset);
      }
    }
    translate(0, -wave);

    if (isshape==0) {
      ellipse(0, 0, chaUnit, chaUnit);//ellipse(_xOffset, _yOffset, chaUnit, chaUnit);
    } else {
      rect(0, 0, chaUnit, chaUnit);
    }


    popMatrix();
  }



  public void seek(PVector target, int iseffect) {
    //target.add(xiangxin);

    distance = PVector.sub(target, _position).mag();
    //PVector desired = PVector.sub(target, _position);  // A vector pointing from the position to the target
    ////目标向量与自身位置相减 

    //desired.setMag(maxspeed);//设置向量的长度为maxspeed 进行一个缩放


    //PVector steer = PVector.sub(desired, velocity);
    //steer.limit(maxforce);  // Limit to maximum steering force
    //限制steer的长度不超过maxforce  如超过 转变为maxforce对于的向量值
    //applyForce(steer);

    if (iseffect==1) {
      if (distance >10 && GGG == true) {
        applyForce(PVector.sub(PVector.sub(target, _position), velocity));
      } else {
        applyForce(PVector.sub(PVector.sub(
          new PVector(_xOffset+target.x-rectWidth/2, _yOffset+target.y-rectHeight/2), _position), velocity));
        GGG = false;
      }
      if (retarget != target) {
        GGG = true;
      }
      retarget = target;
    } else {
      applyForce(PVector.sub(PVector.sub(target, _position), velocity));  //luanqibazao
    }
  }


  public void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);//实时更新加速度
  }
}
  public void settings() {  size(960, 540); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TextObjects" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
