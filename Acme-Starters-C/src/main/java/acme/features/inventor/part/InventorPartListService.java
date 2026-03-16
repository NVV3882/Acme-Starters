
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	InventorPartRepository	repositorio;

	Collection<Part>		partes;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		this.partes = this.repositorio.findPartsByInventionId(inventionId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int inventionId = super.getRequest().getData("inventionId", int.class);
		Invention invention = this.repositorio.findInventionByInventionId(inventionId);
		if (invention.getInventor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		super.unbindObjects(this.partes, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", inventionId);

	}
}
