package com.cafeteria.cafedealtura.security.aspect;

import com.cafeteria.cafedealtura.exception.UnauthorizedException;
import com.cafeteria.cafedealtura.security.annotation.RequireRole;
import com.cafeteria.cafedealtura.security.evaluator.RoleEvaluator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RoleAspect {

    @Autowired
    private RoleEvaluator roleEvaluator;

    @Before("@annotation(com.cafeteria.cafedealtura.security.annotation.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireRole requireRole = method.getAnnotation(RequireRole.class);

        if (!roleEvaluator.hasAnyRole(requireRole)) {
            throw new UnauthorizedException("No tienes los roles necesarios para acceder a este recurso");
        }
    }
}