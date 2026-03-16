
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
public class InventorPartDeleteService extends AbstractService<Inventor, Part> {

	@Autowired
	InventorPartRepository	repositorio;

	Part					parte;


	@Override
	public void load() {
		int parteId = super.getRequest().getData("id", int.class);
		this.parte = this.repositorio.findPartByPartId(parteId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int inventionId = this.parte.getInvention().getId();
		Invention invention = this.repositorio.findInventionByInventionId(inventionId);
		if (invention.getDraftMode().equals(true) && invention.getInventor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void bind() {
		super.bindObject(this.parte, "name", "description", "cost", "kind");
	}
	@Override
	public void validate() {
		super.validateObject(this.parte);
	}
	@Override
	public void execute() {
		this.repositorio.delete(this.parte);
	}
	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(PartKind.class, this.parte.getKind());

		tuple = super.unbindObject(this.parte, "name", "description", "cost", "kind");
		tuple.put("inventionId", this.parte.getInvention().getId());

		// tuple.put("draftMode", this.parte.getInvention().getDraftMode());

		tuple.put("kinds", choices);
	}
}
