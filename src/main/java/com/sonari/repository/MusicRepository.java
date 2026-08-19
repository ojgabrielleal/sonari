package com.sonari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonari.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByUuid(UUID uuid);
}
