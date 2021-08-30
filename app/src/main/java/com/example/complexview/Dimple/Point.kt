package com.example.complexview.Dimple

//点的属性
class Point ( var x:Float,//X坐标
              var y:Float,//Y坐标
              var radius:Float,//半径
              var speed:Float,//速度
              var alpha: Int,//透明度
              var maxOffset: Float=300f,//定义一个最大距离，超过最大距离就重置
              var offset: Int,//当前移动距离
              var angle: Double,//粒子角度
)