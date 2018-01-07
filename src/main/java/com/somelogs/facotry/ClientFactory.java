package com.somelogs.facotry;

import com.google.common.base.Preconditions;
import com.somelogs.client.BaseClient;
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

    private ServerAccessor ACCESSOR;

    public ClientFactory(String serverUrl) {
        ACCESSOR = new ServerAccessor(serverUrl);
    }

    public BaseClient create(ClientConfig config) {
        Preconditions.checkNotNull(config, "create client config is null");
        Preconditions.checkArgument(StringUtils.isNotBlank(config.getClientClassName()),
                "class qualified name is blank when creating client");
        Preconditions.checkArgument(StringUtils.isNotBlank(config.getServerUrl()),
                "client server url is blank when creating client");
        Class<?> clientClass;
        try {
            clientClass = Class.forName(config.getClientClassName());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("create client <" + config.getClientClassName() + "> error", e);
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clientClass);
        enhancer.setCallback(ACCESSOR);
        return (BaseClient) enhancer.create();
    }
}
