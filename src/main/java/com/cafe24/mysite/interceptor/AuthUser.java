package com.cafe24.mysite.interceptor;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthUser {
	public enum Role {ADMIN, USER}
}
