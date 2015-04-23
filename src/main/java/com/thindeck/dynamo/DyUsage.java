/**
 * Copyright (c) 2014-2015, Thindeck.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the thindeck.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.thindeck.dynamo;

import com.jcabi.aspects.Immutable;
import com.thindeck.api.Usage;
import com.thindeck.api.User;
import java.util.Date;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Dynamo implementation of {@link Usage}.
 *
 * @author Carlos Miranda (miranda.cma@gmail.com)
 * @version $Id$
 * @since 0.5
 */
@Immutable
@ToString
@EqualsAndHashCode (of = "usr")
public final class DyUsage implements Usage {
    /**
     * User.
     */
    private final transient User usr;

    /**
     * Ctor.
     * @param user The user
     */
    DyUsage(final User user) {
        this.usr = user;
    }

    @Override
    public User user() {
        return this.usr;
    }

    // @todo #408:30min Let's implement the add() method. This method should
    //  add the supplied Value to the usage info of the current user. Make sure
    //  to include unit tests for this method in DyUsageTest.
    @Override
    public void add(final Value value) {
        throw new UnsupportedOperationException("add not yet implemented");
    }

    // @todo #408:30min Let's implement the select() method. This method should
    //  return the values that fit the given criteria (user and date range).
    //  Make sure to include unit tests for this method in DyUsageTest.
    @Override
    public Iterable<Value> select(final Date start, final Date end) {
        throw new UnsupportedOperationException("select not yet implemented");
    }

    // @todo #408:30min Let's implement the sum() method. This method should
    //  return the aggregate sum of values that fit the given criteria. Make
    //  sure to include unit tests for this method in DyUsageTest.
    // @checkstyle ParameterNumber (3 lines)
    @Override
    public double sum(final Date start, final Date end, final Type type,
        final Map<String, String> dims) {
        throw new UnsupportedOperationException("sum not yet implemented");
    }
}
