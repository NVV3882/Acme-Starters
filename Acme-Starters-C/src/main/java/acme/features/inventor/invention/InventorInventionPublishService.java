/*
 * InventorInventionPublishService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getDraftMode().equals(true) && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);

	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		{
			boolean MinimoUnaParteParaSerPublicado;
			Collection<Part> parts = this.repository.findPartsByInventionId(this.invention.getId());
			MinimoUnaParteParaSerPublicado = parts.size() >= 1;

			super.state(MinimoUnaParteParaSerPublicado, "*", "acme.validation.invention.MinimoUnaParteParaSerPublicado");

		}
		{
			boolean correctIntervale;

			boolean startNotNull = this.invention.getStartMoment() != null;
			boolean endNotNull = this.invention.getEndMoment() != null;

			correctIntervale = startNotNull && endNotNull && MomentHelper.isFuture(this.invention.getStartMoment()) && MomentHelper.isFuture(this.invention.getEndMoment())
				&& MomentHelper.isAfter(this.invention.getEndMoment(), this.invention.getStartMoment());

			super.state(correctIntervale, "*", "acme.validation.invention.correctsMoments.message");
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "cost");
	}

}
