package cn.okcoming.dbproxy.core;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 *
 * Created by bluces on 2016/12/30.
 */
public interface DBRow extends  AutoCloseable{

    public String getString(final String key);

    public Boolean getBoolean(final String key);

    public Byte getByte(final String key);

    public Short getShort(final String key);

    public Integer getInt(final String key);

    public Integer getInteger(final String key);

    public Long getLong(final String key);

    public Float getFloat(final String key);

    public Double getDouble(final String key);

    public byte[] getBytes(final String key);

    public Date getDate(final String key);

    public Timestamp getTimestamp(final String key);

    public Object getObject(final String key);

    public BigDecimal getBigDecimal(final String key);

    public <T> T getOnlyObject();

    public <T> T getOnlyObject(final Class<T> requiredType);
}

