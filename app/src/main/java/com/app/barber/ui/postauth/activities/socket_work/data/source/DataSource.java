/*
 * Copyright 2018 Mayur Rokade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.app.barber.ui.postauth.activities.socket_work.data.source;

import com.app.barber.models.chat_model.ChatMessageModel;
import com.app.barber.ui.postauth.activities.socket_work.eventservice.EventListener;

import java.net.URISyntaxException;

import io.reactivex.Flowable;

/**
 * Main interface for accessing data. It extends EventListener to receive
 * incoming events from a remote data source. In this case, a chat server.
 */
public interface DataSource extends EventListener {

    void setEventListener(EventListener eventListener);

    void connect(String username) throws URISyntaxException;

    void disconnect();

    Flowable<ChatMessageModel> sendMessage(ChatMessageModel chatMessage);

    void onTyping();

    void onStopTyping();
}