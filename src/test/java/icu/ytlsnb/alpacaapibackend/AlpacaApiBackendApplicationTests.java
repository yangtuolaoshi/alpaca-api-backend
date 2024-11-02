package icu.ytlsnb.alpacaapibackend;

import icu.ytlsnb.alpacaapibackend.mapper.InterfaceInfoMapper;
import icu.ytlsnb.alpacaapibackend.service.UserInterfaceInfoService;
import icu.ytlsnb.alpacaapiclientsdk.client.AlpacaAPIClient;
import icu.ytlsnb.alpacaapiclientsdk.client.AlpacaAPIClientConfig;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(AlpacaAPIClientConfig.class)
class AlpacaApiBackendApplicationTests {
    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    private AlpacaAPIClient alpacaAPIClient;

    @Test
    public void testSDK() {
        alpacaAPIClient.msg1();
    }

    @Test
    public void testMyBatisPlus() {
//        System.out.println(interfaceInfoMapper.selectList(null));
        interfaceInfoMapper.deleteById(22);
    }

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void testInvokeTimeChange() {
        userInterfaceInfoService.invokeTimeChange(1L, 1L);
    }
}
