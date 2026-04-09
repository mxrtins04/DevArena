package com.devarena.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devarena.vote.entity.Vote;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
    Boolean existsByVoterIdAndProjectId(UUID voterId, UUID projectId);

    Optional<Vote> findByVoterIdAndProjectId(UUID voterId, UUID projectId);

}