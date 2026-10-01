package com.coreleo.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import com.coreleo.SimpleException;
import com.coreleo.util.sql.DBUtil;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/** Values passed to setters or bound to SQL may be secrets, so they never reach logs or exception messages. */
class SecretLoggingTest {
    private static final String SECRET = "6f6c642d68617368";
    private final Logger logger = (Logger) LoggerFactory.getLogger(LogUtil.class);
    private final ListAppender<ILoggingEvent> appender = new ListAppender<>();
    private Level previous;

    /** A class used only here, so its setter is not already in ReflectionUtil's method cache. */
    public static class Account {
        private String password;

        public void setPassword(final String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }

    @BeforeEach
    void capture() {
        previous = logger.getLevel();
        logger.setLevel(Level.TRACE);
        appender.start();
        logger.addAppender(appender);
    }

    @AfterEach
    void release() {
        logger.detachAppender(appender);
        logger.setLevel(previous);
    }

    private void assertNotLogged() {
        assertFalse(appender.list.isEmpty(), "expected debug output");
        appender.list.forEach(event -> assertFalse(event.getFormattedMessage().contains(SECRET),
                event.getFormattedMessage()));
    }

    @Test
    void aFoundSetterLogsItsParameterTypesButNotTheValue() {
        final Account account = new Account();
        ReflectionUtil.invoke(account, "setPassword", SECRET);
        assertTrue(SECRET.equals(account.getPassword()));
        assertTrue(appender.list.stream().anyMatch(event -> event.getFormattedMessage().contains("setPassword")
                && event.getFormattedMessage().contains("java.lang.String")));
        assertNotLogged();
    }

    @Test
    void aMissingSetterDoesNotPutTheValueInTheException() {
        final SimpleException e = assertThrows(SimpleException.class,
                () -> ReflectionUtil.invoke(new Account(), "setMissing", SECRET));
        for (Throwable t = e; t != null; t = t.getCause())
            assertFalse(String.valueOf(t.getMessage()).contains(SECRET), t.getMessage());
    }

    @Test
    void sqlLogsTheNumberOfBoundValuesButNotTheValues() throws Exception {
        final Method log = DBUtil.class.getDeclaredMethod("getLogString", String.class, int.class, int.class, int.class,
                String.class, Object[].class);
        log.setAccessible(true);
        final String text = (String) log.invoke(null, "update", -1, -1, 600,
                "update users set password = ? where id = ?", new Object[] { SECRET, 3 });
        assertTrue(text.contains("paramCount=2"), text);
        assertTrue(text.contains("update users set password = ? where id = ?"), text);
        assertFalse(text.contains(SECRET), text);
    }
}
