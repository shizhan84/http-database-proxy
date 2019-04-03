package cn.okcoming.dbproxy.util;

import cn.okcoming.baseutils.ExceptionUtils;
import cn.okcoming.dbproxy.bean.QueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * Created by bluces on 2016/11/28.
 */
@Slf4j
public class MethodUtils {

    public static final <T extends Serializable>  byte[] encode(T obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(obj);
            bytes = bout.toByteArray();
        }catch (IOException e) {
            log.warn("Error serializing object[{}], message[{}]!",obj,e);
        }
        return bytes;
    }

    @SuppressWarnings("unchecked")
    public static final <T extends Serializable>  T  decode(byte [] bytes){
        T t = null;
        try {
            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bytes));
            t = (T) oin.readObject();
        }catch (Exception e) {
            log.warn("Error decoding byte[] data [{}], message[{}]!",bytes,e);
        }
        return t;
    }


    public static void transform(QueryResponse response, ResultSet rset) throws SQLException {
        List<List<Object>> rowList   = new ArrayList<>();
        List<String>            colList   = new ArrayList<>();
        List<Integer>            typeList  = new ArrayList<>();

        final ResultSetMetaData rsMetaData = rset.getMetaData();
        final int               numCols      = rsMetaData.getColumnCount();
        boolean                 firstRow     = true;

        //Loop through all the result ROWs
        while(rset.next()) {
            ArrayList<Object> valList  = new ArrayList<>();
            //JSONArray valArray = new JSONArray();
            //Loop through all the result COLs
            for(int i=1; i <= numCols; i++) {
                if(firstRow) {
                    colList.add(rsMetaData.getColumnName(i));
                    typeList.add(rsMetaData.getColumnType(i));
                }
                valList.add(rset.getObject(i));
            }
            //Add each result row to a list of rows
            rowList.add(valList);
            firstRow = false;
        }

        //Final JSON for this query is a JSON object
        //The rows attribute can be in either document or standard format
        response.setColList(colList);
        response.setRowList(rowList);
        response.setTypeList(typeList);

    }
    /**
     *
     * @param target
     * @param source
     */
    public static void transform(QueryResponse target, SqlRowSet source) {
        List<List<Object>> rowList   = new ArrayList<>();
        List<String>            colList   = new ArrayList<>();
        List<Integer>            typeList  = new ArrayList<>();

        final SqlRowSetMetaData rsMetaData = source.getMetaData();
        final int               numCols      = rsMetaData.getColumnCount();
        boolean                 firstRow     = true;

        //Loop through all the result ROWs
        while(source.next()) {
            ArrayList<Object> valList  = new ArrayList<>();
            //JSONArray valArray = new JSONArray();
            //Loop through all the result COLs
            for(int i=1; i <= numCols; i++) {
                if(firstRow) {
                    colList.add(rsMetaData.getColumnLabel(i));
                    typeList.add(rsMetaData.getColumnType(i));
                }
                Object value = source.getObject(i);
                if(value instanceof byte[]){
                    valList.add(Base64.getEncoder().encode((byte[]) value));
                }else{
                    valList.add(source.getObject(i));
                }
            }
            //Add each result row to a list of rows
            rowList.add(valList);
            firstRow = false;
        }

        target.setColList(colList);
        target.setRowList(rowList);
        target.setTypeList(typeList);
    }

}
