package com.devarena.vote.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*; 

@Setter
@Getter
@AllArgsConstructor
public class VoteResponseDto {
    private UUID voteId;
    
    private UUID votersId;

    private UUID projectId;

    @CreationTimestamp
    LocalDateTime createdAt;
}
