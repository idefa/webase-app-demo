package cn.lijunhua.patient.config;

import com.webank.webase.app.sdk.client.AppClient;
import com.webank.webase.app.sdk.config.HttpConfig;
import com.webank.webase.app.sdk.dto.req.ReqAppRegister;
import com.webank.webase.app.sdk.util.JacksonUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "webaseapp")
public class WebaseAppConfig {
    // 节点管理服务地址
    private String nodeManagerUrl;
    // 节点前置服务地址
    private String frontUrl;
    // 应用Key
    private String appKey;
    // 应用密码
    private String appSecret;

    // 是否加密传输
    private boolean transferEncrypt;

    private String appIp;
    private int appPort;
    private String appLink;

    @Bean(name = "AppClient")
    public AppClient getAppClient() {
        HttpConfig httpConfig = new HttpConfig(30, 30, 30);
        AppClient appClient = new AppClient(nodeManagerUrl, appKey, appSecret, transferEncrypt, httpConfig);
        System.out.println("testInitClient:" + JacksonUtil.objToString(appClient));
        ReqAppRegister req = new ReqAppRegister();
        req.setAppIp(appIp);
        req.setAppPort(appPort);
        req.setAppLink(appLink);
        appClient.appRegister(req);
        System.out.println("appRegister end.");
        return appClient;
    }


}
