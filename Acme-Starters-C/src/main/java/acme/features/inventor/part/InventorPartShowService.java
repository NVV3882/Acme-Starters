
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		res = false;
		if (this.parte != null && this.parte.getInvention() != null) {
			res = this.parte.getInvention().getInventor().isPrincipal();
		}
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.parte, "name", "description", "cost", "kind");
		super.unbindGlobal("draftMode", this.parte.getInvention().getDraftMode());
		super.unbindGlobal("inventionId", this.parte.getInvention().getId());
		SelectChoices tipos = SelectChoices.from(PartKind.class, this.parte.getKind());
		super.unbindGlobal("kinds", tipos);

	}
}
