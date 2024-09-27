//package com.fang.screw.communal.aop;
//
//import cn.hutool.http.HttpRequest;
//import com.fang.screw.communal.annotation.Decrypt;
//import com.fang.screw.communal.annotation.Encrypt;
//import com.fang.screw.communal.utils.RsaUtils;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.binary.Base64;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.ServletRequestAttributeEvent;
//import javax.servlet.http.HttpServletRequest;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Map;
//
//import static org.bouncycastle.asn1.x509.ObjectDigestInfo.publicKey;
//
///**
// * @FileName SafetyAspect
// * @Description AES + RSA 加解密AOP处理
// * @Author yaoHui
// * @date 2024-09-27
// **/
//@Aspect
//@Component
//@Slf4j
//public class SafetyAspect {
//
//    @Pointcut(value = "execution(public * com.fang.screw.*.controller.*.*(..))")
//    public void safetyAspect(){
//
//    }
//
//    @Around(value = "safetyAspect()")
//    public Object around(ProceedingJoinPoint pjp){
//
//        try{
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            if(ObjectUtils.isEmpty(attributes)){
//                return null;
//            }
//            HttpServletRequest request = attributes.getRequest();
//            String httpMethod = request.getMethod().toLowerCase();
//            Method method = ((MethodSignature)pjp.getSignature()).getMethod();
//            Annotation[] annotations = method.getAnnotations();
//            Object[] args = pjp.getArgs();
//
//            boolean hasDecrypt = false;
//            int decryptType = 0;
//            boolean hasEncrypt = false;
//            int encryptType = 0;
//
//            for(Annotation annotation : annotations){
//                if (annotation.annotationType() == Decrypt.class){
//                    hasDecrypt = true;
//                    Decrypt decrypt = (Decrypt) annotation;
//                    decryptType = decrypt.value();
//                }
//                if(annotation.annotationType() == Encrypt.class){
//                    hasEncrypt = true;
//                    Encrypt encrypt = (Encrypt) annotation;
//                    encryptType = encrypt.value();
//                }
//            }
//
//            String AesPublicKey = null;
//
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//
//            if("post".equals(httpMethod) && hasDecrypt){
//
//                Map<String, String[]>  map = request.getParameterMap();
//
//                log.info(request.getParameterMap().toString());
//                //使用RSA私钥进行解密
//                if(decryptType == 1){
//                    String privateKey = RsaUtils.getPrivateKey();
//
//                }
//
//                if(decryptType == 0){
//
//                }
//
////                byte[] plaintext = RsaUtils.decryptByPrivateKey(Base64.decodeBase64(date),RsaUtils.getPrivateKey());
////                String decrypt = Base64.encodeBase64String(plaintext);
////                设置到方法的形参中，目前只能设置只有一个参数的情况
////                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//                if(args.length > 0){
////                    args[0] = mapper.readValue(decrypt, args[0].getClass());
//                }
//
//            }
//
//            //执行并替换最新形参参数   PS：这里有一个需要注意的地方，method方法必须是要public修饰的才能设置值，private的设置不了
//            Object o = pjp.proceed(args);
//
//            return o;
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//}
