/*
 * InventorInventionController.java
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

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Controller
public class InventorInventionController extends AbstractController<Inventor, Invention> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", InventorInventionListService.class);
		super.addBasicCommand("show", InventorInventionShowService.class);
		super.addBasicCommand("create", InventorInventionCreateService.class);
		super.addBasicCommand("update", InventorInventionUpdateService.class);
		super.addBasicCommand("delete", InventorInventionDeleteService.class);

		super.addCustomCommand("publish", "update", InventorInventionPublishService.class);
	}

}
