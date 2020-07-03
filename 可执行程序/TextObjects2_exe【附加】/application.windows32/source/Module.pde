
class Module {
  int _xOffset;   //粒子位置
  int _yOffset;
  float _unit;   //粒子半径
  float chaUnit;   //粒子半径最大值
  int _index;  //粒子相位
  color _color;
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
  Module(int xOffsetTemp, int yOffsetTemp, float tempUnit, int tempIndex, color tempColor) {
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


  void update() {
    chaUnit = _unit;
    positionX = _xOffset;
    positionY =_yOffset;
  }

  void update1(float a1, float a2, float b1, float b2) {
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

      positionX = map(_xOffset, a1, a2, a1-0.5*t, a2+0.5*t); 
      positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1*t, 500-b2+0.1*t);
      //positionX =  _position.x;
      //positionY = _position .y;
    } 
    if(tt>150){
      positionX = map(_xOffset, a1, a2, a1-0.5*120, a2+0.5*120); 
      positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1*120, 500-b2+0.1*120);
    }
    
    if(tt>200){
        positionX = map(_xOffset, a1, a2, a1-0.5*120, a2+0.5*120); 
        positionY = map(500-_yOffset, 500-b1, 500-b2, 500-b1-0.1*120, 500-b2+0.1*120)+ 0.5*random(1, 5)*(tt-200)*(tt-200);
        if (positionY>540)  positionY = 540;
    }

    tt++;
  }

  void update2(float _zhouqi, float _xiangwei) {
    chaUnit = _unit;
    //  chaUnit = (_banjing+_unit)* sin(PI/_zhouqi * tt + _index*(_xiangwei/(2*PI)));
    tt++;

    positionX = _xOffset;
    positionY =_yOffset + 0.5*random(1, 5)*tt*tt;

    if (positionY>540)  positionY = 540;
  }

  void display() {
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
