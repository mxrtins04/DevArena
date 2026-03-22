package com.devarena.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devarena.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId")
    List<Project> findByOwnerId(@Param("ownerId")Long ownerId);

    List<Project> findByTopic(String topic, Pageable pageable);

    @Query("SELECT p FROM PROJECT p WHERE p.title CONTAINS :Lower(keyword)")
    List<Project> searchByTitleCOntaining(@Param("keyword") String keyword);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    @EntityGraph(attributePaths = {"owner"})
    Optional<Project> findProjectWithOwner(@Param("projectId") Long projectId);

}
