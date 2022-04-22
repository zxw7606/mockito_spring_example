package com.example.mockito_spring_example.base;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.mockito_spring_example.base.MockitoBaseTests.App;
import lombok.Data;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


@SpringBootTest(classes = {App.class})
public class MockitoBaseTests {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Autowired
    @InjectMocks
    @Spy
    private TestBeanA testBeanA;

    @MockBean
//    按理来说@Mock 可以被 @InjectMocks 注解检测并注入 但是测试的时候不行,不知道为什么
//    @Mock

    private TestBeanB testBeanB;

    @Before
    public void before() {
//        帮助查找当前测试Bean下包含@Mock注解的对象
//        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInjectDependencyMockObject() {
        when(testBeanB.say()).thenReturn("mock b");
        assertThat(testBeanA.getTestBeanB().say()).isEqualTo("mock b");
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class App{

        @Bean
        public TestBeanA testBeanA() {
            return new TestBeanA();
        }

        @Bean
        public TestBeanB testBeanB() {
            return new TestBeanB();
        }
    }

}

@Data
class TestBeanA{
    @Autowired
    private TestBeanB testBeanB;

}


@Data
class TestBeanB{

    public String say() {
        return "b";
    }

}