
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.entities.invention.PartKind;
import acme.realms.Inventor;

@Service
public class InventorPartShowService extends AbstractService<Inventor, Part> {

	@Autowired
	InventorPartRepository	repositorio;

	Part					parte;


	@Override
	public void load() {
		int partId = super.getRequest().getData("id", int.class);
		this.parte = this.repositorio.findPartByPartId(partId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int inventionId = this.parte.getInvention().getId();
		Invention invento = this.repositorio.findInventionByInventionId(inventionId);
		if (invento.getInventor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		Tuple tupla;
		SelectChoices tipos = SelectChoices.from(PartKind.class, this.parte.getKind());
		tupla = super.unbindObject(this.parte, "name", "notes", "money", "kind");
		tupla.put("inventionId", this.parte.getInvention().getId());
		tupla.put("kinds", tipos);

		// tupla.put("draftMode", this.parte.getInvention().getDraftMode());

	}
}
