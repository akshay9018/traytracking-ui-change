/*
 * package com.pointclickcare.nutrition.configuration;
 * import java.util.Arrays;
 * import org.apache.activemq.ActiveMQConnectionFactory;
 * import org.apache.activemq.RedeliveryPolicy;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.jms.annotation.EnableJms;
 * import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
 * import org.springframework.jms.core.JmsTemplate;
 * import com.pointclickcare.nutrition.repository.SystemConfigRepository;
 * @auther: Shilpa Sareen
 * @Configuration
 * @EnableJms
 * public class JmsConfig {
 * String BROKER_URL = "tcp://localhost:61616";
 * String BROKER_USERNAME = "admin";
 * String BROKER_PASSWORD = "admin";
 * private static final String BROKER_URL =
 * "ssl://b-cb8bf204-d2ea-44a2-949f-bc0621782d51-1.mq.ap-south-1.amazonaws.com:61617";
 * String BROKER_USERNAME = "testMq";
 * String BROKER_PASSWORD = "testMq#comp@ss";
 * @Autowired SystemConfigRepository systemConfigRepository;
 * private static final String BROKER_URL = "broker.url";
 * private static final String BROKER_USERNAME = "broker.username";
 * private static final String BROKER_PASSWORD = "broker.password";
 * @Bean
 * public org.apache.activemq.ActiveMQConnectionFactory connectionFactory()
 * {
 * ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
 * systemConfigRepository.findByPropertyName(BROKER_URL).getValue());
 * connectionFactory.setPassword(systemConfigRepository.findByPropertyName(BROKER_PASSWORD).getValue
 * ());
 * connectionFactory.setUserName(systemConfigRepository.findByPropertyName(BROKER_USERNAME).getValue
 * ());
 * RedeliveryPolicy policy = connectionFactory.getRedeliveryPolicy();
 * policy.setInitialRedeliveryDelay(50000);
 * policy.setBackOffMultiplier(2);
 * policy.setUseExponentialBackOff(true);
 * policy.setMaximumRedeliveries(2);
 * connectionFactory.setRedeliveryPolicy(policy);
 * connectionFactory.setTrustedPackages(Arrays.asList("com.pointclickcare.nutrition"));
 * return connectionFactory;
 * }
 * @Bean
 * public JmsTemplate jmsTemplate(){
 * JmsTemplate template = new JmsTemplate();
 * template.setConnectionFactory(connectionFactory());
 * template.setPubSubDomain(true);
 * return template;
 * }
 * @Bean
 * public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
 * // uses a pull approach. It sits in infinite loop to receive messages.
 * //alternate is The SimpleMessageListenerContainer (SMLC): uses a push approach.
 * DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
 * factory.setConnectionFactory(connectionFactory());
 * factory.setPubSubDomain(true);
 * return factory;
 * }
 * }
 */