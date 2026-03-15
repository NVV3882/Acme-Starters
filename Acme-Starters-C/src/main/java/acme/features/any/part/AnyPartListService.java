
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	AnyPartRepository	repositorio;

	Collection<Part>	partes;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		this.partes = this.repositorio.listAllPartsByInventionId(inventionId);
	}
	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.partes, "name", "description", "cost", "kind");
	}
}
