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

package org.cloudfoundry.operations.spaceadmin;

import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.client.v2.organizations.ListOrganizationSpaceQuotaDefinitionsRequest;
import org.cloudfoundry.client.v2.organizations.ListOrganizationSpaceQuotaDefinitionsResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.SpaceQuotaDefinitionResource;
import org.cloudfoundry.operations.AbstractOperationsApiTest;
import org.junit.Before;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.ScriptedSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cloudfoundry.operations.TestObjects.fill;
import static org.mockito.Mockito.when;

public final class DefaultSpaceAdminTest {

    private static void requestSpaceQuotaDefinitions(CloudFoundryClient cloudFoundryClient, String organizationId) {
        when(cloudFoundryClient.organizations()
            .listSpaceQuotaDefinitions(ListOrganizationSpaceQuotaDefinitionsRequest.builder()
                .organizationId(organizationId)
                .page(1)
                .build()))
            .thenReturn(Mono
                .just(fill(ListOrganizationSpaceQuotaDefinitionsResponse.builder())
                    .resource(fill(SpaceQuotaDefinitionResource.builder(), "space-quota-definition-")
                        .build())
                    .build()));
    }

    private static void requestSpaceQuotaDefinitionsEmpty(CloudFoundryClient cloudFoundryClient, String organizationId) {
        when(cloudFoundryClient.organizations()
            .listSpaceQuotaDefinitions(ListOrganizationSpaceQuotaDefinitionsRequest.builder()
                .organizationId(organizationId)
                .page(1)
                .build()))
            .thenReturn(Mono
                .just(fill(ListOrganizationSpaceQuotaDefinitionsResponse.builder())
                    .build()));
    }

    public static final class Get extends AbstractOperationsApiTest<SpaceQuota> {

        private final DefaultSpaceAdmin spaceAdmin = new DefaultSpaceAdmin(Mono.just(this.cloudFoundryClient), Mono.just(TEST_ORGANIZATION_ID));

        @Before
        public void setUp() throws Exception {
            requestSpaceQuotaDefinitions(this.cloudFoundryClient, TEST_ORGANIZATION_ID);
        }

        @Override
        protected ScriptedSubscriber<SpaceQuota> expectations() {
            return ScriptedSubscriber.<SpaceQuota>create()
                .expectNext(fill(SpaceQuota.builder(), "space-quota-definition-")
                    .build())
                .expectComplete();
        }

        @Override
        protected Mono<SpaceQuota> invoke() {
            return this.spaceAdmin
                .get(GetSpaceQuotaRequest.builder()
                    .name("test-space-quota-definition-name")
                    .build());
        }

    }

    public static final class GetNotFound extends AbstractOperationsApiTest<SpaceQuota> {

        private final DefaultSpaceAdmin spaceAdmin = new DefaultSpaceAdmin(Mono.just(this.cloudFoundryClient), Mono.just(TEST_ORGANIZATION_ID));

        @Before
        public void setUp() throws Exception {
            requestSpaceQuotaDefinitionsEmpty(this.cloudFoundryClient, TEST_ORGANIZATION_ID);
        }

        @Override
        protected ScriptedSubscriber<SpaceQuota> expectations() {
            return ScriptedSubscriber.<SpaceQuota>create()
                .consumeErrorWith(t -> assertThat(t).isInstanceOf(IllegalArgumentException.class).hasMessage("Space Quota test-space-quota-definition-name does not exist"));
        }

        @Override
        protected Mono<SpaceQuota> invoke() {
            return this.spaceAdmin
                .get(GetSpaceQuotaRequest.builder()
                    .name("test-space-quota-definition-name")
                    .build());
        }

    }

    public static final class List extends AbstractOperationsApiTest<SpaceQuota> {

        private final DefaultSpaceAdmin spaceAdmin = new DefaultSpaceAdmin(Mono.just(this.cloudFoundryClient), Mono.just(TEST_ORGANIZATION_ID));

        @Before
        public void setUp() throws Exception {
            requestSpaceQuotaDefinitions(this.cloudFoundryClient, TEST_ORGANIZATION_ID);
        }

        @Override
        protected ScriptedSubscriber<SpaceQuota> expectations() {
            return ScriptedSubscriber.<SpaceQuota>create()
                .expectNext(fill(SpaceQuota.builder(), "space-quota-definition-")
                    .build())
                .expectComplete();
        }

        @Override
        protected Publisher<SpaceQuota> invoke() {
            return this.spaceAdmin
                .listQuotas();
        }

    }

}
