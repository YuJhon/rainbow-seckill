package com.jhon.rain.common.validator;

import com.jhon.rain.common.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>功能描述</br>电话校验器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 10:04
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

  private boolean required = false;

  @Override
  public void initialize(IsMobile constraintAnnotation) {
    required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (required) {
      return ValidatorUtil.isMobile(value);
    } else {
      if (StringUtils.isEmpty(value)) {
        return true;
      } else {
        return ValidatorUtil.isMobile(value);
      }
    }
  }
}
