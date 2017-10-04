package ru.zaochno.zaochno.data.annotation.processor;

import android.content.Context;
import android.util.Log;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import ru.zaochno.zaochno.data.annotation.Restrict;
import ru.zaochno.zaochno.data.provider.AuthProvider;

public class RestrictProcessor {
    private static final String TAG = "RestrictProcessor";

    public static void bind(Context context) {
        if (context == null)
            return;

        Boolean isLogged = AuthProvider.getInstance(context).isAuthenticated();

        for (Method method : context.getClass().getMethods()) {
            Restrict restrict = method.getAnnotation(Restrict.class);

            if (restrict != null) {
                switch (restrict.userAuthLevel()) {
                    case ANONYMOUS:
                        if (!isLogged)
                            runMethod(context, method);
                        break;
                    case LOGGED:
                        if (isLogged)
                            runMethod(context, method);
                        break;
                }
            }
        }
    }

    private static void runMethod(Context parent, Method method) {
        try {
            method.invoke(parent, null);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "runMethod: ", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(TAG, "runMethod: ", e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e(TAG, "runMethod: ", e);
            e.printStackTrace();
        }
    }

    public static void runAllAnnotatedWith(Class<? extends Annotation> annotation)
            throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

        for (Method m : methods) {
            // for simplicity, invokes methods as static without parameters
            m.invoke(null);
        }
    }
}
