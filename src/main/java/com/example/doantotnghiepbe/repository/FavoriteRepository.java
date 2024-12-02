package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    //like post & isFavorite
    boolean existsByUserUserIdAndPostPostId(Long userId, Integer postId);

    //getFavoritesByUserId
    @Query("SELECT f FROM Favorite f JOIN FETCH f.post p WHERE f.user.userId = :userId AND p.status = 'ACTIVE'")
    Page<Favorite> getFavoritesByUserUserId(@Param("userId") Long userId, Pageable pageable);

    //Unlike post && toggle favorite
    Optional<Favorite> findByUserUserIdAndPostPostId(Long userId, Integer postId);


}
