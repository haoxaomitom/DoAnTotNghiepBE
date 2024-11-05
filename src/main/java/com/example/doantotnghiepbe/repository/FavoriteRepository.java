package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    boolean existsByUserUserIdAndPostPostId(Integer userId, Integer postId);

    @Query("SELECT f FROM Favorite f JOIN FETCH f.post WHERE f.user.userId = :userId")
    List<Favorite> getFavoritesByUserUserId(@Param("userId") Integer userId);

    Optional<Favorite> findByUserUserIdAndPostPostId(Integer userId, Integer postId);


}
