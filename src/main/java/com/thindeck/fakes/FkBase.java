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
package com.thindeck.fakes;

import com.google.common.io.Files;
import com.jcabi.aspects.Immutable;
import com.thindeck.api.Base;
import com.thindeck.api.Deck;
import com.thindeck.api.User;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.io.FileUtils;

/**
 * Mock of {@link Base}.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 0.4
 */
@Immutable
@ToString
@EqualsAndHashCode
public final class FkBase implements Base {

    /**
     * Dir path.
     */
    private final transient String path;

    /**
     * Ctor.
     * @throws IOException If fails
     */
    public FkBase() throws IOException {
        this(FkBase.temp());
    }

    /**
     * Ctor.
     * @param file File to use for XML
     */
    public FkBase(final File file) {
        this.path = file.getAbsolutePath();
    }

    @Override
    public User user(final String name) {
        return new FkUser(new File(this.path));
    }

    @Override
    public Iterable<Deck> active() throws IOException {
        return Collections.<Deck>singleton(new FkDeck());
    }

    /**
     * Create temp dir.
     * @return Temp dir
     * @throws IOException If fails
     */
    private static File temp() throws IOException {
        final File file = Files.createTempDir();
        FileUtils.forceDeleteOnExit(file);
        return file;
    }

}
