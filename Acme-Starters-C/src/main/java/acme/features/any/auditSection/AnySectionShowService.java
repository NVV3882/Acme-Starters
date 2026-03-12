
package acme.features.any.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;

@Service
public class AnySectionShowService extends AbstractService<Any, AuditSection> {

	@Autowired
	AnySectionRepository	repositorio;

	AuditSection			section;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.section = this.repositorio.showSectionById(id);
	}
	@Override
	public void authorise() {
		super.setAuthorised(true);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.section, "name", "notes", "hours", "kind");
	}

}
