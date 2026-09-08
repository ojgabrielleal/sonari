package com.sonari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonari.entity.PlaylistMusic;

public interface MusicRepository extends JpaRepository<PlaylistMusic, Long> {
    Optional<PlaylistMusic> findByUuid(UUID uuid);
}
