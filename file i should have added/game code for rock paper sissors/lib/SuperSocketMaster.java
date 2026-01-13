package lib;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.AWTEventMulticaster;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.net.SocketException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.Enumeration;

/**
 * SuperSocketMaster - lightweight socket helper for simple text-based messaging.
 * This version provides server/client behavior and posts ActionEvents when new
 * text arrives so Swing programs can respond.
 */
public class SuperSocketMaster {
    private int intPort = 1337;
    private String strServerIP = null;
    private String strIncomingText = null;
    private SocketConnection soccon = null;
    transient ActionListener actionListener = null;

    public SuperSocketMaster(int intPort, ActionListener listener) {
        this.addActionListener(listener);
        this.intPort = intPort;
    }

    public SuperSocketMaster(String strServerIP, int intPort, ActionListener listener) {
        this.addActionListener(listener);
        this.intPort = intPort;
        this.strServerIP = strServerIP;
    }

    public boolean sendText(String strText) {
        if (soccon != null) {
            return soccon.sendText(strText);
        }
        return false;
    }

    public String readText() {
        if (soccon != null) {
            return strIncomingText;
        } else {
            return "";
        }
    }

    public void disconnect() {
        if (soccon != null) {
            soccon.closeConnection();
            soccon = null;
        }
    }

    public String getMyAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> niAddresses = networkInterface.getInetAddresses();
                while (niAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) niAddresses.nextElement();
                    if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "127.0.0.1";
    }

    public String getMyHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public boolean connect() {
        soccon = new SocketConnection(strServerIP, intPort, this);
        if (soccon.openConnection()) {
            return true;
        } else {
            soccon = null;
            return false;
        }
    }

    private synchronized void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }

    private synchronized void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    private void postActionEvent() {
        ActionListener listener = actionListener;
        if (listener != null) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Network Message"));
        }
    }

    /********************************************************************
     * SocketConnection inner class handles server/client connection and IO
     ********************************************************************/
    private class SocketConnection implements Runnable {
        SuperSocketMaster parentssm = null;
        int intPort = 1337;
        String strServerIP = null;
        String strIncomingText = "";
        ServerSocket serverSocketObject = null;
        Socket socketObject = null;
        PrintWriter outBuffer = null;
        BufferedReader inBuffer = null;
        Vector<ClientConnection> clientconnections = new Vector<ClientConnection>();
        boolean blnListenForClients = true;

        public SocketConnection(String strServerIP, int intPort, SuperSocketMaster parentssm) {
            this.strServerIP = strServerIP;
            this.intPort = intPort;
            this.parentssm = parentssm;
        }

        public boolean sendText(String strText) {
            if (strServerIP == null || strServerIP.equals("")) {
                for (int intCounter = 0; intCounter < clientconnections.size(); intCounter++) {
                    clientconnections.get(intCounter).sendText(strText);
                }
                return true;
            } else {
                if (socketObject != null) {
                    if (outBuffer.checkError()) {
                        closeConnection();
                        return false;
                    } else {
                        outBuffer.println(strText);
                        return true;
                    }
                }
                return false;
            }
        }

        public void removeClient(ClientConnection clientConnection) {
            if (clientConnection.socketObject != null) {
                try {
                    try {
                        clientConnection.socketObject.shutdownInput();
                        clientConnection.socketObject.shutdownOutput();
                        clientConnection.socketObject.close();
                        clientConnection.outBuffer.close();
                        clientConnection.inBuffer.close();
                        clientConnection.socketObject = null;
                        clientConnection.inBuffer = null;
                        clientConnection.outBuffer = null;
                        clientConnection.strIncomingText = null;
                        clientconnections.remove(clientConnection);
                        clientConnection = null;
                    } catch (NullPointerException e) {
                    }
                } catch (IOException e) {
                }
            }
        }

        public void run() {
            if (strServerIP == null || strServerIP.equals("")) {
                while (blnListenForClients) {
                    try {
                        socketObject = serverSocketObject.accept();
                        ClientConnection singleconnection = new ClientConnection(this.parentssm, this.socketObject, this);
                        clientconnections.addElement(singleconnection);
                        Thread t1 = new Thread(singleconnection);
                        t1.start();
                    } catch (IOException e) {
                        blnListenForClients = false;
                    }
                }
            } else {
                while (strIncomingText != null) {
                    try {
                        strIncomingText = inBuffer.readLine();
                        if (strIncomingText != null) {
                            this.parentssm.strIncomingText = strIncomingText;
                            this.parentssm.postActionEvent();
                        }
                    } catch (IOException e) {
                    }
                }
                closeConnection();
            }
        }

        public void closeConnection() {
            if (strServerIP == null || strServerIP.equals("")) {
                blnListenForClients = false;
                while (clientconnections.size() > 0) {
                    removeClient(clientconnections.get(0));
                }
                try {
                    if (serverSocketObject != null) serverSocketObject.close();
                } catch (IOException e) {
                }
                serverSocketObject = null;
                clientconnections = null;
            } else {
                if (socketObject != null) {
                    try {
                        try {
                            socketObject.shutdownInput();
                            socketObject.shutdownOutput();
                            socketObject.close();
                            outBuffer.close();
                            inBuffer.close();
                            socketObject = null;
                            inBuffer = null;
                            outBuffer = null;
                            strIncomingText = null;
                        } catch (NullPointerException e) {
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }

        public boolean openConnection() {
            if (strServerIP == null || strServerIP.equals("")) {
                try {
                    serverSocketObject = new ServerSocket(intPort);
                } catch (IOException e) {
                    return false;
                }
                Thread t1 = new Thread(this);
                t1.start();
                return true;
            } else {
                try {
                    socketObject = new Socket(strServerIP, intPort);
                    outBuffer = new PrintWriter(socketObject.getOutputStream(), true);
                    inBuffer = new BufferedReader(new InputStreamReader(socketObject.getInputStream()));
                } catch (IOException e) {
                    return false;
                }
                Thread t1 = new Thread(this);
                t1.start();
                return true;
            }
        }
    }

    private class ClientConnection implements Runnable {
        SuperSocketMaster parentssm = null;
        SocketConnection socketConnection = null;
        String strIncomingText = "";
        Socket socketObject = null;
        PrintWriter outBuffer = null;
        BufferedReader inBuffer = null;

        public ClientConnection(SuperSocketMaster parentssm, Socket socketObject, SocketConnection socketConnection) {
            this.parentssm = parentssm;
            this.socketObject = socketObject;
            this.socketConnection = socketConnection;
        }

        public void run() {
            try {
                inBuffer = new BufferedReader(new InputStreamReader(socketObject.getInputStream()));
                outBuffer = new PrintWriter(socketObject.getOutputStream(), true);
            } catch (IOException e) {
            }
            while (strIncomingText != null) {
                try {
                    strIncomingText = inBuffer.readLine();
                    if (strIncomingText != null) {
                        for (int intCounter = 0; intCounter < socketConnection.clientconnections.size(); intCounter++) {
                            if (socketConnection.clientconnections.get(intCounter) != this) {
                                socketConnection.clientconnections.get(intCounter).sendText(strIncomingText);
                            }
                        }
                        this.parentssm.strIncomingText = strIncomingText;
                        this.parentssm.postActionEvent();
                    }
                } catch (IOException e) {
                }
            }
            socketConnection.removeClient(this);
        }

        public boolean sendText(String strText) {
            if (outBuffer != null) {
                outBuffer.println(strText);
                return true;
            }
            return false;
        }
        }
}