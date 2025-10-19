package com.bird.say.hi.common.grpc;

import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.sdk.common.ErrorCode;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.function.Consumer;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
@Slf4j
public class GrpcTemplate {
    /**
     * 执行调用业务
     */
    public static <Req extends GeneratedMessageV3, Res extends GeneratedMessageV3> void execute(
            String invokeName, Req req, Consumer<Req> checkParams, ProcessFunc<Req, Res> process,
            ResponseProcess<Res> responseProcess, StreamObserver<Res> streamObserver) {
        StopWatch stopWatch = StopWatch.createStarted();
        Res resp = null;
        try {
            // 检查参数
            if (checkParams != null) {
                checkParams.accept(req);
            }
            // 业务执行
            resp = process.apply(req);
        } catch (IllegalArgumentException | IllegalStateException argumentException) {
            log.error("invoke [{}] failed for arguments. cost:{}ms, request:{}",
                    invokeName, stopWatch.getTime(), ProtoUtils.oneLine(req), argumentException);
            resp = responseProcess.genResponse(ErrorCode.ILLEGAL_PARAMETER_VALUE, argumentException.getMessage());
        } catch (Throwable throwable) {
            log.error("invoke [{}] failed. cost:{}ms, request:{}", invokeName, stopWatch.getTime(), ProtoUtils.oneLine(req), throwable);
            resp = responseProcess.genResponse(ErrorCode.SYSTEM_BUSY_VALUE, "系统繁忙");
        } finally {
            log.info("grpc invoke [{}]. cost:{}ms, request:{}, processResult={}",
                    invokeName, stopWatch.getTime(), ProtoUtils.oneLine(req), ProtoUtils.oneLine(resp));
            // 错误码统一打点
            if (resp != null) {
                int errorCode = responseProcess.getErrorCode(resp);
                if (errorCode != ErrorCode.SUCCESS_VALUE) {

                }
            }
            // 结果返回
            streamObserver.onNext(resp);
            streamObserver.onCompleted();
        }
    }

    /**
     * 处理类
     */
    @FunctionalInterface
    public interface ProcessFunc<I, O> {

        /**
         * 执行
         *
         * @param input i
         * @return r
         */
        O apply(I input) throws Throwable;
    }

    /**
     * 处理类，请求失败时返回错误码，错误信息
     * 对外sdk接口需要实现这个方法，用于服务可用性统计
     */
    @FunctionalInterface
    public interface ProcessException<O> {

        /**
         * 执行
         *
         * @param errorCode i
         * @return r
         */
        O apply(int errorCode, String errorMsg);
    }

    /**
     * 处理类
     */
    @FunctionalInterface
    public interface ProcessThrottling<I, O> {

        /**
         * 执行
         *
         * @param input i
         * @return r
         */
        O apply(I input);
    }
}
