package com.somelogs.client;

/**
 * client configuration
 *
 * @author LBG - 2018/1/5 0005
 */
public class ClientConfig {

    /**
     * qualified name of the specified client class
     */
    private String clientClassName;

    public ClientConfig() {
    }

    /**
     * client server url
     */
    private String serverUrl;

    public ClientConfig(String clientClassName, String serverUrl) {
        this.clientClassName = clientClassName;
        this.serverUrl = serverUrl;
    }

    public String getClientClassName() {
        return clientClassName;
    }

    public void setClientClassName(String clientClassName) {
        this.clientClassName = clientClassName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
