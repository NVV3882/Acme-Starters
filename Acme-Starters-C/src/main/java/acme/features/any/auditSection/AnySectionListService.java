
package acme.features.any.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;

@Service
public class AnySectionListService extends AbstractService<Any, AuditSection> {

	@Autowired
	AnySectionRepository		repositorio;

	Collection<AuditSection>	sections;


	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		this.sections = this.repositorio.listAllSectionsByReportId(reportId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int reportId = super.getRequest().getData("reportId", int.class);
		if (this.repositorio.reportIsPublished(reportId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sections, "name", "notes", "hours", "kind");
	}

}
