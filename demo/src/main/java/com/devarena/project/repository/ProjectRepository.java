package com.devarena.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devarena.project.dto.response.ProjectSummaryResponseDto;
import com.devarena.project.entity.Project;
import com.devarena.project.enums.ProjectStatus;

import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("SELECT new com.devarena.project.dto.response.ProjectSummaryResponseDto(p.id, p.title, p.topic, p.owner.username) "
            + "FROM Project p WHERE p.owner.id = :ownerId")
    List<ProjectSummaryResponseDto> findByOwnerId(@Param("ownerId") UUID ownerId);

    @Query("SELECT new com.devarena.project.dto.response" +
            ".ProjectSummaryResponseDto(p.id, p.title, p.topic, p.owner.username) "
            + "FROM Project p WHERE p.topic = :topic")
    List<ProjectSummaryResponseDto> findByTopic(@Param("topic") String topic, Pageable pageable);

    @Transactional
    @Query("update Project p set p.voteCount = 1 + p.voteCount where p.id = :uuid")
    @Modifying(clearAutomatically = true)
    int incrementVoteCount(@Param("uuid") UUID projectId);

    @Transactional(readOnly = true)
    @Query("select p.voteCount from Project p where p.id = :uuid")
    int getVoteCountFromProjectId(@Param("uuid") UUID projectId);

    @Query("SELECT new com.devarena.project.dto.response.ProjectSummaryResponseDto(p.id, p.title, p.topic, p.owner.username) "
            + "FROM Project p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProjectSummaryResponseDto> searchByTitleContaining(@Param("keyword") String keyword);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    @EntityGraph(attributePaths = { "owner" })
    Optional<Project> findProjectWithOwner(@Param("projectId") UUID projectId);

    List<Project> findByOwnerIdAndStatusOrderByCreatedAtDesc(UUID ownerId, ProjectStatus status);

    List<Project> findByStatusOrderByVoteCountDesc(ProjectStatus status);

    long countByOwnerId(UUID ownerId);

}
