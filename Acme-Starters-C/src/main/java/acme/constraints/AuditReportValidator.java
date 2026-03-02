
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditReportRepository;
import acme.entities.audit.AuditSection;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repositorio;


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport audit, final ConstraintValidatorContext context) {

		assert context != null;
		boolean result;
		if (audit == null)
			result = true;
		else {

			{
				boolean auditUnico;
				AuditReport auditExistente;

				auditExistente = this.repositorio.findAuditReportByTicker(audit.getTicker());
				auditUnico = auditExistente == null || auditExistente.equals(audit);

				super.state(context, auditUnico, "ticker", "acme.validation.audit.duplicated-ticker.message");
			}
			{
				boolean auditSectionsCorrectos;
				java.util.List<AuditSection> auditSections;

				auditSections = this.repositorio.findAuditSectionsByAuditReportId(audit.getId());
				auditSectionsCorrectos = auditSections != null && !auditSections.isEmpty();

				super.state(context, auditSectionsCorrectos, "*", "acme.validation.audit.correct-audit-sections.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
