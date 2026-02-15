package com.content.framework.security.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CAPTCHA_KEY_PREFIX = "captcha:";
    private static final long CAPTCHA_EXPIRE_TIME = 300L;

    /**
     * 生成验证码
     *
     * @return 验证码信息（uuid和图片Base64）
     */
    public Map<String, Object> generateCaptcha() {
        String uuid = IdUtil.simpleUUID();
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(150, 40, 4, 20);
        String code = captcha.getCode();
        
        String cacheKey = CAPTCHA_KEY_PREFIX + uuid;
        redisTemplate.opsForValue().set(cacheKey, code, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        
        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", captcha.getImageBase64());
        
        log.debug("生成验证码: uuid={}, code={}", uuid, code);
        return result;
    }

    /**
     * 验证验证码
     *
     * @param uuid 验证码唯一标识
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    public boolean validateCaptcha(String uuid, String code) {
        if (uuid == null || code == null) {
            return false;
        }
        
        String cacheKey = CAPTCHA_KEY_PREFIX + uuid;
        Object cachedCode = redisTemplate.opsForValue().get(cacheKey);
        
        if (cachedCode == null) {
            log.warn("验证码已过期: uuid={}", uuid);
            return false;
        }
        
        boolean result = code.equalsIgnoreCase(cachedCode.toString());
        
        if (result) {
            redisTemplate.delete(cacheKey);
            log.debug("验证码验证成功: uuid={}", uuid);
        } else {
            log.warn("验证码验证失败: uuid={}, input={}, expected={}", 
                uuid, code, cachedCode);
        }
        
        return result;
    }

    /**
     * 删除验证码
     *
     * @param uuid 验证码唯一标识
     */
    public void deleteCaptcha(String uuid) {
        if (uuid != null) {
            String cacheKey = CAPTCHA_KEY_PREFIX + uuid;
            redisTemplate.delete(cacheKey);
        }
    }
}
