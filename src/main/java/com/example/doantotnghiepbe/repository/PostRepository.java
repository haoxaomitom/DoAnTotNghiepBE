package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Sort posts by price with ascending and descending order
    @Query("SELECT p FROM Post p ORDER BY p.price ASC")
    Page<Post> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.price DESC")
    Page<Post> findAllByOrderByPriceDesc(Pageable pageable);

    // Count of posts by district with summed comment counts
    @Query("SELECT p.districtName, COUNT(p), SUM(p.commentCount) FROM Post p GROUP BY p.districtName")
    List<Object[]> countPostsByDistrict();

    // Search across multiple columns with case-insensitive partial matching
    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(CONCAT(p.parkingName, ' ', p.wardName, ' ', p.districtName, ' ', p.provinceName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Search by vehicle type
    @Query("SELECT p FROM Post p JOIN p.vehicleTypes vt WHERE LOWER(vt.vehicleTypeName) = LOWER(:vehicleType)")
    Page<Post> searchPostsByVehicleType(@Param("vehicleType") String vehicleType, Pageable pageable);

    Post findByPostId(Integer postId);

    List<Post> findAllByUserUserId(Long userId);
}
