package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Files;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File.FileNotContentContainUsersIdDTO;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File.FileNotContentDTO;
import jakarta.transaction.Transactional;

@Repository
public interface FilesRepository extends JpaRepository<Files, UUID> {
   @Query("SELECT DISTINCT f FROM Files f JOIN f.users u WHERE u.id = :userId AND f.fileType IN :fileTypes")
   List<Files> findFilesByUserAndTypes(@Param("userId") Integer userId,
         @Param("fileTypes") List<FileTypeEnum> fileTypes);

   @Query("SELECT DISTINCT new br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File.FileNotContentDTO(f.id, f.fileName, f.fileType, f.insertDate, f.updateDate) "
         +
         "FROM Files f JOIN f.users u WHERE u.id = :userId AND f.fileType IN :fileTypes")
   List<FileNotContentDTO> findFileSummariesByUserAndTypes(@Param("userId") Integer userId,
         @Param("fileTypes") List<FileTypeEnum> fileTypes);

   @Query("SELECT DISTINCT new br.gabriel.infnet.gabrielvictorapi.Infraestructure.DTO.File.FileNotContentContainUsersIdDTO("
         +
         "f.id, f.fileName, f.fileType, f.insertDate, f.updateDate, " +
         "(SELECT u.id FROM Files f2 JOIN f2.users u WHERE f2.id = f.id)) " +
         "FROM Files f WHERE f.id = :id")
   Optional<FileNotContentContainUsersIdDTO> findFileSummaryById(@Param("id") UUID id);

   @Query("""
      SELECT DISTINCT f FROM Files f
      LEFT JOIN FETCH f.users
      LEFT JOIN FETCH f.owners o1
      LEFT JOIN FETCH o1.user
      LEFT JOIN FETCH f.products p
      LEFT JOIN FETCH p.owners o2
      LEFT JOIN FETCH o2.user
      WHERE f.id = :id
   """)
   Optional<Files> findByIdWithDeepAssociations(@Param("id") UUID id);



   @Modifying
   @Transactional
   @Query(value = "DELETE FROM user_files WHERE file_id = :fileId", nativeQuery = true)
   int deleteUserAssociations(@Param("fileId") UUID fileId);

   @Modifying
   @Transactional
   @Query(value = "DELETE FROM owner_files WHERE file_id = :fileId", nativeQuery = true)
   int deleteOwnersAssociations(@Param("fileId") UUID fileId);

   @Modifying
   @Transactional
   @Query(value = "DELETE FROM product_files WHERE file_id = :fileId", nativeQuery = true)
   int deleteProductAssociations(@Param("fileId") UUID fileId);

}
