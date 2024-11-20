/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.server.worker.registry;

import static org.mockito.Mockito.times;

import org.apache.dolphinscheduler.registry.api.ConnectionState;
import org.apache.dolphinscheduler.registry.api.RegistryClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * worker registry test
 */
@ExtendWith(MockitoExtension.class)
public class WorkerConnectionStateListenerTest {

    @InjectMocks
    private WorkerConnectionStateListener workerConnectionStateListener;

    @Mock
    private RegistryClient registryClient;

    @Test
    public void testWorkerConnectionStateListener() {
        Mockito.when(registryClient.getStoppable()).thenReturn(cause -> {
            // do nothing
        });

        workerConnectionStateListener.onUpdate(ConnectionState.CONNECTED);
        workerConnectionStateListener.onUpdate(ConnectionState.RECONNECTED);
        workerConnectionStateListener.onUpdate(ConnectionState.SUSPENDED);
        Mockito.verify(registryClient, times(0)).getStoppable();
        workerConnectionStateListener.onUpdate(ConnectionState.DISCONNECTED);
        Mockito.verify(registryClient, times(1)).getStoppable();
    }
}
