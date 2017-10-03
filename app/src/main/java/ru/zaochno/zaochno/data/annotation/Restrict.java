package ru.zaochno.zaochno.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ru.zaochno.zaochno.data.enums.UserAuthLevel;
import ru.zaochno.zaochno.data.enums.UserType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Restrict {
    UserType userType() default UserType.DEFAULT;

    UserAuthLevel userAuthLevel() default UserAuthLevel.ANONYMOUS;
}