package com.bird.say.hi.application.config;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 单体模式下，手搓的服务注册器
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-20
 */
@Component
@Slf4j
public class MultiServiceRegistrar {
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServeAddr;

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${grpc.server.port}")
    private String grpcServerPort;

    @PostConstruct
    public void registerInstance() throws Exception {
        String ipAddress = "localhost";

        NamingService naming = NamingFactory.createNamingService(nacosServeAddr);
        List<String> serviceNameList = Arrays.asList("say-hi-gateway", "say-hi-im-message");
        for (String serviceName : serviceNameList) {
            Instance instance = new Instance();
            instance.setIp(ipAddress);
            instance.setPort(serverPort);
            instance.setMetadata(Map.of(
                    "gRPC_port", grpcServerPort
            ));

            naming.registerInstance(serviceName, "DEFAULT_GROUP", instance);
            log.info("服务注册成功：{}", serviceName);
        }
    }
}

