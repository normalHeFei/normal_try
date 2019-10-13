package com.normal.trysth.network;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hefei on 2017/5/24.
 */
public class NetworkTest {
    public static void main(String[] args) throws Throwable {
        testCommonApi();
    }

    public static void testCommonApi() throws Throwable {
        ServerSocket socket = new ServerSocket(8888);
        while (true) {
            Socket accept = socket.accept();
            InputStream inputStream = accept.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            StringBuffer result = new StringBuffer();
            while (true) {
                int c = reader.read();
                result.append((char) c);
                if (c == -1) {
                    break;
                }
            }
            System.out.println(result.toString());
            //return response
            OutputStream outputStream = accept.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write("this is response");
            writer.flush();
            writer.close();
        }
    }
}



