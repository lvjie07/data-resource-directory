package com.ruizhi.data.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * Created by lvjie on 2020/7/24
 */
public class GeneratorServiceEntity {

    @Test
    public void generateCode() {
        String packageName = "com.ruizhi.data.dal";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        //需要的表名，多个表名传数组
        generateByTables(serviceNameStartWithI, packageName,
//                "data_source_info",
//                "sen_lev_info",
//                "user_info",
//                "cascade_sort_info",
//                "fld_typ_info",
//                "tbl_typ_info",
//                "fld_tbl_pnd",
//                "flw_info",
//                "rtl_flw_db",
//                    "rtl_flw_db_tbl",
//                "fld_clt_rst",
                "rel_result",
                "rtl_tbl");
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/data_resource?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();

        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setEntitySerialVersionUID(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        config.setActiveRecord(false)
                .setAuthor("lvjie")
                //代码生成目录
                .setOutputDir("D:\\workspace\\")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entitys")
                                .setXml("mapper")
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
