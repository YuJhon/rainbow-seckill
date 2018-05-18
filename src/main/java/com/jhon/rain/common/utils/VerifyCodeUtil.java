package com.jhon.rain.common.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Random;

/**
 * <p>功能描述</br>验证码工具类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 11:07
 */
public class VerifyCodeUtil {

  /**
   * <pre>随机运算符号</pre>
   */
  private static char[] ops = new char[]{'+', '-', '*'};

  /**
   * <pre>生成验证码表达式</pre>
   *
   * @param random 随机数
   * @return 验证码计算表达式
   */
  public static String generateVerifyCodeExpression(Random random) {
    int num1 = random.nextInt(10);
    int num2 = random.nextInt(10);
    int num3 = random.nextInt(10);
    char operatorOne = ops[random.nextInt(3)];
    char operatorTwo = ops[random.nextInt(3)];
    return new StringBuffer()
            .append(num1)
            .append(operatorOne)
            .append(num2)
            .append(operatorTwo)
            .append(num3)
            .toString();
  }

  /**
   * <pre>计算验证码表达式的值(使用java调用script的方式)</pre>
   *
   * @param verifyCodeExpression 验证码表达式
   * @return 计算结果
   */
  public static int calcVerifyCodeExpression(String verifyCodeExpression) {
    try {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("JavaScript");
      return (Integer) engine.eval(verifyCodeExpression);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
}
