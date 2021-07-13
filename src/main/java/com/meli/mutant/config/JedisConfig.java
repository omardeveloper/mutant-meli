package com.meli.mutant.config;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;

@Configuration
@EnableCaching
public class JedisConfig {

	@Value("${mutant.jedis.server}")
	String server;

	@Value("${mutant.jedis.port}")
	int port;

	/**
	 * This Method have configuration of JedisConnectionFactory
	 * 
	 * @return JedisConnectionFactory
	 */
//	@Bean
//	public JedisConnectionFactory jedisConnectionFactory() {
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//		redisStandaloneConfiguration.setHostName(server);
//		redisStandaloneConfiguration.setPort(port);
//
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
//		return jedisConnectionFactory;
//	}
	
	@Bean
	  public LettuceConnectionFactory redisConnectionFactory() {
		
		String serverUrl = server + ":" + String.valueOf(port);
		List<String> nodes = Collections.singletonList(serverUrl);
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodes);
		return new LettuceConnectionFactory(clusterConfiguration);
		
	    //return new LettuceConnectionFactory(new RedisStandaloneConfiguration(server, port));
	  }

	/**
	 * This Method have configuration of RedisTemplate
	 * 
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
	
//	@Bean(name = "cacheManager")
//	  public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
//	    return RedisCacheManager.builder(jedisConnectionFactory)
//	        .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
//	        .build();
//	}
//	@Primary
//    @Bean(name = "cacheManager") // Default cache manager is infinite
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix)).build();
//    }
//
//    @Bean(name = "cacheManager1Hour")
//    public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory) {
//        Duration expiration = Duration.ofHours(1);
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix).entryTtl(expiration)).build();
//    }
}
