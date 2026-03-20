
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
		Boolean res = false;
		if (this.parte != null && this.parte.getInvention() != null) {
			int inventionId = this.parte.getInvention().getId();
			Invention invention = this.repositorio.findInventionByInventionId(inventionId);
			res = invention != null && invention.getDraftMode().equals(true) && invention.getInventor().isPrincipal();
		}
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
		super.unbindObject(this.parte, "name", "description", "cost", "kind");
		super.unbindGlobal("draftMode", this.parte.getInvention().getDraftMode());
		SelectChoices kinds = SelectChoices.from(PartKind.class, this.parte.getKind());
		super.unbindGlobal("kinds", kinds);

	}
}
