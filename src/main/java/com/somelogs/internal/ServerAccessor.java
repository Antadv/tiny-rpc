package com.somelogs.internal;

import com.somelogs.annotation.RequestRoute;
import com.somelogs.model.Response;
import com.somelogs.model.ResponseStatus;
import com.somelogs.utils.HttpUtils;
import com.somelogs.utils.JsonUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * server accessor
 *
 * @author LBG - 2018/1/5 0005
 */
public class ServerAccessor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAccessor.class);

    private String SERVER_URL;

    public ServerAccessor(String serverUrl) {
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }
        this.SERVER_URL = serverUrl;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        RequestRoute methodAnnotation = method.getAnnotation(RequestRoute.class);
        if (methodAnnotation == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        RequestRoute typeAnnotation = method.getDeclaringClass().getAnnotation(RequestRoute.class);
        String postBody = JsonUtils.object2JSONString(objects[0]);
        Response response;
        try {
            String requestUrl = SERVER_URL + typeAnnotation.url() + methodAnnotation.url();
            LOGGER.info("Server accessor request url:" + requestUrl);
            String responseJson = HttpUtils.postJson(requestUrl, postBody);
            response = JsonUtils.readValue(responseJson, method.getGenericReturnType());
        } catch (Exception e) {
            LOGGER.error("Server accessor request error", e);
            response = new Response<>();
            response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
            response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
        return response;
    }
}
