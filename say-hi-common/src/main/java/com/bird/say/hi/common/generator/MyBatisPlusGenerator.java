package com.bird.say.hi.common.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;

import static com.bird.say.hi.common.generator.MyBatisPlusGenerator.ModelName.MESSAGE;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
public class MyBatisPlusGenerator {
    @Getter
    @AllArgsConstructor
    enum ModelName {
        GATEWAY("say-hi-gateway", ""),
        CHAT("say-hi-im-chat", "im.chat"),
        GROUP("say-hi-im-group", "im.group"),
        MESSAGE("say-hi-im-message", "im.message"),
        INTERACTION("say-hi-interaction", ""),
        USER("say-hi-user", ""),
        ;

        private final String moduleName;
        private final String modulePackage;

    }

    public static void main(String[] args) {
        ModelName modelName = MESSAGE;
        String tableName = "message";
        String path = Paths.get(System.getProperty("user.dir")) // say-hi-server 根项目路径
                + "/" + modelName.getModuleName();


        FastAutoGenerator.create("jdbc:mysql://192.168.68.198:3306/say_hi", "root", "root")
                .globalConfig(builder -> {
                    builder.author("Bird") // 设置作者
                            .commentDate("yyyy-MM-dd")
                            .disableOpenDir()
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(path + "/src/main/java");  // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.TINYINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.bird.say.hi") // 设置父包名
                                .moduleName(modelName.getModulePackage()) // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, path + "/src/main/resources/mapper"))  // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                                builder.addInclude(tableName) // 设置需要生成的表名
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                        .controllerBuilder().disable()
//                                        .controllerBuilder().enableFileOverride()
                                        .serviceBuilder().disable() // 感觉service没啥必要，先禁用了吧
//                                        .serviceBuilder().enableFileOverride()
                                        .entityBuilder().enableFileOverride()
                                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
