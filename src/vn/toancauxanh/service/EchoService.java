package vn.toancauxanh.service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.zkoss.zk.ui.http.ZKWebSocket;

import vn.toancauxanh.model.NhanVien;

@ServerEndpoint(value = "/echo",configurator = ZKWebSocket.class)
public class EchoService extends BasicService<NhanVien>{
	
	@OnOpen
	public void onOpen(Session session) {
//		System.out.println(session);
		System.out.println("Open session");
//		System.out.println("-------information user 1-----------");
//		System.out.println(getNhanVien());
//		System.out.println("----------------------------------");
		
	}
	
	@OnMessage
	public void onMessage(String message, Session session){
//		Storage storage = (Storage) ZKWebSocket.getDesktopStorage(session);
//		
//        if ("receive".equals(message)) {
//            Integer count = ((org.zkoss.zk.ui.sys.Storage) storage).getItem("count");
//            try {
//                session.getBasicRemote().sendText("Received..." + count);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                ((org.zkoss.zk.ui.sys.Storage) storage).setItem("count", Integer.parseInt(message));
//                session.getBasicRemote().sendText("Sent..." + message);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
	}
	
	@OnClose
    public void onClose(Session session){
		
		System.out.println("Close session");
	}
}
