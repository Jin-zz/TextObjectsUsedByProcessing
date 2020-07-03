
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


color[] colors = {
  #f44336, #e91e63, #9c27b0, #673ab7, #3f51b5, #2196f3, #03a9f4, #00bcd4, 
  #009688, #4CAF50, #8BC34A, #CDDC39, #FFEB3B, #FFC107, #FF9800, #FF5722
};
color myTextColor = color(245,237,8);//浅灰色文字
PFont _font ;
int Time = 0;
boolean isUpdate1= false;

color yellow = color(245,237,10);
color green = color(69,173,31);
float a1 = 0;
float b1 = 0;
float a2 = 0;
float b2 = 0;
float GG = 0;
Letter[] letters;
String ss = "Thanks :)";
void setup() {
  size(960, 540);
  _font = createFont("simhei.ttf", 36);

  background(green);
  createText1("今天是", 100);
  // createText2("2018/06/24",600);
  startModule();
}

void draw() {
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

  if (Time/30==3.5) {
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

  if (Time/30==8.5) {
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

void createText0(String s, float xx) {
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


void createText1(String s, float xx) {
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


void createText2(String s, float xx, float angle, float rr, float r) {
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



void createText3(String s, float xx) {
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
void createText4(String s, float xx) {
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

void createText5(String s, float xx) {
  rectMode(CORNER);
  textFont(_font);
  // textAlign(CENTER);
  // textSize(150);
  textSize(50);
  fill(myTextColor);
  text(s, xx, 3 * rectHeight/4);
}

void createText6(String s, float x)
{
  letters = new Letter[s.length()];
  textSize(100);
  //int x = 216;
  for (int i = 0; i < s.length (); i ++ ) {
    letters[i] = new Letter(x, 270, s.charAt(i)); 
    x += textWidth(s.charAt(i));
  }
}

void startModule() {

  count = 0;      //粒子个数
  index = 0;  //遍历粒子
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      color c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        count++;
      }
    }
  }


  //count = rectWidth * rectHeight;
  mods = new Module[count];
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      color c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        mods[index] = new Module(StartX+i, StartY+j, unit, index, yellow); 
        //  mods[index].update(50, 5);
        // mods[index].display();
        index++;
      }
    }
  }
}

void startMirrorModule() {

  count = 0;      //粒子个数
  index = 0;  //遍历粒子
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      color c = get(StartX+i, StartY+j); 
      if (c == myTextColor) { 
        count++;
      }
    }
  }


  //count = rectWidth * rectHeight;
  mods = new Module[count];
  for (int i = 0; i<rectWidth; i += gridX ) {
    for (int j = 0; j<rectHeight; j += gridY ) {
      color c = get(StartX+i, StartY+j); 
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
void update2Module() {

  for (int i = 0; i<index; i++) {
    mods[i].update2(50, 0);
  }
}

void update1Module() {

  for (int i = 0; i<index; i++) {
    mods[i].update1(a1, a2, b1, b2);
  }
}
void updateModule() {

  for (int i = 0; i<index; i++) {
    mods[i].update();
  }
}
void displayModule() {

  for (int i = 0; i<index; i++) {
    mods[i].display();
  }
}
