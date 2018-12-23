// Copyright 2018 Google LLC.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.android.things.iotcore;

/**
 * Callback interface to receive information about the status of the Cloud IoT Core connection.
 */
public abstract class ConnectionCallback {

    /** Disconnect reason codes. */
    public enum DisconnectReason{
        	/** Could not determine the source of the error. */
            REASON_UNKNOWN,
            /** The parameters used to connect to Cloud IoT Core were invalid. */
            REASON_NOT_AUTHORIZED,
            /** The device lost connection to Cloud IoT Core. */
            REASON_CONNECTION_LOST,
            /** Timeout occurred while connecting to the MQTT bridge. */
            REASON_CONNECTION_TIMEOUT,
            /** The client closed the connection. */
            REASON_CLIENT_CLOSED,
    }

    /** Invoked when the Cloud IoT Core connection is established. */
    public abstract void onConnected();

    /**
     * Invoked when the Cloud IoT Core connection is lost.
     *
     * @param reason the reason the connection was lost
     */
    public abstract void onDisconnected(DisconnectReason reason);
}
