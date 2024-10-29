package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p.districtName, COUNT(p), SUM(p.commentCount) FROM Post p GROUP BY p.districtName")
    List<Object[]> countPostsByDistrict();

    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(CONCAT(p.parkingName, ' ', p.wardName, ' ', p.districtName, ' ', p.provinceName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.vehicleTypes vt WHERE LOWER(vt.vehicleTypeName) = LOWER(:vehicleType)")
    Page<Post> searchPostsByVehicleType(@Param("vehicleType") String vehicleType, Pageable pageable);

    Page<Post> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Post> findAllByOrderByPriceDesc(Pageable pageable);
}


