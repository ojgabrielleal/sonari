package com.sonari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonari.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByUuid(UUID uuid);
}
