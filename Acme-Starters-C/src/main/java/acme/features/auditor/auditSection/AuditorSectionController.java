
package acme.features.auditor.auditSection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Controller
public class AuditorSectionController extends AbstractController<Auditor, AuditSection> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditorSectionListService.class);
		super.addBasicCommand("show", AuditorSectionShowService.class);
		super.addBasicCommand("create", AuditorSectionCreateService.class);
		super.addBasicCommand("update", AuditorSectionUpdateService.class);
		super.addBasicCommand("delete", AuditorSectionDeleteService.class);
	}
}
