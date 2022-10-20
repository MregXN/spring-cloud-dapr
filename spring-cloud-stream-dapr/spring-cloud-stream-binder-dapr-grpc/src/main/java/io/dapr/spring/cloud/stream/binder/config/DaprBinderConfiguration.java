package io.dapr.spring.cloud.stream.binder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientGrpcBuilder;
//import io.dapr.client.DaprClientBuilder;
import io.dapr.spring.cloud.stream.binder.DaprGrpcService;
import io.dapr.spring.cloud.stream.binder.DaprMessageChannelBinder;
import io.dapr.spring.cloud.stream.binder.messaging.DaprMessageConverter;
import io.dapr.spring.cloud.stream.binder.properties.DaprBinderConfigurationProperties;
import io.dapr.spring.cloud.stream.binder.properties.DaprExtendedBindingProperties;
import io.dapr.spring.cloud.stream.binder.provisioning.DaprBinderProvisioner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;

/**
 * Dapr binder's Spring Boot AutoConfiguration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(Binder.class)
@EnableConfigurationProperties({ DaprBinderConfigurationProperties.class, DaprExtendedBindingProperties.class })
public class DaprBinderConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DaprBinderProvisioner daprBinderProvisioner() {
		return new DaprBinderProvisioner();
	}

	@Bean
	@ConditionalOnMissingBean
	public DaprMessageConverter daprMessageConverter() {
		return new DaprMessageConverter();
	}

	@Bean
	@ConditionalOnMissingBean
	public DaprMessageChannelBinder daprMessageChannelBinder(DaprBinderProvisioner daprBinderProvisioner,
			DaprExtendedBindingProperties daprExtendedBindingProperties, DaprClient daprClient,
			DaprGrpcService daprGrpcService,
			DaprMessageConverter daprMessageConverter) {
		return new DaprMessageChannelBinder(
				null,
				daprBinderProvisioner,
				daprExtendedBindingProperties,
				daprClient, daprGrpcService, daprMessageConverter);
	}




	@Bean
	@ConditionalOnMissingBean
	public DaprClient daprClient(DaprBinderConfigurationProperties properties) {
		DaprClient client = new DaprClientGrpcBuilder()
		//DaprClient client = new DaprClientBuilder()
				.build();
		return client;
	}
}
