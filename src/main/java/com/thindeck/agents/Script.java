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
package com.thindeck.agents;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.jcabi.aspects.Immutable;
import com.jcabi.log.Logger;
import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

/**
 * Execs a script.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 0.1
 */
@Immutable
final class Script {

    /**
     * Sript name.
     */
    private final transient String name;

    /**
     * Ctor.
     * @param res Resource name
     */
    Script(final String res) {
        this.name = res;
    }

    /**
     * Run it.
     * @param host The host to run it on
     * @param args Arguments to pass into it
     * @throws IOException If fails
     */
    public void exec(final String host, final Map<String, String> args)
        throws IOException {
        new Shell.Safe(new Shell.Safe(new Remote(host))).exec(
            Joiner.on(" && ").join(
                "dir=$(mktemp -d -t td-XXXX)",
                "cd \"${dir}\"",
                "cat > script.sh",
                "chmod a+x script.sh",
                Joiner.on(' ').join(
                    Iterables.transform(
                        args.entrySet(),
                        new Function<Map.Entry<String, String>, String>() {
                            @Override
                            public String apply(
                                final Map.Entry<String, String> ent) {
                                return SSH.escape(
                                    String.format(
                                        "export %s=%s",
                                        ent.getKey(), ent.getValue()
                                    )
                                );
                            }
                        }
                    )
                ),
                "./script.sh %s",
                "rm -rf \"${dir}\""
            ),
            this.getClass().getResourceAsStream(this.name),
            Logger.stream(Level.INFO, this),
            Logger.stream(Level.WARNING, this)
        );
    }

}
