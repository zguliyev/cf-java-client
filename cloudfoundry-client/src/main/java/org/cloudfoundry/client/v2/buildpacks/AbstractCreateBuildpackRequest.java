/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.client.v2.buildpacks;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudfoundry.Nullable;
import org.immutables.value.Value;

/**
 * The request payload for the Create Buildpack
 */
@Value.Immutable
abstract class AbstractCreateBuildpackRequest {

    /**
     * The enabled flag
     */
    @JsonProperty("enabled")
    @Nullable
    abstract Boolean getEnabled();

    /**
     * The filename
     */
    @JsonProperty("filename")
    @Nullable
    abstract String getFilename();

    /**
     * The locked flag
     */
    @JsonProperty("locked")
    @Nullable
    abstract Boolean getLocked();

    /**
     * The name
     */
    @JsonProperty("name")
    abstract String getName();

    /**
     * The position
     */
    @JsonProperty("position")
    @Nullable
    abstract Integer getPosition();

}