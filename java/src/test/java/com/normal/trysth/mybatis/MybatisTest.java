package com.normal.trysth.mybatis;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.apache.ibatis.session.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * @author hefei
 * @date 2017/8/10
 */
public class MybatisTest {
    private static Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    SqlSession sqlSession;

    ReferenceConfig<GenericService> refer;
    @Before
    public void initSqlSession() throws FileNotFoundException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(new FileReader("F:\\normal-all\\normal-try\\src\\test\\resources\\mybatis\\mybatis-config.xml"));
        sqlSession = factory.openSession(true);
    }
//    @Before
    public void init() throws Throwable {


        //set up dubbo service & refer
        DynamicDaoWrapper dynamicDaoWrapper = new DynamicDaoWrapper();

        DynamicDao mapper = sqlSession.getMapper(DynamicDao.class);
        Field dynamicDao = dynamicDaoWrapper.getClass().getDeclaredField("dynamicDao");
        dynamicDao.setAccessible(true);
        dynamicDao.set(dynamicDaoWrapper, mapper);

        ApplicationConfig appConfig = new ApplicationConfig("dynamic-dao-app");

        ServiceConfig<GenericService> serverConfig = new ServiceConfig<>();
        serverConfig.setApplication(appConfig);
        serverConfig.setRegister(false);
        serverConfig.setRef(dynamicDaoWrapper);
        serverConfig.setInterface("com.xxx.XxxService");
        serverConfig.setVersion("1.0.0");
        serverConfig.export();


        ReferenceConfig<GenericService> refer = new ReferenceConfig<>();
        refer.setUrl("dubbo://127.0.0.1:20880");
        refer.setApplication(appConfig);
        refer.setInterface("com.xxx.XxxService");
        refer.setVersion("1.0.0");
        refer.setGeneric(true);
        this.refer = refer;
    }

    @Test
    public void testQueryProcess() throws Throwable {
        List<Object> queryResult = sqlSession.selectList("com.normal.stuff.mybatis.CfetsBondDlgMapper.testQuery", null);
    }

    @Test
    public void testCache() {
        //默认, 同一个namespace的更新方法(updateWithSimpleParam,delete,insert)会flush select 方法
        sqlSession.selectList("com.normal.stuff.mybatis.CfetsBondDlgMapper.testQuery", null);
        sqlSession.selectList("com.normal.stuff.mybatis.CfetsBondDlgMapper.testQuery", null);
        sqlSession.update("com.normal.stuff.mybatis.CfetsBondDlgMapper.testUpdate", new Date().toString());
        sqlSession.selectList("com.normal.stuff.mybatis.CfetsBondDlgMapper.testQuery", null);
    }

    @Test
    public void testDynamicUpdate() {
        DynamicDao mapper = sqlSession.getMapper(DynamicDao.class);
        int count = mapper.updateWithSimpleParam("87654321");

        UpdateBean bean = new UpdateBean();
        mapper.updateWithBean(bean);
    }

    @Test
    public void testDynamicUpdateWithDubbo() {
        GenericService dynamicDao = refer.get();
        Object count = dynamicDao.$invoke("updateWithSimpleParam", new String[]{"java.lang.String"}, new Object[]{"xiaobai"});

    }

    @Test
    public void testDy() {

        CfetsBondDlgMapper mapper = sqlSession.getMapper(CfetsBondDlgMapper.class);
        List<Map<String, Object>> maps = mapper.selectAll();
        logger.info(String.valueOf(maps.size()));
    }
}
