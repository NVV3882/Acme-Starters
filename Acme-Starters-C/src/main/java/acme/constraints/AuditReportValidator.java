
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditReportRepository;

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

				super.state(context, auditUnico, "ticker", "acme.validation.audit-report.duplicated-ticker.message");
			}
			{
				boolean auditSectionsCorrectos;

				auditSectionsCorrectos = !this.repositorio.findAuditSectionsByAuditReportId(audit.getId()).isEmpty() || audit.getDraftMode();
				super.state(context, auditSectionsCorrectos, "*", "acme.validation.audit-report.correct-audit-sections.message");
			}
			{

				if (audit.getDraftMode().equals(false)) {
					boolean intervaloCorrectoTiempo;
					Date fechaInicio = audit.getStartMoment();
					Date fechaFinal = audit.getEndMoment();

					intervaloCorrectoTiempo = fechaInicio != null && fechaFinal != null && MomentHelper.isAfter(fechaFinal, fechaInicio);

					super.state(context, intervaloCorrectoTiempo, "*", "acme.validation.audit-report.incorrect-dates-intervale.message");
				}
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
