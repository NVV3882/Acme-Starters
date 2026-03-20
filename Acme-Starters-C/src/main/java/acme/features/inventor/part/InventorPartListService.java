
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

	private Invention		invention;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		this.partes = this.repositorio.findPartsByInventionId(inventionId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repositorio.findInventionByInventionId(inventionId);
		if (this.invention != null && this.invention.getInventor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {

		super.unbindObjects(this.partes, "name", "description", "cost", "kind");
		super.unbindGlobal("draftMode", this.invention.getDraftMode());
		super.unbindGlobal("inventionId", this.invention.getId());

	}
}
