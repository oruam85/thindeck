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
import com.jcabi.dynamo.Item;
import com.thindeck.api.Console;
import com.thindeck.api.Memo;
import com.thindeck.api.Repo;
import com.thindeck.api.mock.MkConsole;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Dynamo implementation of {@link Repo}.
 *
 * @author Krzysztof Krason (Krzysztof.Krason@gmail.com)
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 */
@ToString
@Immutable
@EqualsAndHashCode(of = "item")
public final class DyRepo implements Repo {

    /**
     * Table name.
     */
    public static final String TBL = "repos";

    /**
     * URN of the user (owner of the repo).
     */
    public static final String HASH = "urn";

    /**
     * Unique name of the repo, for that user.
     */
    public static final String RANGE = "name";

    /**
     * When updated.
     */
    public static final String ATTR_UPDATED = "updated";

    /**
     * Memo.
     */
    public static final String ATTR_MEMO = "memo";

    /**
     * Item.
     */
    private final transient Item item;

    /**
     * Ctor.
     * @param itm Item
     */
    public DyRepo(@NotNull final Item itm) {
        this.item = itm;
    }

    @Override
    public String name() throws IOException {
        return this.item.get(DyRepo.RANGE).getS();
    }

    @Override
    public Console console() {
        return new MkConsole();
    }

    @Override
    public Memo memo() {
        return new DyMemo(this.item);
    }
}
