package com.playtomic.tests.wallet.configuration;

import com.playtomic.tests.wallet.service.payment.PaymentFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentFactoryConfiguration {

    @Bean("paymentFactory")
    public ServiceLocatorFactoryBean getBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(PaymentFactory.class);
        return factoryBean;
    }
}