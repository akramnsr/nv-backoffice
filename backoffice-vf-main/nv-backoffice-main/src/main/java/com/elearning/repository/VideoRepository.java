// src/main/java/com/elearning/repository/VideoRepository.java
package com.elearning.repository;

import com.elearning.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    // on pourra chercher par formation si besoin
    List<Video> findByFormationId(Long formationId);
}
