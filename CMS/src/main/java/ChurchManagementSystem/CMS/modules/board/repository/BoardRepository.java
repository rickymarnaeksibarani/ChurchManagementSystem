package ChurchManagementSystem.CMS.modules.board.repository;

import ChurchManagementSystem.CMS.modules.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, JpaSpecificationExecutor<BoardEntity>{
//    @Query("""
//    SELECT b FROM BoardEntity b
//    ORDER BY
//      CASE b.fungsi
//        WHEN 'PENDETA' THEN 1
//        WHEN 'CALON_PENDETA' THEN 2
//        WHEN 'SINTUA' THEN 3
//        WHEN 'GURU_HURIA' THEN 4
//        WHEN 'BIBELVROUW' THEN 5
//        WHEN 'DIAGONES' THEN 6
//        WHEN 'CALON_DIAGONES' THEN 7
//        WHEN 'CALON_SINTUA' THEN 8
//        WHEN 'STAFF' THEN 9
//      END
//""")
//    Page<BoardEntity> findAllOrderByFungsi(Pageable pageable);

}
