
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
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	@Autowired
	InventorPartRepository	repository;

	Part					part;

	Invention				invention;


	@Override
	public void load() {
		int inventionId;
		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionByInventionId(inventionId);

		this.part = super.newObject(Part.class);
		this.part.setInvention(this.invention);
	}

	@Override
	public void authorise() {
		boolean res;

		res = this.invention != null && this.invention.getDraftMode().equals(true) && this.invention.getInventor().isPrincipal();

		super.setAuthorised(res);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
		super.unbindGlobal("draftMode", this.part.getInvention().getDraftMode());
		super.unbindGlobal("inventionId", this.invention.getId());
		SelectChoices kinds = SelectChoices.from(PartKind.class, this.part.getKind());
		super.unbindGlobal("kinds", kinds);
	}

}
