package com.practica12.cloud.feign_ribbon_example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
* Clase que representa una interfaz para feign
*/
@FeignClient("ZUULSERVER")
public interface StringClient
{
    @RequestMapping(method = RequestMethod.GET, value = "/microservicestring")
    public String home();
   
    @RequestMapping(method = RequestMethod.GET, value = "/locate")
    public String geoinfo();
   
}
