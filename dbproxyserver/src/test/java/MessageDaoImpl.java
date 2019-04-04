

import cn.okcoming.dbproxy.client.JdbcOperationClient;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import rx.functions.Func1;

import java.util.Date;
import java.util.List;

//
//public class cn.okcoming.MessageDaoImpl   {
//
//
//    private static final DBRowMapper<MessageBean> MESSAGES_ROW_MAPPER =
//            new DBRowMapper<MessageBean>() {
//                @Override
//                public MessageBean mapRow(final DBRow rs){
//                    final MessageBean record = new MessageBean();
//                    record.setMessageId(rs.getLong("id"));
//                    record.setSequence(rs.getString("id"));
//                    record.setReadFlag(rs.getInt("is_read"));
//                    record.setCreateTime(rs.getTimestamp("create_time").getTime());
//                    record.setShowCreateTime(DateUtils.formatDateTime(rs.getTimestamp("create_time")));
//
//                    return record;
//                }};
//
//
//
//    @Autowired
//    private JdbcOperationClient _jdbcOperationClient;
//
//
//    public Observable<List<MessageBean>> findMessages(final String accountId,final  String clientType,final  int maxSize ) {
//        return this._jdbcOperationClient.query(findMessages_prefix + findMessages_suffix, MESSAGES_ROW_MAPPER,accountId,clientType,accountId,clientType,accountId,clientType,maxSize);
//
//    }


//    public Observable<Integer> readMessage(final String accountId, final long messageId) {
//        return _jdbcOperationClient.update("update yjy_message_pull_manage set is_read = 1  where id = ? and account_id = ? ", messageId,accountId);
//    }
//
//
//    public Observable<Integer> deleteMessage(final String accountId, final long messageId) {
//        return _jdbcOperationClient.update("update yjy_message_pull_manage set is_deleted = 1  where id = ? and account_id = ? ", messageId,accountId);
//    }
//
//
//
//    public Observable<Integer> asyncInsertPrivateMessage(final MessageBean bean) {
//        final String sql = "insert into yjy_message_private(create_time,image_url,title,content,action_route,param1,param2,param3, " +
//                " message_type,message_category,client_type,account_id,sender_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
//
//        return _jdbcOperationClient.<Integer>updateAndNeedReturnId(sql, null
//                , bean.getImageUrl()
//                , bean.getTitle()
//                , bean.getContent()
//                , bean.getAction()
//                , bean.getParam1()
//                , bean.getParam2()
//                , bean.getSenderId()).flatMap(new Func1<Integer, Observable<Integer>>() {
//            @Override
//            public Observable<Integer> call(Integer object) {
//                return _jdbcOperationClient.update("insert into yjy_message_pull_manage(client_type,account_id,is_read,message_table,message_id,is_deleted,create_time) values" +
//                        "(?,?,?,?,?,?,?) ", bean.getClientType(), bean.getAccountId(), 0, 1, object, 0, new Date());
//            }
//        });
//
//    }
//
//
//    public Observable<Integer> syncPublicMessage(String accountId, String clientType) {
//        return _jdbcOperationClient.update("insert ignore into yjy_message_pull_manage( " +
//                "        client_type,account_id,is_read,message_table,message_id,is_deleted,create_time) " +
//                "select  ?,?,0,2,id,0,now()  " +
//                "from yjy_message_public_publish_manage  " +
//                "where is_enabled = 1 and now() >= start_time and now() <= end_time and (client_type = ? or client_type is null or length(client_type)=0) ",clientType,accountId,clientType);
//    }
//}

