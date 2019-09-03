package cn.okcoming.dbproxy.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;


public interface OperatorDao {

    /**
     * 查询数据 返回集合格式数据
     * @param query
     * @param parameters 可空
     * @return
     * @throws Exception
     */
    SqlRowSet query(final String database,final String query, final Object[] parameters);

    /**
     * 插入或者更新数据记录
     * @param query
     * @param parameters
     * @return 受影响的记录条数
     * @throws Exception
     */
    int update(final String database,final String query, final Object[] parameters);

    /**
     * 插入数据记录并且获取自动生成的主键
     * @param query
     * @param parameters
     * @return 获取自动生成的主键
     * @throws Exception
     */
    Object updateAndReturnId(final String database,String query, Object[] parameters);

    /**
     * 指定执行语句 根据参数列表批量执行更新操作
     * @param query
     * @param parameters
     * @return 返回每一个操作影响到的记录条数集合
     * @throws Exception
     */
    int[] batchUpdate(final String database,String query, List<Object[]> parameters);

    /**
     * 批量执行指定的sql语句
     * @param querys
     * @return
     * @throws Exception
     */
    int[] batchUpdate(final String database,String[] querys);

    void execute(final String database,String query);
}
