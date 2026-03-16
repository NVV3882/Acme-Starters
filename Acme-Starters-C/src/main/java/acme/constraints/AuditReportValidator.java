
package acme.constraints;

import java.time.temporal.ChronoUnit;
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

				super.state(context, auditUnico, "ticker", "acme.validation.audit.duplicated-ticker.message");
			}
			{
				boolean auditSectionsCorrectos;

				auditSectionsCorrectos = !this.repositorio.findAuditSectionsByAuditReportId(audit.getId()).isEmpty() || audit.getDraftMode();
				super.state(context, auditSectionsCorrectos, "*", "acme.validation.audit.correct-audit-sections.message");
			}
			{
				boolean intervaloCorrectoTiempo;
				boolean fechasValidas;
				Date fechaInicio = audit.getStartMoment();
				Date fechaFinal = audit.getEndMoment();
				if (audit.getDraftMode().equals(false))
					intervaloCorrectoTiempo = MomentHelper.computeDifference(fechaInicio, fechaFinal, ChronoUnit.DAYS) >= 1;
				else
					intervaloCorrectoTiempo = true;
				if (fechaInicio != null && fechaFinal != null)
					fechasValidas = MomentHelper.isAfter(fechaFinal, fechaInicio);
				else
					fechasValidas = false;

				super.state(context, intervaloCorrectoTiempo && fechasValidas, "*", "acme.validation.invention.incorrect-dates-intervale.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
