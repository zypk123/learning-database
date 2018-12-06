package com.zhang.shiro.session;

import com.zhang.shiro.utils.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangyu
 * @create 2018-11-09 11:15
 **/
@Repository
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private JedisUtil jedisUtil;

    private final static String SHIRO_SESSION_PREFIX = "zhang-session:";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);

        assignSessionId(session, sessionId);

        byte[] key = getKey(session.getId().toString());
        byte[] value = SerializationUtils.serialize(session);

        jedisUtil.set(key, value);
        jedisUtil.exprie(key, 600);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {

        System.out.println("read session");

        if (null == serializable) {
            return null;
        }

        byte[] key = getKey(serializable.toString());
        byte[] value = jedisUtil.get(key);

        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {

        byte[] key = getKey(session.getId().toString());
        byte[] value = SerializationUtils.serialize(session);

        jedisUtil.set(key, value);
        jedisUtil.exprie(key, 600);
    }

    @Override
    public void delete(Session session) {

        if (session == null || null == session.getId()) {
            return;
        }

        byte[] key = getKey(session.getId().toString());
        jedisUtil.delete(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.getKeys(SHIRO_SESSION_PREFIX);

        Set<Session> sessions = new HashSet<Session>();

        if (CollectionUtils.isEmpty(sessions)) {
            return sessions;
        }

        for (byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }

}
