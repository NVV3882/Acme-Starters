
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired
	AnyPartRepository	repositorio;

	Part				parte;
	Invention			invento;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.parte = this.repositorio.showPartById(id);
		if (this.parte != null)
			this.invento = this.parte.getInvention();
	}
	@Override
	public void authorise() {
		//puedo ver una parte si esta publicada el invento, o si yo soy el creador
		if (this.parte == null)
			super.setAuthorised(false);
		else if (this.invento.getDraftMode().equals(false) || this.invento.getInventor().getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername()))
			super.setAuthorised(true);
		else
			super.setAuthorised(false);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.parte, "name", "description", "cost", "kind");
	}

}
