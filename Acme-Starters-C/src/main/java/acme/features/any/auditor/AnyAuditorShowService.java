
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AnyAuditorShowService extends AbstractService<Any, Auditor> {

	@Autowired
	AnyAuditorRepository	repositorio;

	Auditor					auditor;


	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		this.auditor = this.repositorio.findAuditorByReportId(reportId);
	}
	@Override
	public void authorise() {
		int reportId = super.getRequest().getData("reportId", int.class);
		Boolean res;
		if (this.repositorio.reportIsPublished(reportId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
	}
}
