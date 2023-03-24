package com.accionmfb.ussd.ussdapplication.annotation;

import com.accionmfb.ussd.ussdapplication.context.ContextManager;
import com.accionmfb.ussd.ussdapplication.context.Item;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface UssdMenuHandler {
    String value() default Strings.EMPTY;
}
