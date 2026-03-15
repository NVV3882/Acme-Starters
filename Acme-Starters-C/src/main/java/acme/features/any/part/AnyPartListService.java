
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	AnyPartRepository	repositorio;

	Collection<Part>	partes;

	Invention			invento;
	int					inventionId;


	@Override
	public void load() {
		this.invento = this.repositorio.getInvention(super.getRequest().getData("inventionId", int.class));
		this.inventionId = super.getRequest().getData("inventionId", int.class);
		this.partes = this.repositorio.listAllPartsByInventionId(this.inventionId);

	}
	@Override
	public void authorise() {
		// puedo listar las partes de un invento, si dicho invento esta publicado, o si yo soy el inventor.
		if (this.invento == null)
			super.setAuthorised(false);
		else if (this.invento.getDraftMode().equals(false) || this.invento.getInventor().getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername()))
			super.setAuthorised(true);
		else
			super.setAuthorised(false);

	}

	@Override
	public void unbind() {
		super.unbindObjects(this.partes, "name", "description", "cost", "kind");
	}
}
