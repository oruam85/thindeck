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
package com.thindeck.api;

import com.jcabi.aspects.Immutable;
import com.jcabi.urn.URN;
import java.net.URISyntaxException;
import javax.validation.constraints.NotNull;

/**
 * User.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface User {
    /**
     * Default User.
     */
    User DEFAULT = new User() {
        @Override
        public URN urn() {
            try {
                return new URN("urn:thindeck:DEFAULT");
            } catch (final URISyntaxException ex) {
                throw new IllegalStateException(ex);
            }
        }
        @Override
        public Repos repos() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Usage usage() {
            throw new UnsupportedOperationException();
        }
    };

    /**
     * URN.
     *
     * <p>Every URN is a unique identifier of a user, in the entire system.
     * URN's are provided by OAuth authenticators
     * (like Github, Facebook, Twitter, etc.).
     *
     * @return URN
     */
    @NotNull(message = "URN can't be null")
    URN urn();

    /**
     * Repositories.
     * @return All repositories of this user
     */
    @NotNull(message = "repositories can't be null")
    Repos repos();

    /**
     * Consumption of resources.
     * @return Usage
     */
    @NotNull(message = "usage can't be null")
    Usage usage();

}
