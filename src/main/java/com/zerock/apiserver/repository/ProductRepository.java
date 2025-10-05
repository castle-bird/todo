package com.zerock.apiserver.repository;

import com.zerock.apiserver.domain.Product;
import com.zerock.apiserver.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface  ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    // 약간 LEFT JOIN 한다고 생각
    // @EntityGraph 안들어가면 쿼리 2번 보내야함
    @EntityGraph(attributePaths = "imageList")
    @Query("SELECT p FROM Product p WHERE p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("UPDATE Product p SET p.delFlag = :delFlag WHERE p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("delFlag") boolean delFlag);

    @Query("SELECT p, pi FROM Product p LEFT JOIN p.imageList pi WHERE pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
