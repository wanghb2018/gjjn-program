package com.mochen.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 设置fastjson白名单
		ParserConfig.getGlobalInstance().addAccept("com.mochen.");

		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(fastJsonRedisSerializer);

		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair)
				.disableCachingNullValues();

		Set<String> cacheNames = new HashSet<>();
		cacheNames.add(Constant.CACHE_YEAR);
		cacheNames.add(Constant.CACHE_HOUR);

		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		configMap.put(Constant.CACHE_YEAR, config.entryTtl(Duration.ofDays(365)));
		configMap.put(Constant.CACHE_HOUR, config.entryTtl(Duration.ofHours(1)));

		return RedisCacheManager.builder(redisConnectionFactory).initialCacheNames(cacheNames)
				.withInitialCacheConfigurations(configMap).build();
	}

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.database}")
	private int datatabase;
	@Value("${spring.redis.session.host}")
	private String sessionHost;
	@Value("${spring.redis.session.port}")
	private int sessionPort;
	@Value("${spring.redis.session.database}")
	private int sessionDatabase;

	@Bean
	@Primary
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setDatabase(datatabase);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	@SpringSessionRedisConnectionFactory
	@Qualifier("sessionRedisConnectionFactory")
	public RedisConnectionFactory sessionRedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(sessionHost);
		redisStandaloneConfiguration.setPort(sessionPort);
		redisStandaloneConfiguration.setDatabase(sessionDatabase);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}
}
