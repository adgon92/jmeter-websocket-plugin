package com.jmeter.websocket.plugin.elements.samplers;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.jmeter.websocket.plugin.elements.configurations.WebsocketSessionsManager;
import com.jmeter.websocket.plugin.endpoint.WebsocketClient;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.TestElementProperty;

public abstract class AbstractWebsocketSampler extends AbstractSampler {

    public static final String WEBSOCKET_MANAGER = "websocket_manager";
    public static final String PATH = "path";
    public static final String SERVER_NAME_OR_IP = "serverNameOrIp";
    public static final String PORT_NUMBER = "portNumber";
    public static final String PROTOCOL = "protocol";
    public static final String COOKIE_MANAGER = "cookie_manager";
    public static final String HEADER_MANAGER = "header_manager";
    public static final String SESSION_ID = "session_id";

    protected WebsocketSessionsManager getWebsocketSessionsManager() {
        return Optional.fromNullable(getProperty(WEBSOCKET_MANAGER).getObjectValue())
                .transform(
                        new Function<Object, WebsocketSessionsManager>() {
                            @Override
                            public WebsocketSessionsManager apply(Object property) {
                                return (WebsocketSessionsManager) property;
                            }
                        })
                .orNull();
    }

    protected void setWebsocketSessionsManager(WebsocketSessionsManager websocketSessionsManager) {
        setProperty(new TestElementProperty(WEBSOCKET_MANAGER, websocketSessionsManager));
    }

    public String getSessionId() {
        return getPropertyAsString(SESSION_ID);
    }

    public void setSessionId(String sessionId) {
        setProperty(SESSION_ID, sessionId, "");
    }

    public WebsocketClient<String> getWebsocketClient() {
        return Optional.fromNullable(getWebsocketSessionsManager())
                .transform(
                        new Function<WebsocketSessionsManager, WebsocketClient<String>>() {
                            @Override
                            public WebsocketClient<String> apply(WebsocketSessionsManager manager) {
                                return WebsocketSessionsManager.getWebsocketClient();
                            }
                        }
                )
                .orNull();
    }

    @Override
    public void addTestElement(TestElement el) {
        if (el instanceof WebsocketSessionsManager) {
            setWebsocketSessionsManager((WebsocketSessionsManager) el);
        } else {
            super.addTestElement(el);
        }
    }
}
