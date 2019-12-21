package com.practica12.cloud.feign_ribbon_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestHomeController
{
    @Autowired
    private StringClient stringClient;
    @Autowired
    private PhraseRetriever phraseRetriever;
    
    @RequestMapping("/test_hystrix")
    public String printHystrixPhrase()
    {
        String results = phraseRetriever.home() + " I'm at HYSTRIX " +
        phraseRetriever.geoInfo();
        return results;
    }

    @RequestMapping("/")
    public String printPhrase()
    {
        return stringClient.home() + " I'm at " + stringClient.geoinfo();
    }
}