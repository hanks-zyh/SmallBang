package xyz.hanks.library;

/**
 * Created by hanks on 15/12/23.
 */
public class SmallBang {
    // 将下面的view变小
    // 画圆半径从小到大,同时颜色渐变
    // 当半径到达 MAX_RADIUS, 中间画空心圆,空闲圆半径变大
    // 当空心圆半径达到 MAX_RADIUS - RINGWIDTH, 此时变成圆环,在圆环上生成个DOT_NUMBER个小圆,均匀分布
    // 空心圆继续变大,逐渐圆环消失; 同时小圆向外扩散,扩散过程小圆半径减小,颜色渐变;同时下面的view逐渐变大
}
