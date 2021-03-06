/*
 * Copyright 2013-2017 the original author or authors.
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

package org.cloudfoundry.uaa.tokens;

import org.cloudfoundry.Nullable;
import org.cloudfoundry.QueryParameter;
import org.immutables.value.Value;

/**
 * The request payload for the get token by OpenId operation
 */
@Value.Immutable
abstract class _GetTokenByOpenIdRequest {

    /**
     * The authorization code
     */
    @QueryParameter("code")
    abstract String getAuthorizationCode();

    /**
     * The client identifier
     */
    @QueryParameter("client_id")
    abstract String getClientId();

    /**
     * The client's secret passphrase
     */
    @QueryParameter("client_secret")
    abstract String getClientSecret();

    /**
     * The redirection URI
     */
    @Nullable
    @QueryParameter("redirect_uri")
    abstract String getRedirectUri();

    /**
     * The token format
     */
    @Nullable
    @QueryParameter("token_format")
    abstract TokenFormat getTokenFormat();

}
