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

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.jcabi.aspects.Immutable;
import com.jcabi.dynamo.Credentials;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import com.jcabi.dynamo.retry.ReRegion;
import com.jcabi.manifests.Manifests;

/**
 * Region for tests.
 *
 * @author Adam Siemion (adam.siemion.null@lemonsoftware.pl)
 * @version $Id$
 */
@Immutable
public final class RegionLocalDynamo implements Region {

    /**
     * Region.
     */
    private final transient Region region;

    /**
     * Public constructor.
     */
    public RegionLocalDynamo() {
        this.region = new Region.Prefixed(
            new ReRegion(
                new Region.Simple(
                    new Credentials.Direct(
                        new Credentials.Simple(
                            Manifests.read("Thindeck-DynamoKey"),
                            Manifests.read("Thindeck-DynamoSecret")
                        ),
                        Integer.parseInt(System.getProperty("dynamo.port"))
                    )
                )
            ),
            "td-"
        );
    }

    @Override
    public AmazonDynamoDB aws() {
        return this.region.aws();
    }

    @Override
    public Table table(final String name) {
        return this.region.table(name);
    }
}
