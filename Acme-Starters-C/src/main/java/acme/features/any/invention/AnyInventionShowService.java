
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	AnyInventionRepository	repositorio;

	Invention				invento;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invento = this.repositorio.showInvention(id);
	}
	@Override
	public void authorise() {
		// Puedo ver un invento si está publicado, o si yo soy el inventor
		if (this.invento == null)
			super.setAuthorised(false);
		else if (this.invento.getDraftMode().equals(false) || this.invento.getInventor().getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername())) //Compruebo si el inventor es el principal
			super.setAuthorised(true);
		else
			super.setAuthorised(false);

	}

	@Override
	public void unbind() {
		super.unbindObject(this.invento, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "cost");

	}

}
