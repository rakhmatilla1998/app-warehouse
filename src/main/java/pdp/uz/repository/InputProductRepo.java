package pdp.uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.entity.InputProduct;

@Repository
public interface InputProductRepo extends JpaRepository<InputProduct, Long> {
}
