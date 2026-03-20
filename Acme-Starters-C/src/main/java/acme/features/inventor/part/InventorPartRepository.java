
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Repository
public interface InventorPartRepository extends AbstractRepository {

	@Query("Select p from Part p where p.invention.id=:inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("Select i from Invention i where i.id=:inventionId")
	Invention findInventionByInventionId(int inventionId);

	@Query("Select p from Part p where p.id=:partId")
	Part findPartByPartId(int partId);
}
