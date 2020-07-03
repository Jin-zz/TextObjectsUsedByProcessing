import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TextObjects2 extends PApplet {


int StartX = 0;  //遍历矩形区域的像素点 左上角坐标
int StartY = 0;
int rectWidth = 960;
int rectHeight = 540;//遍历矩形区域的像素点  矩形区域高宽
int count = 0;

Module[] mods;  //存放粒子数组
float unit = 7;   //粒子半径最大值
//float chaUnit;
int gridX = 4;  //遍历矩形区域时的 取样差
int gridY = 4;
int index = 0;  //粒子数组长度


int[] colors = {
  0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7, 0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4, 
  0xff009688, 0xff4CAF50, 0xff8BC34A, 0xffCDDC39, 0xffFFEB3B, 0xffFFC107, 0xffFF9800, 0xffFF5722
};
int myTextColor = color(245,237,8);//浅灰色文字
PFont _font ;
int Time = 0;
boolean isUpdate1= false;

int yellow = color(245,237,10);
int green = color(69,173,31);
float a1 = 0;
float b1 = 0;
float a2 = 0;
float b2 = 0;
float GG = 0;
Letter[] letters;
String ss = "Thanks :)";
public void setup() {
  
  _font = createFont("simhei.ttf", 36);

  background(green);
  createText1("今天是", 100);
  // createText2("2018/06/24",600);
  startModule();
}

public void draw() {
  background(green);


  if (Time/30==0) {
    createText1("今天是", 100);  
    startModule();
  }

  if (Time/30==2) {
    createText2("2018/06/25", 600, PI/4, 70, 100);
    startModule();
  }

  if (Time/30==3) {
    createText3("这", 300);
    startModule();
  }

  if (Time/30==3.5f) {
    createText3("这里", 300);
    startModule();
  }

  if (Time/30==4) {
    createText3("这里是", 300);
    startModule();
  }

  if (Time/30==5) {
    createText3("互动媒体", 100);
    createText4("课程设计", 400);
    startModule();
  }

  if (Time/30==8) {
    createText4("这", 300);
    startModule();
  }

  if (Time/30==8.5f) {
    createText4("这里", 300);
    startModule();
  }

  if (Time/30==9) {
    createText4("这里有", 300);
    startModule();
  }

  if (Time>300&&Time<390) {
    createText5("很多很多很多很多很多很多很多很多很多很多很多很多很多", 1500-Time*4);
    startModule();
  }

  if (Time/30==13) {
    isUpdate1 = true;
    createText0("TextObjects", 210);
    startMirrorModule();
  }

  if (Time>690&&Time<735) {
    isUpdate1 = false;
    createText2("希望你们喜欢它", 500, PI/120*(Time-690), 80, 150);
    startModule();
  }



  if (Time==750) {
    isUpdate1 = false;
    createText6(ss, (float)180);
  }
  if (Time>750) {
    for (int i = 0; i < letters.length; i ++ ) {
      letters[i].display();
      if (mousePressed) {
        letters[i].home();
      } else {
        letters[i].shake();
      }
    }
    startModule();
  }






  //if (Time%2==0 && isGravity == true) {
  //  updateModule();
  //}
  if (isUpdate1) {
    update1Module();
  } else {
    update2Module();
    ;
  }


  // updateModule();

  displayModule();

  Time++;
}

public void createText0(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);

  // float xx = 100;
  for (int ii = 0; ii<s.length(); ii++) {
    char c = s.charAt(ii);
    print("  "+c);
    textSize(100);
    fill(myTextColor);
    text(c, xx, rectHeight/2-40);

    xx = xx + textWidth(c);
  }
}


public void createText1(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);

  // float xx = 100;
  for (int ii = 0; ii<s.length(); ii++) {
    char c = s.charAt(ii);
    print("  "+c);
    textSize(random(50, 128));
    fill(myTextColor);
    text(c, xx, rectHeight/2);

    xx = xx + textWidth(c);
  }
}


public void createText2(String s, float xx, float angle, float rr, float r) {
  // float r = 100;
  // Start in the center and draw the circle
  pushMatrix();
  translate(xx, height/2);
  //noFill();
  //stroke(0);
  //ellipse(0, 0, r*2, r*2);

  rectMode(CORNER);
  textFont(createFont("simhei.ttf", 36));
  // textAlign(CENTER);
  textSize(rr);
  float arclength = 0;

  for (int i = 0; i < s.length (); i ++ ) {
    char currentChar = s.charAt(i);
    float w = textWidth(currentChar); 
    arclength += w/2;
    float theta = PI/2 + angle + arclength / r;

    pushMatrix();

    translate(r*cos(theta), r*sin(theta)); 
    rotate(theta + PI/2); 
    fill(myTextColor);
    text(currentChar, 0, 0);

    popMatrix();
    arclength += w/2;
  }
  popMatrix();
}



public void createText3(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);

  // float xx = 100;
  for (int ii = 0; ii<s.length(); ii++) {
    char c = s.charAt(ii);
    print("  "+c);
    textSize(50+ii*20);
    fill(myTextColor);
    text(c, xx, rectHeight/2);

    xx = xx + textWidth(c);
  }
}
public void createText4(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);

  // float xx = 100;
  for (int ii = 0; ii<s.length(); ii++) {
    char c = s.charAt(ii);
    print("  "+c);
    textSize(120-ii*20);
    fill(myTextColor);
    text(c, xx, rectHeight/2);

    xx = xx + textWidth(c);
  }
}

public void createText5(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);
  textSize(50);
  fill(myTextColor);
  text(s, xx, 3 * rectHeight/4);
}

public void createText6(String s, float x)
{
  letters = new Letter[s.length()];
  textSize(100);
  //int x = 216;
  for (int i = 0; i < s.length (); i ++ ) {
    letters[i] = new Letter(x, 270, s.charAt(i)); 
    x += textWidth(s.charAt(i));
  }
}

public void startModule() {

  count = 0;      //粒子个数
  index = 0;  //遍历粒子
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        count++;
      }
    }
  }


  //count = rectWidth * rectHeight;
  mods = new Module[count];
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        mods[index] = new Module(StartX+i, StartY+j, unit, index, yellow); 
        //  mods[index].update(50, 5);
        // mods[index].display();
        index++;
      }
    }
  }
}

public void startMirrorModule() {

  count = 0;      //粒子个数
  index = 0;  //遍历粒子
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        count++;
      }
    }
  }


  //count = rectWidth * rectHeight;
  mods = new Module[count];
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      int c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        //关于250对称
        mods[index] = new Module(StartX+i, 500+StartY-j, unit, index, yellow); 
        //  mods[index].update(50, 5);
        // mods[index].display();
        index++;
      }
    }
  }


  a1 =  mods[0]._xOffset;
  b1 =  mods[0]._yOffset;
  a2 =  mods[index-1]._xOffset;
  b2 =  mods[index-1]._yOffset;
}
public void update2Module() {

  for (int i = 0; i<index; i++) {
    mods[i].update2(50, 0);
  }
}

public void update1Module() {

  for (int i = 0; i<index; i++) {
    mods[i].update1(a1, a2, b1, b2);
  }
}
public void updateModule() {

  for (int i = 0; i<index; i++) {
    mods[i].update();
  }
}
public void displayModule() {

  for (int i = 0; i<index; i++) {
    mods[i].display();
  }
}

class Letter {
  char letter;
  
  // The object knows its original " home " location
  float homex,homey;
  
  // As well as its current location
  float x,y;
  
  Letter(float x_, float y_, char letter_) {
    homex = x = x_;
    homey = y = y_;
    letter = letter_;
  }
  
  // Display the letter
  public void display() {
    fill(color(245,237,8));
    textAlign(LEFT);
    textSize(80);
    text(letter,x,y);
  }
  
  // Move the letter randomly
  public void shake() {
    x += random(-2,2);
    y += random(-2,2);
  }
  
  // At any point, the current location can be set back to the home location by calling the home() function.
  public void home() { 
    x = homex;
    y = homey;
  }
}

class Module {
  int _xOffset;   //粒子位置
  int _yOffset;
  float _unit;   //粒子半径
  float chaUnit;   //粒子半径最大值
  int _index;  //粒子相位
  int _color;
  float tt = 0;

  float positionX;
  float positionY;
  float distance;
  PVector _position ;
  PVector velocity;
  PVector acceleration;
  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed
  // Contructor
  Module(int xOffsetTemp, int yOffsetTemp, float tempUnit, int tempIndex, int tempColor) {
    _xOffset = xOffsetTemp;
    _yOffset = yOffsetTemp; 
    _unit = tempUnit;
    _index = tempIndex;
    _color = tempColor;
    positionX = _xOffset;
    positionY = _yOffset;

    _position = new PVector(_xOffset, _yOffset);
    acceleration = new PVector(0, 0);
    velocity = new PVector(0, -2);
    maxspeed = 4;
    maxforce = 2;
  }


  public void update() {
    chaUnit = _unit;
    positionX = _xOffset;
    positionY =_yOffset;
  }

  public void update1(float a1, float a2, float b1, float b2) {
    chaUnit = _unit;
    positionX =_xOffset;
    //  positionY =_yOffset;
    if (positionY>500-_yOffset) {
      positionY = _yOffset-tt*5;
    }
    if (tt>30&&tt<150) {
      //positionX = map(_xOffset, a1, a2, (a2-a1)/random(0.2, 0.5)+a1, a2-(a2-a1)/random(0.2, 0.5)); 
      //seek( new PVector(map(_xOffset, a1, a2, 0, 960), map(_yOffset, b1, b2, 200, 300)));

      float t = tt-30;

      positionX = map(_xOffset, a1, a2, a1-0.5f*t, a2+0.5f*t); 
      positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1f*t, 500-b2+0.1f*t);
      //positionX =  _position.x;
      //positionY = _position .y;
    } 
    if(tt>150){
      positionX = map(_xOffset, a1, a2, a1-0.5f*120, a2+0.5f*120); 
      positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1f*120, 500-b2+0.1f*120);
    }
    
    if(tt>200){
        positionX = map(_xOffset, a1, a2, a1-0.5f*120, a2+0.5f*120); 
        positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1f*120, 500-b2+0.1f*120)+ 0.5f*random(1, 5)*(tt-200)*(tt-200);
        if (positionY>540)  positionY = 540;
    }

    tt++;
  }

  public void update2(float _zhouqi, float _xiangwei) {
    chaUnit = _unit;
    //  chaUnit = (_banjing+_unit)* sin(PI/_zhouqi * tt + _index*(_xiangwei/(2*PI)));
    tt++;

    positionX = _xOffset;
    positionY =_yOffset + 0.5f*random(1, 5)*tt*tt;

    if (positionY>540)  positionY = 540;
  }

  public void display() {
    fill(_color);
    noStroke();

    pushMatrix();
    //  translate(_xOffset, _yOffset);
    translate(positionX, positionY);
    //  ellipse(0, 0, _unit, _unit);
    ellipse(0, 0, chaUnit, chaUnit);

    popMatrix();
  }

  //void seek(PVector target) {
  //  distance = PVector.sub(target, _position).mag();
  //  applyForce(PVector.sub(PVector.sub(target, _position), velocity));
  //}

  //void applyForce(PVector force) {
  //  // We could add mass here if we want A = F / M
  //  acceleration.add(force);//实时更新加速度
  //  velocity.add(acceleration);
  //  velocity.limit(maxspeed);
  //  _position.add(velocity);
  //}
}
  public void settings() {  size(960, 540); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TextObjects2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
