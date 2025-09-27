import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-27
 */
public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.68.198:3306/say_hi", "root", "root")
                .globalConfig(builder -> {
                    builder.author("Bird") // 设置作者
                            .commentDate("yyyy-MM-dd")
                            .disableOpenDir()
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java");  // 指定输出目录
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
                        builder.parent("com.bird.say.hi.server") // 设置父包名
                                .moduleName("im") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, Paths.get(System.getProperty("user.dir")) + "/src/main/resources/mapper"))  // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("message") // 设置需要生成的表名
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                .controllerBuilder().enableFileOverride()
                                .serviceBuilder().enableFileOverride()
                                .entityBuilder().enableFileOverride()
                                .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
