package org.accula.api.db.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pull {
    @EqualsAndHashCode.Include
    Long id;
    Integer number;
    String title;
    boolean open;
    Instant createdAt;
    Instant updatedAt;
    CommitSnapshot head;
    CommitSnapshot base;
    GithubUser author;
    Long projectId;
}
