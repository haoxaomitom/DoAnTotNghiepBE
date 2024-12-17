package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

//    @Query("SELECT p FROM Post p " +
//            "WHERE p.topPostEnd IS NULL OR p.topPostEnd <= CURRENT_DATE " +
//            "ORDER BY p.createdAt DESC")
//    Page<Post> findAllExpiredOrNeverToppedPostsOrderByCreatedAtDesc(Pageable pageable);


    // Sort posts by price with ascending and descending order
    @Query("SELECT p FROM Post p WHERE p.status = 'ACTIVE' ORDER BY p.price ASC")
    Page<Post> findAllByOrderByPriceAsc(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.status = 'ACTIVE' ORDER BY p.price DESC")
    Page<Post> findAllByOrderByPriceDesc(Pageable pageable);

    // Count of posts by district with summed comment counts
    @Query("SELECT p.districtName, COUNT(p) " +
            "FROM Post p WHERE p.status = 'ACTIVE' GROUP BY p.districtName")
    List<Object[]> countPostsByDistrict();


    // Search across multiple columns with case-insensitive partial matching
    @Query("SELECT p FROM Post p WHERE " +
            "p.status = 'ACTIVE' AND " + // Thêm điều kiện trạng thái
            "LOWER(CONCAT(p.parkingName, ' ', p.wardName, ' ', p.districtName, ' ', p.provinceName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);


    // Search by vehicle type
    @Query("SELECT p FROM Post p JOIN p.vehicleTypes vt WHERE " +
            "p.status = 'ACTIVE' AND " + // Thêm điều kiện trạng thái
            "LOWER(vt.vehicleTypesName) = LOWER(:vehicleType)")
    Page<Post> searchPostsByVehicleType(@Param("vehicleType") String vehicleType, Pageable pageable);


    Post findByPostId(Integer postId);

//    Page<Post> findAllByUserUserId(Long userId, Pageable pageable);


    Page<Post> findAllByUserUserIdAndStatus(Long userId, String status, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE CAST(p.postId AS string) LIKE %:postId%")
    Page<Post> findByPostIdContaining(@Param("postId") String postId, Pageable pageable);

    List<Post> findAllByUserUserId(Long userId);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN Payment pay ON p = pay.postId " +
            "WHERE p.status = 'ACTIVE' " +
            "GROUP BY p.postId, p.capacity, p.commentCount, p.createdAt, p.description, p.districtName, " +
            "p.latitude, p.longitude, p.parkingName, p.price, p.priceUnit, p.provinceName, p.status, " +
            "p.street, p.topPostEnd, p.user.userId, p.wardName " +
            "ORDER BY " +
            "CASE WHEN (MAX(pay.topPostEnd) > CURRENT_DATE OR p.topPostEnd > CURRENT_DATE) THEN 1 ELSE 0 END DESC, " +
            "MAX(CASE WHEN pay.topPostEnd > CURRENT_DATE THEN pay.paymentAmount ELSE 0 END) DESC, " +
            "MAX(pay.topPostEnd) DESC, p.createdAt DESC")
    Page<Post> findAllTopPostsOrderByPaymentAndDate(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.status = 'DELETE' WHERE p.postId = :postId")
    void softDeletePostById(Integer postId);

    @Query("SELECT p.status, COUNT(p) FROM Post p GROUP BY p.status")
    List<Object[]> countPostsGroupedByStatus();

    @Query("SELECT FUNCTION('MONTH', p.createdAt) AS month, COUNT(p) AS postCount " +
            "FROM Post p " +
            "WHERE p.status = 'ACTIVE' AND FUNCTION('YEAR', p.createdAt) = :year " +
            "GROUP BY FUNCTION('MONTH', p.createdAt) " +
            "ORDER BY month")
    List<Object[]> countActivePostsByMonthAndYear(@Param("year") int year);

}
