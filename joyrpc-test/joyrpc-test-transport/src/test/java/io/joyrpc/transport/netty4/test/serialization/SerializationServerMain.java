package io.joyrpc.transport.netty4.test.serialization;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.joyrpc.extension.URL;
import io.joyrpc.transport.DefaultEndpointFactory;
import io.joyrpc.transport.Server;
import io.joyrpc.transport.channel.ChannelHandlerChain;

/**
 * @date: 2019/1/28
 */
public class SerializationServerMain {

    public static void main(String[] orgs) throws InterruptedException {
        URL url = URL.valueOf("mock://127.0.0.1:22000");
        Server server = new DefaultEndpointFactory().createServer(url);
        server.setCodec(new SerializationCodec());
        server.setChannelHandlerChain(
                new ChannelHandlerChain()
                        .addLast(new MockMsgBodyChannelHandler())
        );
        server.open();

        synchronized (SerializationServerMain.class) {
            while (true) {
                try {
                    SerializationServerMain.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
