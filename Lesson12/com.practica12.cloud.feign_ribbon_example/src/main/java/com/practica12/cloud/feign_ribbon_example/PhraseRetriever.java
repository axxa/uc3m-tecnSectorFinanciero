package com.practica12.cloud.feign_ribbon_example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhraseRetriever
{
    @Autowired
    private StringClient stringClient;

    @HystrixCommand(fallbackMethod = "retrieveFallbackGeoinfo")
    public String geoInfo()
    {
        return stringClient.geoinfo();
    }
    @HystrixCommand(fallbackMethod = "retrieveFallbackHome")
    
    public String home()
    {
        return stringClient.home();
    }
    
    public String retrieveFallbackGeoinfo()
    {
        return "Geo location Failed";
    }
    
    public String retrieveFallbackHome()
    {
        return "Service name HOME Failed";
    }
}