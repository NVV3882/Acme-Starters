
package acme.entities.strategy;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private StrategyRepository	repository;


	@Transient
	public Double getMonthsActive() {
		Date fechaini = this.startMoment;
		Date fechafin = this.endMoment;
		if (fechaini == null || fechafin == null)
			return 0.0;
		Double res = MomentHelper.computeDifference(fechaini, fechafin, ChronoUnit.MONTHS);
		MathHelper.roundOff(res, 1);
		return res;
	}

	@ValidScore
	@Transient
	public Double getExpectedPercentaje() {
		Double result = this.repository.getExpectedPercentaje(this.getId());
		Double res = result == null ? 0 : result.doubleValue();
		return res;
	}


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser fundraiser;

}
