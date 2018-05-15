package com.jhon.rain.common.exception;

import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>功能描述</br>全局异常处理器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/2/8 14:10
 */
@ControllerAdvice
public class RainGlobalExceptionHandler {

  /**
   * <pre>异常处理</pre>
   *
   * @param request
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public RainResponse<String> exceptionHandler(HttpServletRequest request, Exception e) {
    e.printStackTrace();
    if (e instanceof RainGlobalException) {
      RainGlobalException ex = (RainGlobalException) e;
      return RainResponse.error(ex.getRainCodeMsg());
    } else if (e instanceof BindException) {
      BindException ex = (BindException) e;
      List<ObjectError> errors = ex.getAllErrors();
      ObjectError objectError = errors.get(0);
      String msg = objectError.getDefaultMessage();
      return RainResponse.error(RainCodeMsg.BIND_ERROR.fillArgs(msg));
    } else {
      return RainResponse.error(RainCodeMsg.SERVER_ERROR);
    }
  }
}
