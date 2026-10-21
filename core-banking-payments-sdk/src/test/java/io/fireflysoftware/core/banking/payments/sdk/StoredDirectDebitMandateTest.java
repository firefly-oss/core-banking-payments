/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.banking.payments.sdk;

import io.fireflysoftware.core.banking.payments.sdk.api.DirectDebitCollectionApi;
import io.fireflysoftware.core.banking.payments.sdk.api.DirectDebitMandateApi;
import io.fireflysoftware.core.banking.payments.sdk.api.StandingOrderRunApi;
import io.fireflysoftware.core.banking.payments.sdk.invoker.ApiClient;
import io.fireflysoftware.core.banking.payments.sdk.model.DirectDebitCollectionDTO;
import io.fireflysoftware.core.banking.payments.sdk.model.DirectDebitMandateDTO;
import io.fireflysoftware.core.banking.payments.sdk.model.StandingOrderRunDTO;
import io.fireflysoftware.core.banking.payments.web.PaymentsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PaymentsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.application.name=core-banking-payments",
                "spring.application.version=26.10.4",
                "spring.application.description=Payments core",
                "spring.application.team.name=Firefly Software Foundation",
                "spring.application.team.email=dev@fireflysoftware.io",
                "spring.r2dbc.url=r2dbc:postgresql://127.0.0.1:5432/firefly_payments_it?sslMode=disable",
                "spring.r2dbc.username=ancongui",
                "spring.r2dbc.password=",
                "spring.flyway.url=jdbc:postgresql://127.0.0.1:5432/firefly_payments_it?sslMode=disable",
                "spring.flyway.user=ancongui",
                "spring.flyway.password="
        })
class StoredDirectDebitMandateTest {

    static {
        System.setProperty("DB_HOST", "127.0.0.1");
        System.setProperty("DB_PORT", "5432");
        System.setProperty("DB_NAME", "firefly_payments_it");
        System.setProperty("DB_USERNAME", "ancongui");
        System.setProperty("DB_PASSWORD", "");
        System.setProperty("DB_SSL_MODE", "disable");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "ancongui", "");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE DATABASE firefly_payments_it");
        } catch (SQLException exception) {
            if (!"42P04".equals(exception.getSQLState()) && !"23505".equals(exception.getSQLState())) {
                throw new IllegalStateException(exception);
            }
        }
    }

    @LocalServerPort
    private int port;

    @Test
    void directDebitMandateIsSavedAndReadBackThroughTheSdk() {
        UUID debtor = UUID.randomUUID();
        UUID creditor = UUID.randomUUID();
        DirectDebitMandateApi api = new DirectDebitMandateApi(client());
        DirectDebitMandateDTO created = api.createDirectDebitMandate(new DirectDebitMandateDTO()
                .debtorAccountId(debtor)
                .creditorPartyId(creditor)
                .status(DirectDebitMandateDTO.StatusEnum.ACTIVE)
                .amountLimit(new BigDecimal("100.00"))
                .currency("GBP")
                .reference("MANDATE-STORED-1"), "dd-store").block();
        DirectDebitMandateDTO read = api.getDirectDebitMandate(created.getDirectDebitMandateId(), "dd-read").block();
        assertThat(read.getDebtorAccountId()).isEqualTo(debtor);
        assertThat(read.getCreditorPartyId()).isEqualTo(creditor);
        assertThat(read.getStatus()).isEqualTo(DirectDebitMandateDTO.StatusEnum.ACTIVE);
        assertThat(read.getAmountLimit()).isEqualByComparingTo("100.00");
        assertThat(read.getCurrency()).isEqualTo("GBP");
        assertThat(read.getReference()).isEqualTo("MANDATE-STORED-1");

        DirectDebitCollectionApi collections = new DirectDebitCollectionApi(client());
        DirectDebitCollectionDTO collected = collections.collectDirectDebit(new DirectDebitCollectionDTO()
                .directDebitMandateId(created.getDirectDebitMandateId())
                .amount(new BigDecimal("40.00"))
                .currency("GBP")
                .valueDate(LocalDate.of(2026, 10, 21)), "dd-collect").block();
        DirectDebitCollectionDTO readCollection = collections.getDirectDebitCollection(
                collected.getDirectDebitCollectionId(), "dd-collect-read").block();
        assertThat(readCollection.getStatus()).isEqualTo("CONFIRMED");
        assertThat(readCollection.getAmount()).isEqualByComparingTo("40.00");
        assertThat(readCollection.getDirectDebitMandateId()).isEqualTo(created.getDirectDebitMandateId());
    }

    @Test
    void standingOrderRunIsSavedAndReadBack() {
        UUID scheduleId = UUID.randomUUID();
        StandingOrderRunApi api = new StandingOrderRunApi(client());
        StandingOrderRunDTO created = api.runStandingOrder(new StandingOrderRunDTO()
                .paymentScheduleId(scheduleId)
                .amount(new BigDecimal("25.00"))
                .currency("GBP")
                .valueDate(LocalDate.of(2026, 10, 21))
                .nextExecutionDate(LocalDate.of(2026, 10, 21))
                .executionCount(0), "so-run").block();
        StandingOrderRunDTO read = api.getStandingOrderRun(created.getStandingOrderRunId(), "so-read").block();
        assertThat(read.getPaymentScheduleId()).isEqualTo(scheduleId);
        assertThat(read.getAmount()).isEqualByComparingTo("25.00");
        assertThat(read.getNextExecutionDate()).isEqualTo(LocalDate.of(2026, 11, 21));
        assertThat(read.getExecutionCount()).isEqualTo(1);
    }

    private ApiClient client() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://127.0.0.1:" + port);
        return apiClient;
    }
}
