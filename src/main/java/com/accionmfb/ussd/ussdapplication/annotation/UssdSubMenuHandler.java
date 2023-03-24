package com.accionmfb.ussd.ussdapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UssdSubMenuHandler
{
    String value() default "";
}
