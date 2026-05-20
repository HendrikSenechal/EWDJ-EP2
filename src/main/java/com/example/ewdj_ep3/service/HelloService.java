package com.example.ewdj_ep3.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService{
    public String sayHello(String name) {
        return "Hello %s!".formatted((name != null)?name:"");
    }
}