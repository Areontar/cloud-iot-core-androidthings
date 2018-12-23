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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

/** Represents a telemetry event to publish to Cloud IoT Core. */
public class TelemetryEvent {

    private final String mTopicSubpath;
    private final byte[] mData;
    private final QOSPolicy mQos;

    /** Quality of service options. */
    public enum QOSPolicy{
        /** At most once delivery. */
    	QOS_AT_MOST_ONCE
    	{
			@Override
			public int getid() {
				
				return 0;
			}
		},
    	/** At least once delivery. */
    	QOS_AT_LEAST_ONCE
    	
    	{
			@Override
			public int getid() {
				// TODO Auto-generated method stub
				return 1;
			}
    	};    
		public abstract int getid();
		};



    /**
     * Constructs a new TelemetryEvent with the data to publish and an
     * optional topic subpath destination.
     *
     * @param data the telemetry event data to send to Cloud IoT Core
     * @param topicSubpath the subpath under "../device/../events/"
     * @param qos the quality of service to use when sending the message
     */
    public TelemetryEvent(@Nonnull byte[] data, @Nullable String topicSubpath, QOSPolicy qos) {
        if (!qos.equals(QOSPolicy.QOS_AT_MOST_ONCE) && !qos.equals(QOSPolicy.QOS_AT_LEAST_ONCE)) {
            throw new IllegalArgumentException("Invalid quality of service provided.");
        }


        if (StringUtils.isEmpty(topicSubpath)) {
            topicSubpath = "";
        } else if (topicSubpath.charAt(0) != '/') {
            topicSubpath = "/" + topicSubpath;
        }

        mTopicSubpath = topicSubpath;
        mData = data;
        mQos = qos;
    }

    /**
     * Gets this event's data.
     *
     * @return this event's data
     */
    public byte[] getData() {
        return mData;
    }

    /**
     * Gets this event's topic subpath.
     *
     * <p>Non-empty strings returned by this method always begin with a slash (e.g. /foo).
     *
     * @return this event's topic subpath
     */
    public String getTopicSubpath() {
        return mTopicSubpath;
    }

    /**
     * Gets this event's quality of service settings.
     *
     * @return this event's QOS settings
     */
    public QOSPolicy getQos() {
        return mQos;
    }
}
