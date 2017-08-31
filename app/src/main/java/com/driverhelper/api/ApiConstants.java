package com.driverhelper.api;

public class ApiConstants {

    public static final String RELEASE_URL = "http://61.183.146.219:8080/";

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.RELEASE_URL:
                host = RELEASE_URL;
                break;
            case HostType.TEST_URL:     //TEST_URL
                host = RELEASE_URL;
                break;
            case HostType.LOCATION:     //本地
                host = RELEASE_URL;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
