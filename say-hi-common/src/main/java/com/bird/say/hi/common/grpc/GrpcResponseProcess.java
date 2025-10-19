package com.bird.say.hi.common.grpc;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Response处理类。需要满足有字段error_code, error_msg
 *
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
public class GrpcResponseProcess<Resp extends GeneratedMessageV3> implements ResponseProcess<Resp> {
    private static final Map<Class, GrpcResponseProcess> PROCESS_MAP = new ConcurrentHashMap<>();
    private Class<Resp> respClass;

    /**
     * 获取对应response的处理对象
     *
     * @param cl response的类对象
     * @param <Resp> response的类
     * @return response的处理对象
     */
    @SuppressWarnings("unchecked")
    public static <Resp extends GeneratedMessageV3> GrpcResponseProcess<Resp> getResponseProcess(Class<Resp> cl) {
        return (GrpcResponseProcess<Resp>) PROCESS_MAP.computeIfAbsent(cl, a -> new GrpcResponseProcess<>(cl));
    }

    private GrpcResponseProcess(Class<Resp> cl) {
        this.respClass = cl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Resp genResponse(int errorCode, String errorMsg) {
        try {
            Method method = respClass.getMethod("newBuilder");
            Object builder = method.invoke(null, (Object[]) null);

            Class builderClass = builder.getClass();
            builderClass.getMethod("setErrorCode", int.class).invoke(builder, errorCode);
            // errorMsg不为null才赋值，避免pb的npe
            if (errorMsg != null) {
                builderClass.getMethod("setErrorMsg", String.class).invoke(builder, errorMsg);
            }

            return (Resp) builderClass.getMethod("build").invoke(builder);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            throw new RuntimeException("genResponse unsupported class: " + respClass.getSimpleName(), e);
        }
    }

    @Override
    public int getErrorCode(Resp response) {
        // 从当前response中获取error_code的value
        Map<Descriptors.FieldDescriptor, Object> allFields = response.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            if (entry.getKey().getName().equals("error_code")) {
                return (Integer) entry.getValue();
            }
        }

        // 值为0时获取不到field（pb实现机制，具体可以看getAllFields方法），返回0
        return 0;
    }

    @Override
    public String getErrorMsg(Resp response) {
        // 从当前response中获取error_msg的value
        Map<Descriptors.FieldDescriptor, Object> allFields = response.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            if (entry.getKey().getName().equals("error_msg")) {
                return (String) entry.getValue();
            }
        }

        // 获取不到数据时，返回空字符串
        return StringUtils.EMPTY;
    }
}