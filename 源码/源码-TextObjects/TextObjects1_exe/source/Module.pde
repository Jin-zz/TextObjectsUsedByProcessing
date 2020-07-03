class Module {
  float _xOffset;   //粒子位置
  float _yOffset;
  float _unit;   //粒子半径最大值
  float chaUnit;
  int _index;  //粒子相位
  float tt = 0;
  color _color;
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
  Module(int xOffsetTemp, int yOffsetTemp, float tempUnit, int tempIndex, color tempColor) {
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
  void update(float _zhouqi, float xiangwei, float _banjing) {
    chaUnit = (_banjing+_unit)* sin(PI/_zhouqi * tt + _index*(xiangwei/(2*PI)));
    tt++;

    velocity.add(acceleration);
    velocity.limit(maxspeed);
    _position.add(velocity);
    acceleration.mult(0);  //与0相乘  重置
  }

  // Custom method for drawing the object
  void display(int isshape, float GG, float wave, int iseffect) {
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



  void seek(PVector target, int iseffect) {
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


  void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);//实时更新加速度
  }
}
