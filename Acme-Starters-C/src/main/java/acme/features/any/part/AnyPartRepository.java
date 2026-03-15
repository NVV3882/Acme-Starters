
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("Select p from Part p where p.invention.id= :inventionId")
	Collection<Part> listAllPartsByInventionId(int inventionId);

	@Query("Select p from Part p where p.id = :id")
	Part showPartById(int id);
}
