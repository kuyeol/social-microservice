/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acme.account.util;

import java.util.Date;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class Time {

    private static volatile int offset;


    public static int currentTime() {
        return ((int) (System.currentTimeMillis() / 1000)) + offset;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis() + (offset * 1000L);
    }


    public static Date toDate(int time) {
        return new Date(time * 1000L);
    }


    public static Date toDate(long time) {
        return new Date(time);
    }


    public static long toMillis(long time) {
        return time * 1000L;
    }


    public static int getOffset() {
        return offset;
    }


    public static void setOffset(int offset) {
        Time.offset = offset;
    }

}
