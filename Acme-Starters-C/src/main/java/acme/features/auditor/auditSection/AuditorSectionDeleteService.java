
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.entities.audit.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorSectionRepository	repository;

	private AuditSection				section;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.section = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.section != null && this.section.getReport().getDraftMode() && this.section.getReport().getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		super.validateObject(this.section);
	}

	@Override
	public void execute() {
		this.repository.delete(this.section);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.section.getKind());

		tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind");
		tuple.put("reportId", this.section.getReport().getId());
		tuple.put("draftMode", this.section.getReport().getDraftMode());
		tuple.put("kinds", choices);
	}
}
