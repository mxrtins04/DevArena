package com.devarena.project.dto.response;

import java.util.UUID;

import com.devarena.project.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {
    private UUID projectId;
    private String ownerUsername;
    private String title;
    private String description;
    private String repositoryUrl;
    private Integer voteCount;
    private ProjectStatus status;
    private LocalDateTime createdAt;
}
