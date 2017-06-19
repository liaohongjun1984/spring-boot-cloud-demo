package com.idohoo.db.config.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@MapperScan("com.idohoo.*.dao")
@EnableConfigurationProperties(MysqlConfig.class)
@EnableTransactionManagement
public class MysqlFactoryConfig {

	// mybaits mapper xml搜索路径
    @Value("${mysql.jdbc.mapperlocations}")
    private String mapperlocations;
    
    // 数据库实体类的路径
    @Value("${mysql.jdbc.typeAliasPackage}")
    private String typeAliasPackage;

    @Autowired
    private MysqlConfig mysqlConfig;
    
    private DataSource datasource = null;
    
    @Bean(destroyMethod = "close")
	public DataSource dataSource() {	
    	datasource = mysqlConfig.druidDataSource();
		return datasource;
	}

    @PreDestroy
    public void close() {
        if(datasource != null){
            ((DruidDataSource) datasource).close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperlocations));
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    
}
