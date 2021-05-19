package com.udacity.jwdnd.course1.cloudstorage;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestClassDemo {
    @Test
    @Order(1)
    public void test2(){
        System.out.println("Second");
    }
    @Test
    public void test1(){
        System.out.println("First");
    }

    @Test
    @Order(2)
    public void test3(){
        System.out.println("Third");
    }
}
