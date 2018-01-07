package com.somelogs.factory;

import com.google.common.base.Preconditions;
import com.somelogs.client.ClientConfig;
import com.somelogs.internal.ServerAccessor;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.StringUtils;

/**
 * Client Factory
 * create client for consumer
 *
 * @author LBG - 2018/1/5 0005
 */
public class ClientFactory {

    private static final ServerAccessor ACCESSOR = ServerAccessor.singleInstance();

    private ClientFactory() {}

    public static Object create(ClientConfig config) {
        Preconditions.checkNotNull(config, "create client config is null");
        Preconditions.checkArgument(StringUtils.isNotBlank(config.getClientClassName()),
                "class qualified name is blank when creating client");
        Preconditions.checkArgument(StringUtils.isNotBlank(config.getServerUrl()),
                "client server url is blank when creating client");
        String clientClassName = config.getClientClassName();
        Class<?> clientClass;
        try {
            clientClass = Class.forName(clientClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("create client <" + config.getClientClassName() + "> error", e);
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clientClass);
        enhancer.setCallback(ACCESSOR);
        ServerAccessor.addClient(clientClass, config.getServerUrl());
        return clientClass.cast(enhancer.create());
    }
}
