package cn.okcoming.dbproxy.core;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bluces on 2016/12/30.
 */
public class DefaultDBRow implements DBRow {

    private static final ThreadLocal<DateFormat> DATETIME_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private Map<String,Object> rowMap;

    public static void main(String[] args) throws ParseException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse("2019-04-01T07:41:26.000+0000"));
    }
    public DefaultDBRow(final List<Object> row,final  List<String> cols,final  List<Integer> types){
        rowMap = new HashMap<>();
        for (int j = 0; j < cols.size(); j++) {//列
            String colName = cols.get(j);
            Integer colType = types.get(j);
            Object colValue = row.get(j);

            if (colValue != null) {
                switch (colType) {
                    case Types.INTEGER:
                        rowMap.put(colName, colValue);
                        break;
                    case Types.BIGINT:
                        rowMap.put(colName, colValue);
                        break;
                    case Types.DECIMAL:
                    case Types.NUMERIC:
                        rowMap.put(colName, colValue);
                        break;
                    case Types.FLOAT:
                    case Types.REAL:
                    case Types.DOUBLE:
                        if(colValue instanceof BigDecimal){
                            rowMap.put(colName, ((BigDecimal) colValue).doubleValue());
                        }else{
                            rowMap.put(colName, colValue);
                        }
                        break;
                    case Types.CHAR:
                    case Types.NVARCHAR:
                    case Types.VARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.LONGVARCHAR:
                        rowMap.put(colName, colValue);
                        break;
                    case Types.BOOLEAN:
                    case Types.BIT:
                        rowMap.put(colName, Objects.equals(colValue,Boolean.TRUE) ? 1 : 0);
                        break;
                    case Types.TINYINT:
                        rowMap.put(colName, colValue);
                        break;
                    case Types.SMALLINT:
                        rowMap.put(colName, Short.valueOf(String.valueOf(colValue)));
                        break;
                    case Types.DATE:
                        if(Long.class.isInstance(colValue)){
                            rowMap.put(colName, new Date((Long) colValue));
                        }else if(colValue instanceof String){//"2019-04-26"
                            try {
                                rowMap.put(colName, DATE_FORMAT.get().parse((String)colValue));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            rowMap.put(colName,colValue);
                        }
                        break;
                    case Types.TIMESTAMP:
                        if(Long.class.isInstance(colValue)){
                            rowMap.put(colName, new Timestamp((Long) colValue));
                        }else if(colValue instanceof String){//"2019-04-01T07:41:26.000+0000
                            try {
                                rowMap.put(colName, new Timestamp(DATETIME_FORMAT.get().parse((String)colValue).getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            rowMap.put(colName,colValue);
                        }
                        break;
                    case Types.BINARY:
                    case Types.VARBINARY:
                    case Types.LONGVARBINARY:
                    case Types.BLOB:
                    case Types.CLOB:
                        rowMap.put(colName, Base64.getDecoder().decode((String) colValue));
                        break;
                    default:
                        break;
                }
            }
        }
    }
    @Override
    public String getString(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else {
            return value.toString();
        }
    }
    @Override
    public Boolean getBoolean(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        }else if(Boolean.class.isInstance(value)){
            return (Boolean)value;
        }else if(Number.class.isInstance(value)){
            return  Objects.equals(value,0) ? Boolean.FALSE : Boolean.TRUE;
        }else{
            try{
                return Boolean.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public Byte getByte(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        }else if(Byte.class.isInstance(value)){
            return (Byte)value;
        }else{
            try{
                return Byte.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public Short getShort(final String key)  {
        Object value = this.rowMap.get(key);
        if (value == null) {
            return null;
        }else if(Short.class.isInstance(value)){
            return (Short)value;
        }else{
            try{
                return Short.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public Integer getInt(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return 0;
        }else if(Integer.class.isInstance(value)){
            return (Integer)value;
        }else if(Boolean.class.isInstance(value)){
            return (Boolean)value ? 1 : 0;
        }else{
            try{
                return Integer.parseInt(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }

    @Override
    public Integer getInteger(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        }else if(Integer.class.isInstance(value)){
            return (Integer)value;
        }else if(Boolean.class.isInstance(value)){
            return (Boolean)value ? 1 : 0;
        }else{
            try{
                return Integer.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }

    @Override
    public Long getLong(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else if(Long.class.isInstance(value)){
            return (Long)value;
        }else{
            try{
                return Long.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public Float getFloat(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else if(Float.class.isInstance(value)){
            return (Float)value;
        }else{
            try{
                return Float.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public Double getDouble(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else if(Double.class.isInstance(value)){
            return (Double)value;
        }else{
            try{
                return Double.valueOf(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public byte[] getBytes(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else if(byte[].class.isInstance(value)||Byte[].class.isInstance(value)){
            return (byte[])value;
        }else{
            throw new ClassCastException("数据格式转换出错：key="+key+",value="+value);
        }
    }
    @Override
    public Date getDate(final String key){
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        }else if(Date.class.isInstance(value)){
            return (Date)value;
        }else{
            throw new ClassCastException("数据格式转换出错：key="+key+",value="+value);
        }
    }
    @Override
    public Timestamp getTimestamp(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        }else if(Timestamp.class.isInstance(value)){
            return (Timestamp)value;
        }else{
            throw new ClassCastException("数据格式转换出错：key="+key+",value="+value);
        }
    }
    @Override
    public Object getObject(final String key) {
        return this.rowMap.get(key);
    }
    @Override
    public BigDecimal getBigDecimal(final String key) {
        Object value = this.rowMap.get(key);
        if(value == null) {
            return null;
        } else if(BigDecimal.class.isInstance(value)){
            return (BigDecimal)value;
        }else{
            try{
                return new BigDecimal(value.toString());
            }catch (NumberFormatException e){
                throw e;
            }
        }
    }
    @Override
    public <T> T getOnlyObject(){
        Collection collection = this.rowMap.values();
        if(collection.size()==0){
            return null;
        }else{
            return (T)collection.toArray()[0];
        }
    }

    @Override
    public <T> T getOnlyObject(final Class<T> requiredType){
        Collection collection = this.rowMap.values();
        if(collection.size()==0){
            return null;
        }else{
            Object value = collection.toArray()[0];
            if(Objects.equals(requiredType,String.class)){
                return (T)String.valueOf(value);
            }else if(Objects.equals(requiredType,Integer.class)){
                return (T)Integer.valueOf(String.valueOf(value));
            }else if(Objects.equals(requiredType,Short.class)){
                return (T)Short.valueOf(String.valueOf(value));
            }else if(Objects.equals(requiredType,Long.class)){
                return (T)Long.valueOf(String.valueOf(value));
            }else if(Objects.equals(requiredType,Float.class)){
                return (T)Float.valueOf(String.valueOf(value));
            }else if(Objects.equals(requiredType,Double.class)){
                return (T)Double.valueOf(String.valueOf(value));
            }else if(Objects.equals(requiredType,Boolean.class)){
                return (T) ((Objects.equals(value,0) || Objects.equals(value,Boolean.FALSE)) ? Boolean.FALSE : Boolean.TRUE);
            }else{
                return (T) value;
            }
        }
    }

    @Override
    public void close() throws Exception {
        if(rowMap!=null){
            rowMap.clear();
        }
    }

}

