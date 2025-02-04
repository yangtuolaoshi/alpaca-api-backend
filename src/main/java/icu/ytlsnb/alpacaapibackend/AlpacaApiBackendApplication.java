package icu.ytlsnb.alpacaapibackend;

import icu.ytlsnb.alpacaapiclientsdk.client.AlpacaAPIClientConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableDubbo
@SpringBootApplication
@MapperScan("icu.ytlsnb.alpacaapibackend.mapper")
@EnableConfigurationProperties(AlpacaAPIClientConfig.class)
public class AlpacaApiBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlpacaApiBackendApplication.class, args);
    }
}
