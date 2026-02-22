
package acme.entities.audit;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	//@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	//@ValidHeader
	@Column
	private String					name;

	@Mandatory
	//@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	//@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private AuditReportRepository	repository;

	//	@Transient
	//	public Double monthsActive() {
	//		Date fecha = this.startMoment;
	//
	//	}


	@Transient
	public Integer getHours() {
		Integer result;

		result = this.repository.computeHours(this.getId());

		return result == null ? 0 : result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne
	private acme.realms.Auditor	auditor;

	@Valid
	@OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
	private List<AuditSection>	sections;

}
