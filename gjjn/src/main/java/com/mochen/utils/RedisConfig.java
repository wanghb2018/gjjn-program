package com.mochen.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.alibaba.fastjson.parser.ParserConfig;

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
}
