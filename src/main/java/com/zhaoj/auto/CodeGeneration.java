package com.zhaoj.auto;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 作者:zhaoj
 * 创建时间:2020/9/22    20:37
 * 类的作用:
 */

public class CodeGeneration {

    public static void main(String[] args) {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir("E:\\月神模拟器")//设置代码生成的位置
                .setAuthor("zhaoj")//设置作者
                .setBaseResultMap(true)//是否生成resultMap
                .setBaseColumnList(true)//是否生成sql片段
                .setFileOverride(true)//是否覆盖
                .setControllerName("%sController")//设置控制器类名
                .setServiceName("%sService")//service接口名
                .setServiceImplName("%sServiceImpl")
                .setMapperName("%sDao")//接口名规则
                .setXmlName("%sMapper")
                ;
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/test")
                .setUsername("root")
                .setPassword("123456");

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig()
                .setTablePrefix(new String[]{"yx_"})//设置表名的前缀,未来生成的实体类自动去掉前缀
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(new String[]{//指定生成哪些表对应的实体类
                    "yx_log"
                        /*"yx_admin",
                        "yx_attention",
                        "yx_category",
                        "yx_collect",
                        "yx_comment",
                        "yx_feedback",
                        "yx_group",
                        "yx_history",
                        "yx_img",
                        "yx_like",
                        "yx_play",
                        "yx_user",
                        "yx_video"*/
                })
                .setEntityLombokModel(true);//生成lombook实体类

        // 包结构配置
        PackageConfig packageConfig = new PackageConfig()
                .setParent("com.zhaoj")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("entity")
                .setMapper("dao")//设置dao接口包
                .setXml("mapper");

        // 整合
        AutoGenerator autoGenerator = new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig);

        autoGenerator.execute();

    }
}