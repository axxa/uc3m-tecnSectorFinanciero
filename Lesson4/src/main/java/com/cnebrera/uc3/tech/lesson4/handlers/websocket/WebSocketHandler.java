package com.cnebrera.uc3.tech.lesson4.handlers.websocket ;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.atmosphere.websocket.WebSocketStreamingHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnebrera.uc3.tech.lesson4.handlers.IPricesListener;
import com.cnebrera.uc3.tech.lesson4.handlers.PricesPublisher;

/**
 * Atmosphere handler for Web Sockets
 * --------------------------------------
 * @author Francisco Manuel Benitez Chico
 * --------------------------------------
 */

// TODO 1
@WebSocketHandlerService (path="/prices/websocket", broadcaster=SimpleBroadcaster.class, 
	atmosphereConfig={"org.atmosphere.websocket.WebSocketProtocol=org.atmosphere.websocket.protocol.StreamingHttpProtocol"})
public class WebSocketHandler extends WebSocketStreamingHandlerAdapter
{
	/** Logger for this class */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class) ;
	
	/** Attribute - Prices Listener - Web sockets */
	private IPricesListener pricesListener ;
	
	@Override
    public void onOpen(final WebSocket webSocket)
	{
		// TODO 2
		// New prices listener
		this.pricesListener = new PricesListenerWebsocket(webSocket);
		// TODO 3
		// Add the listener to the publisher
		PricesPublisher.getInstance().addPricesListener(this.pricesListener);
    	// Add event listener when disconnecting
        webSocket.resource().addEventListener(new WebSocketEventListenerAdapter()
        {
            @Override
            public void onDisconnect(AtmosphereResourceEvent event)
            {
            	// TODO 4
				if (event.isCancelled() || event.isClosedByClient() || event.isClosedByApplication())
				{
					if (event.isCancelled())
					{
						WebSocketHandler.LOGGER.warn("Browser {} unexpectedly disconnected",
						event.getResource().uuid()) ;
					}
					else if (event.isClosedByClient())
					{
						WebSocketHandler.LOGGER.warn("Browser {} closed the connection",
						event.getResource().uuid()) ;
					}
					else if (event.isClosedByApplication())
					{
						WebSocketHandler.LOGGER.warn("Application {} closed the connection",
						event.getResource().uuid()) ;
					}
					
					PricesPublisher.getInstance().removePricesListener(WebSocketHandler.this.pricesListener) ;
				}
				// TODO 5
				else if (event.isResumedOnTimeout())
				{
					WebSocketHandler.LOGGER.warn("Event resumed on timeout {}", event.getResource().uuid()) ;
				}
				else if (event.isResuming())
				{
					WebSocketHandler.LOGGER.warn("Event resuming {}", event.getResource().uuid()) ;
				}
				else if (event.isSuspended())
				{
					WebSocketHandler.LOGGER.warn("Event suspended {}", event.getResource().uuid()) ;
				} 


            }
        });
    }
}
